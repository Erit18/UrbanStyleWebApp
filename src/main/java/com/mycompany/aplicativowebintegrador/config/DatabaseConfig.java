package com.mycompany.aplicativowebintegrador.config;

public class DatabaseConfig implements IDatabaseConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/UrbanStyleDB?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "erits321123";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final int MAX_POOL_SIZE = 10;
    private static final int MIN_IDLE = 5;
    private static final int IDLE_TIMEOUT = 300000;
    private static final int CONNECTION_TIMEOUT = 20000;

    @Override
    public String getUrl() { return URL; }
    
    @Override
    public String getUser() { return USER; }
    
    @Override
    public String getPassword() { return PASSWORD; }
    
    @Override
    public String getDriver() { return DRIVER; }
    
    @Override
    public int getMaxPoolSize() { return MAX_POOL_SIZE; }
    
    @Override
    public int getMinIdle() { return MIN_IDLE; }
    
    @Override
    public int getIdleTimeout() { return IDLE_TIMEOUT; }
    
    @Override
    public int getConnectionTimeout() { return CONNECTION_TIMEOUT; }
}