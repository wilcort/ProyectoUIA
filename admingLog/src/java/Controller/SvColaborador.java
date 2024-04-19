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

<<<<<<< HEAD
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
=======
  
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
            ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
            String accion;
            RequestDispatcher dispatcher = null;
            
            accion = request.getParameter("accion");
            
            if (accion == null || accion.isEmpty()) {

            List<Colaborador> listaColaboradores = colaboradorDAO.listarColaboradores();
            request.setAttribute("lista", listaColaboradores);
            dispatcher = request.getRequestDispatcher("vistaAdmin/indexAdmin.jsp");

        } else if ("nuevo".equals(accion)) {
            
            dispatcher = request.getRequestDispatcher("vistaAdmin/nuevo.jsp");
        
        } else if ("insertar".equals(accion)) {
            System.out.println("hola inserat");

            // Crear el objeto Cargo
            Cargo cargo = new Cargo();
            cargo.setNombreCargo(request.getParameter("cargo")); // Obtener el cargo del formulario
            cargo.setEstado(Integer.parseInt(request.getParameter("estado_cargo")) == 1); // Obtener el estado del formulario
        
                System.out.println("Cargo:");
                System.out.println("Nombre: " + cargo.getNombreCargo());
                System.out.println("Estado: " + cargo.isEstado());
                
            // Crear el objeto Usuario
            Usuario usuario = new Usuario();
            usuario.setNombreUsuario(request.getParameter("nombreUsuario")); // Obtener el nombre de usuario del formulario
            usuario.setClave(request.getParameter("clave")); // Obtener la clave del formulario
            usuario.setEstado(Integer.parseInt(request.getParameter("estado_cargo")) == 1); // Obtener el estado del formulario
                
                System.out.println(" ");
                System.out.println("usuario: " + usuario.getNombreUsuario());
                
             //  objeto Colaborador
            
            int num_documento;
            String nombre;
            String apellido_1;
            String apellido_2;
            int telefono = 0;
            String direccion;
            
            String num_documentoParam = request.getParameter("num_documento");
            if (num_documentoParam != null && !num_documentoParam.isEmpty()) {
                num_documento = Integer.parseInt(num_documentoParam);
            } else {
                // Manejo de error: el parámetro "num_documento" es nulo o vacío
                // Puedes redirigir a una página de error o mostrar un mensaje al usuario
                // Aquí he asignado un valor predeterminado de 0, puedes ajustarlo según sea necesario
                num_documento = 0;
            }
            
            nombre = request.getParameter("nombre");
            apellido_1 = request.getParameter("apellido_1");
            apellido_2 = request.getParameter("apellido_2"); 
            direccion = request.getParameter("direccion");
            

            Colaborador colaborador = new Colaborador(num_documento, nombre, apellido_1, apellido_2,
                        telefono, direccion, null); 
             
            System.out.println(" ");
            System.out.println("direccion : " + colaborador.getApellido_1());
            
            // Insertar los datos en la base de datos
                colaboradorDAO.insertarColaboradores(cargo, usuario, colaborador);

            // Redirigir a la página de índice
            List<Colaborador> listaColaboradores = colaboradorDAO.listarColaboradores();
            request.setAttribute("lista", listaColaboradores);
            dispatcher = request.getRequestDispatcher("vistaAdmin/indexAdmin.jsp");
        }
        if (dispatcher != null) {
            dispatcher.forward(request, response);
        } else {
            System.out.println("El dispatcher es nulo. No se puede redirigir la solicitud.");
        }
>>>>>>> 37196b40e8dbddc60c6412cf3a08212e5bbf284e
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
        boolean insercionExitosa = colaboradorDAO.insertarColaboradores(cargo, usuario, colaborador);

 
        
        // Si la inserción fue exitosa, redirigir al usuario a la página principal
        if (insercionExitosa) {
            // Obtener la lista actualizada de colaboradores
            List<Colaborador> listaColaboradores = colaboradorDAO.listarColaboradores();
            // Establecer la lista como atributo de solicitud
            request.setAttribute("lista", listaColaboradores);
            // Redirigir al usuario a la página principal
            request.getRequestDispatcher("/vistaAdmin/indexAdmin.jsp").forward(request, response);
        } else {
            // Si la inserción falló, puedes manejarlo de alguna manera, como mostrar un mensaje de error
            // o redirigir a una página de error
            response.sendRedirect(request.getContextPath() + "/error.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
