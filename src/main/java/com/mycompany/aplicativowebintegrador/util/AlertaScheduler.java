package com.mycompany.aplicativowebintegrador.util;

import com.mycompany.aplicativowebintegrador.dao.AlertaDAO;
import com.mycompany.aplicativowebintegrador.modelo.Alerta;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@WebListener
public class AlertaScheduler implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(AlertaScheduler.class);
    private ScheduledExecutorService scheduler;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.info("Iniciando AlertaScheduler...");
        scheduler = Executors.newSingleThreadScheduledExecutor();
        
        // Programar la tarea para que se ejecute cada hora
        scheduler.scheduleAtFixedRate(() -> {
            logger.debug("Ejecutando verificación de alertas automáticas...");
            try (Connection conn = DatabaseConnection.getConnection();
                 CallableStatement stmt = conn.prepareCall("{CALL GenerarAlertasAutomaticas()}")) {
                
                stmt.execute();
                logger.info("Verificación de alertas completada exitosamente");
                
            } catch (SQLException e) {
                logger.error("Error al ejecutar la verificación de alertas: {}", e.getMessage(), e);
            }
        }, 0, 1, TimeUnit.HOURS);
        
        logger.info("AlertaScheduler iniciado correctamente");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        logger.info("Deteniendo AlertaScheduler...");
        if (scheduler != null) {
            try {
                // Intentar un apagado ordenado
                scheduler.shutdown();
                if (!scheduler.awaitTermination(60, TimeUnit.SECONDS)) {
                    // Forzar el apagado si no termina en 60 segundos
                    scheduler.shutdownNow();
                }
                logger.info("AlertaScheduler detenido correctamente");
            } catch (InterruptedException e) {
                logger.error("Error al detener AlertaScheduler: {}", e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    // Método auxiliar para verificar el estado del scheduler
    public boolean isRunning() {
        return scheduler != null && !scheduler.isShutdown();
    }
} 