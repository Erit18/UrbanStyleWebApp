package com.mycompany.aplicativowebintegrador.servlet;

import com.google.gson.Gson;
import com.mycompany.aplicativowebintegrador.modelo.Usuario;
import com.mycompany.aplicativowebintegrador.servicio.UsuarioService;
import com.mycompany.aplicativowebintegrador.util.ResponseBuilder;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet("/usuario/*")
public class UsuarioServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(UsuarioServlet.class);
    private UsuarioService usuarioService;
    private ResponseBuilder responseBuilder;
    private Gson gson;

    @Override
    public void init() throws ServletException {
        super.init();
        this.usuarioService = new UsuarioService();
        this.responseBuilder = new ResponseBuilder();
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            String pathInfo = request.getPathInfo();
            if (pathInfo == null || pathInfo.equals("/")) {
                List<Usuario> usuarios = usuarioService.listarUsuarios();
                response.getWriter().write(gson.toJson(usuarios));
            } else {
                int id = Integer.parseInt(pathInfo.substring(1));
                Usuario usuario = usuarioService.obtenerUsuario(id);
                if (usuario != null) {
                    response.getWriter().write(gson.toJson(usuario));
                } else {
                    responseBuilder.enviarRespuestaError(response, 
                        new Exception("Usuario no encontrado"));
                }
            }
        } catch (Exception e) {
            logger.error("Error en GET /usuario", e);
            responseBuilder.enviarRespuestaError(response, e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            Usuario usuario = gson.fromJson(request.getReader(), Usuario.class);
            usuarioService.registrarUsuario(usuario);
            responseBuilder.enviarRespuestaCreada(response, "Usuario creado exitosamente");
        } catch (Exception e) {
            logger.error("Error en POST /usuario", e);
            responseBuilder.enviarRespuestaError(response, e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            Usuario usuario = gson.fromJson(request.getReader(), Usuario.class);
            usuarioService.actualizarUsuario(usuario);
            responseBuilder.enviarRespuestaExitosa(response, "Usuario actualizado exitosamente");
        } catch (Exception e) {
            logger.error("Error en PUT /usuario", e);
            responseBuilder.enviarRespuestaError(response, e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        response.setContentType("application/json");
        try {
            int id = Integer.parseInt(request.getPathInfo().substring(1));
            usuarioService.eliminarUsuario(id);
            responseBuilder.enviarRespuestaExitosa(response, "Usuario eliminado exitosamente");
        } catch (Exception e) {
            logger.error("Error en DELETE /usuario", e);
            responseBuilder.enviarRespuestaError(response, e);
        }
    }
}
