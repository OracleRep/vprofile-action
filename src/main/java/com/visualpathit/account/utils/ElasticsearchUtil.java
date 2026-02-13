package com.visualpathit.account.utils;

import com.visualpathit.account.beans.Components;

import java.net.InetSocketAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Utility service for creating an Elasticsearch TransportClient.
 */
@Service
public class ElasticsearchUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticsearchUtil.class);

    private final Components components;

    public ElasticsearchUtil(final Components components) {
        this.components = components;
    }

    /**
     * Creates and returns an Elasticsearch TransportClient.
     *
     * @return transport client instance
     */
    public TransportClient transportClient() {
        final String host = components.getElasticsearchHost();
        final String port = components.getElasticsearchPort();
        final String cluster = components.getElasticsearchCluster();
        final String node = components.getElasticsearchNode();

        LOGGER.debug("Creating Elasticsearch client for host={}, port={}, cluster={}, node={}",
                host, port, cluster, node);

        final Settings settings = Settings.builder()
                .put("cluster.name", cluster)
                .put("node.name", node)
                .build();

        try {
            return new PreBuiltTransportClient(settings)
                    .addTransportAddress(
                            new InetSocketTransportAddress(
                                    new InetSocketAddress(host, Integer.parseInt(port))
                            )
                    );
        } catch (RuntimeException ex) {
            LOGGER.error("Failed to create Elasticsearch client", ex);
            throw ex;
        }
    }
} 

