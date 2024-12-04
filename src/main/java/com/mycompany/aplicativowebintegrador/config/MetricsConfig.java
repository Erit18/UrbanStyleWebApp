package com.mycompany.aplicativowebintegrador.config;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;

public class MetricsConfig {
    private static final PrometheusMeterRegistry registry = 
        new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

    public static MeterRegistry getRegistry() {
        return registry;
    }

    public static String scrape() {
        return registry.scrape();
    }
} 