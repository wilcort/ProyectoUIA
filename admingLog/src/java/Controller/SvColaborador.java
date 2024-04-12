
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
     
        } else if("nuevo".equals(accion)){
            
            dispatcher = request.getRequestDispatcher("vistaAdmin/nuevo.jsp");
        
        } else if ("insertar".equals(accion)) {
            try {
                // Verificar si los parámetros no son nulos antes de convertirlos a enteros
                if (request.getParameter("num_documento") != null && request.getParameter("telefono") != null) {
                    int num_documento = Integer.parseInt(request.getParameter("num_documento"));
                    String nombre = request.getParameter("nombre");
                    String apellido_1 = request.getParameter("apellido_1");
                    String apellido_2 = request.getParameter("apellido_2");
                    int telefono = Integer.parseInt(request.getParameter("telefono"));
                    String direccion = request.getParameter("direccion");
                    
                    Cargo cargo = new Cargo();
                    cargo.setNombreCargo("Nombre del Cargo");
                    cargo.setEstado(true);
                
                    Colaborador colaborador = new Colaborador(num_documento, nombre, apellido_1, apellido_2,
                            telefono, direccion, null);
                    Usuario usuario = null;
                                     
                    colaboradorDAO.insertar(cargo, usuario, colaborador);
                    dispatcher = request.getRequestDispatcher("vistaAdmin/indexAdmin.jsp");

                    List<Colaborador> listaColaboradores = colaboradorDAO.listarColaboradores();
                    request.setAttribute("lista", listaColaboradores);
                } else {
                    // Manejo de error si los parámetros son nulos
                    // Por ejemplo, redirigir a una página de error o mostrar un mensaje al usuario
                    
                    dispatcher = request.getRequestDispatcher("vistaAdmin/nuevo.jsp");
                }
            } catch (NumberFormatException e) {
                // Manejo de error si los parámetros no son números enteros válidos
                // Por ejemplo, redirigir a una página de error o mostrar un mensaje al usuario
                 dispatcher = request.getRequestDispatcher("vistaAdmin/nuevo.jsp");
            }
        }
        dispatcher.forward(request, response);
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
