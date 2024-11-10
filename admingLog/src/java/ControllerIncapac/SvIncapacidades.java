/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerIncapac;

import Model.Colaborador;
import Model.ColaboradorDAO;
import Model.VacacionesDAO;
import ModelEmpleado.EmpleadoDAO;
import ModelEmpleado.Vacaciones;
import ModelIncapacidad.IncapacidadDAO;
import ModelIncapacidad.IncapacidadEnfComun;
import ModelIncapacidad.IncapacidadMaternidad;
import ModelIncapacidad.Incapacidades;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Blob;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.sql.SQLException;


/**
 *
 * @author Dell
 */
@WebServlet(name = "SvIncapacidades", urlPatterns = {"/SvIncapacidades"})
@MultipartConfig
public class SvIncapacidades extends HttpServlet {

    private IncapacidadDAO incapacidadDAO;
    private EmpleadoDAO empleadoDAO;
    private ColaboradorDAO colaboradorDAO;
    private String vista; // Definir la variable vista como un campo de clase
    private String vista01;
    
    @Override
    public void init() throws ServletException {
        super.init();
        incapacidadDAO = new IncapacidadDAO();
        empleadoDAO = new EmpleadoDAO();
        vista = "adminLog/vistasLog/empleado.jsp"; // Inicializar la variable vista
        vista01 = "admingLog/vistaIncapacidad/formularioMaternidad.jsp";
                
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        
        String accion = request.getParameter("accion");
        System.out.println("Acción recibida en GET: " + accion);  // Depuración

        if ("Ver_Solicitudes".equals(accion)) {
            verSolicitudes(request, response);
        } else if ("Ver_Solicitudes_Incap".equals(accion)) {
            verSolicitudesIncap(request, response);
        } else if ("Ver_Incap_Admin".equals(accion)) {
            VerIncapAdmin(request, response);
        } else if ("Ver_documentos".equals(accion)) {
            verDocumento(request, response);
        }  else {
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }
        
    }
//----------------------------------------------------------------

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        System.out.println("Acción recibida en POST: " + accion);  // Depuración
        
        String detalle = request.getParameter("detalle");
        System.out.println("Detalle: " + detalle); // Depuració
        
        if ("Ver_Formulario".equals(accion)) {
            verFormulario(request, response);
        } else if ("Realizar_Solicitud".equals(accion)) {
            realizarSolicitud(request, response);
        }else if ("Enviar_Maternidad".equals(accion)) {
            enviarmaternidad(request, response);
        }else if ("Enviar_EnferComun".equals(accion)) {
            enviarEnferComun(request, response);
        }else if ("Ver_Solicitudes".equals(accion)) {
            verSolicitudes(request, response);
        }else if ("Ver_Solicitudes_Incap".equals(accion)) {
            verSolicitudesIncap(request, response);
        }else if ("Ver_Incap_Admin".equals(accion)) {
            VerIncapAdmin(request, response);
        }else if ("Ver_documentos".equals(accion)) {
            verDocumento(request, response);
        }else if ("Aprobar_Incapacidad".equals(accion)) {
            actualizarIncapacidad(request, response);
        } else if ("Denegar_Incapacidad".equals(accion)) {
            denegarIncapacidad(request, response);
        } else {
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }

    }
//---------------------------------------------------------------------

    @Override
    public String getServletInfo() {
        return "Short description";
    }
//--------------------------------------------------------------------

    private void verFormulario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener el id_usuario del formulario
        String idUsuarioStr = request.getParameter("id_usuario");
        Integer idUsuario = null;

        // Validar y convertir el id_usuario
        if (idUsuarioStr != null && !idUsuarioStr.isEmpty()) {
            try {
                idUsuario = Integer.parseInt(idUsuarioStr);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir id_usuario: " + e.getMessage());
                response.sendRedirect("/WEB-INF/error.jsp");
                return;
            }
        }
        Integer idEmpleado = empleadoDAO.obtenerIdEmpleado(idUsuario);
        request.getSession().setAttribute("id_empleado", idEmpleado);

        System.out.println("empleado y usuario " + idEmpleado + " " + idUsuario);

        // Asignar el idUsuario a la sesión 
        request.getSession().setAttribute("id_empleado", idEmpleado);

        request.getRequestDispatcher("/vistaIncapacidad/formularioIncap.jsp").forward(request, response);
    }

