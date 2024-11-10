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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.RequestDispatcher;
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
    private String vista02;
    private String vista03;
    private String vista04;
    private String vista05;

    @Override
    public void init() throws ServletException {
        super.init();
        vacacionesDAO = new VacacionesDAO();
        empleadoDAO = new EmpleadoDAO();
        colaboradorDAO = new ColaboradorDAO();
        vista = "vistaVacacion/adminVacaciones.jsp"; // Inicializar la variable vista
        vista02 = "vistaVacacion/verSolicitudesVacas.jsp";
        vista03 = "/vistaVacacion/indexVacaciones.jsp";
        vista04 = "/vistaVacacion/verSolicitudesAdmin.jsp";
        vista05 = "/vistaVacacion/vacacionesAprovadas.jsp";
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        System.out.println("Acción recibida en GET: " + accion);  // Depuración

        if ("Ver_Datos".equals(accion)) {
            verVacaciones(request, response);
        } else if ("Realizar_Solicitud".equals(accion)) {
            realizarSolicitud(request, response);
        } else if ("Ver_Solicitudes".equals(accion)) {
            verSolicitudesEmpleado(request, response);
        } else if ("Ver_Solicitudes_Admin".equals(accion)) {
            verSolicitudesAdmin(request, response);
        } else {
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
        System.out.println("Acción recibida en POST: " + accion);  // Traza de impresión

        if ("Ver_Datos".equals(accion)) {
            verVacaciones(request, response);
        } else if ("Realizar_Solicitud".equals(accion)) {
            realizarSolicitud(request, response);
        } else if ("Ver_Solicitudes".equals(accion)) {
            verSolicitudesEmpleado(request, response);
        }else if ("Ver_Solicitudes_Admin".equals(accion)) {
            verSolicitudesAdmin(request, response);
        }else if ("Ver_Vacaciones_Empleados".equals(accion)) {
            verVacacionesTodo(request, response);
        }else if ("Aprobar_Vacaciones".equals(accion)) {
            actualizarVacaciones(request, response);
        } else if ("Denegar_Vacaciones".equals(accion)) {
            denegarVacaciones(request, response);
        } else {
            response.sendRedirect("/WEB-INF/error.jsp");
        }
    }

//-----------------------------------------------
   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
//---------------- CALCULAR VACACIONES ------
//------------------------------------------------   
private void verVacaciones(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idUsuario = (Integer) request.getSession().getAttribute("id_usuario");
        Integer idEmpleado = empleadoDAO.obtenerIdEmpleado(idUsuario);

        request.getSession().setAttribute("id_empleado", idEmpleado);

        // Obtener y calcular vacaciones
        List<Vacaciones> vacacionesList = vacacionesDAO.mostrarVacaciones(idEmpleado);

        if (!vacacionesList.isEmpty()) {
            Vacaciones vacaciones = vacacionesList.get(0); // Tomar el primer registro de vacaciones

            // Enviar los datos del empleado al request
            request.setAttribute("nombreEmpleado", vacaciones.getColaborador().getNombre());
            request.setAttribute("apellidoEmpleado",
                    vacaciones.getColaborador().getApellido_1() + " " + vacaciones.getColaborador().getApellido_2());
            request.setAttribute("fechaContratacion", vacaciones.getColaborador().getFecha_contratacion());
            request.setAttribute("diasVacacionesTotal", vacaciones.getDiasVacacionesTotal());

            System.out.println("Días de Vacaciones Total para JSP: " + vacaciones.getDiasVacacionesTotal());
        }

        // Redirigir a la vista de vacaciones
        request.getRequestDispatcher(vista).forward(request, response);
    }
 //------------------------------------------------------------
private void realizarSolicitud(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idEmpleado = (Integer) request.getSession().getAttribute("id_empleado");
        if (idEmpleado == null) {
            response.sendRedirect("/WEB-INF/error.jsp");
            return; // Salir si el ID del empleado no está disponible
        }
        System.out.println("ID empleado: " + idEmpleado);

        // Obtener los datos del formulario
        String fechaInicioStr = request.getParameter("fechaInicio");
        String fechaFinStr = request.getParameter("fechaFin");
        int diasVacaciones;

        try {
            diasVacaciones = Integer.parseInt(request.getParameter("diasVacaciones"));
        } catch (NumberFormatException e) {
            System.out.println("Error al convertir días de vacaciones: " + e.getMessage());
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }

        // Convertir las fechas de String a java.util.Date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fechaInicio;
        Date fechaFin;

        try {
            fechaInicio = sdf.parse(fechaInicioStr);
            fechaFin = sdf.parse(fechaFinStr);

            // Validar que la fecha de inicio no sea posterior a la fecha de fin
            if (fechaInicio.after(fechaFin)) {
                System.out.println("La fecha de inicio no puede ser posterior a la fecha de fin.");
                response.sendRedirect("/WEB-INF/error.jsp");
                return;
            }
        } catch (ParseException e) {
            System.out.println("Error al parsear las fechas: " + e.getMessage());
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }

        // Crear un objeto Vacaciones
        Vacaciones vacaciones = new Vacaciones();
        vacaciones.setFechaSolicitud(new Date());  // Fecha actual como fecha de solicitud
        vacaciones.setFechaInicio(fechaInicio);
        vacaciones.setFechaFin(fechaFin);
        vacaciones.setDiasVacacionesSolicitados(diasVacaciones);
    
        // Llamar al método solicitarVacaciones
        boolean resultado = vacacionesDAO.solicitarVacaciones(idEmpleado, vacaciones);

        if (resultado) {
            // Redirigir a una página de éxito
            response.sendRedirect("/admingLog/pages/solicitudVacaExito.jsp");
        } else {
            // Redirigir a una página de error
            response.sendRedirect("/WEB-INF/error.jsp");
        }
    }

 //-------------------------------------        
    private void verSolicitudesEmpleado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idEmpleado = (Integer) request.getSession().getAttribute("id_empleado");
        if (idEmpleado == null) {
            response.sendRedirect("/WEB-INF/error.jsp");
            return; // Salir si el ID del empleado no está disponible
        }
        System.out.println("ID empleado: " + idEmpleado);
        // Obtener la lista de solicitudes de vacaciones para el empleado
        List<Vacaciones> listarVacacionesEmpleado = vacacionesDAO.listarVacacionesPendienteEmpeado(idEmpleado);

        // Si la lista es null, la inicializamos como lista vacía
        if (listarVacacionesEmpleado == null) {
            listarVacacionesEmpleado = new ArrayList<>();
        }

        System.out.println("Número de solicitudes de vacaciones: " + listarVacacionesEmpleado.size());

        // Configurar el atributo y redirigir al JSP
        request.setAttribute("listarVacaciones", listarVacacionesEmpleado);
        request.getRequestDispatcher(vista02).forward(request, response);
}

