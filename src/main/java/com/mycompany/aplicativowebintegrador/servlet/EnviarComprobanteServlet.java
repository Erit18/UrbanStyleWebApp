package com.mycompany.aplicativowebintegrador.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Properties;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import jakarta.mail.util.ByteArrayDataSource;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import java.util.Base64;

@WebServlet("/enviar-comprobante")
public class EnviarComprobanteServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        String email = request.getParameter("email");
        String pdfBase64 = request.getParameter("pdf");
        String tipoDocumento = request.getParameter("tipoDocumento");
        Map<String, Object> jsonResponse = new HashMap<>();
        
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            // Credenciales del correo (usar las mismas que en RecuperarPasswordServlet)
            final String remitente = "urbanstyleperu.contacto@gmail.com";
            final String password = "datj qvqx dwtz ktic";

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(remitente, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(remitente, "Urban Style"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Comprobante de Pago - Urban Style");

            String htmlContent = String.format(
                "<div style='font-family: Arial, sans-serif; padding: 20px;'>" +
                "<h2 style='color: #333;'>¡Gracias por tu compra!</h2>" +
                "<p>Adjunto encontrarás tu %s electrónica.</p>" +
                "<p>Si tienes alguna pregunta, no dudes en contactarnos.</p>" +
                "<hr style='border: 1px solid #ddd;'>" +
                "<p style='font-size: 12px; color: #666;'>Urban Style © 2024</p>" +
                "</div>",
                tipoDocumento.equals("boleta") ? "Boleta" : "Factura"
            );

            Multipart multipart = new MimeMultipart();

            MimeBodyPart htmlPart = new MimeBodyPart();
            htmlPart.setContent(htmlContent, "text/html; charset=utf-8");
            multipart.addBodyPart(htmlPart);

            MimeBodyPart pdfPart = new MimeBodyPart();
            byte[] pdfBytes = Base64.getDecoder().decode(pdfBase64.split(",")[1]);
            DataSource source = new ByteArrayDataSource(pdfBytes, "application/pdf");
            pdfPart.setDataHandler(new DataHandler(source));
            pdfPart.setFileName("comprobante_urban_style.pdf");
            multipart.addBodyPart(pdfPart);

            message.setContent(multipart);
            Transport.send(message);

            jsonResponse.put("success", true);
            jsonResponse.put("message", "Comprobante enviado exitosamente a su correo");
        } catch (Exception e) {
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error al enviar el comprobante: " + e.getMessage());
        }

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(new Gson().toJson(jsonResponse));
    }
} 