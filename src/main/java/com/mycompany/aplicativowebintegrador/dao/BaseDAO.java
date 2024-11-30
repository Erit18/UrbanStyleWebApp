package com.mycompany.aplicativowebintegrador.dao;

import com.mycompany.aplicativowebintegrador.exceptions.DAOException;
import com.mycompany.aplicativowebintegrador.config.IDatabaseConfig;
import com.mycompany.aplicativowebintegrador.util.DatabaseConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class BaseDAO {
    protected final Logger logger;
    
    protected BaseDAO() {
        this.logger = LoggerFactory.getLogger(getClass());
    }
    
    protected Connection getConnection() throws SQLException {
        try {
            return DatabaseConnection.getConnection();
        } catch (SQLException e) {
            logger.error("Error al obtener conexión de base de datos", e);
            throw new DAOException("Error de conexión a la base de datos", e);
        }
    }
    
    protected void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            logger.error("Error cerrando recursos de base de datos", e);
        }
    }
    
    protected void closeResources(Connection conn, Statement stmt) {
        closeResources(conn, stmt, null);
    }
    
    protected void validateNotNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
    
    protected void validateStringNotEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " no puede estar vacío");
        }
    }
    
    protected void validatePositiveNumber(Number value, String fieldName) {
        if (value == null || value.doubleValue() <= 0) {
            throw new IllegalArgumentException(fieldName + " debe ser un número positivo");
        }
    }
    
    protected void handleSQLException(SQLException e, String operacion) {
        logger.error("Error en operación de base de datos: " + operacion, e);
        if (e.getErrorCode() == 1062) { // Código MySQL para duplicate entry
            throw new DAOException.DuplicateKeyException("Registro duplicado");
        } else if (e.getErrorCode() == 1452) { // Foreign key constraint fails
            throw new DAOException.DataIntegrityException("Error de integridad referencial");
        } else {
            throw new DAOException("Error en operación: " + operacion, e);
        }
    }
}