//----------------------------------------------

    private void verVacacionesTodo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        // Llamar al método para obtener la lista de vacaciones de todos los empleados
        List<Vacaciones> listarVacacionesEmpleado = vacacionesDAO.listarVacacionesEmpleados();

        // Imprimir la cantidad de registros recuperados (para verificar que se están obteniendo datos)
        System.out.println("Número de vacaciones de empleados: " + listarVacacionesEmpleado.size());

        // Pasar la lista de vacaciones al JSP como un atributo
        request.setAttribute("listarVacaciones", listarVacacionesEmpleado);
        

        request.getRequestDispatcher(vista03).forward(request, response);

    }
//----------------------------------------------------------------------------------------------------

    private void verSolicitudesAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar si el parámetro existe y no es null
        String idEmpleadoParam = request.getParameter("id_empleado");
        if (idEmpleadoParam == null) {
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }

        int idEmpleado = Integer.parseInt(idEmpleadoParam);
        System.out.println("ID empleado admin ***: " + idEmpleado);

        // Obtener la lista de solicitudes de vacaciones para el empleado
        List<Vacaciones> listarVacacionesEmpleado = vacacionesDAO.listarVacacionesPendienteEmpeado(idEmpleado);

        // Si la lista es null, la inicializamos como lista vacía
        if (listarVacacionesEmpleado == null) {
            listarVacacionesEmpleado = new ArrayList<>();
        }

        System.out.println("Número de solicitudes de vacaciones: " + listarVacacionesEmpleado.size());

        // Configurar el atributo y redirigir al JSP
        request.setAttribute("listarVacaciones", listarVacacionesEmpleado);
        request.getRequestDispatcher(vista04).forward(request, response);
    }

