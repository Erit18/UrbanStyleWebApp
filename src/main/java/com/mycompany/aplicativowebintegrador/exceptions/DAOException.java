package com.mycompany.aplicativowebintegrador.exceptions;

public class DAOException extends RuntimeException {
    
    public DAOException(String message) {
        super(message);
    }
    
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public static class RecordNotFoundException extends DAOException {
        public RecordNotFoundException(String message) {
            super(message);
        }
    }
    
    public static class DuplicateKeyException extends DAOException {
        public DuplicateKeyException(String message) {
            super(message);
        }
    }
    
    public static class DataIntegrityException extends DAOException {
        public DataIntegrityException(String message) {
            super(message);
        }
    }
} 