/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerEmpleado;

import Model.Colaborador;
import Model.ColaboradorDAO;
import ModelEmpleado.EmpleadoDAO;
import ModelEmpleado.Vacaciones;
import ModelEmpleado.VacacionesDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;


@WebServlet(name = "SvVacaciones", urlPatterns = {"/SvVacaciones"})
public class SvVacaciones extends HttpServlet {

    private VacacionesDAO vacacionesDAO;
    private EmpleadoDAO empleadoDAO;
    private ColaboradorDAO colaboradorDAO;
    private String vista;
    
    @Override
    public void init() throws ServletException {
        super.init();
        vacacionesDAO = new VacacionesDAO();
        empleadoDAO = new EmpleadoDAO();
        colaboradorDAO = new ColaboradorDAO();
        vista = "vistaVacaciones/vacaciones.jsp"; // Inicializar la variable vista
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       String accion = request.getParameter("accion");
       
        if ("Calculo_Vacaciones".equals(accion)) {
            calculo_Vacaciones(request, response);
            return;
        }else {
            // Si la acción no coincide con ninguna de las anteriores, redirige a una página de error
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("Calculo_Vacaciones".equals(accion)) {
            calculo_Vacaciones(request, response);
        } else if ("solicitar_Vacaciones".equals(accion)) {
            solicitar_Vacaciones(request, response);
        } else {
            response.sendRedirect("/WEB-INF/error.jsp");
        }

    }

  //--------------------------------------------------------------
    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
//---------------------------------------------------
    private void calculo_Vacaciones(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener el ID de usuario de la sesión
        Integer idUsuario = (Integer) request.getSession().getAttribute("id_usuario");
        System.out.println("ID de usuario: " + idUsuario);

        // Obtener el ID del empleado basado en el ID de usuario
        Integer idEmpleado = empleadoDAO.obtenerIdEmpleado(idUsuario);
        System.out.println("ID empleado: " + idEmpleado);

        Vacaciones vacacionesDisponibles = null; // Inicializa el objeto vacaciones

        if (idEmpleado != null) {
            vacacionesDisponibles = vacacionesDAO.calcular(idEmpleado);
            List<Vacaciones> listaVacaciones = vacacionesDAO.obtenerTodasVacaciones(idEmpleado);
            
             // Verificar los datos obtenidos
    System.out.println("Vacaciones Disponibles: " + vacacionesDisponibles);
    System.out.println("Lista de Vacaciones: " + listaVacaciones);
            
            // Establecer la lista de vacaciones como atributo para la vista
            request.setAttribute("listaVacaciones", listaVacaciones);
        }

        request.setAttribute("vacacionesDisponibles", vacacionesDisponibles);
        request.getRequestDispatcher(vista).forward(request, response);
    }


//----------------------------------------------------

//-----------------------------------------------------------    

private void solicitar_Vacaciones(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    String idEmpleadoStr = request.getParameter("id_empleado");

    // Validar el idEmpleadoStr
    if (idEmpleadoStr == null || idEmpleadoStr.trim().isEmpty()) {
        request.setAttribute("error", "El ID de empleado no puede estar vacío.");
        request.getRequestDispatcher("vistaVacaciones/vacaciones.jsp").forward(request, response);
        return;
    }

    int idEmpleado;
    try {
        idEmpleado = Integer.parseInt(idEmpleadoStr);
    } catch (NumberFormatException e) {
        request.setAttribute("error", "El ID de empleado debe ser un número válido.");
        request.getRequestDispatcher("vistaVacaciones/vacaciones.jsp").forward(request, response);
        return;
    }

    SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
    Date fechaSolicitud = new Date(); // Fecha actual
    Date fechaInicio = null;
    Date fechaFin = null;

    try {
        // Convertir las fechas desde el formulario a Date
        fechaInicio = formatoFecha.parse(request.getParameter("fechaInicio"));
        fechaFin = formatoFecha.parse(request.getParameter("fechaFin"));
    } catch (ParseException e) {
        e.printStackTrace();
        request.setAttribute("error", "Formato de fecha inválido.");
        request.getRequestDispatcher("vistaVacaciones/vacaciones.jsp").forward(request, response);
        return;
    }

    String diasVacacionesStr = request.getParameter("diasVacaciones");
    int diasVacacionesSolicitados;
    try {
        diasVacacionesSolicitados = Integer.parseInt(diasVacacionesStr);
    } catch (NumberFormatException e) {
        request.setAttribute("error", "Días de vacaciones inválidos.");
        request.getRequestDispatcher("vistaVacaciones/vacaciones.jsp").forward(request, response);
        return;
    }

    String comentario = request.getParameter("comentario");

    // Crear el objeto Vacaciones y solicitar la actualización
    Vacaciones vacaciones = new Vacaciones(idEmpleado, 
            fechaSolicitud, fechaInicio, fechaFin, 
            diasVacacionesSolicitados, diasVacacionesSolicitados,
            null, comentario, fechaInicio, idEmpleado);
    
    boolean isUpdated = vacacionesDAO.solicitarVacaciones(idEmpleado, vacaciones);

    // Mensaje de consola y redireccionamiento
    if (isUpdated) {
        System.out.println("Solicitud de vacaciones guardada para el empleado ID: " + idEmpleado);
        System.out.println("Detalles de la solicitud: ");
        System.out.println("Fecha de solicitud: " + formatoFecha.format(fechaSolicitud));
        System.out.println("Fecha de inicio: " + formatoFecha.format(fechaInicio));
        System.out.println("Fecha de fin: " + formatoFecha.format(fechaFin));
        System.out.println("Días solicitados: " + diasVacacionesSolicitados);
        System.out.println("Comentario: " + comentario);
        request.setAttribute("success", "Solicitud de vacaciones guardada con éxito.");
    } else {
        System.out.println("No se pudo guardar la solicitud de vacaciones para el empleado ID: " + idEmpleado);
        request.setAttribute("error", "No se pudo guardar la solicitud de vacaciones.");
    }

    request.getRequestDispatcher("vistaVacaciones/vacaciones.jsp").forward(request, response);
}


 //---------------------------------------------------------------------  
 //---------------------------- ENVIAR SOLICITUD -----------------------------
    
  
    
    
 //-----------------------------------------------------------------------------   
}

