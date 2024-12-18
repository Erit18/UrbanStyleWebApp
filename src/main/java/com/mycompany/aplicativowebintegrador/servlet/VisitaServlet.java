package com.mycompany.aplicativowebintegrador.servlet;

import io.micrometer.core.instrument.Counter;
import com.mycompany.aplicativowebintegrador.config.MetricsConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/visita")
public class VisitaServlet extends HttpServlet {
    private final Counter visitasCounter = Counter
        .builder("pagina_visitas_total")
        .description("Número total de visitas")
        .tag("pagina", "index")
        .register(MetricsConfig.getRegistry());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws IOException {
        String pagina = req.getParameter("pagina");
        visitasCounter.increment();
        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
