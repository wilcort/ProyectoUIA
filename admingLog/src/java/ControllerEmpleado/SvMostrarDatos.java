/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerEmpleado;

import Model.Colaborador;
import ModelEmpleado.EmpleadoDAO;
import ModelEmpleado.Marcas;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

@WebServlet(name = "SvMostrarDatos", urlPatterns = {"/SvMostrarDatos"})
public class SvMostrarDatos extends HttpServlet {

    private EmpleadoDAO empleadoDAO;
    private String vista; // Definir la variable vista como un campo de clase
    
    @Override
    public void init() throws ServletException {
        super.init();
        empleadoDAO = new EmpleadoDAO();
        vista = "vistaEmpleado/datosEmpleado.jsp"; // Inicializar la variable vista
    }
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String accion = request.getParameter("accion");

        if ("Ver_Empleado".equals(accion)) {
            ver_Empleado(request, response);
            return;
        } else if("realizar_Marca".equals(accion)){
             request.getRequestDispatcher("vistaEmpleado/marcasEmpleado.jsp").forward(request, response); 
             
        } else if("ver_Marcas".equals(accion)){
            request.getRequestDispatcher("vistaEmpleado/verMarcas.jsp").forward(request, response); 
             
        }
        else if("verMarcasAnteriores".equals(accion)){
            request.getRequestDispatcher("vistaEmpleado/verMarcasAnteriores.jsp").forward(request, response); 
             
        }else {
            // Si la acción no coincide con ninguna de las anteriores, redirige a una página de error
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }
    }
//----------------------------------------------------------------
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        try {
            if ("realizar_Marca".equals(accion)) {
                realizar_Marca(request, response);
            } else if ("ver_Marcas".equals(accion)) {
                ver_Marcas(request, response);
            } else if ("enviar_Mensaje".equals(accion)) {
                enviar_Mensaje(request, response);
            } else if ("verMarcasAnteriores".equals(accion)) {
                verMarcasAnteriores(request, response);
            } else {
                response.sendRedirect("/WEB-INF/error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/WEB-INF/error.jsp");
        }
    }


//-------------------------------------------------------------------------------
    
    private void ver_Empleado(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        // Obtener el ID de usuario de la sesión
        Integer idUsuario = (Integer) request.getSession().getAttribute("id_usuario");
        System.out.println("hola " + idUsuario);
         

        if (idUsuario != null) {
            // Llamar al método mostrarEmpleado de EmpleadoDAO
            Colaborador colaborador = empleadoDAO.mostrarEmpleado(idUsuario);
            
            // Verificar si se encontró el colaborador
            if (colaborador != null) {
                // Agregar el colaborador al request para pasarlo a la vista
                request.setAttribute("colaborador", colaborador);
                // Redirigir a la vista del empleado
                request.getRequestDispatcher(vista).forward(request, response);
            } else {
                // Manejar el caso en que no se encontró el colaborador
                request.setAttribute("error", "No se encontró el empleado.");
                request.getRequestDispatcher(vista).forward(request, response);
            }
        } else {
            // Manejar el caso en que el ID de usuario no está presente
            response.sendRedirect("errorPage.jsp"); // Redirigir a una página de error o login
        }
    }
    
//--------------------------------------------------------------------------------------------
    
