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
    private static final DatabaseConfig dbConfig = new DatabaseConfig();

    static {
        try {
            Class.forName(dbConfig.getDriver());
            initializeDataSource();
        } catch (ClassNotFoundException e) {
            logger.error("Error al cargar el driver de MySQL", e);
            throw new RuntimeException("Error al cargar el driver de MySQL", e);
        }
    }

    private static void initializeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(dbConfig.getUrl());
        config.setUsername(dbConfig.getUser());
        config.setPassword(dbConfig.getPassword());
        
        config.setMaximumPoolSize(dbConfig.getMaxPoolSize());
        config.setMinimumIdle(dbConfig.getMinIdle());
        config.setIdleTimeout(dbConfig.getIdleTimeout());
        config.setConnectionTimeout(dbConfig.getConnectionTimeout());
        
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

    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}