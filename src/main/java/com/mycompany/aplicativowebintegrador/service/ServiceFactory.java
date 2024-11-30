package com.mycompany.aplicativowebintegrador.service;

import com.mycompany.aplicativowebintegrador.dao.*;

public class ServiceFactory {
    private static ServiceFactory instance;
    private final DAOFactory daoFactory;
    
    private ServiceFactory() {
        this.daoFactory = new MySQLDAOFactory();
    }
    
    public static ServiceFactory getInstance() {
        if (instance == null) {
            instance = new ServiceFactory();
        }
        return instance;
    }
    
    public ProductoService createProductoService() {
        return new ProductoService(daoFactory.createProductoDAO());
    }
    
    public UsuarioService createUsuarioService() {
        return new UsuarioService(daoFactory.createUsuarioDAO());
    }
    
    public ProveedorService createProveedorService() {
        return new ProveedorService(daoFactory.createProveedorDAO());
    }
    
    public AlertaService createAlertaService() {
        return new AlertaService(daoFactory.createAlertaDAO());
    }
} 