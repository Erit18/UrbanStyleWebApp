package com.mycompany.aplicativowebintegrador.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.aplicativowebintegrador.dao.AlertaDAO;
import com.mycompany.aplicativowebintegrador.modelo.Alerta;
import com.mycompany.aplicativowebintegrador.servicio.AlertaService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
// Imports de Jakarta EE
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/api/alertas/*")
public class AlertaServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(AlertaServlet.class);
    private AlertaService alertaService;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        alertaService = new AlertaService(new AlertaDAO());
        gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .create();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Alerta> alertas = alertaService.listarAlertas();
                response.getWriter().write(gson.toJson(alertas));
            } else {
                int id = Integer.parseInt(pathInfo.substring(1));
                Alerta alerta = alertaService.obtenerAlerta(id);
                if (alerta != null) {
                    response.getWriter().write(gson.toJson(alerta));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(gson.toJson("Alerta no encontrada"));
                }
            }
        } catch (Exception e) {
            handleError(response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Leer el cuerpo de la petición y convertirlo a objeto Alerta
            Alerta alerta = gson.fromJson(request.getReader(), Alerta.class);
            boolean resultado = alertaService.crearAlerta(alerta);
            
            try (PrintWriter out = response.getWriter()) {
                out.print(gson.toJson(resultado));
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print(gson.toJson("Error al crear la alerta: " + e.getMessage()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Leer el cuerpo de la petición y convertirlo a objeto Alerta
            Alerta alerta = gson.fromJson(request.getReader(), Alerta.class);
            boolean resultado = alertaService.actualizarAlerta(alerta);
            
            try (PrintWriter out = response.getWriter()) {
                out.print(gson.toJson(resultado));
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print(gson.toJson("Error al actualizar la alerta: " + e.getMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().print(gson.toJson("ID de alerta no proporcionado"));
            return;
        }

        try {
            int id = Integer.parseInt(pathInfo.substring(1));
            boolean resultado = alertaService.eliminarAlerta(id);
            
            try (PrintWriter out = response.getWriter()) {
                out.print(gson.toJson(resultado));
            }
        } catch (Exception e) {
            handleError(response, e);
        }
    }

    private void handleError(HttpServletResponse response, Exception e) throws IOException {
        logger.error("Error en AlertaServlet", e);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write(gson.toJson(Map.of("error", e.getMessage())));
    }
}
