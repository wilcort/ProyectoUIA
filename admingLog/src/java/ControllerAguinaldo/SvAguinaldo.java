/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerAguinaldo;

import Model.Colaborador;
import Model.ColaboradorDAO;
import ModelAguinaldo.Aguinaldo;
import ModelAguinaldo.AguinaldoDAO;
import ModelEmpleado.EmpleadoDAO;
import ModelIncapacidad.IncapacidadDAO;
import ModelPanilla.Planilla;
import ModelPanilla.PlanillaDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Dell
 */
@WebServlet(name = "SvAguinaldo", urlPatterns = {"/SvAguinaldo"})
public class SvAguinaldo extends HttpServlet {
    
   private AguinaldoDAO aguinaldoDAO;
    private PlanillaDAO planillaDAO;
    private String vista;
    private ColaboradorDAO colaboradorDAO;
    private IncapacidadDAO incapacidadDAO;
    private EmpleadoDAO empleadoDAO;
    
    @Override
    public void init() throws ServletException {
        super.init();
         planillaDAO = new PlanillaDAO();
        aguinaldoDAO = new AguinaldoDAO(); // Corregido aquí
        incapacidadDAO = new IncapacidadDAO();
        empleadoDAO = new EmpleadoDAO();
        

        vista = "vistaAguinaldo/generarAguinaldo.jsp";
    }
    

//--------------------------------------------------------------
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        System.out.println("Acción recibida en GET: " + accion);
       
        if ("Listar_Empleados".equals(accion)) {
            listarEmpleados(request, response);
        }else if ("Mostrar_Aguinaldo".equals(accion)) {
            mostrarAguinaldo(request, response);
        } else {
            // Manejar otras acciones o redirigir a un error
            response.sendRedirect("/WEB-INF/error.jsp");
        }
    }

//---------------------------------------------------------------  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");
        System.out.println("Acción recibida en POST: " + accion);
        
        if ("Calcular_Aguinaldo".equals(accion)) {
            calcularAguinaldo(request, response);
        }else if ("Ver_Aguinaldo_Empleado".equals(accion)) {
            aguinaldoEmpleado(request, response);
        }  else if ("Ver_Planilla_Empleado".equals(accion)) {
            verPlanillaEmpleado(request, response);
        } else if ("Mostrar_Aguinaldo".equals(accion)) {
            mostrarAguinaldo(request, response);
        }else {
            response.sendRedirect("/WEB-INF/error.jsp");
        }
        
    }

//-----------------------------------------------------------------------   
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
//--------------------------------------------------------------------
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
//----------------------------------------------
//-----------------------------------------------------------------------    
    private void calcularAguinaldo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String anioStr = request.getParameter("anio");

        if (anioStr == null) {
            request.setAttribute("mensajeError", "El año es obligatorio.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        try {
            int anioSeleccionado = Integer.parseInt(anioStr);
            List<Aguinaldo> aguinaldos = aguinaldoDAO.calculoAguinaldo(anioSeleccionado);
            aguinaldoDAO.insertarAguinaldos(aguinaldos);

            response.sendRedirect("pages/exitoAguinaldo.jsp");
        } catch (NumberFormatException e) {
            request.setAttribute("mensajeError", "El año debe ser un número válido.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("mensajeError", "Error al calcular aguinaldos.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }
        
  
//----------------------------------------------------------------

   private void aguinaldoEmpleado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    Integer idEmpleado = Integer.valueOf(request.getParameter("id_empleado"));

    // Almacenar el id_empleado en la sesión
    request.getSession().setAttribute("id_empleado", idEmpleado);

    // Verificar si ya existe un reporte de aguinaldo para este empleado
    Aguinaldo aguinaldo = aguinaldoDAO.verAguinaldoEmpleado(idEmpleado);

    if (aguinaldo != null) {
        // Pasa el objeto Aguinaldo y su colaborador al request
        request.setAttribute("aguinaldo", aguinaldo);
          } 

    request.getRequestDispatcher("vistaAguinaldo/reporteAguinaldo.jsp").forward(request, response);
}
//----------------------------    

   private void verPlanillaEmpleado(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

       Integer idEmpleado = Integer.valueOf(request.getParameter("id_empleado"));

       // Almacenar el id_empleado en la sesión
       request.getSession().setAttribute("id_empleado", idEmpleado);

       // Verificar si ya existe un reporte de aguinaldo para este empleado
       Aguinaldo aguinaldo = aguinaldoDAO.verAguinaldoEmpleado(idEmpleado);

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
        request.getRequestDispatcher("vistaAguinaldo/verPlanillaAgu.jsp").forward(request, response);
    }
   
 //-----------------------------------------------------------

    private void mostrarAguinaldo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
        Integer idUsuario = (Integer) request.getSession().getAttribute("id_usuario");
        Integer idEmpleado = empleadoDAO.obtenerIdEmpleado(idUsuario);

        request.getSession().setAttribute("id_empleado", idEmpleado);
        System.out.println("id " + idUsuario);
       
// Verificar si ya existe un reporte de aguinaldo para este empleado
    Aguinaldo aguinaldo = aguinaldoDAO.verAguinaldoEmpleado(idEmpleado);

    if (aguinaldo != null) {
        // Pasa el objeto Aguinaldo y su colaborador al request
        request.setAttribute("aguinaldo", aguinaldo);
          } 

    request.getRequestDispatcher("vistaAguinaldo/aguinaldoEmplReporte.jsp").forward(request, response);


    }
}
