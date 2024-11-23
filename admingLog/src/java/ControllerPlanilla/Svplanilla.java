/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerPlanilla;

import Model.Colaborador;
import Model.ColaboradorDAO;
import ModelEmpleado.EmpleadoDAO;
import ModelIncapacidad.IncapacidadDAO;
import ModelPanilla.Planilla;
import ModelPanilla.PlanillaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.IntStream;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dell
 */
@WebServlet(name = "Svplanilla", urlPatterns = {"/Svplanilla"})
public class Svplanilla extends HttpServlet {

    private PlanillaDAO planillaDAO;
    private String vista;
    private ColaboradorDAO colaboradorDAO;
    private IncapacidadDAO incapacidadDAO;
    private EmpleadoDAO empleadoDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        planillaDAO = new PlanillaDAO();
        PlanillaDAO planillaDAO = new PlanillaDAO();
        incapacidadDAO = new IncapacidadDAO();
        empleadoDAO = new EmpleadoDAO();

        vista = "vistaPlanilla/generarPlanilla.jsp";
    }

//----------------------------------------------------------    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        System.out.println("Acción recibida en GET: " + accion);

        if ("Mostrar_Formulario".equals(accion)) {
            // Redirige al formulario de generación de planilla
            request.getRequestDispatcher("vistaPlanilla/generarPlanilla.jsp").forward(request, response);
        } else if ("Listar_Empleados".equals(accion)) {
            listarEmpleados(request, response);
        } else {
            // Manejar otras acciones o redirigir a un error
            response.sendRedirect("/WEB-INF/error.jsp");
        }
    }

//------------------------------------------------------------------------  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        System.out.println("Acción recibida en POST: " + accion);
        if ("Generar_Planilla_Quincena".equals(accion)) {
            GenerarPlanillaQuincena(request, response);
        } else if ("Generar_Planilla_Mensual".equals(accion)) {
            GenerarPlanillaMensual(request, response);
        }else if ("Planilla_Empleado".equals(accion)) {
            planillaEmpleado(request, response);
        } else if ("Ver_Planilla_Empleado".equals(accion)) {
            verPlanillaEmpleado(request, response);
        } else if ("Ver_Detalle_Planilla".equals(accion)) {
            verDetallePlanilla(request, response);
        } else {
            response.sendRedirect("/WEB-INF/error.jsp");
        }

    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
//------------------------------------------------------------

    private void GenerarPlanillaQuincena(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String mesStr = request.getParameter("mes");
        String anioStr = request.getParameter("anio");

        if (mesStr == null || anioStr == null) {
            response.getWriter().println("Mes y año son obligatorios.");
            return;
        }

        try {
            int mesSeleccionado = Integer.parseInt(mesStr);
            int anioSeleccionado = Integer.parseInt(anioStr);

            planillaDAO.generarPlanillaQuincenalParaTodos(mesSeleccionado, anioSeleccionado);

            // Redirigir a exitoplanilla.jsp
            response.sendRedirect("pages/exitoPlanilla.jsp");
        } catch (NumberFormatException e) {
            response.getWriter().println("Mes y año deben ser números válidos.");
            e.printStackTrace();
        } catch (Exception e) {
            response.getWriter().println("Ocurrió un error al generar la planilla.");
            e.printStackTrace();
        }
    }
    //--------------------------------------------------------------------------------

    private void GenerarPlanillaMensual(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener los datos del formulario
        String mesStr = request.getParameter("mes");
        String anioStr = request.getParameter("anio");

        if (mesStr == null || anioStr == null) {
            response.getWriter().println("Mes y año son obligatorios.");
            return;
        }

        try {

            int mesSeleccionado = Integer.parseInt(mesStr);
            int anioSeleccionado = Integer.parseInt(anioStr);

            planillaDAO.generarPlanillaMensualParaTodos(mesSeleccionado, anioSeleccionado);

            // Redirigir a exitoplanilla.jsp
            response.sendRedirect("pages/exitoPlanilla.jsp");
        } catch (NumberFormatException e) {
            response.getWriter().println("Mes y año deben ser números válidos.");
            e.printStackTrace();
        } catch (Exception e) {
            response.getWriter().println("Ocurrió un error al generar la planilla.");
            e.printStackTrace();
        }
    }

