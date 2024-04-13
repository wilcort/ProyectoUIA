
package Controller;

import Model.Cargo;
import Model.Colaborador;
import Model.ColaboradorDAO;
import Model.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;


@WebServlet(name = "SvColaborador", urlPatterns = {"/SvColaborador"})
public class SvColaborador extends HttpServlet {

 
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ColaboradorDAO colaboradorDAO = new ColaboradorDAO();
        String accion;
        RequestDispatcher dispatcher = null;

        accion = request.getParameter("accion");

        if (accion == null || accion.isEmpty()) {

            dispatcher = request.getRequestDispatcher("vistaAdmin/indexAdmin.jsp");
            List<Colaborador> listaColaboradores = colaboradorDAO.listarColaboradores();
            request.setAttribute("lista", listaColaboradores);

        } else if ("nuevo".equals(accion)) {

            dispatcher = request.getRequestDispatcher("vistaAdmin/nuevo.jsp");

        } else if ("insertar".equals(accion)) {

            System.out.println("hola");

            try {
                // Crear el objeto Cargo
                Cargo cargo = new Cargo();
                cargo.setNombreCargo(request.getParameter("cargo")); // Obtener el cargo del formulario
                cargo.setEstado(Integer.parseInt(request.getParameter("estado")) == 1); // Obtener el estado del formulario

                // Crear el objeto Usuario
                Usuario usuario = new Usuario();
                usuario.setNombreUsuario(request.getParameter("nombreUsuario")); // Obtener el nombre de usuario del formulario
                usuario.setClave(request.getParameter("clave")); // Obtener la clave del formulario
                usuario.setEstado(Integer.parseInt(request.getParameter("estado")) == 1); // Obtener el estado del formulario

                //  objeto Colaborador
                int num_documento = Integer.parseInt(request.getParameter("num_documento"));
                String nombre = request.getParameter("nombre");
                String apellido_1 = request.getParameter("apellido_1");
                String apellido_2 = request.getParameter("apellido_2");
                int telefono = Integer.parseInt(request.getParameter("telefono"));
                String direccion = request.getParameter("direccion");

                Colaborador colaborador = new Colaborador(num_documento, nombre, apellido_1, apellido_2,
                        telefono, direccion, null);


                // Insertar los datos en la base de datos
                colaboradorDAO.insertarColaboradores(cargo, usuario, colaborador);

                // Redirigir a la página de índice
                dispatcher = request.getRequestDispatcher("vistaAdmin/indexAdmin.jsp");

            } catch (Exception e) {

                // Manejo de error si los parámetros no son números enteros válidos
                dispatcher = request.getRequestDispatcher("vistaAdmin/nuevo.jsp");
            }

            dispatcher.forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Método POST delega al método GET para manejar la solicitud
        doGet(request, response);
    }


  
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
