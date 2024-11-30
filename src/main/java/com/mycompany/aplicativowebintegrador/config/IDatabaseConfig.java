package com.mycompany.aplicativowebintegrador.config;

public interface IDatabaseConfig {
    String getUrl();
    String getUser();
    String getPassword();
    String getDriver();
    int getMaxPoolSize();
    int getMinIdle();
    int getIdleTimeout();
    int getConnectionTimeout();
}