//--------------------------------------------------------------------------
    private void realizarSolicitud(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idEmpleado = (Integer) request.getSession().getAttribute("id_empleado");
        if (idEmpleado == null) {
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }

        // Obtener los datos del formulario
        String tipoIncapacidad = request.getParameter("tipo_incapacidad");
        String fechaInicioStr = request.getParameter("fecha_inicio");
        String fechaFinStr = request.getParameter("fecha_fin");
        int diasIncapacidad;

        try {
            diasIncapacidad = Integer.parseInt(request.getParameter("dias_Incapacidad"));
        } catch (NumberFormatException e) {
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fecha_inicio, fecha_fin;

        try {
            fecha_inicio = sdf.parse(fechaInicioStr);
            fecha_fin = sdf.parse(fechaFinStr);
            if (fecha_inicio.after(fecha_fin)) {
                response.sendRedirect("/WEB-INF/error.jsp");
                return;
            }
        } catch (ParseException e) {
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }

        Incapacidades incapacidades = new Incapacidades();
        incapacidades.setMotivo(tipoIncapacidad);
        incapacidades.setFechaInicio(fecha_inicio);
        incapacidades.setFechaFin(fecha_fin);
        incapacidades.setDiasIncapacidad(diasIncapacidad);

        // Obtener el ID de la incapacidad generada
        Integer idIncapacidadGenerado = incapacidadDAO.solicitarIncapacidad(incapacidades, idEmpleado);

        if (idIncapacidadGenerado != null) {
            // Guardar el ID en la sesión
            request.getSession().setAttribute("id_incapacidad", idIncapacidadGenerado);

            if ("maternidad".equals(tipoIncapacidad)) {
                response.sendRedirect("vistaIncapacidad/formularioMaternidad.jsp");
            } else if ("enfermedad".equals(tipoIncapacidad)) {
                response.sendRedirect("vistaIncapacidad/formularioEnfComun.jsp");
            } else {
                response.sendRedirect("/WEB-INF/error.jsp");
            }
        }
    }
//-----------------------------------------------------------------

    private void enviarmaternidad(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idEmpleado = (Integer) request.getSession().getAttribute("id_empleado");
        Integer idIncapacidad = (Integer) request.getSession().getAttribute("id_incapacidad");

        System.out.println("ID Empleado: " + idEmpleado);
        System.out.println("ID Incapacidad generada: " + idIncapacidad);

        // Obtener los datos del formulario
        String detalle = request.getParameter("detalle");
        int semanasIncapa;

        try {
            semanasIncapa = Integer.parseInt(request.getParameter("semanas_de_gestacion"));
        } catch (Exception e) {
            System.out.println("Error al convertir semanas de gestación: " + e.getMessage());
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }

        // Obtener el archivo adjunto
        Part filePart = request.getPart("documento");
        InputStream documentoInputStream = null;
        if (filePart != null && filePart.getSize() > 0) {
            documentoInputStream = filePart.getInputStream();
        } else {
            System.out.println("No se adjuntó ningún documento.");
        }

        // Crear el objeto IncapacidadMaternidad y asignar los valores
        IncapacidadMaternidad incapacidad = new IncapacidadMaternidad();
        incapacidad.setDetalle(detalle);
        incapacidad.setSemanasDeGestacion(semanasIncapa);
        incapacidad.setDocumento(documentoInputStream); // Asigna el InputStream al objeto

        // Llamar al método DAO para insertar la incapacidad de maternidad
        boolean resultado = incapacidadDAO.insertarIncapacidadMaternidad(incapacidad, idIncapacidad);

        if (resultado) {
            response.sendRedirect("pages/exitoIncapacidades.jsp");
            System.out.println("enviado");
        } else {
            response.sendRedirect("/WEB-INF/error.jsp");
        }
    }
//------------------------------------------------

    private void enviarEnferComun(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idEmpleado = (Integer) request.getSession().getAttribute("id_empleado");
        Integer idIncapacidad = (Integer) request.getSession().getAttribute("id_incapacidad");

        System.out.println("ID Empleado: " + idEmpleado);
        System.out.println("ID Incapacidad generada: " + idIncapacidad);

        // Obtener los datos del formulario
        String detalle = request.getParameter("detalle");
      
        // Obtener el archivo adjunto
        Part filePart = request.getPart("documento");
        InputStream documentoInputStream = null;
        if (filePart != null && filePart.getSize() > 0) {
            documentoInputStream = filePart.getInputStream();
        } else {
            System.out.println("No se adjuntó ningún documento.");
        }

        // Crear el objeto IncapacidadCoun y asignar los valores
        IncapacidadEnfComun incapacidad = new  IncapacidadEnfComun();
        incapacidad.setDetalle(detalle);
        incapacidad.setDocumento(documentoInputStream); // Asigna el InputStream al objeto

        // Llamar al método DAO para insertar la incapacidad de maternidad
        boolean resultado = incapacidadDAO.insertarIncapacidadEnfermedadComun(incapacidad, idIncapacidad);

        if (resultado) {
            response.sendRedirect("pages/exitoIncapacidades.jsp");
            System.out.println("enviado");
        } else {
            response.sendRedirect("/WEB-INF/error.jsp");
        }
    }
    
//--------------------------------------------------------------------------    

    private void verSolicitudes(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idEmpleado = (Integer) request.getSession().getAttribute("id_empleado");

        System.out.println("ID Empleado: " + idEmpleado);

        List<Incapacidades> listarIncapEmpleado  = incapacidadDAO.listarIncapEmpleado(idEmpleado);

        // Si la lista es null, la inicializamos como lista vacía
        if (listarIncapEmpleado  == null) {
            listarIncapEmpleado  = new ArrayList<>();
        }

        System.out.println("Número de solicitudes de vacaciones: " + listarIncapEmpleado .size());

        // Configurar el atributo y redirigir al JSP
        request.setAttribute("listarIncapEmpleado", listarIncapEmpleado);


        request.getRequestDispatcher("vistaIncapacidad/listarIncapEmpleado.jsp").forward(request, response);

    }

    //----------------------------------------------

    private void verSolicitudesIncap(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        System.out.println(" HOLA");
        
         // Llamar al método para obtener la lista de incapacidades de todos los empleados
         List<Colaborador> listarEmpleados = incapacidadDAO.listarEmpleados();
        // Imprimir la cantidad de registros recuperados (para verificar que se están obteniendo datos)
        System.out.println("Número de incapacidaees de empleados: " + listarEmpleados.size());

        // Pasar la lista de vacaciones al JSP como un atributo
        request.setAttribute("listarIncapEmpleado", listarEmpleados);
        
      
    
      request.getRequestDispatcher("vistaIncapacidad/listarTodoIncap.jsp").forward(request, response);

    }

    private void VerIncapAdmin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        System.out.println("hola");
        String idEmpleadoParam = request.getParameter("id_empleado");
        if (idEmpleadoParam == null) {
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }

        int idEmpleado = Integer.parseInt(idEmpleadoParam);
        System.out.println("ID empleado admin ***: " + idEmpleado);
        
       
        // Obtener la lista de solicitudes de vacaciones para el empleado
        List<Incapacidades> listarIncapEmpleado = incapacidadDAO.listarIncapEmpleado(idEmpleado);

        // Si la lista es null, la inicializamos como lista vacía
        if (listarIncapEmpleado == null) {
            listarIncapEmpleado = new ArrayList<>();
        }

        System.out.println("Número de solicitudes de vacaciones: " + listarIncapEmpleado.size());

        // Configurar el atributo y redirigir al JSP
        request.setAttribute("listarIncapEmpleado", listarIncapEmpleado);
        request.getRequestDispatcher("vistaIncapacidad/verSolicitudIncap.jsp").forward(request, response);
    
    }
