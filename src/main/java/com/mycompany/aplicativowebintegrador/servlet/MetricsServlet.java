package com.mycompany.aplicativowebintegrador.servlet;

import com.mycompany.aplicativowebintegrador.config.MetricsConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/metrics")
public class MetricsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write(MetricsConfig.scrape());
    }
}