//-------------------------------------------------------------------------    

    private void actualizarVacaciones(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

         // Verificar si los parámetros existen y no son null
        String idEmpleadoParam = request.getParameter("id_empleado");
        String idVacacionStr = request.getParameter("id_Vacacion");
        
        System.out.println(" empleado consulta id " + idEmpleadoParam);
        System.out.println(" id vacac consulta id " + idVacacionStr);
        
        if (idEmpleadoParam == null || idVacacionStr == null) {
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }

        // Obtener los valores de los parámetros
        int idEmpleado = Integer.parseInt(idEmpleadoParam);
        int idVacacion = Integer.parseInt(idVacacionStr);
        System.out.println("ID empleado admin: " + idEmpleado + ", ID vacación: " + idVacacion);
        
        try {
 
            // Crear un objeto de Vacaciones con los detalles de aprobación
            Vacaciones vacacionAprobada = new Vacaciones();
            vacacionAprobada.setIdEmpleado(idEmpleado);
            vacacionAprobada.setFechaAprobacion(new java.sql.Date(System.currentTimeMillis()));
            vacacionAprobada.setDiasVacacionesSolicitados(vacacionAprobada.getDiasVacacionesSolicitados());
            vacacionAprobada.setDiasVacacionesTotal(vacacionAprobada.getDiasVacacionesTotal());

       
           // Llamar al DAO para aprobar y actualizar la vacación
            boolean isUpdated = vacacionesDAO.actualizaVacacionAprobar(idVacacion, vacacionAprobada);

    //----------------------- APROBADAS --------------        
            Vacaciones vacasAproba = vacacionesDAO.mostrarVacacionesAprobado(idVacacion);           
            int vacacionesAprobadas = vacasAproba.getDiasVacacionesSolicitados(); 
            System.out.println("aprovaadadDDDDDD " + vacacionesAprobadas);
            
    //----------------------------------------------------
            Vacaciones vacasConsulta = vacacionesDAO.mostrarVacacionesConsulta(idEmpleado);           
            int vacacionesConsulta = vacasConsulta.getDiasVacacionesTotal();
            System.out.println("aprovaadadDDDDDD consulta" + vacacionesConsulta);
            
    //----- ACTUALIZACION DE VACACIONES ------------------
    
        int idConsulta = vacacionesDAO.obtenerIdVacacionEnConsulta(idEmpleado);
            System.out.println(" ID CONSULTA CONSULTA " + idConsulta);
            
            if (vacacionesConsulta >= vacacionesAprobadas) {
                
                int vacasActulizadas = vacacionesConsulta - vacacionesAprobadas;
                Vacaciones consultaActualizada = new Vacaciones();
                consultaActualizada.setIdVacacion(idConsulta);
                consultaActualizada.setDiasVacacionesTotal(vacasActulizadas);
                consultaActualizada.setDiasVacacionesRestantes(vacasActulizadas);
                consultaActualizada.setFechaAprobacion(new java.sql.Date(System.currentTimeMillis()));

                boolean consultaActualizadaResult = vacacionesDAO.actualizarVacacionesConsulta(consultaActualizada);

            } else{
            
                // Si no hay suficientes días disponibles, denegar la vacación
                boolean actualizaVacacionDenegarResult = vacacionesDAO.actualizaVacacionDenegar(idVacacion, vacacionAprobada);
                request.setAttribute("mensaje", "Error: No hay suficientes días de vacaciones disponibles.");
                System.out.println("Error: No se pueden actualizar las vacaciones, días insuficientes.");
                request.getRequestDispatcher("/vistaVacacion/vacacionesDenegadas.jsp").forward(request, response);                
            return;
            }

    //--------------------------------------------------------        
    
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendRedirect("/WEB-INF/error.jsp");
        }
        
        // Redirigir a la vista de vacaciones
        request.getRequestDispatcher(vista05).forward(request, response);
    }

//-----------------------------------------------------------------
  
 //----------------------------------------------------------------- 

    private void denegarVacaciones(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar si los parámetros existen y no son null
        String idEmpleadoParam = request.getParameter("id_empleado");
        String idVacacionStr = request.getParameter("id_Vacacion");

        System.out.println("Empleado consulta id: " + idEmpleadoParam);
        System.out.println("ID vacación consulta id: " + idVacacionStr);

        if (idEmpleadoParam == null || idVacacionStr == null) {
            response.sendRedirect("");
            return;
        }

        try {
            // Obtener los valores de los parámetros
            int idEmpleado = Integer.parseInt(idEmpleadoParam);
            int idVacacion = Integer.parseInt(idVacacionStr);
            System.out.println("ID empleado admin: " + idEmpleado + ", ID vacación: " + idVacacion);

            // Crear un objeto de Vacaciones para denegar la solicitud
            Vacaciones vacacionDenegada = new Vacaciones();
            vacacionDenegada.setIdEmpleado(idEmpleado);
            vacacionDenegada.setFechaAprobacion(new java.sql.Date(System.currentTimeMillis()));

            // Llamar al DAO para denegar y actualizar la vacación
            boolean isDenied = vacacionesDAO.actualizaVacacionDenegar(idVacacion, vacacionDenegada);

            // Validar si la operación fue exitosa
            if (isDenied) {
                request.setAttribute("mensaje", "La solicitud de vacaciones ha sido denegada exitosamente.");
                request.getRequestDispatcher("/vistaVacacion/vacacionesDenegadas.jsp").forward(request, response);
            } else {
                request.setAttribute("mensaje", "Error: No se pudo denegar la solicitud de vacaciones.");
                request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            }

        }  catch (Exception e) {
            System.err.println("Error al intentar denegar las vacaciones: " + e.getMessage());
            response.sendRedirect("/WEB-INF/error.jsp");
        }
    }

    
  //---------------------------------------------------------------------------------
}