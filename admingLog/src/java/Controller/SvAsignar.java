/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.AsignarDAO;
import Model.Cargo;
import Model.Colaborador;
import Model.Horarios;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "SvAsignar", urlPatterns = {"/SvAsignar"})
public class SvAsignar extends HttpServlet {
    
    private AsignarDAO asignarDAO;
    
    
    //inicializar el servlet. En este caso, está creando una instancia de AsignarDAO 
    //cuando el servlet se carga por primera vez.
    
    @Override
    public void init() throws ServletException {
        super.init();
        asignarDAO = new AsignarDAO();
    }
    
    
// Se usa para manejar solicitudes HTTP GET. Obtener datos
//Comúnmente se utiliza para obtener datos del servidor y mostrarlos al usuario.
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("asignar_Cargo".equals(accion)) {
            List<Cargo> listaCargos = asignarDAO.listarCargos();
            request.setAttribute("cargos", listaCargos);

            request.getRequestDispatcher("/asignaciones/asignarCargo.jsp").forward(request, response);

        } else if ("asignar_Horario".equals(accion)) {
            List<Horarios> listaHorarios = asignarDAO.listarHorarios();
            request.setAttribute("horarios", listaHorarios);

            // Asegúrate de que el id_empleado esté disponible en la sesión o como parámetro
            HttpSession session = request.getSession();
            session.setAttribute("id_empleado", request.getParameter("id_empleado"));

            request.getRequestDispatcher("/asignaciones/asignarHorario.jsp").forward(request, response);

        } else {
            // Si la acción no coincide con ninguna de las anteriores, redirige a una página de error
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }

    }

//Se usa para manejar solicitudes HTTP POST.
//Comúnmente se utiliza para enviar datos al servidor, como en el caso de formularios HTML.  
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        
        if ("asignar_Cargo".equals(accion)) {
            asignar_Cargo(request, response);
        } else if ("asignar_Horario".equals(accion)) {
            asignar_Horario(request, response);
        } else {
            // Si la acción no coincide con ninguna de las anteriores, redirige a una página de error
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }
    }
//------------------------------------------------------------

    private void asignar_Cargo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            // Obtener el ID del empleado
            String idEmpleadoStr = request.getParameter("id_empleado");
            
            if (idEmpleadoStr == null || idEmpleadoStr.isEmpty()) {
                throw new IllegalArgumentException("ID del empleado no proporcionado.");
            }
            int idEmpleado = Integer.parseInt(idEmpleadoStr);

            int idCargo = Integer.parseInt(request.getParameter("idCargo"));
            Cargo cargo = asignarDAO.obtenerCargoPorId(idCargo);

            // Obtener datos Usuario desde formulario nuevo html
            cargo.setIdCargo(idCargo);

            Colaborador colaborador = new Colaborador(idEmpleado,
                    0, null, null, null, 0,
                    null, null, null,
                    null, null, cargo,null);
            
            // Insertar los datos en la base de datos
            boolean exito = asignarDAO.asignarCargo(colaborador, cargo);

            if (exito) {
                response.sendRedirect("SvColaborador?accion=Ver_Empleado&id=" + idEmpleado);
                System.out.println("si");

            } else {
                request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
                System.out.println("no error XXX");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
//----------------------------------------------------
    
    private void asignar_Horario(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {

            // Obtener el ID del empleado
            String idEmpleadoStr = request.getParameter("id_empleado");
            
            if (idEmpleadoStr == null || idEmpleadoStr.isEmpty()) {
                throw new IllegalArgumentException("ID del empleado no proporcionado.");
            }
            int idEmpleado = Integer.parseInt(idEmpleadoStr);

            int idHorario = Integer.parseInt(request.getParameter("idHorario"));
            Horarios horario = asignarDAO.obtenerHorarioPorId(idHorario);
            
            horario.setIdHorario(idHorario);
            

             Colaborador colaborador = new Colaborador(idEmpleado, 0, null, 
                     null, null, 0, null, null, 
                     null, null, null, null, horario);
            
            // Insertar los datos en la base de datos
            boolean exito = asignarDAO.asignarHorario(colaborador, horario);

            if (exito) {
                response.sendRedirect("SvColaborador?accion=Ver_Empleado&id=" + idEmpleado);
                System.out.println("si horario");

            } else {
                request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
                System.out.println("no error XXX");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
    
    
 //------------------------------------------------------------     
    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
