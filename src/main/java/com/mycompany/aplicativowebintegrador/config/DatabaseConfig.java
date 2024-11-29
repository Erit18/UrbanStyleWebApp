package com.mycompany.aplicativowebintegrador.config;

public class DatabaseConfig {
    private static final String URL = "jdbc:mysql://localhost:3306/UrbanStyleDB?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "susanibar3";
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // Getters para acceder a la configuración
    public static String getUrl() { return URL; }
    public static String getUser() { return USER; }
    public static String getPassword() { return PASSWORD; }
    public static String getDriver() { return DRIVER; }
} 