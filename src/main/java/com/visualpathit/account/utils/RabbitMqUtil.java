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
    private static Components COMPONENTS;

    /**
     * Injects application components used by this utility.
     *
     * @param components configuration components
     */
    @Autowired
    public void setComponents(final Components components) {
        RabbitMqUtil.COMPONENTS = components;
    }

    /**
     * Returns RabbitMQ host.
     *
     * @return RabbitMQ host
     */
    public static String getRabbitMqHost() {
        return COMPONENTS.getRabbitMqHost();
    }

    /**
     * Returns RabbitMQ port.
     *
     * @return RabbitMQ port
     */
    public static String getRabbitMqPort() {
        return COMPONENTS.getRabbitMqPort();
    }

    /**
     * Returns RabbitMQ username.
     *
     * @return RabbitMQ username
     */
    public static String getRabbitMqUser() {
        return COMPONENTS.getRabbitMqUser();
    }

    /**
     * Returns RabbitMQ password.
     *
     * @return RabbitMQ password
     */
    public static String getRabbitMqPassword() {
        return COMPONENTS.getRabbitMqPassword();
    }
}
