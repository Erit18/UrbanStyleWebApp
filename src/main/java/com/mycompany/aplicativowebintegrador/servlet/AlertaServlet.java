package com.mycompany.aplicativowebintegrador.servlet;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mycompany.aplicativowebintegrador.dao.AlertaDAO;
import com.mycompany.aplicativowebintegrador.modelo.Alerta;
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

@WebServlet("/api/alertas/*")
public class AlertaServlet extends HttpServlet {
    private AlertaDAO alertaDAO;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        System.out.println("DEBUG - AlertaServlet inicializado");
        alertaDAO = new AlertaDAO();
        gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
            .create();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("=== DEBUG - AlertaServlet.doGet() - INICIO ===");
        System.out.println("URL solicitada: " + request.getRequestURL());
        System.out.println("Query string: " + request.getQueryString());
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        
        try {
            String pathInfo = request.getPathInfo();
            System.out.println("PathInfo: " + pathInfo);
            
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Alerta> alertas = alertaDAO.listarAlertas();
                System.out.println("Número de alertas recuperadas: " + alertas.size());
                
                // Imprimir cada alerta
                for (Alerta alerta : alertas) {
                    System.out.println("Alerta: {" +
                        "id=" + alerta.getId_alerta() + 
                        ", producto=" + alerta.getNombre_producto() + 
                        ", mensaje=" + alerta.getMensaje() + 
                        ", fecha=" + alerta.getFecha_alerta() +
                        "}");
                }
                
                String json = gson.toJson(alertas);
                System.out.println("JSON a enviar: " + json);
                response.getWriter().write(json);
            } else {
                int id = Integer.parseInt(pathInfo.substring(1));
                Alerta alerta = alertaDAO.obtenerAlertaPorId(id);
                if (alerta != null) {
                    response.getWriter().write(gson.toJson(alerta));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    response.getWriter().write(gson.toJson("Alerta no encontrada"));
                }
            }
        } catch (Exception e) {
            System.err.println("ERROR en AlertaServlet.doGet(): " + e.getMessage());
            e.printStackTrace();
            handleError(response, e);
        }
        System.out.println("=== DEBUG - AlertaServlet.doGet() - FIN ===");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            // Leer el cuerpo de la petición y convertirlo a objeto Alerta
            Alerta alerta = gson.fromJson(request.getReader(), Alerta.class);
            boolean resultado = alertaDAO.crearAlerta(alerta);
            
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
            boolean resultado = alertaDAO.actualizarAlerta(alerta);
            
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
            boolean resultado = alertaDAO.eliminarAlerta(id);
            
            try (PrintWriter out = response.getWriter()) {
                out.print(gson.toJson(resultado));
            }
        } catch (Exception e) {
            handleError(response, e);
        }
    }

    private void handleError(HttpServletResponse response, Exception e) throws IOException {
        e.printStackTrace();
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        Map<String, String> error = new HashMap<>();
        error.put("error", e.getMessage());
        response.getWriter().write(gson.toJson(error));
    }
}
