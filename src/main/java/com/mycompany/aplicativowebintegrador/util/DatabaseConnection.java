package com.mycompany.aplicativowebintegrador.util;

import com.mycompany.aplicativowebintegrador.config.DatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.sql.DataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnection {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
    private static HikariDataSource dataSource;

    static {
        try {
            Class.forName(DatabaseConfig.getDriver());
            initializeDataSource();
        } catch (ClassNotFoundException e) {
            logger.error("Error al cargar el driver de MySQL", e);
            throw new RuntimeException("Error al cargar el driver de MySQL", e);
        }
    }

    private static void initializeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DatabaseConfig.getUrl());
        config.setUsername(DatabaseConfig.getUser());
        config.setPassword(DatabaseConfig.getPassword());
        
        // Configuración del pool de conexiones
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(5);
        config.setIdleTimeout(300000);
        config.setConnectionTimeout(20000);
        
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("Error al obtener conexión de la base de datos", e);
            throw e;
        }
    }

    // Método para cerrar el pool de conexiones
    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}