package com.mycompany.aplicativowebintegrador.servlet;

import com.mycompany.aplicativowebintegrador.dao.ReclamoDAO;
import com.mycompany.aplicativowebintegrador.modelo.Reclamo;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/ReclamoServlet")
public class ReclamoServlet extends HttpServlet {
    private ReclamoDAO reclamoDAO;
    
    public void init() {
        reclamoDAO = new ReclamoDAO();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        
        if ("registrar".equals(accion)) {
            registrarReclamo(request, response);
        } else if ("actualizarEstado".equals(accion)) {
            actualizarEstadoReclamo(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        
        if ("listar".equals(accion)) {
            listarReclamos(request, response);
        } else if ("obtenerDetalle".equals(accion)) {
            obtenerDetalleReclamo(request, response);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        
        }
    }
    
    private void registrarReclamo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Reclamo reclamo = new Reclamo();
            reclamo.setTipoDocumento(request.getParameter("tipoDocumento"));
            reclamo.setNumeroDocumento(request.getParameter("numeroDocumento"));
            reclamo.setNombre(request.getParameter("nombre"));
            reclamo.setApellido(request.getParameter("apellido"));
            reclamo.setDepartamento(request.getParameter("departamento"));
            reclamo.setProvincia(request.getParameter("provincia"));
            reclamo.setDistrito(request.getParameter("distrito"));
            reclamo.setTelefono(request.getParameter("telefono"));
            reclamo.setCorreoElectronico(request.getParameter("correoElectronico"));
            reclamo.setTipoReclamo(request.getParameter("tipoReclamo"));
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            reclamo.setFechaCompra(sdf.parse(request.getParameter("fechaCompra")));
            
            reclamo.setNumeroBoleta(request.getParameter("numeroBoleta"));
            reclamo.setDetalleReclamo(request.getParameter("detalleReclamo"));
            
            if (reclamoDAO.registrarReclamo(reclamo)) {
                response.sendRedirect("LibroReclamaciones.html?exito=true");
            } else {
                response.sendRedirect("LibroReclamaciones.html?error=true");
            }
        } catch (ParseException e) {
            e.printStackTrace();
            response.sendRedirect("LibroReclamaciones.html?error=true");
        }
    }
    
    private void listarReclamos(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setAttribute("reclamos", reclamoDAO.listarReclamos());
        request.getRequestDispatcher("admin/gestionreclamos.jsp").forward(request, response);
    }
    
    private void obtenerDetalleReclamo(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    int id = Integer.parseInt(request.getParameter("id"));
    Reclamo reclamo = reclamoDAO.obtenerReclamoPorId(id);

    if (reclamo != null) {
        request.setAttribute("reclamo", reclamo);
        request.getRequestDispatcher("/views/intranex/detalleReclamo.jsp")
            .forward(request, response);
    } else {
        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
    }

    private void actualizarEstadoReclamo(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    int id = Integer.parseInt(request.getParameter("id"));
    String estado = request.getParameter("estado");

    if (reclamoDAO.actualizarEstadoReclamo(id, estado)) {
        response.getWriter().write("success");
    } else {
        response.getWriter().write("error");
    }
  }
}

