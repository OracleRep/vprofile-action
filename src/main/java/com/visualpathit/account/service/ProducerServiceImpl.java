package com.visualpathit.account.service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.visualpathit.account.utils.RabbitMqUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ProducerServiceImpl implements ProducerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerServiceImpl.class);

    /**
     * The name of the exchange.
     */
    private static final String EXCHANGE_NAME = "messages";

    private static final String EXCHANGE_TYPE = "fanout";
    private static final String ROUTING_KEY = "";
    private static final String RESPONSE_OK = "response";

    /**
     * Publishes a message to RabbitMQ.
     *
     * @param message the message to publish
     * @return response string
     */
    @Override
    public String produceMessage(final String message) {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(RabbitMqUtil.getRabbitMqHost());
        factory.setPort(Integer.parseInt(RabbitMqUtil.getRabbitMqPort()));
        factory.setUsername(RabbitMqUtil.getRabbitMqUser());
        factory.setPassword(RabbitMqUtil.getRabbitMqPassword());

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            LOGGER.debug("RabbitMQ connection open: {}", connection.isOpen());

            channel.