//--------------------------------------------------------       
    //----
   private void realizar_Marca(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener el ID de usuario de la sesión
        Integer idUsuario = (Integer) request.getSession().getAttribute("id_usuario");
        System.out.println("hola 2 " + idUsuario);

        Integer idEmpleado = empleadoDAO.obtenerIdEmpleado(idUsuario);

        System.out.println("ID Usuario: " + idUsuario);

        // Obtener el ID del empleado basado en el ID del usuario
        System.out.println("ID Empleado: " + idEmpleado);

        // Verificar si se encontró el empleado
        if (idEmpleado != null) {
            // Obtener y convertir los datos de la marca (ejemplo: fecha y horas de entrada/salida)
            String fechaMarcaStr = request.getParameter("fecha_marca");
            java.sql.Date fechaMarca = null;

            if (fechaMarcaStr != null && !fechaMarcaStr.isEmpty()) {
                fechaMarca = java.sql.Date.valueOf(fechaMarcaStr);
            }
            //---------------------       
            // Verificar si ya existe una marca para esa fecha
           
            //-----------------
            String marcaEntradaStr = request.getParameter("hora_entrada");
            String marcaSalidaStr = request.getParameter("hora_salida");
            String marcaSalidaAlmuerzoStr = request.getParameter("hora_salida_almuerzo");
            String marcaEntradaAlmuerzoStr = request.getParameter("hora_entrada_almuerzo");

            Time hora_entrada = parseTime(marcaEntradaStr);
            Time hora_salida = parseTime(marcaSalidaStr);
            Time hora_salida_almuerzo = parseTime(marcaSalidaAlmuerzoStr);
            Time hora_entrada_almuerzo = parseTime(marcaEntradaAlmuerzoStr);

            Marcas nuevaMarca = new Marcas();
            nuevaMarca.setFechaMarca(fechaMarca);
            nuevaMarca.setMarcaEntrada(hora_entrada); // Asegúrate de incluir esto si es necesario
            nuevaMarca.setMarcaSalida(hora_salida);
            nuevaMarca.setMarcaSalidaAlmuerzo(hora_salida_almuerzo);
            nuevaMarca.setMarcaEntradaAlmuerzo(hora_entrada_almuerzo);
            nuevaMarca.setIdEmpleado(idEmpleado); // Asegúrate de asignar el idEmpleado

            boolean insercionExitosa = empleadoDAO.realizarMarca(nuevaMarca);
            System.out.println("entrada : " + marcaEntradaStr);
            System.out.println("entrada : " + fechaMarca);
            System.out.println("empleado " + idEmpleado);

            if (insercionExitosa) {
                // Redirigir a la página de éxito si la inserción fue exitosa
                response.sendRedirect("pages/exitoRegistro.jsp");
                return; // Asegúrate de salir del método después de redirigir

            } else {
                request.setAttribute("error", "Error al registrar la marca.");
            }

            // Añadir más atributos al request, como el idEmpleado 
            request.setAttribute("idEmpleado", idEmpleado);

        } else {
            request.setAttribute("error", "No se encontró el empleado correspondiente.");
        }

        // Redirigir a la vista correspondiente
        request.getRequestDispatcher("vistaEmpleado/marcasEmpleado.jsp").forward(request, response);
    }
//--------------------------------------------------------------------------
    
    public  Time parseTime(String timeStr) {
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
//----------------------------------------------------------------------------
//-----
    private void ver_Marcas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener el ID de usuario de la sesión
        Integer idUsuario = (Integer) request.getSession().getAttribute("id_usuario");
        System.out.println("ID Usuario: " + idUsuario);

        if (idUsuario != null) {
            Integer idEmpleado = empleadoDAO.obtenerIdEmpleado(idUsuario);
            System.out.println("ID Empleado: " + idEmpleado);

            if (idEmpleado != null) {
                int[] fechaQuincena = SvMostrarDatos.obtenerFechasQuincena();
                int inicio = fechaQuincena[0]; // Día de inicio
                int fin = fechaQuincena[1]; // Día de fin

                // Agregar las fechas como atributos en la solicitud
                request.setAttribute("inicioQuincena", inicio);
                request.setAttribute("finQuincena", fin);

                // Obtener la lista de marcas
                List<Marcas> listaMarcas = empleadoDAO.obtenerMarcasQuincena(idEmpleado, inicio, fin);
                request.setAttribute("listaMarcas", listaMarcas);

                // Calcular y agregar total de horas quincena como atributo
                double[] totalHorasQuincena = empleadoDAO.calculoHorasQuincenas(idEmpleado, inicio, fin);
                request.setAttribute("totalHorasQuincena", totalHorasQuincena[0]); // Suponiendo que el arreglo tiene un solo elemento
                 // Imprimir el total de horas en consola
            System.out.println("Total de horas en la quincena: " + totalHorasQuincena[0]);
            
            } else {
                System.out.println("ID de empleado no encontrado.");
            }
        } else {
            System.out.println("ID de usuario no encontrado en la sesión.");
        }

        // Redirigir a la JSP correspondiente
        request.getRequestDispatcher("vistaEmpleado/verMarcas.jsp").forward(request, response);
    }
