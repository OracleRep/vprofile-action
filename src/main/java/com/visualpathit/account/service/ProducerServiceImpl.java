package com.visualpathit.account.service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Service;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.visualpathit.account.utils.RabbitMqUtil;

@Service
public class ProducerServiceImpl implements ProducerService {

    /**
     * The name of the exchange.
     */
    private static final String EXCHANGE_NAME = "messages";

    /**
     * Publishes a message.
     *
     * @param message the message to publish
     * @return a response string
     */
    @Override
    public String produceMessage(final String message) {
        try {
            ConnectionFactory factory = new ConnectionFactory();

            /*
             * Debug values (kept as comment):
             * System.out.println("Rabbitmq host: "
             *         + RabbitMqUtil.getRabbitMqHost());
             * System.out.println("Rabbitmq port: "
             *         + RabbitMqUtil.getRabbitMqPort());
             * System.out.println("Rabbitmq user: "
             *         + RabbitMqUtil.getRabbitMqUser());
             * System.out.println("Rabbitmq password: "
             *         + RabbitMqUtil.getRabbitMqPassword());
             */

            factory.setHost(RabbitMqUtil.getRabbitMqHost());
            factory.setPort(Integer.parseInt(RabbitMqUtil.getRabbitMqPort()));
            factory.setUsername(RabbitMqUtil.getRabbitMqUser());
            factory.setPassword(RabbitMqUtil.getRabbitMqPassword());

            Connection connection = factory.newConnection();
            System.out.println("Connection open status " + connection.isOpen());

            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            channel.basicPublish(
                    EXCHANGE_NAME,
                    "",
                    null,
                    message.getBytes()
            );

            System.out.println("[x] Sent '" + message + "'");

            channel.close();
            connection.close();
        } catch (IOException io) {
            System.out.println("IOException");
            io.printStackTrace();
        } catch (TimeoutException toe) {
            System.out.println("TimeoutException: " + toe.getMessage());
            toe.printStackTrace();
        }

        return "response";
    }
}
