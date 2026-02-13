package com.visualpathit.account.service;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

/**
 * RabbitMQ consumer service implementation.
 */
@Service
public final class ConsumerServiceImpl implements ConsumerService {

    /**
     * The name of the exchange.
     */
    private static final String EXCHANGE_NAME = "messages";

    /**
     * Consumes messages from RabbitMQ.
     *
     * @param data raw message bytes
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
        final String consumedMessage = new String(data);
        // Keep System.out if not forbidden; otherwise use a logger.
        System.out.println(" [x] Consumed '" + consumedMessage + "'");
    }
}
