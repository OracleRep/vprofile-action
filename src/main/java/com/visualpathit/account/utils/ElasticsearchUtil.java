package com.visualpathit.account.utils;

import java.net.InetSocketAddress;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visualpathit.account.beans.Components;

/**
 * Utility service for creating an Elasticsearch transport client.
 */
@Service
public final class ElasticsearchUtil {

    /**
     * Configuration components (injected once by Spring).
     */
    private static Components components;

    /**
     * Injects application components used by this utility.
     *
     * @param components configuration components
     */
    @Autowired
    public void setComponents(final Components components) {
        ElasticsearchUtil.components = components;
    }

    /**
     * Creates and returns a transport client.
     *
     * @return Elasticsearch transport client
     */
    public static TransportClient trannsportClient() {
        System.out.println("elasticsearch client");

        String elasticsearchHost = components.getElasticsearchHost();
        String elasticsearchPort = components.getElasticsearchPort();
        String elasticsearchCluster = components.getElasticsearchCluster();
        String elasticsearchNode = components.getElasticsearchNode();

        System.out.println(
                "elasticsearchHost ........ " + elasticsearchHost
        );
        System.out.println(
                "elasticsearchPort ........ " + elasticsearchPort
        );

        TransportClient client = null;

        try {
            Settings settings = Settings.builder()
                    .put("cluster.name", elasticsearchCluster)
                    .put("node.name", elasticsearchNode)
                    .build();

            client = new PreBuiltTransportClient(settings)
                    .addTransportAddress(
                            new InetSocketTransportAddress(
                                    new InetSocketAddress(
                                            elasticsearchHost,
                                            Integer.parseInt(elasticsearchPort)
                                    )
                            )
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return client;
    }
}
