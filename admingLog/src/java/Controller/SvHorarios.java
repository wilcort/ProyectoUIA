/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Horarios;
import Model.HorariosDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Time;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author Dell
 */
@WebServlet(name = "SvHorarios", urlPatterns = {"/SvHorarios"})
public class SvHorarios extends HttpServlet {

        private HorariosDAO horariosDAO;
        
         @Override
    public void init() throws ServletException {
        super.init();
        horariosDAO = new HorariosDAO();
    }
        
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        String vista = "vistaHorarios/indexHorarios.jsp";

        if (accion == null || accion.isEmpty()) {
            List<Horarios> listaHorarios = horariosDAO.listarHorarios();
            request.setAttribute("listaHorarios", listaHorarios);
        } else if ("nuevo".equals(accion)) {
            List<Horarios> listaHorarios = horariosDAO.listarHorarios();
            request.setAttribute("listaHorarios", listaHorarios);
            vista = "vistaHorarios/nuevoHorario.jsp";
        } else if ("ver_Horarios".equals(accion)) {
            ver_Horarios(request, response); // Llamada al método para mostrar horario
            return; 
        }else if ("eliminar_Horario".equals(accion)) {
            eliminar_Horario(request, response); 
            return;
        }else {
            // Si la acción no coincide con ninguna de las anteriores, redirige a una página de error
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }
        request.getRequestDispatcher(vista).forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
           
        try {
            if ("insertarHorario".equals(accion)) {
                insertarHorario(request, response);
            } else if ("ver_Horarios".equals(accion)) {
                 ver_Horarios(request, response);               
            }else if ("eliminar_Horario".equals(accion)) {
            eliminar_Horario(request, response);
            } else {
                response.sendRedirect("/WEB-INF/error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();  // Imprime el stack trace en los logs
            response.sendRedirect("/WEB-INF/error.jsp");
        }
    }

//---------------------------------- INSERTAR ----------------------------

    private void insertarHorario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            // Leer datos del formulario
            String horaEntradaStr = request.getParameter("horaEntrada");
            String horaSalidaStr = request.getParameter("horaSalida");

            // Obtén todos los días laborales seleccionados
            String[] diasLaboralesArray = request.getParameterValues("diasLaborales");

            // Validar y convertir los datos a los tipos adecuados
            Time horaEntrada = parseTime(horaEntradaStr);
            Time horaSalida = parseTime(horaSalidaStr);

            // Convertir días laborales de String a Set<String>
            Set<String> diasLaborales = new HashSet<>();
            if (diasLaboralesArray != null) { // Verifica que al menos un checkbox esté seleccionado
                for (String dia : diasLaboralesArray) {
                    diasLaborales.add(dia);
                }
            }

            // Crear un objeto Horarios
            Horarios horario = new Horarios();
            horario.setHoraEntrada(horaEntrada);
            horario.setHoraSalida(horaSalida);
            horario.setDiasLaborales(diasLaborales);

            System.out.println("horaEntradaStr: " + horaEntradaStr);
            System.out.println("horaSalidaStr: " + horaSalidaStr);
            System.out.println("Días laborales seleccionados: " + diasLaborales);

            // Llamar al método para insertar el horario en la base de datos
            boolean insercionExitosa = horariosDAO.crear_Horario(horario);

            if (insercionExitosa) {
                // Obtener la lista actualizada de horarios
                List<Horarios> listaHorarios = horariosDAO.listarHorarios();
                request.setAttribute("listaHorarios", listaHorarios);
                response.sendRedirect("pages/paginaExitoHorarios.jsp");
            } else {
                response.sendRedirect("/WEB-INF/error.jsp");
            }

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            response.sendRedirect("/WEB-INF/error.jsp");
        }
    }
    
//--------------------------------------------------------------    
    
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
    
//-------------------------------------
    
    private void ver_Horarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idHorario = Integer.parseInt(request.getParameter("id"));
            System.out.println("id horario " + idHorario);
            
            Horarios horario = horariosDAO.mostrar_Horario(idHorario);
                       
          

            if (horario != null) {
                
                request.setAttribute("horario", horario);
                request.getRequestDispatcher("vistaHorarios/verHorarios.jsp").forward(request, response);
            } else {
                response.sendRedirect("/WEB-INF/error.jsp");
            }
        } catch (NumberFormatException e) {
            // Manejar el caso en el que 'id' no es un número válido
            e.printStackTrace();
            response.sendRedirect("/WEB-INF/error.jsp");
        }
    }
    
    
    //---------------------------------------------------------------------
   
    private void eliminar_Horario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idHorario = Integer.parseInt(request.getParameter("id"));

            horariosDAO.eliminarHorario(idHorario);

            // Obtener la lista actualizada de horarios
            List<Horarios> listaHorarios = horariosDAO.listarHorarios();

            // Establecer la lista como atributo de solicitud
            request.setAttribute("listaHorarios", listaHorarios);

            request.getRequestDispatcher("/vistaHorarios/indexHorarios.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            // Manejar el error de formato de número
            request.setAttribute("error", "ID de Horario inválido.");
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    } 
    
    
    
    //-------------------------------------------------------
    
     @Override
    public String getServletInfo() {
        return "Short description";
    }
}
