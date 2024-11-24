package com.mycompany.aplicativowebintegrador.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {
    private final Gson gson;

    public ResponseBuilder() {
        this.gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd")
            .create();
    }

    public void enviarRespuestaExitosa(HttpServletResponse response, String mensaje) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", mensaje);
        response.getWriter().write(gson.toJson(successResponse));
    }

    public void enviarRespuestaCreada(HttpServletResponse response, String mensaje) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_CREATED);
        
        Map<String, String> successResponse = new HashMap<>();
        successResponse.put("message", mensaje);
        response.getWriter().write(gson.toJson(successResponse));
    }

    public void enviarRespuestaError(HttpServletResponse response, Exception e) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Error: " + e.getMessage());
        response.getWriter().write(gson.toJson(errorResponse));
    }

    public Gson getGson() {
        return gson;
    }
} 