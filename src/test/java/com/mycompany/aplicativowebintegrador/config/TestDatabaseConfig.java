package com.mycompany.aplicativowebintegrador.config;

public class TestDatabaseConfig implements IDatabaseConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/UrbanStyleDB_Test?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "erits321123";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    
    @Override
    public String getUrl() { return URL; }
    
    @Override
    public String getUser() { return USER; }
    
    @Override
    public String getPassword() { return PASSWORD; }
    
    @Override
    public String getDriver() { return DRIVER; }
    
    @Override
    public int getMaxPoolSize() { return 2; }
    
    @Override
    public int getMinIdle() { return 1; }
    
    @Override
    public int getIdleTimeout() { return 300000; }
    
    @Override
    public int getConnectionTimeout() { return 20000; }
} 