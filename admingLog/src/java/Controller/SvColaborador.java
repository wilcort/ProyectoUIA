package Controller;

import Model.Cargo;
import Model.Colaborador;
import Model.ColaboradorDAO;
import Model.Usuario;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SvColaborador", urlPatterns = {"/SvColaborador"})
public class SvColaborador extends HttpServlet {

    private ColaboradorDAO colaboradorDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        colaboradorDAO = new ColaboradorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        String vista = "vistaAdmin/indexAdmin.jsp";

        if (accion == null || accion.isEmpty()) {
            List<Colaborador> listaColaboradores = colaboradorDAO.listarColaboradores();
            request.setAttribute("lista", listaColaboradores);
        } else if ("nuevo".equals(accion)) {
            vista = "vistaAdmin/nuevo.jsp";
        }

        request.getRequestDispatcher(vista).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("insertar".equals(accion)) {
            insertarColaborador(request, response);
        }
    }

    private void insertarColaborador(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener datos cargo desde formulario nuevo html
        String nombreCargo = request.getParameter("cargo_Usuario");
        Cargo cargo = colaboradorDAO.obtenerCargoPorNombre(nombreCargo);


        // Obtener datos Usuario desde formulario nuevo html
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(request.getParameter("nombreUsuario"));
        usuario.setClave(request.getParameter("claveUsuario"));
        usuario.setEstado(Integer.parseInt(request.getParameter("estado_cargoUsuario")) == 1);

        // Asociar el cargo al usuario
        usuario.setCargo(cargo);
        
        // Crear un nuevo colaborador
        int num_documento = Integer.parseInt(request.getParameter("num_documento"));
        String nombre = request.getParameter("nombre");
        String apellido_1 = request.getParameter("apellido_1");
        String apellido_2 = request.getParameter("apellido_2");
        int telefono = Integer.parseInt(request.getParameter("telefono"));
        String direccion = request.getParameter("direccion");

        Colaborador colaborador = new Colaborador(num_documento, nombre, apellido_1, apellido_2,
                telefono, direccion, usuario);

        // Insertar los datos en la base de datos
        boolean insercionExitosa = colaboradorDAO.insertarColaboradores(usuario, colaborador); 
        
        // Si la inserción fue exitosa, redirigir al usuario a la página principal
        if (insercionExitosa) {
            // Obtener la lista actualizada de colaboradores
            List<Colaborador> listaColaboradores = colaboradorDAO.listarColaboradores();
            // Establecer la lista como atributo de solicitud
            request.setAttribute("lista", listaColaboradores);
            // Redirigir al usuario a la página principal
            request.getRequestDispatcher("/vistaAdmin/indexAdmin.jsp").forward(request, response);
        } else {
            
            response.sendRedirect("/WEB-INF/error.jsp");

        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}