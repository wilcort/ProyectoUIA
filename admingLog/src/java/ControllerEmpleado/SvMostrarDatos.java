/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerEmpleado;

import Model.Colaborador;
import ModelEmpleado.EmpleadoDAO;
import ModelEmpleado.Marcas;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@WebServlet(name = "SvMostrarDatos", urlPatterns = {"/SvMostrarDatos"})
public class SvMostrarDatos extends HttpServlet {

    private EmpleadoDAO empleadoDAO;
    private String vista; // Definir la variable vista como un campo de clase
    
    @Override
    public void init() throws ServletException {
        super.init();
        empleadoDAO = new EmpleadoDAO();
        vista = "vistaEmpleado/datosEmpleado.jsp"; // Inicializar la variable vista
    }
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");

        if ("Ver_Empleado".equals(accion)) {
            ver_Empleado(request, response);
            return;
        } else if("realizar_Marca".equals(accion)){
             request.getRequestDispatcher("vistaEmpleado/marcasEmpleado.jsp").forward(request, response); 
             
        }else {
            // Si la acción no coincide con ninguna de las anteriores, redirige a una página de error
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }
    }
//----------------------------------------------------------------
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        String fechaMarca = request.getParameter("fecha_marca"); // Obtener la fecha de la marca
        boolean marcaRegistrada = false;
        
        try {
            if ("realizar_Marca".equals(accion)) {
                realizar_Marca(request, response);
           
            } else {
                response.sendRedirect("/WEB-INF/error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/WEB-INF/error.jsp");
        }
    }
    
//-------------------------------------------------------------------------------
    
    private void ver_Empleado(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Obtener el ID de usuario de la sesión
        Integer idUsuario = (Integer) request.getSession().getAttribute("id_usuario");
        System.out.println("hola " + idUsuario);
         

        if (idUsuario != null) {
            // Llamar al método mostrarEmpleado de EmpleadoDAO
            Colaborador colaborador = empleadoDAO.mostrarEmpleado(idUsuario);
            
            // Verificar si se encontró el colaborador
            if (colaborador != null) {
                // Agregar el colaborador al request para pasarlo a la vista
                request.setAttribute("colaborador", colaborador);
                // Redirigir a la vista del empleado
                request.getRequestDispatcher(vista).forward(request, response);
            } else {
                // Manejar el caso en que no se encontró el colaborador
                request.setAttribute("error", "No se encontró el empleado.");
                request.getRequestDispatcher(vista).forward(request, response);
            }
        } else {
            // Manejar el caso en que el ID de usuario no está presente
            response.sendRedirect("errorPage.jsp"); // Redirigir a una página de error o login
        }
    }
    
//--------------------------------------------------------------------------------------------
    
//--------------------------------------------------------       
    //----
