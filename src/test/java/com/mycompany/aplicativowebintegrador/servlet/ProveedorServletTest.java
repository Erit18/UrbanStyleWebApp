package com.mycompany.aplicativowebintegrador.servlet;

import com.google.gson.Gson;
import com.mycompany.aplicativowebintegrador.dao.IProveedorDAO;
import com.mycompany.aplicativowebintegrador.modelo.Proveedor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.io.*;
import java.util.Arrays;
import static org.mockito.Mockito.*;

@DisplayName("Pruebas del Servlet de Proveedor")
public class ProveedorServletTest {
    
    @Mock
    private HttpServletRequest request;
    
    @Mock
    private HttpServletResponse response;
    
    @Mock
    private IProveedorDAO proveedorDAO;
    
    private ProveedorServlet servlet;
    private StringWriter stringWriter;
    private PrintWriter writer;
    
    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        servlet = new ProveedorServlet();
        stringWriter = new StringWriter();
        writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);
        
        System.out.println("Iniciando prueba de servlet de proveedor...");
    }
    
    @Test
    @DisplayName("Test GET todos los proveedores")
    public void testDoGetTodos() throws Exception {
        System.out.println("Ejecutando prueba GET todos los proveedores");
        
        Proveedor proveedor = new Proveedor();
        proveedor.setId_proveedor(1);
        proveedor.setNombre("Test Proveedor");
        
        when(request.getPathInfo()).thenReturn("/");
        when(proveedorDAO.obtenerTodos()).thenReturn(Arrays.asList(proveedor));
        
        servlet.doGet(request, response);
        
        verify(response).setContentType("application/json");
        writer.flush();
    }
    
    @Test
    @DisplayName("Test GET proveedor por ID")
    public void testDoGetPorId() throws Exception {
        System.out.println("Ejecutando prueba GET proveedor por ID");
        
        when(request.getPathInfo()).thenReturn("/1");
        Proveedor proveedor = new Proveedor();
        proveedor.setId_proveedor(1);
        when(proveedorDAO.obtenerPorId(1)).thenReturn(proveedor);
        
        servlet.doGet(request, response);
        
        verify(response).setContentType("application/json");
        writer.flush();
    }
} 