//--------------------------------------

    // Método para manejar la solicitud HTTP y devolver el documento
    private void verDocumento(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idIncapacidadParam = request.getParameter("id_incapacidad");

        // Verificar que se ha proporcionado un id_incapacidad
        if (idIncapacidadParam != null && !idIncapacidadParam.isEmpty()) {
            try {
                // Convertir el id_incapacidad a entero
                int idIncapacidad = Integer.parseInt(idIncapacidadParam);

                // Obtener el documento desde el DAO
                InputStream documento = incapacidadDAO.obtenerDocumento(idIncapacidad);

                // Verificar si el documento fue encontrado
                if (documento != null) {
                    // Leer los primeros 5 bytes para obtener el encabezado del archivo
                    byte[] bufferHeader = new byte[5];
                    int bytesRead = documento.read(bufferHeader, 0, 5);
                    if (bytesRead != -1) {
                        String fileHeader = new String(bufferHeader);
                        System.out.println("File Header: " + fileHeader);  // Mostrar el encabezado en la consola
                    }

                    // Establecer el tipo de contenido y el encabezado para mostrar como PDF
                    response.setContentType("application/pdf");
                    response.setHeader("Content-Disposition", "inline; filename=documento_" + idIncapacidad + ".pdf");

                    // Leer y enviar el archivo en bloques
                    try (OutputStream out = response.getOutputStream()) {
                        byte[] buffer = new byte[4096];
                        int bytes;

                        // Leer el documento completo y escribir en la respuesta
                        while ((bytes = documento.read(buffer)) != -1) {
                            out.write(buffer, 0, bytes);
                        }
                        out.flush();  // Asegúrate de hacer flush
                        System.out.println("Documento enviado correctamente.");
                    } catch (IOException e) {
                        // Manejo de errores de escritura en el OutputStream
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al escribir el documento");
                    }

                    documento.close(); // Cerrar el InputStream
                } else {
                    // Si no se encuentra el documento, redirigir a la página de error personalizada
                    request.setAttribute("errorMessage", "Documento no encontrado");
                    request.getRequestDispatcher("pages/incapacidadError.jsp").forward(request, response);
                }

            } catch (NumberFormatException e) {
                // Si el id_incapacidad no es un número válido
                request.setAttribute("errorMessage", "ID de incapacidad no válido");
                request.getRequestDispatcher("pages/incapacidadError.jsp").forward(request, response);
            } catch (SQLException e) {
                // Si hay un error en la base de datos
                e.printStackTrace();
                request.setAttribute("errorMessage", "Error al recuperar el documento desde la base de datos");
                request.getRequestDispatcher("pages/incapacidadError.jsp").forward(request, response);
            }
        } else {
            // Si no se proporciona el parámetro id_incapacidad
            request.setAttribute("errorMessage", "ID de incapacidad no proporcionado");
            request.getRequestDispatcher("pages/incapacidadError.jsp").forward(request, response);
        }
    }


 //--------------------------------------------   

    private void actualizarIncapacidad(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar si los parámetros existen y no son null
        String idEmpleadoParam = request.getParameter("id_empleado");
        String idIncapacidadStr = request.getParameter("id_incapacidad");

        System.out.println(" empleado consulta id " + idEmpleadoParam);
        System.out.println(" id vacac consulta id " + idIncapacidadStr);

        // Verificar si los parámetros existen y no son null
        if (idEmpleadoParam == null || idIncapacidadStr == null) {
            // Si alguno de los parámetros es null, devolver error o redirigir a una página de error
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos.");
            return;
        }

        try {
            // Convertir los parámetros a enteros
            int idEmpleado = Integer.parseInt(idEmpleadoParam);
            int idIncapacidad = Integer.parseInt(idIncapacidadStr);

            // Crear el objeto Incapacidades y establecer el ID de incapacidad
            Incapacidades incapadAprobada = new Incapacidades();
            incapadAprobada.setIdIncapacidad(idIncapacidad);

            // Llamar al método del DAO para aprobar la incapacidad
            boolean incapadAprobadaResult = incapacidadDAO.incapacidadAprobada(idIncapacidad, incapadAprobada);

            // Verificar si la actualización fue exitosa
            if (incapadAprobadaResult) {
                request.getRequestDispatcher("vistaIncapacidad/incapAprobado.jsp").forward(request, response);;
                System.out.println("exito");
                 response.sendRedirect(""); 
            } else {
                response.sendRedirect("paginaError.jsp"); 
            }
            
        } catch (NumberFormatException e) {
            
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros de ID inválidos.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno al procesar la solicitud.");
        }
        
   
    }

