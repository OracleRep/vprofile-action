package com.visualpathit.account.utils;

import com.visualpathit.account.beans.Components;

import org.springframework.stereotype.Service;

/**
 * Utility service that provides RabbitMQ configuration values.
 */
@Service
public class RabbitMqUtil {

    private final Components components;

    public RabbitMqUtil(final Components components) {
        this.components = components;
    }

    public String getRabbitMqHost() {
        return components.getRabbitMqHost();
    }

    public String getRabbitMqPort() {
        return components.getRabbitMqPort();
    }

    public String getRabbitMqUser() {
        return components.getRabbitMqUser();
    }

    public String getRabbitMqPassword() {
        return components.getRabbitMqPassword();
    }
} 

