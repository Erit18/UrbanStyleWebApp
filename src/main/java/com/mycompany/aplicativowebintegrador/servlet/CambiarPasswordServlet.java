package com.mycompany.aplicativowebintegrador.servlet;

import com.mycompany.aplicativowebintegrador.servicio.UsuarioService;
import com.mycompany.aplicativowebintegrador.modelo.Usuario;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cambiar-password")
public class CambiarPasswordServlet extends HttpServlet {
    
    private final UsuarioService usuarioService;
    
    public CambiarPasswordServlet() {
        this.usuarioService = new UsuarioService();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String email = request.getParameter("email");
        String newPassword = request.getParameter("password");
        Map<String, Object> jsonResponse = new HashMap<>();
        
        try {
            Usuario usuario = usuarioService.buscarPorEmail(email);
            if (usuario != null) {
                usuario.setContraseña(newPassword);
                usuarioService.actualizarUsuario(usuario);
                
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Contraseña actualizada exitosamente");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Usuario no encontrado");
            }
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error al actualizar la contraseña: " + e.getMessage());
        }
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(jsonResponse));
    }
} 