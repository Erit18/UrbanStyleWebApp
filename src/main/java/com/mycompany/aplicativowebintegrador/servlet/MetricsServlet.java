package com.mycompany.aplicativowebintegrador.servlet;

import com.mycompany.aplicativowebintegrador.config.MetricsConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/metrics")
public class MetricsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("text/plain");
        try {
            response.getWriter().write(MetricsConfig.scrape());
        } catch (Exception e) {
            response.setStatus(500);
        }
    }
}