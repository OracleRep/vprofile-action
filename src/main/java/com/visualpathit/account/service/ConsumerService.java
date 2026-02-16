package com.visualpathit.account.service;

/**
 * Service interface for consuming messages.
 */
public interface ConsumerService {

    /**
     * Consumes a message payload.
     *
     * @param data the message payload as a byte array
     */
    void consumerMessage(byte[] data);
}
