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

/**
 * Produces messages to RabbitMQ.
 */
@Service
public final class ProducerServiceImpl implements ProducerService {

    /**
     * Logger instance.
     */
    private static final Logger LOGGER =
            LoggerFactory.getLogger(ProducerServiceImpl.class);

    /**
     * Exchange name.
     */
    private static final String EXCHANGE_NAME = "messages";

    /**
     * Exchange type.
     */
    private static final String EXCHANGE_TYPE = "fanout";

    /**
     * Routing key for fanout exchange.
     */
    private static final String ROUTING_KEY = "";

    /**
     * Response message.
     */
    private static final String RESPONSE_OK = "response";

    /**
     * RabbitMQ configuration accessor.
     */
    private final RabbitMqUtil rabbitMqUtil;

    /**
     * Creates a producer service.
     *
     * @param rabbitMqUtilParam RabbitMQ utility
     */
    public ProducerServiceImpl(final RabbitMqUtil rabbitMqUtilParam) {
        this.rabbitMqUtil = rabbitMqUtilParam;
    }

    /**
     * Publishes a message to RabbitMQ.
     *
     * @param message message payload
     * @return response string
     */
    @Override
    public String produceMessage(final String message) {
        final ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitMqUtil.getRabbitMqHost());
        factory.setPort(Integer.parseInt(rabbitMqUtil.getRabbitMqPort()));
        factory.setUsername(rabbitMqUtil.getRabbitMqUser());
        factory.setPassword(rabbitMqUtil.getRabbitMqPassword());

        Channel channel = null;

        try (Connection connection = factory.newConnection()) {
            channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, EXCHANGE_TYPE);
            channel.basicPublish(
                    EXCHANGE_NAME,
                    ROUTING_KEY,
                    null,
                    message.getBytes(StandardCharsets.UTF_8)
            );

            LOGGER.info(" [x] Sent '{}'", message);
            return RESPONSE_OK;

        } catch (IOException | TimeoutException ex) {
            LOGGER.error("Failed to publish message to RabbitMQ", ex);
            return RESPONSE_OK;

        } finally {
            closeQuietly(channel);
        }
    }

    /**
     * Closes the channel without failing the caller.
     *
     * @param channel channel instance
     */
    private void closeQuietly(final Channel channel) {
        if (channel != null) {
            try {
                channel.close();
            } catch (IOException | TimeoutException ex) {
                LOGGER.debug("Failed to close RabbitMQ channel", ex);
            }
        }
    }
}
