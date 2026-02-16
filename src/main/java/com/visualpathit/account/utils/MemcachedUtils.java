package com.visualpathit.account.utils;

import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visualpathit.account.beans.Components;
import com.visualpathit.account.model.User;

import net.spy.memcached.MemcachedClient;

/**
 * Utility service for Memcached operations.
 */
@Service
public final class MemcachedUtils {

    /**
     * Default cache expiry in seconds.
     */
    private static final int DEFAULT_EXPIRY_SECONDS = 900;

    /**
     * Stats key used to retrieve the port identifier.
     */
    private static final String STATS_PORT_KEY = "pid";

    /**
     * Configuration components (injected once by Spring).
     */
    private static Components COMPONENTS;

    /**
     * Injects application components used by this utility.
     *
     * @param components configuration components
     */
    @Autowired
    public void setComponents(final Components components) {
        MemcachedUtils.COMPONENTS = components;
    }

    /**
     * Stores user data in cache for the provided key.
     *
     * @param user the user to cache
     * @param key the cache key
     * @return status message
     */
    public static String memcachedSetData(final User user, final String key) {
        String result = "";

        try {
            MemcachedClient activeClient = memcachedConnection();

            System.out.println("--------------------------------------------");
            System.out.println("Client is :: " + activeClient.getStats());
            System.out.println("--------------------------------------------");

            Future<?> future = activeClient.set(key, DEFAULT_EXPIRY_SECONDS, user);

            System.out.println("set status: " + future.get());
            result = "Data is From DB and Data Inserted In Cache !!";

            activeClient.shutdown();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return result;
    }

    /**
     * Retrieves user data from cache by key.
     *
     * @param key the cache key
     * @return cached user or {@code null} if not found
     */
    public static User memcachedGetData(final String key) {
        String result = "";
        User userData = null;

        try {
            MemcachedClient client = memcachedConnection();

            System.out.println("--------------------------------------------");
            System.out.println("Client Status :: " + client.getStats());
            System.out.println("--------------------------------------------");

            userData = (User) client.get(key);

            System.out.println("user value in cache - " + client.get(key));
            result = "Data Retrieval From Cache !!";
            System.out.println(result);

            client.shutdown();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return userData;
    }

    /**
     * Creates a Memcached connection using active host settings.
     * Falls back to standby connection if active host is not available.
     *
     * @return memcached client or {@code null} if connection fails
     */
    public static MemcachedClient memcachedConnection() {
        MemcachedClient connection = null;

        boolean isActive = true;
        String port = "";

        String activeHost = COMPONENTS.getActiveHost();
        String activePort = COMPONENTS.getActivePort();

        try {
            if (!activeHost.isEmpty()
                    && !activePort.isEmpty()
                    && isActive) {

                connection = new MemcachedClient(
                        new InetSocketAddress(
                                activeHost,
                                Integer.parseInt(activePort)
                        )
                );

                for (SocketAddress address : connection.getStats().keySet()) {
                    System.out.println("Connection SocketAddress :: " + address);
                    port = connection.getStats().get(address).get(STATS_PORT_KEY);
                }

                if (port == null) {
                    System.out.println("Port :: " + port);
                    connection.shutdown();

                    System.out.println("--------------------------------------------");
                    System.out.println(
                            "Connection Failure By Active Host :: " + activeHost
                    );
                    System.out.println("--------------------------------------------");

                    connection = null;
                    isActive = false;

                    return standByMemcachedConn();
                }

                if (!port.isEmpty()) {
                    System.out.println("--------------------------------------------");
                    System.out.println(
                            "Connection to server successful for Active Host :: "
                                    + activeHost
                    );
                    System.out.println("--------------------------------------------");

                    isActive = true;
                    return connection;
                }
            } else if (!activeHost.isEmpty()
                    && !activePort.isEmpty()
                    && !isActive) {

                return standByMemcachedConn();
            } else {
                System.out.println("--------------------------------------------");
                System.out.println(
                        "Connection Failure Due to Incorrect or Empty Host"
                );
                System.out.println("--------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }

    /**
     * Creates a Memcached connection using standby host settings.
     *
     * @return memcached client or {@code null} if connection fails
     */
    public static MemcachedClient standByMemcachedConn() {
        MemcachedClient connection = null;

        String port = "";
        String standByHost = COMPONENTS.getStandByHost();
        String standByPort = COMPONENTS.getStandByPort();

        try {
            if (!standByHost.isEmpty()
                    && !standByPort.isEmpty()
                    && connection == null
                    && port.isEmpty()) {

                connection = new MemcachedClient(
                        new InetSocketAddress(
                                standByHost,
                                Integer.parseInt(standByPort)
                        )
                );

                for (SocketAddress address : connection.getStats().keySet()) {
                    port = connection.getStats().get(address).get(STATS_PORT_KEY);
                }

                if (!port.isEmpty()) {
                    System.out.println("--------------------------------------------");
                    System.out.println(
                            "Connection to server successful by StandBy Host :: "
                                    + standByHost
                    );
                    System.out.println("--------------------------------------------");
                    return connection;
                }

                connection.shutdown();

                System.out.println("--------------------------------------------");
                System.out.println(
                        "Connection Failure By StandBy Host :: " + standByHost
                );
                System.out.println("--------------------------------------------");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return connection;
    }
}
