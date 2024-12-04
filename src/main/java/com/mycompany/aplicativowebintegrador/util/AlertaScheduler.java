package com.mycompany.aplicativowebintegrador.util;

import com.mycompany.aplicativowebintegrador.service.MantenimientoService;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@WebListener
public class AlertaScheduler implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(AlertaScheduler.class);
    private ScheduledExecutorService scheduler;
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        scheduler = Executors.newScheduledThreadPool(2);
        
        // Programar tareas de mantenimiento
        MantenimientoService mantenimientoService = new MantenimientoService();
        
        // Ejecutar backup diario a las 2 AM
        scheduler.scheduleAtFixedRate(() -> {
            try {
                mantenimientoService.realizarBackup();
            } catch (Exception e) {
                logger.error("Error en tarea programada de backup", e);
            }
        }, calcularDelayHasta(2), 24, TimeUnit.HOURS);
        
        logger.info("Tareas de mantenimiento programadas correctamente");
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }
    
    private long calcularDelayHasta(int hora) {
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime proximaEjecucion = ahora.withHour(hora).withMinute(0).withSecond(0);
        if (ahora.compareTo(proximaEjecucion) > 0) {
            proximaEjecucion = proximaEjecucion.plusDays(1);
        }
        return ChronoUnit.MILLIS.between(ahora, proximaEjecucion);
    }
} 