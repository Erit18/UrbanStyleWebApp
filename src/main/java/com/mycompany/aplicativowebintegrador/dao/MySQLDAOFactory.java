package com.mycompany.aplicativowebintegrador.dao;

public class MySQLDAOFactory implements DAOFactory {
    
    @Override
    public IUsuarioDAO createUsuarioDAO() {
        return new UsuarioDAO();
    }
    
    @Override
    public IProductoDAO createProductoDAO() {
        return new ProductoDAO();
    }
    
    @Override
    public IProveedorDAO createProveedorDAO() {
        return new ProveedorDAO();
    }
    
    @Override
    public IVentaDAO createVentaDAO() {
        return new VentaDAO();
    }
    
    @Override
    public IAlertaDAO createAlertaDAO() {
        return new AlertaDAO();
    }
} 