//---------------------------------------------------------------------
 public void verMarcasAnteriores(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idUsuario = (Integer) request.getSession().getAttribute("id_usuario");
        System.out.println("ID Usuario ff: " + idUsuario);

        if (idUsuario != null) {
            Integer idEmpleado = empleadoDAO.obtenerIdEmpleado(idUsuario);
            System.out.println("ID Empleado: " + idEmpleado);

            if (idEmpleado != null) {
                int mes = Integer.parseInt(request.getParameter("mes"));
                int anio = Integer.parseInt(request.getParameter("anio"));

                // Obtener la lista de marcas del mes y año seleccionados
                List<Marcas> listaMarcas = empleadoDAO.obtenerMarcasPorMes(idEmpleado, mes, anio);
                System.out.println("Lista de Marcas: " + listaMarcas); // Verificar el contenido

                request.setAttribute("listaMarcas", listaMarcas);
                double totalHoras = calcularTotalHoras(listaMarcas);
                request.setAttribute("totalHoras", totalHoras);
            } else {
                System.out.println("ID de empleado no encontrado.");
            }
        } else {
            System.out.println("ID de usuario no encontrado en la sesión.");
        }

        request.getRequestDispatcher("vistaEmpleado/verMarcasAnteriores.jsp").forward(request, response);
    }
// Método para calcular el total de horas trabajadas
private double calcularTotalHoras(List<Marcas> listaMarcas) {
    double totalHoras = 0.0;
    for (Marcas marca : listaMarcas) {
        totalHoras += marca.getHorasDia();
    }
    return totalHoras;
}
 
 


//--------------------------------------------------------------------------
     public static int[] obtenerFechasQuincena() {
        // Obtener la fecha actual
        Calendar calendar = Calendar.getInstance();
        int diaActual = calendar.get(Calendar.DAY_OF_MONTH);

        // Determinar las fechas de inicio y fin según la quincena
        int inicio, fin;

        if (diaActual <= 15) {
            // Primera quincena
            inicio = 1;
            fin = 15;
            
            System.out.println(" dias 1 al 15");
        } else {
            // Segunda quincena
            inicio = 16;
            fin = 31;
            System.out.println(" dias 16 al 31");
        }

        // Retornar un arreglo con el inicio y el fin de la quincena
        return new int[]{inicio, fin};
    }
//-------------------------------------------------------------------
//-------------- ENVIAR MENSAJE --------------------------

    public boolean enviar_Mensaje(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener el ID de usuario de la sesión
        Integer idUsuario = (Integer) request.getSession().getAttribute("id_usuario");
        System.out.println("id usuario " + idUsuario);
     
        // Obtener el ID del empleado basado en el ID del usuario
        Integer idEmpleado = empleadoDAO.obtenerIdEmpleado(idUsuario);

        if (idEmpleado != null) {
            // Obtener el mensaje enviado desde el formulario
            String mensaje = request.getParameter("mensaje");

            // Llama al método que inserta el mensaje en la base de datos y devuelve un booleano
            boolean mensajeEnviado = empleadoDAO.enviarMensaje(idEmpleado, mensaje);

            // Verificar si el mensaje fue enviado exitosamente
            if (mensajeEnviado) {
                // Redirigir o mostrar un mensaje de éxito
                response.sendRedirect("pages/paginaConfirmacion.jsp"); // Cambiar a la página de confirmación deseada
                return true;
            } else {
                System.out.println("No se pudo enviar el mensaje.");
                // Redirigir o mostrar un mensaje de error
                response.sendRedirect("paginaError.jsp"); // Cambiar a la página de error deseada
                return false;
            }
        } else {
            System.out.println("No se encontró un empleado para este usuario.");
            response.sendRedirect("paginaError.jsp"); // Redirigir si no se encuentra un empleado
            return false;
        }
    }

//---------------------------------------------------------------
    @Override
    public String getServletInfo() {
        return "Servlet para mostrar los datos del empleado.";
    }

   
  //---------------------------------------------------------

}