package com.mycompany.aplicativowebintegrador.util;

import com.mycompany.aplicativowebintegrador.config.IDatabaseConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnection {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConnection.class);
    private static HikariDataSource dataSource;
    private static IDatabaseConfig dbConfig;

    public static void setDatabaseConfig(IDatabaseConfig config) {
        dbConfig = config;
        initializeDataSource();
    }

    private static void initializeDataSource() {
        try {
            Class.forName(dbConfig.getDriver());
            
            if (dataSource != null && !dataSource.isClosed()) {
                dataSource.close();
            }
            
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(dbConfig.getUrl());
            config.setUsername(dbConfig.getUser());
            config.setPassword(dbConfig.getPassword());
            config.setMaximumPoolSize(dbConfig.getMaxPoolSize());
            config.setMinimumIdle(dbConfig.getMinIdle());
            config.setIdleTimeout(dbConfig.getIdleTimeout());
            config.setConnectionTimeout(dbConfig.getConnectionTimeout());
            
            dataSource = new HikariDataSource(config);
            
        } catch (ClassNotFoundException e) {
            logger.error("Error al cargar el driver de MySQL", e);
            throw new RuntimeException("Error al cargar el driver de MySQL", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        if (dbConfig == null) {
            throw new SQLException("Database configuration not set");
        }
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.error("Error al obtener conexión de la base de datos", e);
            throw e;
        }
    }

    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}