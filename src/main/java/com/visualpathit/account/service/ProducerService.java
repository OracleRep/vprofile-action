package com.visualpathit.account.service;

/**
 * Service interface for producing messages.
 */
public interface ProducerService {

    /**
     * Produces a message to the messaging broker.
     *
     * @param message the message to send
     * @return the produced message
     */
    String produceMessage(String message);
}
