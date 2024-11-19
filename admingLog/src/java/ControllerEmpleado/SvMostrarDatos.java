/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ControllerEmpleado;

import Model.Colaborador;
import ModelEmpleado.EmpleadoDAO;
import ModelIncapacidad.IncapacidadDAO;
import ModelEmpleado.Marcas;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@WebServlet(name = "SvMostrarDatos", urlPatterns = {"/SvMostrarDatos"})
public class SvMostrarDatos extends HttpServlet {

    private EmpleadoDAO empleadoDAO;
    private IncapacidadDAO incapacidadDAO;
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

        System.out.println("Servlet SvMostrarDatos invocado doGET");

        // Registra todos los parámetros recibidos
        request.getParameterMap().forEach((key, value) -> {
            System.out.println("Parámetro: " + key + ", Valor: " + String.join(", ", value));
        });

        String accion = request.getParameter("accion");
        String idEmpleado = request.getParameter("id_empleado");

        System.out.println("Acción: " + accion);
        System.out.println("ID Empleado recibido: " + idEmpleado);

        if ("Ver_Empleado".equals(accion)) {
            ver_Empleado(request, response);
            return;
        } else if ("realizar_Marca".equals(accion)) {
            request.getRequestDispatcher("vistaEmpleado/marcasEmpleado.jsp").forward(request, response);

        } else if ("ver_Marcas".equals(accion)) {
            request.getRequestDispatcher("vistaEmpleado/verMarcas.jsp").forward(request, response);

        } else if ("ver_Todas_Marcas".equals(accion)) {
            verTodasMarcas(request, response);
            return;
        } else if ("filtrar".equals(accion)) {
            verTodasMarcas(request, response);
            return;
        } else if ("ver_Marcas_Update".equals(accion)) {
            verMarcasUpdate(request, response);
        }  else {
            // Si la acción no coincide con ninguna de las anteriores, redirige a una página de error
            response.sendRedirect("/WEB-INF/error.jsp");
            return;
        }
    }
//----------------------------------------------------------------

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");
        System.out.println("Acción recibida en post: " + accion);  // Depuración

        try {
            if ("realizar_Marca".equals(accion)) {
                realizar_Marca(request, response);
            } else if ("ver_Marcas".equals(accion)) {
                ver_Marcas(request, response);
            } else if ("Ver_Empleados".equals(accion)) {
                verEmpleados(request, response);
            } else if ("Modificar_Marca".equals(accion)) {
                modificarMarca(request, response);
            } else if ("Guardar_Modificacion".equals(accion)) {
                guardarModificacion(request, response);
            } else if ("ver_Marcas_Update".equals(accion)) {
                verMarcasUpdate(request, response);
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
//----------------------------------------------------------------------------

    private void ver_Marcas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener el ID de usuario de la sesión
        Integer idUsuario = (Integer) request.getSession().getAttribute("id_usuario");
        System.out.println("ID Usuario: " + idUsuario);

        Integer idEmpleado = empleadoDAO.obtenerIdEmpleado(idUsuario);
        System.out.println("ID Empleado: " + idEmpleado);
        
        String fechaActual = request.getParameter("fecha_actual");
        System.out.println("Fecha Actual: " + fechaActual);

        // Obtener parámetros de mes y quincena
        String mesStr = request.getParameter("mes");
        String quincenaStr = request.getParameter("quincena");

        // Verificar si los parámetros mes y quincena no son null
        if (mesStr == null || quincenaStr == null) {
            System.out.println("Los parámetros mes o quincena no se están enviando correctamente.");
        } else {
            System.out.println("Mes: " + mesStr + ", Quincena: " + quincenaStr);
        }

        // Si los parámetros son válidos, procesarlos
        if (mesStr != null && quincenaStr != null) {
            try {
                int mes = Integer.parseInt(mesStr);
                int quincena = Integer.parseInt(quincenaStr);

                // Obtener la fecha de inicio y fin del mes
                LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), mes, 1);
                LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());

                // Obtener las marcas para el mes y quincena seleccionados
                List<Marcas> listaMarcas = empleadoDAO.obtenerMarcasPorMesQuincena(idEmpleado, startDate, endDate, quincena);

                request.setAttribute("listaMarcas", listaMarcas);
                request.getRequestDispatcher("vistaEmpleado/verMarcas.jsp").forward(request, response);
            } catch (NumberFormatException e) {
                System.out.println("Error al parsear mes o quincena: " + e.getMessage());
            }
        }
    }

//----------------------------------------
    
    private LocalDate obtenerFechaInicio(int mes, int quincena) {
        LocalDate now = LocalDate.now();
        int year = now.getYear();
        return LocalDate.of(year, mes, quincena == 1 ? 1 : 16);
    }

//--------------------------------------------------------------------------
    @Override
    public String getServletInfo() {
        return "Servlet para mostrar los datos del empleado.";
    }

   
  //---------------------------------------------------------
 //--**
    
    private void verTodasMarcas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Integer idUsuario = (Integer) request.getSession().getAttribute("id_usuario");
        Integer idEmpleado = empleadoDAO.obtenerIdEmpleado(idUsuario);
        
        // Obtener el mes seleccionado
        String mes = request.getParameter("mes");
        
        System.out.println("ID Usuario: " + idUsuario);
        System.out.println("ID Empleado: " + idEmpleado);
        System.out.println("Mes seleccionado: " + mes);


        // Lista de marcas según el filtro (o sin filtro si no se selecciona mes)
        List<Marcas> listaMarcas;
        if (mes != null && !mes.isEmpty()) {
            listaMarcas = empleadoDAO.obtenerMarcasPorMes(idEmpleado, mes);
        } else {
            listaMarcas = empleadoDAO.obtenerTodasLasMarcas(idEmpleado);
        }

        request.setAttribute("listaMarcas", listaMarcas);
        request.getRequestDispatcher("vistaEmpleado/listaMarcas.jsp").forward(request, response);

     }

