package com.mycompany.aplicativowebintegrador.servlet;

import com.mycompany.aplicativowebintegrador.servicio.UsuarioService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Random;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/recuperar-password")
public class RecuperarPasswordServlet extends HttpServlet {
    
    private final UsuarioService usuarioService;
    
    public RecuperarPasswordServlet() {
        this.usuarioService = new UsuarioService();
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String email = request.getParameter("email");
        Map<String, Object> jsonResponse = new HashMap<>();
        
        System.out.println("Email recibido: " + email);
        
        try {
            // Verificar si el email existe en la base de datos
            boolean existeEmail = usuarioService.existeEmail(email);
            System.out.println("¿Email existe?: " + existeEmail);
            
            if (existeEmail) {
                // Generar código de verificación
                String codigo = generarCodigo();
                
                // Guardar el código en la sesión o base de datos temporal
                request.getSession().setAttribute("codigoVerificacion_" + email, codigo);
                
                // Enviar email
                enviarEmailVerificacion(email, codigo);
                
                jsonResponse.put("success", true);
                jsonResponse.put("message", "Código de verificación enviado");
            } else {
                jsonResponse.put("success", false);
                jsonResponse.put("message", "Email no encontrado");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error al procesar la solicitud: " + e.getMessage());
        }
        
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonString = new Gson().toJson(jsonResponse);
        System.out.println("Respuesta: " + jsonString);
        response.getWriter().write(jsonString);
    }
    
    private String generarCodigo() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }
    
    private void enviarEmailVerificacion(String email, String codigo) {
        // Configuración del servidor de correo
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        
        // Reemplaza con tu correo y la contraseña de aplicación generada
        final String miEmail = "urbanstyleperu.contacto@gmail.com";
        final String miContraseña = "datj qvqx dwtz ktic";
        
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(miEmail, miContraseña);
            }
        });
        
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(miEmail));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Código de verificación - UrbanStyle");
            
            // Crear un mensaje HTML más atractivo
            String htmlContent = String.format(
                "<div style='font-family: Arial, sans-serif; padding: 20px; background-color: #f5f5f5;'>" +
                "<h2 style='color: #333;'>Recuperación de Contraseña</h2>" +
                "<p>Has solicitado recuperar tu contraseña en UrbanStyle.</p>" +
                "<p>Tu código de verificación es:</p>" +
                "<h1 style='color: #007bff; font-size: 32px; letter-spacing: 5px;'>%s</h1>" +
                "<p>Este código expirará en 15 minutos.</p>" +
                "<p>Si no solicitaste este código, puedes ignorar este correo.</p>" +
                "<hr style='border: 1px solid #ddd;'>" +
                "<p style='font-size: 12px; color: #666;'>Este es un correo automático, por favor no respondas.</p>" +
                "</div>",
                codigo
            );

            // Configurar el contenido HTML
            message.setContent(htmlContent, "text/html; charset=utf-8");
            
            Transport.send(message);
            System.out.println("Correo enviado exitosamente a: " + email);
        } catch (MessagingException e) {
            System.out.println("Error al enviar correo: " + e.getMessage());
            throw new RuntimeException("Error al enviar el correo: " + e.getMessage());
        }
    }
} 