
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