//-----------------------------------------------------------------------------
    private void denegarIncapacidad(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Verificar si los parámetros existen y no son null
        String idEmpleadoParam = request.getParameter("id_empleado");
        String idIncapacidadStr = request.getParameter("id_incapacidad");

        System.out.println("Empleado consulta ID: " + idEmpleadoParam);
        System.out.println("ID incapacidad consulta ID: " + idIncapacidadStr);

        // Verificar si los parámetros existen y no son null
        if (idEmpleadoParam == null || idIncapacidadStr == null) {
            // Si alguno de los parámetros es null, devolver error o redirigir a una página de error
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros inválidos.");
            return;
        }

        try {
            // Convertir los parámetros a enteros
            int idEmpleado = Integer.parseInt(idEmpleadoParam);
            int idIncapacidad = Integer.parseInt(idIncapacidadStr);

            // Crear el objeto Incapacidades y establecer el ID de incapacidad
            Incapacidades incapacidadDenegada = new Incapacidades();
            incapacidadDenegada.setIdIncapacidad(idIncapacidad);

            // Llamar al método del DAO para denegar la incapacidad
            boolean incapacidadDenegadaResult = incapacidadDAO.incapacidadDenegada(idIncapacidad);

            // Verificar si la actualización fue exitosa
            if (incapacidadDenegadaResult) {
                request.getRequestDispatcher("vistaIncapacidad/incapDenegar.jsp").forward(request, response);
                System.out.println("Denegación exitosa");
            } else {
                response.sendRedirect("paginaError.jsp");
            }

        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parámetros de ID inválidos.");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno al procesar la solicitud.");
        }
}
//-----------------------------------------------------------------------------------------------
}
 /* 
        //Imprime todos los parámetros recibidos en el servlet para verificar si el parámetro
        
        Map<String, String[]> parameterMap = request.getParameterMap();
        parameterMap.forEach((key, value) -> System.out.println("Parameter: " + key + " = " + Arrays.toString(value)));
         */