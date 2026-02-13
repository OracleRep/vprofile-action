package com.visualpathit.account.utils;

import com.visualpathit.account.beans.Components;
import com.visualpathit.account.model.User;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import net.spy.memcached.MemcachedClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Memcached helper for storing and retrieving {@link User} objects.
 * Uses active host first and falls back to standby host.
 */
@Service
public class MemcachedUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(MemcachedUtils.class);

    private static final int DEFAULT_EXPIRE_SECONDS = 900;
    private static final String STAT_KEY_PID = "pid";

    private final Components components;

    public MemcachedUtils(final Components components) {
        this.components = components;
    }

    public String memcachedSetData(final User user, final String key) {
        MemcachedClient client = null;
        try {
            client = createConnection();
            if (client == null) {
                return "Cache connection unavailable";
            }

            final Future<Boolean> future = client.set(key, DEFAULT_EXPIRE_SECONDS, user);
            final Boolean ok = future.get();

            LOGGER.debug("Memcached set status for key {}: {}", key, ok);
            return "Data is From DB and Data Inserted In Cache !!";

        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            LOGGER.error("Interrupted while setting memcached key {}", key, ex);
            return "Cache operation interrupted";
        } catch (ExecutionException ex) {
            LOGGER.error("Execution error while setting memcached key {}", key, ex);
            return "Cache operation failed";
        } finally {
            shutdownQuietly(client);
        }
    }

    public User memcachedGetData(final String key) {
        MemcachedClient client = null;
        try {
            client = createConnection();
            if (client == null) {
                return null;
            }

            final Object value = client.get(key);
            if (value instanceof User) {
                LOGGER.debug("User value found in cache for key {}", key);
                return (User) value;
            }

            LOGGER.debug("No user value in cache for key {}", key);
            return null;

        } finally {
            shutdownQuietly(client);
        }
    }

    private MemcachedClient createConnection() {
        final String activeHost = components.getActiveHost();
        final String activePort = components.getActivePort();

        if (isBlank(activeHost) || isBlank(activePort)) {
            LOGGER.warn("Active memcached host/port is empty; trying standby");
            return createStandbyConnection();
        }

        final MemcachedClient activeClient = tryConnect(activeHost, activePort);
        if (activeClient != null) {
            return activeClient;
        }

        return createStandbyConnection();
    }

    private MemcachedClient createStandbyConnection() {
        final String standbyHost = components.getStandByHost();
        final String standbyPort = components.getStandByPort();

        if (isBlank(standbyHost) || isBlank(standbyPort)) {
            LOGGER.warn("Standby memcached host/port is empty");
            return null;
        }

        return tryConnect(standbyHost, standbyPort);
    }

    private MemcachedClient tryConnect(final String host, final String port) {
        MemcachedClient client = null;
        try {
            client = new MemcachedClient(new InetSocketAddress(host, Integer.parseInt(port)));

            if (hasPidStat(client)) {
                LOGGER.info("Connected to memcached host={}", host);
                return client;
            }

            LOGGER.warn("Memcached connection check failed (pid stat missing) for host={}", host);
            shutdownQuietly(client);
            return null;

        } catch (IOException | RuntimeException ex) {
            LOGGER.error("Failed to connect to memcached host={} port={}", host, port, ex);
            shutdownQuietly(client);
            return null;
        }
    }

    private boolean hasPidStat(final MemcachedClient client) {
        final Map<SocketAddress, Map<String, String>> stats = client.getStats();
        for (final Map.Entry<SocketAddress, Map<String, String>> entry : stats.entrySet()) {
            final String pid = entry.getValue().get(STAT_KEY_PID);
            if (pid != null && !pid.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void shutdownQuietly(final MemcachedClient client) {
        if (client != null) {
            client.shutdown();
        }
    }

    private boolean isBlank(final String value) {
        return value == null || value.trim().isEmpty();
    }
} 
