package com.visualpathit.account.service;

import java.nio.charset.StandardCharsets;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    /**
     * The name of the exchange.
     */
    private static final String EXCHANGE_NAME = "messages";

    /**
     * Consumes messages from the broker (RabbitMQ).
     *
     * @param data the message payload
     */
    @Override
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(),
                    exchange = @Exchange(
                            value = EXCHANGE_NAME,
                            type = ExchangeTypes.FANOUT
                    )
            )
    )
    public void consumerMessage(final byte[] data) {
        String consumedMessage = new String(data, StandardCharsets.UTF_8);
        System.out.println("[x] Consumed '" + consumedMessage + "'");
    }
}
