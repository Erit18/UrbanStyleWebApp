package com.mycompany.aplicativowebintegrador.servlet;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/visita")
public class VisitaServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(VisitaServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pagina = request.getParameter("pagina");
        logger.info("Visita registrada a la página: {}", pagina);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
