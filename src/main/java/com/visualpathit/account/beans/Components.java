package com.visualpathit.account.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Application component configuration values loaded from properties.
 */
@Component
public final class Components {

    /**
     * Active memcached host.
     */
    @Value("${memcached.active.host}")
    private String activeHost;

    /**
     * Active memcached port.
     */
    @Value("${memcached.active.port}")
    private String activePort;

    /**
     * Standby memcached host.
     */
    @Value("${memcached.standBy.host}")
    private String standByHost;

    /**
     * Standby memcached port.
     */
    @Value("${memcached.standBy.port}")
    private String standByPort;

    /**
     * RabbitMQ host/address.
     */
    @Value("${rabbitmq.address}")
    private String rabbitMqHost;

    /**
     * RabbitMQ port.
     */
    @Value("${rabbitmq.port}")
    private String rabbitMqPort;

    /**
     * RabbitMQ username.
     */
    @Value("${rabbitmq.username}")
    private String rabbitMqUser;

    /**
     * RabbitMQ password.
     */
    @Value("${rabbitmq.password}")
    private String rabbitMqPassword;

    /**
     * Elasticsearch host.
     */
    @Value("${elasticsearch.host}")
    private String elasticsearchHost;

    /**
     * Elasticsearch port.
     */
    @Value("${elasticsearch.port}")
    private String elasticsearchPort;

    /**
     * Elasticsearch cluster name.
     */
    @Value("${elasticsearch.cluster}")
    private String elasticsearchCluster;

    /**
     * Elasticsearch node name.
     */
    @Value("${elasticsearch.node}")
    private String elasticsearchNode;

    /**
     * Returns active memcached host.
     *
     * @return the active host
     */
    public String getActiveHost() {
        return activeHost;
    }

    /**
     * Returns active memcached port.
     *
     * @return the active port
     */
    public String getActivePort() {
        return activePort;
    }

    /**
     * Returns standby memcached host.
     *
     * @return the standby host
     */
    public String getStandByHost() {
        return standByHost;
    }

    /**
     * Returns standby memcached port.
     *
     * @return the standby port
     */
    public String getStandByPort() {
        return standByPort;
    }

    /**
     * Sets active memcached host.
     *
     * @param newActiveHost the active host
     */
    public void setActiveHost(final String newActiveHost) {
        this.activeHost = newActiveHost;
    }

    /**
     * Sets active memcached port.
     *
     * @param newActivePort the active port
     */
    public void setActivePort(final String newActivePort) {
        this.activePort = newActivePort;
    }

    /**
     * Sets standby memcached host.
     *
     * @param newStandByHost the standby host
     */
    public void setStandByHost(final String newStandByHost) {
        this.standByHost = newStandByHost;
    }

    /**
     * Sets standby memcached port.
     *
     * @param newStandByPort the standby port
     */
    public void setStandByPort(final String newStandByPort) {
        this.standByPort = newStandByPort;
    }

    /**
     * Returns RabbitMQ host.
     *
     * @return the RabbitMQ host
     */
    public String getRabbitMqHost() {
        return rabbitMqHost;
    }

    /**
     * Sets RabbitMQ host.
     *
     * @param newRabbitMqHost the RabbitMQ host
     */
    public void setRabbitMqHost(final String newRabbitMqHost) {
        this.rabbitMqHost = newRabbitMqHost;
    }

    /**
     * Returns RabbitMQ port.
     *
     * @return the RabbitMQ port
     */
    public String getRabbitMqPort() {
        return rabbitMqPort;
    }

    /**
     * Sets RabbitMQ port.
     *
     * @param newRabbitMqPort the RabbitMQ port
     */
    public void setRabbitMqPort(final String newRabbitMqPort) {
        this.rabbitMqPort = newRabbitMqPort;
    }

    /**
     * Returns RabbitMQ username.
     *
     * @return the RabbitMQ username
     */
    public String getRabbitMqUser() {
        return rabbitMqUser;
    }

    /**
     * Sets RabbitMQ username.
     *
     * @param newRabbitMqUser the RabbitMQ username
     */
    public void setRabbitMqUser(final String newRabbitMqUser) {
        this.rabbitMqUser = newRabbitMqUser;
    }

    /**
     * Returns RabbitMQ password.
     *
     * @return the RabbitMQ password
     */
    public String getRabbitMqPassword() {
        return rabbitMqPassword;
    }

    /**
     * Sets RabbitMQ password.
     *
     * @param newRabbitMqPassword the RabbitMQ password
     */
    public void setRabbitMqPassword(final String newRabbitMqPassword) {
        this.rabbitMqPassword = newRabbitMqPassword;
    }

    /**
     * Returns Elasticsearch host.
     *
     * @return the Elasticsearch host
     */
    public String getElasticsearchHost() {
        return elasticsearchHost;
    }

    /**
     * Sets Elasticsearch host.
     *
     * @param newElasticsearchHost the Elasticsearch host
     */
    public void setElasticsearchHost(final String newElasticsearchHost) {
        this.elasticsearchHost = newElasticsearchHost;
    }

    /**
     * Returns Elasticsearch port.
     *
     * @return the Elasticsearch port
     */
    public String getElasticsearchPort() {
        return elasticsearchPort;
    }

    /**
     * Sets Elasticsearch port.
     *
     * @param newElasticsearchPort the Elasticsearch port
     */
    public void setElasticsearchPort(final String newElasticsearchPort) {
        this.elasticsearchPort = newElasticsearchPort;
    }

    /**
     * Returns Elasticsearch cluster.
     *
     * @return the Elasticsearch cluster
     */
    public String getElasticsearchCluster() {
        return elasticsearchCluster;
    }

    /**
     * Sets Elasticsearch cluster.
     *
     * @param newElasticsearchCluster the Elasticsearch cluster
     */
    public void setElasticsearchCluster(final String newElasticsearchCluster) {
        this.elasticsearchCluster = newElasticsearchCluster;
    }

    /**
     * Returns Elasticsearch node.
     *
     * @return the Elasticsearch node
     */
    public String getElasticsearchNode() {
        return elasticsearchNode;
    }

    /**
     * Sets Elasticsearch node.
     *
     * @param newElasticsearchNode the Elasticsearch node
     */
    public void setElasticsearchNode(final String newElasticsearchNode) {
        this.elasticsearchNode = newElasticsearchNode;
    }
}
