package Controller;

import Model.Cargo;
import Model.Colaborador;
import Model.ColaboradorDAO;
import Model.Usuario;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;

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
        } else if ("Ver_Empleado".equals(accion)) {
            ver_Empleado(request, response);
            return;
        } else if ("eliminar_Empleado".equals(accion)) {
            eliminar_Empleado(request, response);
            return;
        }else {
            // Si la acción no coincide con ninguna de las anteriores, redirige a una página de error
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }

        request.getRequestDispatcher(vista).forward(request, response);
    }

//-------------------------------------------------------------------------------------//    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        if ("insertar".equals(accion)) {
            insertarColaborador(request, response);
        } else if ("Ver_Empleado".equals(accion)) {
            ver_Empleado(request, response);
        } else if ("eliminar_Empleado".equals(accion)) {
            eliminar_Empleado(request, response);
        } else if ("modificar_Empleado".equals(accion)) {
            modificar_Empleado(request, response);
        } else if ("actualizar_Empleado".equals(accion)) {
            actualizar_Empleado(request, response);
        }
    }
//-------------------------------------------------------------------------------------//
//------------------------------ INSERTAR -----------------------------------------//

     private void insertarColaborador(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Solicitar datos cargo desde formulario nuevo html


        // Obtener datos Usuario desde formulario nuevo html
        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(request.getParameter("nombreUsuario"));
        usuario.setClave(request.getParameter("claveUsuario"));
        usuario.setEstadoUsuario(Integer.parseInt(request.getParameter("estado_Usuario")) == 1);


        // Crear un nuevo colaborador
        int num_documento = Integer.parseInt(request.getParameter("num_documento"));
        String nombre = request.getParameter("nombre");
        String apellido_1 = request.getParameter("apellido_1");
        String apellido_2 = request.getParameter("apellido_2");
        int telefono = Integer.parseInt(request.getParameter("telefono"));
        String direccion = request.getParameter("direccion");
        String fechaContratacionStr = request.getParameter("fecha_contratacion");
        java.sql.Date fecha_contratacion = null;
        if (fechaContratacionStr != null && !fechaContratacionStr.isEmpty()) {
            fecha_contratacion = java.sql.Date.valueOf(fechaContratacionStr);
        }

        // Convertir el salario base a BigDecimal
        BigDecimal salario_base = BigDecimal.ZERO; // Valor predeterminado
        String salarioBaseStr = request.getParameter("salario_base");
        if (salarioBaseStr != null && !salarioBaseStr.isEmpty()) {
            salario_base = new BigDecimal(salarioBaseStr);
        }

        // Crear el objeto Colaborador con los datos corregidos
              
        Colaborador colaborador = new Colaborador(0,
                        num_documento, nombre, apellido_1, apellido_2, telefono,
                        direccion, fecha_contratacion, null,
                        salario_base, usuario, null,null);
                
        // Validar si el empleado ya existe
        try {
            if (colaboradorDAO.empleadoExiste(num_documento)) {
                request.setAttribute("mensaje", "El empleado con el número de documento proporcionado ya existe.");
                request.getRequestDispatcher("pages/error_cedula.jsp").forward(request, response);
                return;
            }

            // Insertar los datos en la base de datos
            boolean insercionExitosa = colaboradorDAO.insertarColaboradores(usuario, colaborador);

            // Si la inserción fue exitosa, redirigir al usuario a la página principal
            if (insercionExitosa) {
                response.sendRedirect("pages/pagina_exito.jsp");
            } else {
                response.sendRedirect("pages/errores.jsp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("mensaje", "Error al verificar la existencia del empleado.");
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }

//-------------------------------------------------------------------------------------//
//------------------------------ VER EMPLEADO -----------------------------------------//    
    private void ver_Empleado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int idUsuario = Integer.parseInt(request.getParameter("id"));

        Colaborador colaborador = colaboradorDAO.mostrarEmpleado(idUsuario);

        /* Usuario usuario = colaboradorDAO.mostrarUsuario(idUsuario);*/
        if (colaborador != null) {
            // Si se encuentra el colaborador, establecerlo como atributo de solicitud
            request.setAttribute("colaborador", colaborador);
            
            // Llamar a la página JSP para mostrar los detalles del empleado
            request.getRequestDispatcher("vistaAdmin/verEmpleado.jsp").forward(request, response);
        } else {
            // Si no se encuentra el colaborador, manejar el caso de error
            response.sendRedirect("/WEB-INF/error.jsp");
        }
    }

//-------------------------------------------------------------------------------------//
//------------------------------ ELIMINAR -----------------------------------------//  
    private void eliminar_Empleado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idUsuario = Integer.parseInt(request.getParameter("id"));

            colaboradorDAO.eliminarEmpleado(idUsuario);

            // Obtener la lista actualizada de colaboradores
            List<Colaborador> listaColaboradores = colaboradorDAO.listarColaboradores();
            // Establecer la lista como atributo de solicitud
            request.setAttribute("lista", listaColaboradores);
            // Redirigir al usuario a la página principal
            request.getRequestDispatcher("/vistaAdmin/indexAdmin.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            // Manejar el error de formato de número
            request.setAttribute("error", "ID de Usuario inválido.");
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }

//------------------------------------------------------------------------------------//
//-------------------------------------------------------------------------------------//
    //------------------------------ MODIFICAR  -----------------------------------------//
    private void modificar_Empleado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int idEmpleado = Integer.parseInt(request.getParameter("id"));

        
        Colaborador colaborador = colaboradorDAO.mostrarEmpleado(idEmpleado);    
        
        if (colaborador != null) {
            // Si se encuentra el colaborador, establecerlo como atributo de solicitud
            request.setAttribute("colaborador", colaborador);
            // Llamar a la página JSP para mostrar los detalles del empleado
            request.getRequestDispatcher("vistaAdmin/modificar.jsp").forward(request, response);
        } else {
            // Si no se encuentra el colaborador, manejar el caso de error
            response.sendRedirect("/WEB-INF/error.jsp");
        }
    }

    //-------------------------------------------------------------------------------------//
    //------------------------------ ACTUALIZAR  -----------------------------------------//
    private void actualizar_Empleado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
           
            // Obtener el ID del usuario
            String idUsuarioStr = request.getParameter("idUsuario");
            if (idUsuarioStr == null || idUsuarioStr.isEmpty()) {
                throw new IllegalArgumentException("ID del usuario no proporcionado.");
            }
            int idUsuario = Integer.parseInt(idUsuarioStr);
            
            // Obtener el ID del empleado
            String idEmpleadoStr = request.getParameter("id_empleado");
            if (idEmpleadoStr == null || idEmpleadoStr.isEmpty()) {
                throw new IllegalArgumentException("ID del empleado no proporcionado.");
            }
            int idEmpleado = Integer.parseInt(idEmpleadoStr);

            // Obtener datos desde el formulario empleado            
            int num_documento = Integer.parseInt(request.getParameter("num_documento"));
            String nombre = request.getParameter("nombre");
            String apellido_1 = request.getParameter("apellido_1");
            String apellido_2 = request.getParameter("apellido_2");
            int telefono = Integer.parseInt(request.getParameter("telefono"));
            String direccion = request.getParameter("direccion");

            String fechaContratacionStr = request.getParameter("fecha_contratacion");
            java.sql.Date fecha_contratacion = null;
            if (fechaContratacionStr != null && !fechaContratacionStr.isEmpty()) {
                fecha_contratacion = java.sql.Date.valueOf(fechaContratacionStr);
            }

            // Convertir el salario base a BigDecimal
            BigDecimal salario_base = BigDecimal.ZERO; // Valor predeterminado
            String salarioBaseStr = request.getParameter("salario_base");
            if (salarioBaseStr != null && !salarioBaseStr.isEmpty()) {
                salario_base = new BigDecimal(salarioBaseStr);
            }

            // Crear un nuevo usuario y colaborador
            Usuario usuario = new Usuario();
            usuario.setId_usuario(idUsuario); // Asigna el id_usuario al usuario

            Colaborador colaborador = new Colaborador(idEmpleado, num_documento, 
                nombre, apellido_1, apellido_2, telefono, direccion, 
                fecha_contratacion, null, 
                salario_base, usuario, null, null);

            // Manejo del estado del usuario
            boolean modificarEstado = "si".equals(request.getParameter("modificar_estado_usuario"));
           
            if (modificarEstado) {
                int estadoUsuario = Integer.parseInt(request.getParameter("estado_Usuario"));
                usuario.setEstadoUsuario(estadoUsuario == 1);
                } else {
                // Utilizar el valor del input hidden
                boolean estadoAnterior = "1".equals(request.getParameter("estado_actual"));
                usuario.setEstadoUsuario(estadoAnterior);
              }
              
            // Llamar al método de modificación en DAO
            boolean exito = colaboradorDAO.modificarEmpleado(colaborador);

            if (exito) {
                response.sendRedirect("SvColaborador?accion=Ver_Empleado&id=" + idEmpleado);
                System.out.println("si");

            } else {
                request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
   
    //---------------------------------------------------------------------------------------  
    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void asignar_Cargo(HttpServletRequest request, HttpServletResponse response) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