//-----------------------------------------------------------------------------
    private void listarEmpleados(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println(" HOLA  Empleados");

        // Llamar al método para obtener la lista de todos los empleados
        List<Colaborador> listarEmpleados = incapacidadDAO.listarEmpleados();

        // Imprimir la cantidad de registros recuperados 
        System.out.println("Número de incapacidaees de empleados: " + listarEmpleados.size());

        // Pasar la lista de vacaciones al JSP como un atributo
        request.setAttribute("listarTodosEmpleados", listarEmpleados);

        request.getRequestDispatcher(vista).forward(request, response);

    }

//----------------------------------------------------------------

    private void planillaEmpleado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener id_empleado del formulario
        Integer idEmpleado = Integer.valueOf(request.getParameter("id_empleado"));

        // Almacenar el id_empleado en la sesión
        request.getSession().setAttribute("id_empleado", idEmpleado);

        System.out.println("idEmpleado almacenado en sesión: " + idEmpleado);

        // Obtener la lista de planillas
        List<Planilla> listarPlanillas = planillaDAO.verPlanillaEmpleado(idEmpleado);
        System.out.println("Número de reportes de planilla del empleado: " + listarPlanillas.size());

        // Verificar si la lista está vacía
        if (listarPlanillas == null || listarPlanillas.isEmpty()) {
            System.out.println("No hay reportes de planilla para el empleado.");
        }

        // Pasar la lista de planillas al JSP
        request.setAttribute("listarPlanillas", listarPlanillas);

        // Redirigir al JSP que muestra la lista de reportes de planilla
        request.getRequestDispatcher("vistaPlanilla/verPlanillas.jsp").forward(request, response);
    }
//---------------------------------------------------------------------------------
    
    private void verPlanillaEmpleado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idUsuarioStr = request.getParameter("id_usuario");
        Integer idUsuario = null;

        try {
            idUsuario = Integer.parseInt(idUsuarioStr);
        } catch (NumberFormatException e) {
            System.out.println("Error: id_usuario no es un número válido.");
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }

        // Obtener el id del empleado asociado al usuario
        Integer idEmpleado = empleadoDAO.obtenerIdEmpleado(idUsuario);
        if (idEmpleado == null) {
            System.out.println("Error: No se encontró un empleado para el usuario " + idUsuario);
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }

        // Almacenar el idEmpleado en la sesión
        request.getSession().setAttribute("id_empleado", idEmpleado);

        // Obtener la lista de planillas
        List<Planilla> listarPlanillas = planillaDAO.verPlanillaEmpleado(idEmpleado);
        System.out.println("Número de reportes de planilla del empleado: " + (listarPlanillas != null ? listarPlanillas.size() : 0));

        // Verificar si la lista está vacía
        if (listarPlanillas == null || listarPlanillas.isEmpty()) {
            System.out.println("No hay reportes de planilla para el empleado.");
        }

        // Pasar la lista de planillas al JSP
        request.setAttribute("listarPlanillas", listarPlanillas);

        // Redirigir al JSP que muestra la lista de reportes de planilla
        request.getRequestDispatcher("vistaPlanilla/verPlanillaEmp.jsp").forward(request, response);
    }
    
//--------------------------------------

    private void verDetallePlanilla(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idPlanillaStr = request.getParameter("id_planilla");
        Integer idPlanilla = null;

        try {
            idPlanilla = Integer.parseInt(idPlanillaStr);
        } catch (NumberFormatException e) {
            System.out.println("Error: id_planilla no es un número válido.");
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }

        System.out.println("id planilla: " + idPlanilla);

        // Llamamos al método detallePlanilla() para obtener los detalles de la planilla
        Planilla planilla = planillaDAO.detallePlanilla(idPlanilla);

        if (planilla != null) {
            // Si la planilla fue encontrada, la agregamos al request para mostrarla en la JSP
            request.setAttribute("planilla", planilla);
            // Redirigimos a la página JSP donde se mostrarán los detalles
            request.getRequestDispatcher("vistaPlanilla/verDetallePlanilla.jsp").forward(request, response);
        } else {
            // Si no se encuentra la planilla, redirigimos a una página de error
            response.sendRedirect("/WEB-INF/error.jsp");
        }
    }
    
//--------------------------------------------
}
