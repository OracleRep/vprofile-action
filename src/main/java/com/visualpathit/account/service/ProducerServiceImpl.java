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

    private static final String EXCHANGE_NAME = "messages";
    private static final String EXCHANGE_TYPE = "fanout";
    private static final String ROUTING_KEY = "";
    private static final String RESPONSE_OK = "response";

    private final RabbitMqUtil rabbitMqUtil;

    public ProducerServiceImpl(final RabbitMqUtil rabbitMqUtil) {
        this.rabbitMqUtil = rabbitMqUtil;
    }

    @Override
    public String produceMessage(final String message) {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitMqUtil.getRabbitMqHost());
        factory.setPort(Integer.parseInt(rabbitMqUtil.getRabbitMqPort()));
        factory.setUsername(rabbitMqUtil.getRabbitMqUser());
        factory.setPassword(rabbitMqUtil.getRabbitMqPassword());

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            channel.basicPublish(EXCHANGE_NAME, ROUTING_KEY, null, message.getBytes(StandardCharsets.UTF_8));

            LOGGER.info(" [x] Sent '{}'", message);
            return RESPONSE_OK;

        } catch (IOException | TimeoutException ex) {
            LOGGER.error("Failed to publish message to RabbitMQ", ex);
            return RESPONSE_OK;
        }
    }
}