package com.visualpathit.account.service;

/**
 * Service interface for producing messages.
 */
public interface ProducerService {

    /**
     * Produces a message.
     *
     * @param message the message to send
     * @return confirmation message
     */
    String produceMessage(String message);
}