//---------------------------------------

    private void verEmpleados(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        System.out.println(" HOLA");
        List<Colaborador> listarEmpleados = empleadoDAO.listarEmpleados();
        
        System.out.println("Número de marcas de empleado: " + listarEmpleados.size());

        request.setAttribute("listarEmpleados", listarEmpleados);

        // Redirige al JSP donde se mostrarán los datos
        request.getRequestDispatcher("vistaEmpleado/listarEmpleados.jsp").forward(request, response);
    }
   

//------------------------------------------------

    private void verMarcasUpdate(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         
        System.out.println("hola update");

        String idEmpleadoParam = request.getParameter("id_empleado");
        System.out.println("ID Empleado recibido vvvv: " + idEmpleadoParam);
        
        
        int idEmpleado = Integer.parseInt(idEmpleadoParam);
        
        String mes = request.getParameter("mes");
        
        System.out.println("ID Empleado: " + idEmpleado);
        System.out.println("Mes seleccionado: " + mes);


        // Lista de marcas según el filtro (o sin filtro si no se selecciona mes)
        List<Marcas> listaMarcas;
        if (mes != null && !mes.isEmpty()) {
            listaMarcas = empleadoDAO.obtenerMarcasPorMes(idEmpleado, mes);
        } else {
            listaMarcas = empleadoDAO.obtenerTodasLasMarcas(idEmpleado);
        }

        request.setAttribute("listaMarcas", listaMarcas);
        request.getRequestDispatcher("vistaEmpleado/verMarcasUpdate.jsp").forward(request, response);

     }
           


//-------------------------
//--**
    private void modificarMarca(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            
            System.out.println("louo");
            // Obtener los parámetros enviados desde el formulario
            String idEmpleadoParam = request.getParameter("id_empleado");
            String idMarcaParam = request.getParameter("id_marca");
                      
            // Validar y convertir los parámetros
            int idEmpleado = Integer.parseInt(idEmpleadoParam);
            int idMarca = Integer.parseInt(idMarcaParam);
            
            System.out.println("ide marca " + idMarca);

            // Llamar al DAO para obtener la información de la marca
            Marcas marca = empleadoDAO.obtenerMarcaPorId(idMarca);
                    
            System.out.println("marca id " + marca.getIdMarca());
            System.out.println("marca id " + marca.getMarcaEntrada());
            
            if (marca == null) {
                throw new IllegalArgumentException("No se encontró una marca con el ID especificado.");
            }

            // Guardar los datos en atributos para enviarlos a la página JSP
            request.setAttribute("marca", marca);
            request.setAttribute("id_empleado", idEmpleado);

            // Redirigir a la página de edición
            request.getRequestDispatcher("vistaEmpleado/modificarMarcas.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Ocurrió un error esp: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }

    }
 //---------------------------------------   

    private void guardarModificacion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        
          System.out.println("guardar");
        try {
            // Obtener los parámetros enviados desde el formulario
            String idEmpleadoParam = request.getParameter("id_empleado");
            String idMarcaParam = request.getParameter("id_marca");
            String fechaMarcaParam = request.getParameter("fechaMarca");
            String horaEntradaParam = request.getParameter("horaEntrada");
            String horaSalidaParam = request.getParameter("horaSalida");
            String horaSalidaAlmuerzoParam = request.getParameter("horaSalidaAlmuerzo");
            String horaEntradaAlmuerzoParam = request.getParameter("horaEntradaAlmuerzo");
            
            // Validar y convertir parámetros
            int idEmpleado = Integer.parseInt(idEmpleadoParam);
            int idMarca = Integer.parseInt(idMarcaParam);

            // Crear el formato de fecha y hora para convertir las cadenas a Date y Time
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

            // Crear un objeto Marca con los datos
            Marcas marca = new Marcas();
            marca.setIdMarca(idMarca);
            marca.setFechaMarca(dateFormat.parse(fechaMarcaParam));
            marca.setMarcaEntrada(new Time(timeFormat.parse(horaEntradaParam).getTime()));
            marca.setMarcaSalida(new Time(timeFormat.parse(horaSalidaParam).getTime()));
            marca.setMarcaSalidaAlmuerzo(new Time(timeFormat.parse(horaSalidaAlmuerzoParam).getTime()));
            marca.setMarcaEntradaAlmuerzo(new Time(timeFormat.parse(horaEntradaAlmuerzoParam).getTime()));


            // Llamar al método del DAO para guardar la modificación
            boolean exito = empleadoDAO.modificarMarca(marca);

            // Verificar el resultado y redirigir
            if (exito) {
                // Redirigir al listado de marcas con un mensaje de éxito
                request.setAttribute("mensaje", "Marca modificada exitosamente.");
                request.getRequestDispatcher("pages/exitoMarcasUpdate.jsp")
                        .forward(request, response);
            } else {
                // Mostrar un error si no se pudo modificar
                request.setAttribute("error", "No se pudo modificar la marca.");
                request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Manejar errores y redirigir a una página de error
            request.setAttribute("error", "Ocurrió un error al guardar la modificación: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/error.jsp").forward(request, response);
        }
    }



//-------------------


//----------------------------------------------
}