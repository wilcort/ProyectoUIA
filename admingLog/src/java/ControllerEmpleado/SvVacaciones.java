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
import java.math.BigDecimal;
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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpSession;

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
        vista = "vistaVacaciones/adminVacaciones.jsp"; // Inicializar la variable vista
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        String verTodo = "vistaVacaciones/indexVacaciones.jsp";
        
         if (accion == null || accion.isEmpty() || "listar".equals(accion)) {
            List<Vacaciones> listarVacaciones = vacacionesDAO.listarVacacionesEmpleado();
            request.setAttribute("listarVacaciones", listarVacaciones);
            System.out.println("Número de registros en listarVacaciones: " + listarVacaciones.size()); // Verificación
        
        }else if ("Calculo_Vacaciones".equals(accion)) {
            calculo_Vacaciones(request, response);
            return;
        } else if("Realizar_Solicitud".equals(accion)) {
            realizar_Solicitud(request, response);
        } else if ("Ver_Solicitudes".equals(accion)) {
            ver_Solicitud_Vacaciones(request, response);
            return;
        } else {
            // Si la acción no coincide con ninguna de las anteriores, redirige a una página de error
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }
        
       request.getRequestDispatcher(verTodo).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("Calculo_Vacaciones".equals(accion)) {
            calculo_Vacaciones(request, response);
        } else if ("Realizar_Solicitud".equals(accion)) {
            realizar_Solicitud(request, response);
        } else if ("Ver_Solicitudes".equals(accion)) {
            ver_Solicitud_Vacaciones(request, response);
        }else if ("Actualizar".equals(accion)) {
            actualizarVacaciones(request, response);
        }else if ("Aprobar".equals(accion)) {
            aprobarVacaciones(request, response);
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

        // Guardar el id_empleado en la sesión
        request.getSession().setAttribute("id_empleado", idEmpleado);
        System.out.println("ID de Empleado almacenado en sesión: " + request.getSession().getAttribute("id_empleado"));

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
//-------------------------------------------------------------------------

    private void realizar_Solicitud(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener el ID del empleado y verificar que no sea null ni esté vacío
        String idEmpleadoStr = request.getParameter("id_empleado");
        if (idEmpleadoStr == null || idEmpleadoStr.isEmpty()) {
            request.setAttribute("error", "El ID del empleado es requerido.");
            request.getRequestDispatcher("vistaVacaciones/adminVacaciones.jsp").forward(request, response);
            return;
        }

        int idEmpleado;
        try {
            idEmpleado = Integer.parseInt(idEmpleadoStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID del empleado inválido.");
            request.getRequestDispatcher("vistaVacaciones/adminVacaciones.jsp").forward(request, response);
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
            request.getRequestDispatcher("vistaVacaciones/adminVacaciones.jsp").forward(request, response);
            return;
        }

        
        String diasVacacionesStr = request.getParameter("diasVacaciones");
        int diasVacacionesSolicitados;
        try {
            diasVacacionesSolicitados = Integer.parseInt(diasVacacionesStr);
        } catch (NumberFormatException e) {
            request.setAttribute("error", "Días de vacaciones inválidos.");
            request.getRequestDispatcher("vistaVacaciones/adminVacaciones.jsp").forward(request, response);
            return;
        }

        String comentario = request.getParameter("comentario");

        // Crear un Set<String> para los estados
        Set<String> estados = new HashSet<>();
        estados.add("pendiente"); // Agregar el estado inicial

        // Crear el objeto Vacaciones
        Vacaciones vacaciones = new Vacaciones(idEmpleado,
                fechaSolicitud,
                fechaInicio,
                fechaFin,
                diasVacacionesSolicitados,
                diasVacacionesSolicitados,
                estados,
                comentario,
                fechaInicio,
                idEmpleado,
                null);
        //
        
        // Solicitar la actualización
        boolean isUpdated = vacacionesDAO.solicitarVacaciones(idEmpleado, vacaciones);
        
        // Manejo de la respuesta
        if (isUpdated) {
            boolean notificacionEnviada = vacacionesDAO.enviarNotificacion(idEmpleado, "Solicitud de Vacaciones",
                    "El empleado " + idEmpleado + " ha solicitado vacaciones desde " + request.getParameter("fechaInicio")
                    + " hasta " + request.getParameter("fechaFin") + ".");

            if (notificacionEnviada) {
                request.setAttribute("success", "Solicitud de vacaciones enviada y notificación enviada con éxito.");
            } else {
                request.setAttribute("error", "Error al enviar la notificación.");
            }
        } else {
            request.setAttribute("error", "Error al enviar la solicitud de vacaciones.");
        }

        request.getRequestDispatcher("vistaVacaciones/solicitudEnviada.jsp").forward(request, response);
    }

//-----------------------------------------------------------------------------   
private void ver_Solicitud_Vacaciones(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int IdEmpleado = Integer.parseInt(request.getParameter("id_Empleado"));
        System.out.println("ID de empleado: " + IdEmpleado);

        // Obtener la lista de solicitudes de vacaciones para el empleado
        List<Vacaciones> listarVacacionesEmpleado = vacacionesDAO.listarVacacionesPendiente(IdEmpleado);

        // Si la lista es null, la inicializamos como lista vacía
        if (listarVacacionesEmpleado == null) {
            listarVacacionesEmpleado = new ArrayList<>();
        }

        System.out.println("Número de solicitudes de vacaciones: " + listarVacacionesEmpleado.size());

        // Configurar el atributo y redirigir al JSP
        request.setAttribute("listarVacaciones", listarVacacionesEmpleado);
        request.getRequestDispatcher("vistaVacaciones/verPendientes.jsp").forward(request, response);
    }
//-----------------------------------------------------------------------------------
//-----------------------APROBADO -----------------------------------
private void aprobarVacaciones(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
    
    String idVacacionStr = request.getParameter("id_Vacacion");
    String idEmpleadoStr = request.getParameter("id_Empleado");
    
    System.out.println("id empleado y id vacacion " + idVacacionStr + " " + idEmpleadoStr);

    if (idVacacionStr != null) {
        try {
            int idVacacion = Integer.parseInt(idVacacionStr);
            int diasVacacionesSolicitados = Integer.parseInt(request.getParameter("diasSolicitados"));
            int diasVacacionesTotal = Integer.parseInt(request.getParameter("diasTotales"));

            Vacaciones vacaciones = new Vacaciones();
            vacaciones.setIdVacacion(idVacacion);
            vacaciones.setDiasVacacionesSolicitados(diasVacacionesSolicitados);
            vacaciones.setDiasVacacionesTotal(diasVacacionesTotal);

            // Aprobar la vacación
            boolean aprobada = vacacionesDAO.aprobar_Vacaciones(idVacacion, vacaciones);

            if (aprobada) {
                // Si la vacación fue aprobada, llama a actualizarVacaciones
                actualizarVacaciones(request, response); // Llama al método para actualizar

                // Redirigir a la página de éxito
                response.sendRedirect("pages/vacacionesExito.jsp");
            } else {
                response.getWriter().write("Error al aprobar la vacación.");
            }
        } catch (NumberFormatException e) {
            response.getWriter().write("El ID de vacación o los días no son números válidos.");
            e.printStackTrace(); // Añade información sobre el error
        } catch (Exception e) {
            response.getWriter().write("Error inesperado: " + e.getMessage());
            e.printStackTrace(); // Añade información sobre el error
        }
    } else {
        response.getWriter().write("El ID de vacación no fue proporcionado.");
    }
}


//-------------------------------------------------    
//------------- ACTUALIZAR -----------------------------
    private void actualizarVacaciones(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idEmpleadoParam = request.getParameter("id_Empleado");
        System.out.println("ID Empleado recibido: " + idEmpleadoParam);

        String idVacacionStr = request.getParameter("id_Vacacion");
        System.out.println("ID de vacación recibido: " + idVacacionStr);
        

        if (idVacacionStr != null) {
            try {
                int idVacacion = Integer.parseInt(idVacacionStr);
                // Procesar la vacación con el ID recibido
            } catch (NumberFormatException e) {
                System.out.println("El ID de vacación no es válido: " + e.getMessage());
            }
        } else {
            System.out.println("No se recibió el ID de vacación.");
        }

        if (idEmpleadoParam != null) {
            try {
                // Convertimos el ID de empleado a un número entero
                int idEmpleado = Integer.parseInt(idEmpleadoParam);

                int idVacacionApr = Integer.parseInt(idVacacionStr);

                Vacaciones vacacionAprobada = vacacionesDAO.obtenerVacacionPorId(idVacacionApr);

                // Llamamos al método que devuelve la lista de IDs de vacaciones
                List<Integer> idsVacacionesConsulta = vacacionesDAO.obtenerIdVacacionConsulta(idEmpleado);
                
                System.out.println("id vacacion consul " + idsVacacionesConsulta );
                if (idsVacacionesConsulta.isEmpty()) {
                    System.out.println("No hay solicitudes de vacaciones en estado 'consulta' para este empleado.");
                } else {

                    for (Integer idVacacion : idsVacacionesConsulta) {
                        System.out.println("ID de Vacación en consulta: " + idVacacion);

                        boolean actualizado = vacacionesDAO.actualizaVacacionDesdeAprobada(idVacacion, vacacionAprobada);
                    }
                }

            } catch (NumberFormatException e) {
                System.out.println("El ID de empleado no es un número válido: " + e.getMessage());
            }
        } else {
            System.out.println("No se recibió el ID de empleado.");
        }
    }

//-----------------------------------------------------  
    
    
//------------------------------------------------------------
}
