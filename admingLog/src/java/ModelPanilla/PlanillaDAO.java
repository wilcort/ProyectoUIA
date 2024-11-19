/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelPanilla;

import Configur.Conexion;
import Model.Cargo;
import Model.Colaborador;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;



public class PlanillaDAO {
    Connection conexion;

    public PlanillaDAO() {
        Conexion conex = new Conexion();
        conexion = conex.getConectar();
    }
//---------------------------------------   
   public double[] salarioHora(int idEmpleado) {
    PreparedStatement ps = null;
    ResultSet rs = null;
    double[] salarioHora = new double[3];  // Array to store normal hourly wage, extra hourly wage, and base salary

    try {
        String query = "SELECT e.nombre AS nombre_empleado, "
                + "e.apellido_1, e.apellido_2, "
                + "c.nombre_cargo, c.salario AS salario_cargo "
                + "FROM empleado e "
                + "INNER JOIN cargo c ON e.id_cargo = c.id_cargo "
                + "WHERE e.id_empleado = ?";

        ps = conexion.prepareStatement(query);
        ps.setInt(1, idEmpleado);
        rs = ps.executeQuery();

        if (rs.next()) {
            double salarioCargo = rs.getDouble("salario_cargo");

            // Calcula el salario por hora
            double salarioHoraNormal = (salarioCargo / 30) / 8;  // Assuming 30 working days and 8 hours a day
            double salarioHoraExtra = salarioHoraNormal * 1.5;  // Extra hourly wage is 1.5 times normal hourly wage

          
            // Asigna los valores al arreglo
            salarioHora[0] = salarioCargo;       // Base salary
            salarioHora[1] = salarioHoraNormal; // Salario por hora
            salarioHora[2] = salarioHoraExtra;  // Salario por hora extra
        } else {
            System.out.println("No se encontró un empleado con ese ID.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return salarioHora;  // Retornamos el array con los valores calculados
}

//-------------------------------------------------
    public void incapacidadCalculoQuincena(int idEmpleado) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String query = "SELECT "
                    + "    i.id_incapacidad, "
                    + "    i.motivo, "
                    + "    i.fecha_inicio, "
                    + "    i.fecha_fin, "
                    + "    i.dias_incapacidad, "
                    + "    i.estado, "
                    + "    i.empleado_id_empleado, "
                    + "    CASE "
                    + "        WHEN im.id_incapacidad IS NOT NULL THEN 'Maternidad' "
                    + "        WHEN iec.id_incapacidad IS NOT NULL THEN 'Enfermedad Común' "
                    + "        ELSE 'Desconocido' "
                    + "    END AS tipo_incapacidad "
                    + "FROM "
                    + "    incapacidades i "
                    + "LEFT JOIN "
                    + "    incapacidad_maternidad im ON i.id_incapacidad = im.id_incapacidad "
                    + "LEFT JOIN "
                    + "    incapacidad_enfermedad_comun iec ON i.id_incapacidad = iec.id_incapacidad "
                    + "WHERE "
                    + "    i.empleado_id_empleado = ?";

            ps = conexion.prepareStatement(query);
            ps.setInt(1, idEmpleado);
            rs = ps.executeQuery();

            boolean tieneAprobadas = false;

            while (rs.next()) {
                String estado = rs.getString("estado");
                String motivo = rs.getString("motivo");
                int diasIncapacidad = rs.getInt("dias_incapacidad");
                String tipoIncapacidad = rs.getString("tipo_incapacidad");
                Date fechaInicio = rs.getDate("fecha_inicio");
                Date fechaFin = rs.getDate("fecha_fin");

                if ("Aprobada".equalsIgnoreCase(estado)) {
                    tieneAprobadas = true;

                    // Filtrar los días dentro del rango del 1 al 15
                    int diasQuincena1 = calcularDiasQuincena1(fechaInicio, fechaFin);

                    // Si hay días en la primera quincena, calcula el pago
                    if (diasQuincena1 > 0) {
                        double pagoIncapacidad = calcularPagoIncapacidad(idEmpleado, diasQuincena1, motivo);
                                           
                        // Imprimir resultados
                        System.out.println("Motivo: " + motivo);
                        System.out.println("Tipo: " + tipoIncapacidad);
                        System.out.println("Días en Quincena 1 (1-15): " + diasQuincena1);
                        System.out.println("Pago por Incapacidad: " + pagoIncapacidad);
                        System.out.println("Fecha de Inicio: " + fechaInicio);
                        System.out.println("Fecha de Fin: " + fechaFin);
                        System.out.println("-------------------------");
                    }
                }
            }

            if (!tieneAprobadas) {
                System.out.println("No hay incapacidades aprobadas para este empleado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
//-----------------------------------------------------------------
    // Método para obtener el pago de incapacidades en la quincena
    public double obtenerPagoIncapacidades(int idEmpleado, int anio, int mes) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        double totalPagoIncapacidades = 0.0;

        try {
            String query = "SELECT "
                    + "i.id_incapacidad, "
                    + "i.dias_incapacidad, "
                    + "i.estado, "
                    + "CASE "
                    + "    WHEN im.id_incapacidad IS NOT NULL THEN 'Maternidad' "
                    + "    WHEN iec.id_incapacidad IS NOT NULL THEN 'Enfermedad Común' "
                    + "    ELSE 'Desconocido' "
                    + "END AS tipo_incapacidad, "
                    + "i.fecha_inicio, "
                    + "i.fecha_fin "
                    + "FROM incapacidades i "
                    + "LEFT JOIN incapacidad_maternidad im ON i.id_incapacidad = im.id_incapacidad "
                    + "LEFT JOIN incapacidad_enfermedad_comun iec ON i.id_incapacidad = iec.id_incapacidad "
                    + "WHERE i.empleado_id_empleado = ? AND i.estado = 'Aprobada' "
                    + "AND YEAR(i.fecha_inicio) = ? AND MONTH(i.fecha_inicio) = ?";

            ps = conexion.prepareStatement(query);
            ps.setInt(1, idEmpleado);
            ps.setInt(2, anio);
            ps.setInt(3, mes);
            rs = ps.executeQuery();

            while (rs.next()) {
                Date fechaInicio = rs.getDate("fecha_inicio");
                Date fechaFin = rs.getDate("fecha_fin");
                String tipoIncapacidad = rs.getString("tipo_incapacidad");

                // Filtra los días de incapacidad que caen en la quincena (1-15 del mes)
                int diasQuincena1 = calcularDiasQuincena1(fechaInicio, fechaFin);

                if (diasQuincena1 > 0) {
                    // Calcula el pago de la incapacidad para los días en la quincena
                    double pagoIncapacidad = calcularPagoIncapacidad(idEmpleado, diasQuincena1, tipoIncapacidad);
                    totalPagoIncapacidades += pagoIncapacidad;

                    System.out.println("Pago por incapacidad calculado: " + pagoIncapacidad);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return totalPagoIncapacidades;
    }
 //-------------------------------------------------------------------   
 // Método para obtener el pago de incapacidades en la quincena
    public double obtenerPagoIncapacidadesMes(int idEmpleado, int anio, int mes) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        double totalPagoIncapacidades = 0.0;

        try {
            String query = "SELECT "
                    + "i.id_incapacidad, "
                    + "i.dias_incapacidad, "
                    + "i.estado, "
                    + "CASE "
                    + "    WHEN im.id_incapacidad IS NOT NULL THEN 'Maternidad' "
                    + "    WHEN iec.id_incapacidad IS NOT NULL THEN 'Enfermedad Común' "
                    + "    ELSE 'Desconocido' "
                    + "END AS tipo_incapacidad, "
                    + "i.fecha_inicio, "
                    + "i.fecha_fin "
                    + "FROM incapacidades i "
                    + "LEFT JOIN incapacidad_maternidad im ON i.id_incapacidad = im.id_incapacidad "
                    + "LEFT JOIN incapacidad_enfermedad_comun iec ON i.id_incapacidad = iec.id_incapacidad "
                    + "WHERE i.empleado_id_empleado = ? AND i.estado = 'Aprobada' "
                    + "AND YEAR(i.fecha_inicio) = ? AND MONTH(i.fecha_inicio) = ?";

            ps = conexion.prepareStatement(query);
            ps.setInt(1, idEmpleado);
            ps.setInt(2, anio);
            ps.setInt(3, mes);
            rs = ps.executeQuery();

            while (rs.next()) {
                Date fechaInicio = rs.getDate("fecha_inicio");
                Date fechaFin = rs.getDate("fecha_fin");
                String tipoIncapacidad = rs.getString("tipo_incapacidad");

                // Filtra los días de incapacidad que caen en la quincena (16-31 del mes)
                int diasQuincena2 = calcularDiasQuincena2(fechaInicio, fechaFin);  // Aquí usamos calcularDiasQuincena2

                if (diasQuincena2 > 0) {
                    // Calcula el pago de la incapacidad para los días en la quincena
                    double pagoIncapacidad = calcularPagoIncapacidad(idEmpleado, diasQuincena2, tipoIncapacidad);
                    totalPagoIncapacidades += pagoIncapacidad;

                    System.out.println("Pago por incapacidad calculado: " + pagoIncapacidad);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return totalPagoIncapacidades;
    }
//------------------------------------------------------------------
    // Método auxiliar para calcular los días en la quincena 1 (1 al 15)
    private int calcularDiasQuincena1(Date fechaInicio, Date fechaFin) {
        // Convertir java.sql.Date a LocalDate
        LocalDate inicio = fechaInicio.toLocalDate();
        LocalDate fin = fechaFin.toLocalDate();

        // Definir los límites de la quincena 1
        LocalDate inicioQuincena1 = LocalDate.of(inicio.getYear(), inicio.getMonthValue(), 1);
        LocalDate finQuincena1 = LocalDate.of(inicio.getYear(), inicio.getMonthValue(), 15);

        // Ajustar las fechas de la incapacidad para que no salgan fuera del rango de la quincena
        LocalDate inicioAjustado = inicio.isBefore(inicioQuincena1) ? inicioQuincena1 : inicio;
        LocalDate finAjustado = fin.isAfter(finQuincena1) ? finQuincena1 : fin;

        // Si el rango de fechas de incapacidad se solapa con la quincena 1
        if (!inicioAjustado.isAfter(finAjustado)) {
            // Calculamos la cantidad de días dentro de la quincena
            return (int) ChronoUnit.DAYS.between(inicioAjustado, finAjustado) + 1;  // +1 porque es inclusivo
        }

        // Si no hay solapamiento, devolver 0 días
        return 0;
    }
 //------------------------------------------
    private int calcularDiasQuincena2(Date fechaInicio, Date fechaFin) {
        // Convertir java.sql.Date a LocalDate
        LocalDate inicio = fechaInicio.toLocalDate();
        LocalDate fin = fechaFin.toLocalDate();

        // Definir los límites de la segunda quincena (16-último día del mes)
        LocalDate inicioQuincena2 = LocalDate.of(inicio.getYear(), inicio.getMonthValue(), 16);
        // Utilizar YearMonth para obtener el último día del mes
        LocalDate finQuincena2 = YearMonth.of(inicio.getYear(), inicio.getMonthValue()).atEndOfMonth();

        // Ajustar las fechas de la incapacidad para que no salgan fuera del rango de la quincena
        LocalDate inicioAjustado = inicio.isBefore(inicioQuincena2) ? inicioQuincena2 : inicio;
        LocalDate finAjustado = fin.isAfter(finQuincena2) ? finQuincena2 : fin;

        // Si el rango de fechas de incapacidad se solapa con la quincena (16-último día del mes)
        if (!inicioAjustado.isAfter(finAjustado)) {
            // Calculamos la cantidad de días dentro de la quincena
            return (int) ChronoUnit.DAYS.between(inicioAjustado, finAjustado) + 1;  // +1 porque es inclusivo
        }

        // Si no hay solapamiento, devolver 0 días
        return 0;
    }
 //--------------------------------------
    public double calcularPagoIncapacidad(int idEmpleado, int diasIncapacidad, String motivo) {
        // Obtén el salario y tarifas por hora
        double[] salarioHora = salarioHora(idEmpleado);
        double salarioBase = salarioHora[0];
        double salarioHoraNormal = salarioHora[1];

        // Valida que los datos de salario sean correctos
        if (salarioBase == 0 || salarioHoraNormal == 0) {
            System.out.println("No se pudo calcular el salario por hora. Verifique los datos del empleado.");
            return 0;
        }

        double pagoEmpleador = 0;

        // Verifica si el motivo es maternidad
        if ("Maternidad".equalsIgnoreCase(motivo)) {
            // Cálculo especial para maternidad: Empleador paga el 50% del salario por cada día
            pagoEmpleador = diasIncapacidad * salarioHoraNormal * 8 * 0.5;
            System.out.println("Pago por incapacidad de maternidad (50% del salario): " + pagoEmpleador);
            return pagoEmpleador;
        }

        // Cálculo general para otros tipos de incapacidades
        if (diasIncapacidad <= 3) {
            // Si la incapacidad es de 3 días o menos, el empleador paga el 50%
            pagoEmpleador = diasIncapacidad * salarioHoraNormal * 8 * 0.5;
        } else {
            // Primeros 3 días paga el empleador al 50%
            pagoEmpleador = 3 * salarioHoraNormal * 8 * 0.5;

            // A partir del cuarto día, el empleador paga el 40%
            int diasDesdeCuarto = diasIncapacidad - 3;
            pagoEmpleador += diasDesdeCuarto * salarioHoraNormal * 8 * 0.4;
        }

        // Impresión de resultados
        System.out.println("Pago Empleador (50% primeros 3 días + 40% a partir del 4° día): " + pagoEmpleador);

        // Retorna el pago total calculado
        return pagoEmpleador;
    }

//-------------------------------------------------------------------------    
    public void actualizarOCrearReporteQuincenal(int idEmpleado, int mesSeleccionado, int anioSeleccionado) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Verificar si ya existe un reporte para el mes y año seleccionados
            String queryVerificar = "SELECT id_planilla FROM planilla "
                    + "WHERE YEAR(mes_pago) = ? AND MONTH(mes_pago) = ? "
                    + "AND empleado_id_empleado = ?";
            
            ps = conexion.prepareStatement(queryVerificar);
            ps.setInt(1, anioSeleccionado);
            ps.setInt(2, mesSeleccionado);
            ps.setInt(3, idEmpleado);
            rs = ps.executeQuery();

            boolean reporteExiste = rs.next();
            int idPlanilla = reporteExiste ? rs.getInt("id_planilla") : -1;

            // Consulta para calcular horas
            String queryHoras = "SELECT "
                    + "SUM(horas_dia_normal) AS total_horas_normales, "
                    + "SUM(horas_dia_extra) AS total_horas_extras "
                    + "FROM marcas "
                    + "WHERE DAY(fecha_marca) BETWEEN 1 AND 15 "
                    + "AND id_empleado = ? "
                    + "AND YEAR(fecha_marca) = ? "
                    + "AND MONTH(fecha_marca) = ?";
            ps = conexion.prepareStatement(queryHoras);
            ps.setInt(1, idEmpleado);
            ps.setInt(2, anioSeleccionado);
            ps.setInt(3, mesSeleccionado);
            rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("No hay datos para el mes y año seleccionados.");
                return;
            }

            double totalHorasNormales = rs.getDouble("total_horas_normales");
            double totalHorasExtras = rs.getDouble("total_horas_extras");

            // Obtener salario por hora
            double[] salarioPorHora = salarioHora(idEmpleado);
            double salarioCargo = salarioPorHora[0];
            double salarioHoraNormal = salarioPorHora[1];
            double salarioHoraExtra = salarioPorHora[2];

            // Obtener pago por incapacidades
            double pagoIncapacidades = obtenerPagoIncapacidades(idEmpleado, anioSeleccionado, mesSeleccionado);

            // Asignar fecha de pago
            String mesPago = String.format("%d-%02d-01", anioSeleccionado, mesSeleccionado);
            java.sql.Date dateMesPago = java.sql.Date.valueOf(mesPago);

            if (reporteExiste) {
                // Actualizar reporte existente
                String queryActualizar = "UPDATE planilla SET "
                        + "salario_bruto = ?, salario_horas_extra = ?, "
                        + "salario_horas_regulares = ?, "
                        + "horas_extra = ?, horas_regulares = ?,"
                        + " pago_incapacidades = ?, mes_pago = ? "
                        + "WHERE id_planilla = ?";
                
                ps = conexion.prepareStatement(queryActualizar);
                ps.setDouble(1, salarioCargo);
                ps.setDouble(2, salarioHoraExtra);
                ps.setDouble(3, salarioHoraNormal);
                ps.setDouble(4, totalHorasExtras);
                ps.setDouble(5, totalHorasNormales);
                ps.setDouble(6, pagoIncapacidades);
                ps.setDate(7, dateMesPago);
                ps.setInt(8, idPlanilla);

                int filasActualizadas = ps.executeUpdate();
                System.out.println(filasActualizadas > 0 ? "Reporte actualizado correctamente." : "Error al actualizar el reporte.");
            } else {
                // Crear un nuevo reporte
                String queryInsertar = "INSERT INTO planilla (empleado_id_empleado, "
                        + "salario_bruto, salario_horas_extra, "
                        + "salario_horas_regulares, "
                        + "horas_extra, horas_regulares, "
                        + "pago_incapacidades, mes_pago, periodo) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
                ps = conexion.prepareStatement(queryInsertar);
                ps.setInt(1, idEmpleado);
                ps.setDouble(2, salarioCargo);
                ps.setDouble(3, salarioHoraExtra);
                ps.setDouble(4, salarioHoraNormal);
                ps.setDouble(5, totalHorasExtras);
                ps.setDouble(6, totalHorasNormales);
                ps.setDouble(7, pagoIncapacidades);
                ps.setDate(8, dateMesPago);
                ps.setString(9, "Quincenal");

                int filasInsertadas = ps.executeUpdate();
                System.out.println(filasInsertadas > 0 ? "Reporte creado correctamente." : "Error al crear el reporte.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
//----------------------------------------------------------
 
//------------------------------------------------------------
    public void actualizarOCrearReporteMensual(int idEmpleado, int mesSeleccionado, int anioSeleccionado) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Consultar horas del 16 al 31
            String queryHoras = "SELECT "
                    + "SUM(horas_dia_normal) AS total_horas_normales, "
                    + "SUM(horas_dia_extra) AS total_horas_extras "
                    + "FROM marcas "
                    + "WHERE DAY(fecha_marca) BETWEEN 16 AND 31 "
                    + "AND id_empleado = ? "
                    + "AND YEAR(fecha_marca) = ? "
                    + "AND MONTH(fecha_marca) = ?";

            ps = conexion.prepareStatement(queryHoras);
            ps.setInt(1, idEmpleado);
            ps.setInt(2, anioSeleccionado);
            ps.setInt(3, mesSeleccionado);
            rs = ps.executeQuery();

            if (!rs.next()) {
                System.out.println("No hay datos para la segunda quincena.");
                return;
            }

            double totalHorasNormalesMensual = rs.getDouble("total_horas_normales");
            double totalHorasExtrasMensual = rs.getDouble("total_horas_extras");

            // Obtener salario por hora
            double[] salarioPorHora = salarioHora(idEmpleado);
            double salarioCargo = salarioPorHora[0];
            double salarioHoraNormal = salarioPorHora[1];
            double salarioHoraExtra = salarioPorHora[2];

            // Obtener pago por incapacidades
            double pagoIncapacidades = obtenerPagoIncapacidadesMes(idEmpleado, anioSeleccionado, mesSeleccionado);

            // Asignar fecha de pago (16 al 31)
            String mesPagoMensual = String.format("%d-%02d-16", anioSeleccionado, mesSeleccionado);
            java.sql.Date dateMesPagoMensual = java.sql.Date.valueOf(mesPagoMensual);

            // Verificar si ya existe un reporte mensual
            String queryVerificarMensual = "SELECT id_planilla FROM planilla "
                    + "WHERE YEAR(mes_pago) = ? AND MONTH(mes_pago) = ? "
                    + "AND empleado_id_empleado = ? AND periodo = 'Mensual'";

            ps = conexion.prepareStatement(queryVerificarMensual);
            ps.setInt(1, anioSeleccionado);
            ps.setInt(2, mesSeleccionado);
            ps.setInt(3, idEmpleado);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Actualizar reporte mensual existente
                int idPlanillaMensual = rs.getInt("id_planilla");
                String queryActualizarMensual = "UPDATE planilla SET "
                        + "salario_bruto = ?, salario_horas_extra = ?, "
                        + "salario_horas_regulares = ?, "
                        + "horas_extra = ?, horas_regulares = ?, "
                        + "pago_incapacidades = ?, mes_pago = ? "
                        + "WHERE id_planilla = ?";

                ps = conexion.prepareStatement(queryActualizarMensual);
                ps.setDouble(1, salarioCargo);
                ps.setDouble(2, salarioHoraExtra);
                ps.setDouble(3, salarioHoraNormal);
                ps.setDouble(4, totalHorasExtrasMensual);
                ps.setDouble(5, totalHorasNormalesMensual);
                ps.setDouble(6, pagoIncapacidades);
                ps.setDate(7, dateMesPagoMensual);
                ps.setInt(8, idPlanillaMensual);

                int filasActualizadas = ps.executeUpdate();
                System.out.println(filasActualizadas > 0 ? "Reporte mensual actualizado correctamente." : "Error al actualizar el reporte mensual.");
            } else {
                // Crear nuevo reporte mensual
                String queryInsertarMensual = "INSERT INTO planilla "
                        + "(empleado_id_empleado, salario_bruto, salario_horas_extra, salario_horas_regulares, "
                        + "horas_extra, horas_regulares, pago_incapacidades, mes_pago, periodo) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

                ps = conexion.prepareStatement(queryInsertarMensual);
                ps.setInt(1, idEmpleado);
                ps.setDouble(2, salarioCargo);
                ps.setDouble(3, salarioHoraExtra);
                ps.setDouble(4, salarioHoraNormal);
                ps.setDouble(5, totalHorasExtrasMensual);
                ps.setDouble(6, totalHorasNormalesMensual);
                ps.setDouble(7, pagoIncapacidades);
                ps.setDate(8, dateMesPagoMensual);
                ps.setString(9, "Mensual");

                int filasInsertadas = ps.executeUpdate();
                System.out.println(filasInsertadas > 0 ? "Reporte mensual creado correctamente." : "Error al crear el reporte mensual.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
 //------------------------------------------------------------------   
//---------------------------------------------------------------------
    
//--------------------- INGRESAR DATOS A PLANILLA ------------------------------    
    public boolean ingresarDatos(int idEmpleado, Planilla planilla) {

        PreparedStatement ps = null;

        try {
            String query = "INSERT INTO planilla "
                    + "(periodo,"
                    + " salario_bruto, "
                    + "deducciones_CCSS, "
                    + "pago_incapacidades, "
                    + "pago_vacaciones, "
                    + "deducciones_impuestos, "
                    + "salario_horas_extra, "
                    + "salario_horas_regulares, "
                    + "horas_extra, "
                    + "horas_regulares, "
                    + "salario_neto, "
                    + "mes_pago, "
                    + "id_empleado) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            ps = conexion.prepareStatement(query);

            ps.setString(1, planilla.getPeriodo());
            ps.setDouble(2, planilla.getSalarioBruto());
            ps.setDouble(3, planilla.getDeduccionesCCSS());
            ps.setDouble(4, planilla.getPagoIncapacidades());
            ps.setDouble(5, planilla.getPagoVacaciones());
            ps.setDouble(6, planilla.getDeduccionesImpuestos());
            ps.setDouble(7, planilla.getSalarioHorasExtra());
            ps.setDouble(8, planilla.getSalarioHorasRegulares());
            ps.setDouble(9, planilla.getHorasExtra());
            ps.setDouble(10, planilla.getHorasRegulares());
            ps.setDouble(11, planilla.getSalarioNeto());
            
            // Si la fechaPago es null, no la pasamos
            if (planilla.getMesPago() != null) {
                ps.setDate(12, new java.sql.Date(planilla.getMesPago().getTime()));
            } else {
                ps.setNull(12, java.sql.Types.DATE);  // Aquí usamos setNull para permitir que la fecha sea null en la base de datos
            }

            ps.setInt(13, idEmpleado);
            
            

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Datos insertados correctamente en la planilla.");
                return true;
            } else {
                System.out.println("Error al insertar los datos en la planilla.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


//-----------------------
}
