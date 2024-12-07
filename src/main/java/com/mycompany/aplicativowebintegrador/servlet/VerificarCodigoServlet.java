package com.mycompany.aplicativowebintegrador.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/verificar-codigo")
public class VerificarCodigoServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String email = request.getParameter("email");
        String codigo = request.getParameter("codigo");
        Map<String, Object> jsonResponse = new HashMap<>();
        
        // Obtener el código almacenado en la sesión
        String codigoAlmacenado = (String) request.getSession().getAttribute("codigoVerificacion_" + email);
        
        if (codigoAlmacenado != null && codigoAlmacenado.equals(codigo)) {
            jsonResponse.put("success", true);
            jsonResponse.put("message", "Código verificado correctamente");
            // Limpiar el código de la sesión
            request.getSession().removeAttribute("codigoVerificacion_" + email);
        } else {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Código incorrecto");
        }
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(jsonResponse));
    }
} 