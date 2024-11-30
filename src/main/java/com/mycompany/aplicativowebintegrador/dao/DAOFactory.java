package com.mycompany.aplicativowebintegrador.dao;

public interface DAOFactory {
    IUsuarioDAO createUsuarioDAO();
    IProductoDAO createProductoDAO();
    IProveedorDAO createProveedorDAO();
    IVentaDAO createVentaDAO();
    IAlertaDAO createAlertaDAO();
}