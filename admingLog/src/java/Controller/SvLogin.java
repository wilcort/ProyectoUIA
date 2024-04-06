
package Controller;

import Model.Usuario;
import Model.UsuarioDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "SvLogin", urlPatterns = {"/SvLogin"})
public class SvLogin extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        if ("cerrar".equals(accion)) {
            CerrarSession(request, response);
        } else {
            // Verificar si el usuario ya está autenticado
            HttpSession sesion = request.getSession(false);
            
            if (sesion != null && sesion.getAttribute("usuario") != null) {
                // Si el usuario ya está autenticado, redirige a la página de inicio
                response.sendRedirect("vistasLog/inicio.jsp");
            } else {
                // Si no, muestra la página de inicio de sesión
                System.out.println("error");
                RequestDispatcher dispatcher = request.getRequestDispatcher("inicio.jsp");
                dispatcher.forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        if ("verificar".equals(accion)) {
            try {
                verificar(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("ERROR" + ex.getMessage());
            }
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void verificar(HttpServletRequest request, HttpServletResponse response) throws Exception {
        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = obtenerUsuario(request);
        usuario = dao.identificar(usuario);

        if (usuario != null && usuario.getCargo().getNombreCargo().equalsIgnoreCase("administrador")) {
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", usuario);
            response.sendRedirect("vistasLog/administrador.jsp");
            
        } else if (usuario != null && usuario.getCargo().getNombreCargo().equalsIgnoreCase("vendedor")) {
            HttpSession sesion = request.getSession();
            sesion.setAttribute("usuario", usuario);
            response.sendRedirect("vistasLog/vendedor.jsp");
            
        } else {
            // Añadir una declaración de impresión para depurar
            System.out.println("Error al verificar credenciales: Usuario o cargo incorrecto.");

            request.setAttribute("msj", "ERROR CREDENCIALES");
            request.getRequestDispatcher("identificar.jsp").forward(request, response);
        }
    }

    private void CerrarSession(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession sesion = request.getSession();
        sesion.removeAttribute("usuario");
        sesion.invalidate();
        response.sendRedirect("identificar.jsp");
    }

    private Usuario obtenerUsuario(HttpServletRequest request) {
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(request.getParameter("txtUsu"));
        usuario.setClave(request.getParameter("txtPass"));
        return usuario;
    }
}
