package com.mycompany.aplicativowebintegrador.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/best-sellers")
public class BestSellersServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        JSONArray bestSellers = new JSONArray();
        // Aquí deberías obtener los datos reales de tu base de datos
        bestSellers.put(new JSONObject().put("nombre", "Producto 1").put("precio", 99.99));
        bestSellers.put(new JSONObject().put("nombre", "Producto 2").put("precio", 149.99));
        // Añade más productos según sea necesario

        PrintWriter out = response.getWriter();
        out.print(bestSellers.toString());
        out.flush();
    }
}
