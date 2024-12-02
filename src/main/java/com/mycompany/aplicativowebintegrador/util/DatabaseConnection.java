package com.mycompany.aplicativowebintegrador.util;

import com.mycompany.aplicativowebintegrador.config.IDatabaseConfig;
import com.mycompany.aplicativowebintegrador.config.DatabaseConfig;
import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DatabaseConnection {
    private static IDatabaseConfig config = new DatabaseConfig();
    private static HikariDataSource dataSource;
    
    public static void setConfig(IDatabaseConfig newConfig) {
        config = newConfig;
        if (dataSource != null) {
            dataSource.close();
            dataSource = null;
        }
    }
    
    private static void initDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(config.getUrl());
        hikariConfig.setUsername(config.getUser());
        hikariConfig.setPassword(config.getPassword());
        hikariConfig.setDriverClassName(config.getDriver());
        hikariConfig.setMaximumPoolSize(config.getMaxPoolSize());
        hikariConfig.setMinimumIdle(config.getMinIdle());
        hikariConfig.setIdleTimeout(config.getIdleTimeout());
        hikariConfig.setConnectionTimeout(config.getConnectionTimeout());
        
        dataSource = new HikariDataSource(hikariConfig);
    }
    
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            initDataSource();
        }
        return dataSource.getConnection();
    }

    // Método alias para mantener compatibilidad
    public static void setDatabaseConfig(IDatabaseConfig newConfig) {
        setConfig(newConfig);
    }
}