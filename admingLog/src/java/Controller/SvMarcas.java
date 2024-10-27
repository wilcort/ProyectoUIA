package Controller;

import ControllerEmpleado.SvMostrarDatos;
import Model.ColaboradorDAO;
import ModelEmpleado.EmpleadoDAO;
import ModelEmpleado.Marcas;
import java.text.SimpleDateFormat;
import java.sql.Time;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;

@WebServlet(name = "SvMarcas", urlPatterns = {"/SvMarcas"})
public class SvMarcas extends HttpServlet {

    private EmpleadoDAO empleadoDAO;
    private ColaboradorDAO colaboradorDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        empleadoDAO = new EmpleadoDAO();
        colaboradorDAO = new ColaboradorDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String accion = request.getParameter("accion");

        if ("ver_marcas".equals(accion)) {
            ver_marcas(request, response);
        } else {
            // Si la acción no coincide, redirige a una página de error
            response.sendRedirect("/WEB-INF/error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");

        try {
            if ("ver_marcas".equals(accion)) {
                ver_marcas(request, response);
            } else if ("modificar_Marca".equals(accion)) {
                modificar_Marca(request, response);
            } else if ("actualizar_Marca".equals(accion)) {
                actualizar_Marca(request, response);
            } else {
                response.sendRedirect("/WEB-INF/error.jsp");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Log de errores
            response.sendRedirect("/WEB-INF/error.jsp");
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    // Método para obtener las marcas del empleado por mes y año
    private void ver_marcas(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Obtener el ID del empleado y verificar que no sea null ni esté vacío
        String idEmpleadoStr = request.getParameter("id_empleado");
        if (idEmpleadoStr == null || idEmpleadoStr.isEmpty()) {
            throw new IllegalArgumentException("ID del empleado no proporcionado.");
        }
        int idEmpleado = Integer.parseInt(idEmpleadoStr);

        // Obtener el mes y año para filtrar
        String mesStr = request.getParameter("mes");
        String anioStr = request.getParameter("anio");

        int mes = mesStr != null && !mesStr.isEmpty() ? Integer.parseInt(mesStr) : 0; // Valor por defecto 0
        int anio = anioStr != null && !anioStr.isEmpty() ? Integer.parseInt(anioStr) : 0; // Valor por defecto 0

        try {
            // Obtener la lista de marcas filtradas por mes y año
            List<Marcas> listaMarcas = empleadoDAO.obtenerMarcasPorMes(idEmpleado, mes, anio);
            List<Marcas> todasLasMarcas = empleadoDAO.obtenerTodasLasMarcas(idEmpleado);

            // Setear atributos para la JSP
            request.setAttribute("listaMarcas", listaMarcas);
            double totalHorasMes = calcularTotalHoras(listaMarcas);
            request.setAttribute("totalHorasMes", totalHorasMes);

            request.setAttribute("todasLasMarcas", todasLasMarcas);
            double totalHoras = calcularTotalHoras(todasLasMarcas);
            request.setAttribute("totalHoras", totalHoras);

            // Redirigir a la vista
            request.getRequestDispatcher("vistaAdmin/verMarcasEmpleado.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace(); // Imprimir el error en el log
            request.setAttribute("error", "Error al obtener las marcas del empleado.");
            request.getRequestDispatcher("vistaAdmin/verMarcasEmpleado.jsp").forward(request, response);
        }
    }

    // Método para calcular el total de horas trabajadas
    private double calcularTotalHoras(List<Marcas> listaMarcas) {
        double totalHoras = 0.0;
        for (Marcas marca : listaMarcas) {
            totalHoras += marca.getHorasDia();
        }
        return totalHoras;
    }

//-------------------------- MODIFICAR MARACAS --------------------
//---------------------------------------------------------------
    private void modificar_Marca(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {

        try {
            int idMarca = Integer.parseInt(request.getParameter("id_marca"));
            
            String idEmpleadoStr = request.getParameter("id_empleado");

            System.out.println("id marca: " + idMarca);

            Marcas verMarca = empleadoDAO.verSoloMarca(idMarca);

            // Desplegar información de la marca en consola
            if (verMarca != null) {
                System.out.println("Marca encontrada:");
                System.out.println("Fecha de Marca: " + verMarca.getFechaMarca());
                System.out.println("Hora de Entrada: " + verMarca.getMarcaEntrada());
                System.out.println("Hora de Salida: " + verMarca.getMarcaSalida());
                System.out.println("Hora de Entrada a Almuerzo: " + verMarca.getMarcaEntradaAlmuerzo());
                System.out.println("Hora de Salida de Almuerzo: " + verMarca.getMarcaSalidaAlmuerzo());

                // Pasar el objeto a la JSP si es necesario
                request.setAttribute("varMarca", verMarca);
                request.getRequestDispatcher("vistaAdmin/modificarMarcas.jsp").forward(request, response);
            } else {
                System.out.println("No se encontró una marca con el ID especificado.");
            }

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
//-------------------------------------------------------------------------------------//
//------------------------------ ACTUALIZAR  -----------------------------------------//

    private void actualizar_Marca(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String idMarcaStr = request.getParameter("id_marca");
            String idEmpleadoStr = request.getParameter("id_empleado");

            // Validación de entrada
            if (idMarcaStr == null || idMarcaStr.isEmpty()) {
                throw new IllegalArgumentException("ID de Marca no proporcionado.");
            }

            int idMarca = Integer.parseInt(idMarcaStr);
            int idEmpleado = Integer.parseInt(idEmpleadoStr);
            
             System.out.println("id emplerado marcasd " + idEmpleado);
            
            // Obtener la marca existente
            Marcas marcaExistente = empleadoDAO.verSoloMarca(idMarca);
            if (marcaExistente == null) {
                throw new IllegalArgumentException("Marca no encontrada.");
            }

            // Inicializar fechaMarca
            Date fechaMarca = marcaExistente.getFechaMarca();
            if (fechaMarca == null) {
                throw new IllegalArgumentException("La fecha de la marca no puede ser nula.");
            }

            // Obtener los parámetros de la solicitud
            String marcaEntradaStr = request.getParameter("hora_entrada");
            String marcaSalidaStr = request.getParameter("hora_salida");
            String marcaSalidaAlmuerzoStr = request.getParameter("hora_salida_almuerzo");
            String marcaEntradaAlmuerzoStr = request.getParameter("hora_entrada_almuerzo");

            // Usar el método parseTime, asegurando que no sea nulo
            Time hora_entrada = parseTime(marcaEntradaStr);
            Time hora_salida = parseTime(marcaSalidaStr);
            Time hora_salida_almuerzo = parseTime(marcaSalidaAlmuerzoStr);
            Time hora_entrada_almuerzo = parseTime(marcaEntradaAlmuerzoStr);

            // Crear un nuevo objeto Marcas con los valores actualizados
            Marcas marcaCorregida = new Marcas();
            marcaCorregida.setIdMarca(idMarca); // Asignar el ID de marca
            marcaCorregida.setFechaMarca(fechaMarca);

            // Usar valores existentes si son nulos
            marcaCorregida.setMarcaEntrada(hora_entrada != null ? hora_entrada : marcaExistente.getMarcaEntrada());
            marcaCorregida.setMarcaSalida(hora_salida != null ? hora_salida : marcaExistente.getMarcaSalida());
            marcaCorregida.setMarcaSalidaAlmuerzo(hora_salida_almuerzo != null ? hora_salida_almuerzo : marcaExistente.getMarcaSalidaAlmuerzo());
            marcaCorregida.setMarcaEntradaAlmuerzo(hora_entrada_almuerzo != null ? hora_entrada_almuerzo : marcaExistente.getMarcaEntradaAlmuerzo());
            marcaCorregida.setIdEmpleado(idEmpleado); // Asegúrate de asignar el idEmpleado
            
           
            
            // Calcular las horas trabajadas
            double horasTrabajadas = calcularHorasTrabajadas(marcaCorregida);
            marcaCorregida.setHorasTabajadasDia(horasTrabajadas); // Asegúrate de que el método setHorasDia exista

            // Actualizar la marca en la base de datos
            boolean exito = empleadoDAO.correcionMarcas(marcaCorregida);

            if (exito) {
                response.sendRedirect("SvMarcas?accion=ver_marcas&id_empleado=" + idEmpleado);
            } else {
                response.getWriter().write("No se pudo actualizar la marca.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("/WEB-INF/error.jsp");
        }
    }

//-----------------------------------
    public Time parseTime(String timeStr) {
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

//--------------------------------------------------------------
    private double calcularHorasTrabajadas(Marcas marca) {
        Time entrada = marca.getMarcaEntrada();
        Time salida = marca.getMarcaSalida();
        Time salidaAlmuerzo = marca.getMarcaSalidaAlmuerzo();
        Time entradaAlmuerzo = marca.getMarcaEntradaAlmuerzo();

        if (entrada == null || salida == null) {
            return 0.0; // Si no hay horas de entrada o salida, no hay horas trabajadas
        }

        // Cálculo de horas trabajadas
        long horasTrabajadas = 0;

        // Calcular horas entre entrada y salida
        long totalHoras = salida.getTime() - entrada.getTime();

        // Calcular horas de almuerzo
        long horasAlmuerzo = 0;
        if (salidaAlmuerzo != null && entradaAlmuerzo != null) {
            horasAlmuerzo = entradaAlmuerzo.getTime() - salidaAlmuerzo.getTime();
        }

        // Ajustar el total restando las horas de almuerzo
        totalHoras -= horasAlmuerzo;

        // Convertir a horas
        horasTrabajadas = totalHoras / (1000 * 60 * 60); // Convertir de milisegundos a horas

        return horasTrabajadas; // Retorna el total de horas trabajadas
    }
//------------------------------------------------------------ 
}
