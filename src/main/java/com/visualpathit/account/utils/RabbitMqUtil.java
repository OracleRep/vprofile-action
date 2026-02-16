package com.visualpathit.account.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.visualpathit.account.beans.Components;

/**
 * Utility service for RabbitMQ connection configuration.
 */
@Service
public final class RabbitMqUtil {

    /**
     * Configuration components (injected once by Spring).
     */
    private static Components components;

    /**
     * Injects application components used by this utility.
     *
     * @param injectedComponents configuration components
     */
    @Autowired
    public void setComponents(final Components injectedComponents) {
        RabbitMqUtil.components = injectedComponents;
    }

    /**
     * Returns RabbitMQ host.
     *
     * @return RabbitMQ host
     */
    public static String getRabbitMqHost() {
        return components.getRabbitMqHost();
    }

    /**
     * Returns RabbitMQ port.
     *
     * @return RabbitMQ port
     */
    public static String getRabbitMqPort() {
        return components.getRabbitMqPort();
    }

    /**
     * Returns RabbitMQ username.
     *
     * @return RabbitMQ username
     */
    public static String getRabbitMqUser() {
        return components.getRabbitMqUser();
    }

    /**
     * Returns RabbitMQ password.
     *
     * @return RabbitMQ password
     */
    public static String getRabbitMqPassword() {
        return components.getRabbitMqPassword();
    }
}
