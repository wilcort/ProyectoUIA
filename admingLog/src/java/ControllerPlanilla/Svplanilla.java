/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerPlanilla;

import Model.ColaboradorDAO;
import ModelPanilla.PlanillaDAO;
import java.io.IOException;
import java.io.PrintWriter;
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
    
    @Override
    public void init() throws ServletException {
        super.init();
        planillaDAO = new PlanillaDAO();
        PlanillaDAO planillaDAO = new PlanillaDAO();
        
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
           
        }else {
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
        }else if ("Generar_Planilla_Mensual".equals(accion)) {
            GenerarPlanillaMensual(request, response);
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

        // Obtener los datos del formulario
        String mesStr = request.getParameter("mes");
        String anioStr = request.getParameter("anio");

        if (mesStr == null || anioStr == null) {
            response.getWriter().println("Mes y año son obligatorios.");
            return;
        }
        int mesSeleccionado = Integer.parseInt(mesStr);
        int anioSeleccionado = Integer.parseInt(anioStr);

        planillaDAO.generarPlanillaQuincenalParaTodos(mesSeleccionado, anioSeleccionado);

        try {


            planillaDAO.generarPlanillaQuincenalParaTodos(mesSeleccionado, anioSeleccionado);

            response.getWriter().println("Planilla quincenal generada correctamente para todos los empleados.");

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

            planillaDAO.generarPlanillaMenasualParaTodos(mesSeleccionado, anioSeleccionado);

            response.getWriter().println("Planilla quincenal generada correctamente para todos los empleados.");

        } catch (NumberFormatException e) {
            response.getWriter().println("Mes y año deben ser números válidos.");
            e.printStackTrace();
        } catch (Exception e) {
            response.getWriter().println("Ocurrió un error al generar la planilla.");
            e.printStackTrace();
        }
    }

//-----------------------------------------------------------------------------
}
