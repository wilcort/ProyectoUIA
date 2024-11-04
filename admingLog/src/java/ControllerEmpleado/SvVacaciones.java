/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerEmpleado;

import Model.Colaborador;
import Model.ColaboradorDAO;
import Model.VacacionesDAO;
import ModelEmpleado.EmpleadoDAO;
import ModelEmpleado.Vacaciones;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
        vista = "vistaVacacion/adminVacaciones.jsp"; // Inicializar la variable vista
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
         String accion = request.getParameter("accion");

        if ("Ver_Datos".equals(accion)) {
            verVacaciones(request, response);
        }else if("Realizar_Solicitud".equals(accion)) {
            realizarSolicitud(request, response);
        }else {
            // Si la acción no coincide con ninguna de las anteriores, redirige a una página de error
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }
        
       request.getRequestDispatcher(vista).forward(request, response);
    }
//---------------------------------------------------------
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if ("Ver_Datos".equals(accion)) {
            verVacaciones(request, response);
        }
    }

   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
//---------------- CALCULAR VACACIONES ------
//------------------------------------------------   
private void verVacaciones(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener el ID de usuario de la sesión
        Integer idUsuario = (Integer) request.getSession().getAttribute("id_usuario");
        System.out.println("ID de usuario: " + idUsuario);

        // Obtener el ID del empleado basado en el ID de usuario
        Integer idEmpleado = empleadoDAO.obtenerIdEmpleado(idUsuario);
        System.out.println("ID empleado: " + idEmpleado);

        // Guardar el id_empleado en la sesión
        request.getSession().setAttribute("id_empleado", idEmpleado);

        // Obtener la fecha de contratación del empleado
        Date fechaContratacion = vacacionesDAO.obtenerFechaContratacion(idEmpleado); // Método a implementar
        System.out.println("Fecha de contratación: " + fechaContratacion);

        // Calcular y guardar las vacaciones
        vacacionesDAO.calcularYGuardarVacaciones(fechaContratacion, idEmpleado);

        
        // Redirigir a la vista de vacaciones
        request.getRequestDispatcher(vista).forward(request, response);
    }

 //------------------------------------------------------------

    private void realizarSolicitud(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
    
    
    }

    
}
