package com.visualpathit.account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerServiceImpl.class);

    /**
     * The name of the exchange.
     */
    private static final String EXCHANGE_NAME = "messages";

    /**
     * Consumes messages from RabbitMQ.
     *
     * @param data message payload as bytes
     */
    @Override
    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue,
                    exchange = @Exchange(value = EXCHANGE_NAME, type = ExchangeTypes.FANOUT)
            )
    )
    public void consumerMessage(final byte[] data) {
        final String consumedMessage = new String(data);
        LOGGER.info(" [x] Consumed '{}'", consumedMessage);
    }
}
