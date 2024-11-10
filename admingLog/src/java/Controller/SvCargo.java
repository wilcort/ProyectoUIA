/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import Model.Cargo;
import java.util.List;
import Model.CargosDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.BitSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "SvCargo", urlPatterns = {"/SvCargo"})
public class SvCargo extends HttpServlet {
    
     private CargosDAO cargosDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        cargosDAO = new CargosDAO();
    }
//------------------------------------------------------------------
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        String vista = "vistaCargos/indexCargo.jsp";

        if (accion == null || accion.isEmpty()) {
            List<Cargo> listaCargos = cargosDAO.listarCargos();
            request.setAttribute("listaCargos", listaCargos);
        }else if("nuevo".equals(accion)){
            List<Cargo> listaCargos = cargosDAO.listarCargos();
            request.setAttribute("listaCargos", listaCargos);
            vista = "vistaCargos/nuevoCargos.jsp";
        }else if ("Ver_Cargo".equals(accion)) {
            ver_Cargo(request, response);
            return;
        }else if ("eliminar_Cargo".equals(accion)) {
            eliminar_Cargo(request, response); 
            return;
        }else {
            // Si la acción no coincide con ninguna de las anteriores, redirige a una página de error
            response.sendRedirect("/WEB-INF/error.jsp");
            return; 
        }
        
        request.getRequestDispatcher(vista).forward(request, response);
    }
//------------------------------------------------------------------
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        try {
            if ("insertarCargo".equals(accion)) {
                insertarCargo(request, response);
            } else if ("Ver_Cargo".equals(accion)) {
                ver_Cargo(request, response);
            } else if ("modificar_Cargo".equals(accion)) {
                modificar_Cargo(request, response);
            } else if ("actualizar_Cargo".equals(accion)) {
                actualizar_Cargo(request, response);
            } else if ("eliminar_Cargo".equals(accion)) {
                eliminar_Cargo(request, response);
            } else {
                response.sendRedirect("/WEB-INF/error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();  // Imprime el stack trace en los logs
            response.sendRedirect("/WEB-INF/error.jsp");
        }
    }
//------------------------------------------------------------------
    private void insertarCargo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nombre_cargo = request.getParameter("nombre_cargo");
        boolean estado = "1".equals(request.getParameter("estado"));
        
        // Convertir salario de String a double
        double salario = 0.0; // Inicializar la variable salario

        salario = Double.parseDouble(request.getParameter("salario"));
      
        Cargo nuevoCargo = new Cargo();
        nuevoCargo.setNombreCargo(nombre_cargo);
        nuevoCargo.setEstado(estado);
        nuevoCargo.setSalario(salario);
        
        boolean insercionExitosa = cargosDAO.crear_Cargos(nuevoCargo);

        if (insercionExitosa) {
            response.sendRedirect("pages/paginaExitoCargos.jsp");
        } else {
            response.sendRedirect("/WEB-INF/error.jsp");
        }
    }
//------------------------------------------------------------------    
    private void ver_Cargo (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
    
        int idCargo = Integer.parseInt(request.getParameter("id"));
        
        Cargo cargo = cargosDAO.mostrar_Cargos(idCargo);
        
        System.out.println("id " + cargo.getIdCargo());
        
        if (cargo != null) {
            
            request.setAttribute("cargo", cargo);
            request.getRequestDispatcher("vistaCargos/verCargo.jsp").forward(request, response);
        } else{
            // Si no se encuentra el colaborador, manejar el caso de error
            response.sendRedirect("/WEB-INF/error.jsp");      
        }
    
    }
//------------------------------------------------------------------    
    private void modificar_Cargo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int idCargo = Integer.parseInt(request.getParameter("id"));
        
        Cargo cargo = cargosDAO.mostrar_Cargos(idCargo);
        
        List<Cargo> listaCargos = cargosDAO.listarCargos();
           System.out.println("cargo " + cargo.getNombreCargo());   
           
        if (cargo != null) {
            
            request.setAttribute("cargo", cargo);
            request.setAttribute("cargos", listaCargos);
            request.setAttribute("salario", cargo.getSalario());
            
            // Llamar a la página JSP para mostrar los detalles del empleado
            request.getRequestDispatcher("/vistaCargos/modificarCargo.jsp").forward(request, response);
        } else {
            // Si no se encuentra el colaborador, manejar el caso de error
            response.sendRedirect("/WEB-INF/error.jsp");
        } 
    } 
//------------------------------------------------------------------    
    private void actualizar_Cargo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int idcargo = Integer.parseInt(request.getParameter("id_cargo"));
            String nombre_cargo = request.getParameter("nombre_cargo");
            boolean estado = Boolean.parseBoolean(request.getParameter("estado"));
            double salario = Double.parseDouble(request.getParameter("salario"));
            
            Cargo cargo = new Cargo(idcargo, nombre_cargo, estado, salario);

            boolean exito = cargosDAO.modificar_Cargos(cargo);

            if (exito) {
                response.sendRedirect("SvCargo?accion=Ver_Cargo&id=" + idcargo);
            } else {
                request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }
//------------------------------------------------------------------
    private void eliminar_Cargo(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int idCargo = Integer.parseInt(request.getParameter("id"));
            cargosDAO.eliminarCargo(idCargo);

            // Obtener la lista actualizada de cargos
            List<Cargo> listaCargos = cargosDAO.listarCargos();
            request.setAttribute("listaCargos", listaCargos);

            // Redirigir a la lista actualizada
            request.getRequestDispatcher("/vistaCargos/indexCargo.jsp").forward(request, response);

        } catch (NumberFormatException e) {
            request.setAttribute("error", "ID de Cargo inválido.");
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }

    
//------------------------------------------------------------------
    @Override
    public String getServletInfo() {
        return "Short description";
    }
 
}