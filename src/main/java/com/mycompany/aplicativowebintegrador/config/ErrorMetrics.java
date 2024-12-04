package com.mycompany.aplicativowebintegrador.config;

import io.micrometer.core.instrument.Counter;

public class ErrorMetrics {
    private static final Counter errorCounter = Counter
        .builder("application_errors_total")
        .description("Total de errores en la aplicación")
        .tags("type", "unknown")
        .register(MetricsConfig.getRegistry());

    public static void recordError(String type) {
        Counter.builder("application_errors_total")
            .tags("type", type)
            .register(MetricsConfig.getRegistry())
            .increment();
    }
} 