package com.mycompany.aplicativowebintegrador.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.aplicativowebintegrador.modelo.Venta;
import com.mycompany.aplicativowebintegrador.servicio.VentaService;
import com.mycompany.aplicativowebintegrador.dao.VentaDAO;
import com.mycompany.aplicativowebintegrador.dao.IVentaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet("/api/ventas")
public class VentasServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(VentasServlet.class);
    private VentaService ventaService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        IVentaDAO ventaDAO = new VentaDAO();
        this.ventaService = new VentaService(ventaDAO);
        this.gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            Date fechaInicio = parseDate(request.getParameter("fechaInicio"));
            Date fechaFin = parseDate(request.getParameter("fechaFin"));
            String categoria = request.getParameter("categoria");
            
            List<Venta> ventas = ventaService.buscarVentas(fechaInicio, fechaFin, categoria);
            
            String json = gson.toJson(ventas);
            response.getWriter().write(json);
            
        } catch (Exception e) {
            logger.error("Error al procesar la solicitud", e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            Map<String, String> error = Map.of("error", e.getMessage());
            response.getWriter().write(gson.toJson(error));
        }
    }

    private Date parseDate(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) {
            return null;
        }
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e) {
            logger.warn("Error al parsear fecha: {}", dateStr);
            return null;
        }
    }
} 