private void realizar_Marca(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    // Obtener el ID de usuario de la sesión
    Integer idUsuario = (Integer) request.getSession().getAttribute("id_usuario");
    System.out.println("ID Usuario: " + idUsuario);

    // Obtener el ID del empleado basado en el ID del usuario
    Integer idEmpleado = empleadoDAO.obtenerIdEmpleado(idUsuario);
    System.out.println("ID Empleado: " + idEmpleado);

    // Verificar si se encontró el empleado
    if (idEmpleado != null) {
        request.setAttribute("idEmpleado", idEmpleado);
        request.getSession().setAttribute("idEmpleado", idEmpleado);
    }

    // Obtener la fecha ingresada por el usuario en el formulario
    String fechaMarcaStr = request.getParameter("fecha_marca");
    System.out.println("Fecha seleccionada: " + fechaMarcaStr);

    // Pasar la fecha seleccionada a la JSP
    request.setAttribute("fechaSeleccionada", fechaMarcaStr);

    try {
        // Convertir el String a Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaMarca = dateFormat.parse(fechaMarcaStr); // Convierte el String a Date
        System.out.println("Fecha convertida: " + fechaMarca);

        // Crear y configurar la nueva marca
        Marcas nuevaMarca = new Marcas();
        nuevaMarca.setFechaMarca(fechaMarca);
        nuevaMarca.setMarcaEntrada(parseTime(request.getParameter("hora_entrada"))); // Obtén la hora de entrada desde el formulario
        nuevaMarca.setMarcaSalida(parseTime(request.getParameter("hora_salida"))); // 
        nuevaMarca.setMarcaSalidaAlmuerzo(parseTime(request.getParameter("hora_entrada_almuerzo")));
        nuevaMarca.setMarcaEntradaAlmuerzo(parseTime(request.getParameter("hora_salida_almuerzo")));
        nuevaMarca.setIdEmpleado(idEmpleado);
        
        // Comprobar si hay al menos una marca
        if (nuevaMarca.getMarcaEntrada() == null && nuevaMarca.getMarcaSalida() == null &&
            nuevaMarca.getMarcaSalidaAlmuerzo() == null && nuevaMarca.getMarcaEntradaAlmuerzo() == null) {
            throw new IllegalArgumentException("Debes ingresar al menos una marca (entrada o salida).");
        }

        boolean registradaEntrada = empleadoDAO.marcarEntrada(nuevaMarca);
        if (registradaEntrada) {
            nuevaMarca.setFechaMarca(fechaMarca);
            nuevaMarca.setMarcaEntrada(parseTime(request.getParameter("hora_entrada"))); // Obtén la hora de entrada desde el formulario
            nuevaMarca.setIdEmpleado(idEmpleado);
        } 
         // Asegúrate de que la marca de entrada esté registrada
            boolean registradaSalida = empleadoDAO.marcarSalida(nuevaMarca);
            if (registradaSalida) {
                System.out.println("Marca de salida registrada exitosamente.");
               
                nuevaMarca.setMarcaSalida(parseTime(request.getParameter("hora_salida"))); // Obtén la hora de entrada desde el formulario
                nuevaMarca.setIdEmpleado(idEmpleado);
            
        } 
        

    } catch (Exception e) {
        e.printStackTrace(); // Imprime el error si ocurre algún problema
        request.setAttribute("mensaje", "Error al registrar la marca: " + e.getMessage());
    }

    // Redirigir a la vista correspondiente
    request.getRequestDispatcher("vistaEmpleado/marcasEmpleado.jsp").forward(request, response);
}

//--------------------------------------------------------------------------
    private Time parseTime(String timeStr) {
        if (timeStr != null && !timeStr.isEmpty()) {
            // Asegúrate de que el formato de hora tenga segundos
            if (timeStr.length() == 5) {  // Formato hh:mm
                timeStr += ":00";  // Agregar segundos
            }
            try {
                return Time.valueOf(timeStr);
            } catch (IllegalArgumentException e) {
                // Manejo de error para formato de hora inválido
                throw new IllegalArgumentException("El formato de la hora es inválido: " + timeStr);
            }
        }
        return null; // O manejar el caso de hora nula según sea necesario
    }
//----------------------------------------------------------------------------
// Método para verificar si hay una marca de entrada registrada
public boolean verificarMarcaEntrada(int idEmpleado, Date fechaMarca) {
    // Obtener la lista de marcas para el día específico
    List<Marcas> marcas = empleadoDAO.obtenerMarcasPorDia(idEmpleado, fechaMarca);

    // Verificar si hay alguna marca de entrada registrada
    for (Marcas marca : marcas) {
        if (marca.getMarcaEntrada() != null) {
            // Si hay marca de entrada, devolver verdadero
            return true;
        }
    }
    // Si no hay marca de entrada, devolver falso
    return false;
}
//--------------------------------------------------------------------------
    @Override
    public String getServletInfo() {
        return "Servlet para mostrar los datos del empleado.";
    }
}
