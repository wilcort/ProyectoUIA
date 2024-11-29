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
import java.util.stream.IntStream;

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
//---------------------------------------------------------------------------------------
                 
        
                // Número de horas trabajadas al mes (48 horas por semana, 4.33 semanas al mes)
                double horasPorMes = 48 * 2;
                
                // Calcula el salario por hora
                double salarioHoraNormal = salarioCargo/ 2 / horasPorMes;
                
                // Calcula el salario por hora extra (1.5 veces el salario normal)
                double salarioHoraExtra = salarioHoraNormal * 1.5;
                
        
//-----------------------------------------------------------------------------------                
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
//----------------------------------------------------------------
// Método para obtener el pago de incapacidades en la quincena

    public double obtenerVacacionesQuincena(int idEmpleado, int anio, int mes) {
        double totalPagoVacaciones = 0.0;

        System.out.println("hola");

        String query = "SELECT v.id_vacacion, "
                + "v.fecha_solicitud, "
                + "v.fecha_inicio, v.fecha_fin, "
                + "v.dias_vacaciones_solicitados "
                + "FROM vacaciones v "
                + "WHERE v.empleado_id_empleado = ? "
                + "AND v.estado_solicitud = 'Aprobada' "
                + "AND YEAR(v.fecha_inicio) = ? "
                + "AND MONTH(v.fecha_inicio) = ?";

        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, idEmpleado);
            ps.setInt(2, anio);
            ps.setInt(3, mes);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Date fechaInicio = rs.getDate("fecha_inicio");
                    Date fechaFin = rs.getDate("fecha_fin");
                    int diasSolicitados = rs.getInt("dias_vacaciones_solicitados");

                    System.out.println("fecha fin " + fechaFin);
                    System.out.println("fecha inicio " + fechaInicio);
                    System.out.println("dias slicitads " + diasSolicitados);

                    int diasVacaciones1 = calcularDiasQuincena1(fechaInicio, fechaFin);
                    System.out.println(" diasVacaciones1 : " + diasVacaciones1);

                    if (diasVacaciones1 > 0) {
                        double pagoVacaciones = calcularPagoVacaciones(idEmpleado, diasVacaciones1);
                        totalPagoVacaciones += pagoVacaciones;
                        System.out.println("Pago por vacaciones calculado: " + pagoVacaciones);
                    } else {

                        System.out.println(" NO HAY VACACIONES EN PERIODO QUINCENAL");

                    }

                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las vacaciones: " + e.getMessage());
            e.printStackTrace();
        }

        return totalPagoVacaciones;
    }

//-------------------------------------------------------------------   
// Método para obtener el pago de incapacidades en la mensual
    public double obtenerVacacionesMensual(int idEmpleado, int anio, int mes) {
        double totalPagoVacacionesMes = 0.0;

        System.out.println("hola");

        String query = "SELECT v.id_vacacion, "
                + "v.fecha_solicitud, "
                + "v.fecha_inicio, v.fecha_fin, "
                + "v.dias_vacaciones_solicitados "
                + "FROM vacaciones v "
                + "WHERE v.empleado_id_empleado = ? "
                + "AND v.estado_solicitud = 'Aprobada' "
                + "AND YEAR(v.fecha_inicio) = ? "
                + "AND MONTH(v.fecha_inicio) = ?";

        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, idEmpleado);
            ps.setInt(2, anio);
            ps.setInt(3, mes);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Date fechaInicio = rs.getDate("fecha_inicio");
                    Date fechaFin = rs.getDate("fecha_fin");
                    int diasSolicitados = rs.getInt("dias_vacaciones_solicitados");

                    System.out.println("fecha fin " + fechaFin);
                    System.out.println("fecha inicio " + fechaInicio);
                    System.out.println("dias slicitads " + diasSolicitados);

                    int diasVacaciones2 = calcularDiasQuincena2(fechaInicio, fechaFin);
                    System.out.println(" diasVacaciones2 : " + diasVacaciones2);

                    if (diasVacaciones2 > 0) {
                        double pagoVacacionesMes = calcularPagoVacaciones(idEmpleado, diasVacaciones2);
                        System.out.println("Pago por vacaciones calculado: " + pagoVacacionesMes);
                        totalPagoVacacionesMes += pagoVacacionesMes;
                    }

                }
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener las vacaciones: " + e.getMessage());
            e.printStackTrace();
        }

        return totalPagoVacacionesMes;
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
            System.out.println("quincena 1");
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
            System.out.println("quincena 2");
            return (int) ChronoUnit.DAYS.between(inicioAjustado, finAjustado) + 1;  // +1 porque es inclusivo
        }

        // Si no hay solapamiento, devolver 0 días
        return 0;
    }
//-----------------------------------------------------------------

    public double calcularPagoVacaciones(int idEmpleado, int diasVacaciones) {
        // Obtén el salario y tarifas por hora
        double[] salarioHora = salarioHora(idEmpleado);

        // Valida que los datos de salario sean correctos
        if (salarioHora == null || salarioHora.length < 2) {
            System.out.println("Error: No se pudo obtener el salario del empleado con ID: " + idEmpleado);
            throw new IllegalStateException("No se pudo obtener el salario del empleado con ID: " + idEmpleado);
        }

        double salarioBase = salarioHora[0];
        double salarioHoraNormal = salarioHora[1];

        if (salarioBase == 0 || salarioHoraNormal <= 0) {
            System.out.println("Error: Datos inválidos para el salario. Verifique los datos del empleado con ID: " + idEmpleado);
            throw new IllegalArgumentException("El salario o el salario por hora no son válidos para el empleado con ID: " + idEmpleado);
        }

        // Imprime el salario por hora en consola
        System.out.println("Salario base : " + salarioBase + ", Salario por hora: " + salarioHoraNormal);

        // Calcula y retorna el pago por vacaciones
        return diasVacaciones * salarioHoraNormal * 8;
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
//-----------------------------

//-----------------------------------------------
    public double calcularDeduccionQuincenaCCSS(int idEmpleado) {
        double deduccionCCSS = 0; // Variable para almacenar la deducción

        // Consulta SQL para obtener los datos de la tabla planilla
        String queryCCSS = "SELECT "
                + "salario_horas_extra, "
                + "salario_horas_regulares, "
                + "horas_extra, "
                + "horas_regulares "
                + "FROM planilla "
                + "WHERE empleado_id_empleado = ? "
                + "AND periodo = 'Quincena'";

        try (PreparedStatement ps = conexion.prepareStatement(queryCCSS)) {
            ps.setInt(1, idEmpleado);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Crear una instancia de Planilla
                    Planilla planilla = new Planilla();

                    // Asignar valores del ResultSet a la instancia de Planilla
                    planilla.setSalarioHorasExtra(rs.getDouble("salario_horas_extra"));
                    planilla.setSalarioHorasRegulares(rs.getDouble("salario_horas_regulares"));
                    planilla.setHorasExtra(rs.getDouble("horas_extra"));
                    planilla.setHorasRegulares(rs.getDouble("horas_regulares"));

                    // Mostrar los valores para depuración
                    System.out.println("Horas regulares: " + planilla.getHorasRegulares());
                    System.out.println("Horas extra: " + planilla.getHorasExtra());
                    System.out.println("Salario por horas regulares: " + planilla.getSalarioHorasRegulares());
                    System.out.println("Salario por horas extra: " + planilla.getSalarioHorasExtra());

                    // Calcular el pago por horas regulares y horas extra
                    double pagoHorasRegulares = planilla.getHorasRegulares() * planilla.getSalarioHorasRegulares();
                    double pagoHorasExtra = planilla.getHorasExtra() * planilla.getSalarioHorasExtra();

                    System.out.println("Pago por horas regulares: " + pagoHorasRegulares);
                    System.out.println("Pago por horas extra: " + pagoHorasExtra);

                    // Calcular la deducción de la CCSS
                    deduccionCCSS = (pagoHorasRegulares + pagoHorasExtra) * 0.1067;

                    System.out.println("Deducción CCSS: " + deduccionCCSS);
                } else {
                    System.out.println("No se encontraron datos para el empleado con ID  CCSS: " + idEmpleado);
                }
            }
        } catch (SQLException e) {
            // Usar un sistema de logging adecuado en lugar de printStackTrace
            e.printStackTrace();
        }

        // Retorna el valor de la deducción o -1 si no se encontraron datos
        return deduccionCCSS > 0 ? deduccionCCSS : 0;
    }
    //---------------------------------------------------------------

    public double calcularDeduccionMensualCCSS(int idEmpleado) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        double deduccionCCSS = 0; // Variable para almacenar la deducción

        try {
            // Consulta SQL para obtener los datos de la tabla planilla
            String queryCCSS = "SELECT "
                    + "salario_horas_extra, "
                    + "salario_horas_regulares, "
                    + "horas_extra, "
                    + "horas_regulares "
                    + "FROM planilla "
                    + "WHERE empleado_id_empleado = ? "
                    + "AND periodo = 'Mensual'";

            ps = conexion.prepareStatement(queryCCSS);
            ps.setInt(1, idEmpleado);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Crear una instancia de Planilla
                Planilla planilla = new Planilla();

                // Asignar valores del ResultSet a la instancia de Planilla
                planilla.setSalarioHorasExtra(rs.getDouble("salario_horas_extra"));
                planilla.setSalarioHorasRegulares(rs.getDouble("salario_horas_regulares"));
                planilla.setHorasExtra(rs.getDouble("horas_extra"));
                planilla.setHorasRegulares(rs.getDouble("horas_regulares"));

                // Mostrar los valores para depuración
                System.out.println("Horas regulares: " + planilla.getHorasRegulares());
                System.out.println("Horas extra: " + planilla.getHorasExtra());
                System.out.println("Salario por horas regulares: " + planilla.getSalarioHorasRegulares());
                System.out.println("Salario por horas extra: " + planilla.getSalarioHorasExtra());

                // Calcular el pago por horas regulares y horas extra
                double pagoHorasRegulares = planilla.getHorasRegulares() * planilla.getSalarioHorasRegulares();
                double pagoHorasExtra = planilla.getHorasExtra() * planilla.getSalarioHorasExtra();

                System.out.println("Pago por horas regulares: " + pagoHorasRegulares);
                System.out.println("Pago por horas extra: " + pagoHorasExtra);

                // Calcular la deducción de la CCSS
                deduccionCCSS = (pagoHorasRegulares + pagoHorasExtra) * 0.1067;

                System.out.println("Deducción CCSS: " + deduccionCCSS);
            } else {
                System.out.println("No se encontraron datos para el empleado con ID ccss: " + idEmpleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retorna el valor de la deducción o -1 si no se encontraron datos
        return deduccionCCSS > 0 ? deduccionCCSS : 0;
    }
//---------------------------------------------------------------------------------

    public double calcularRenta(int idEmpleado) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        double deduccionRenta = 0; // Inicializa la deducción de renta

        try {
            // Consulta SQL corregida
            String queryRenta = "SELECT salario_referencia "
                    + "FROM planilla "
                    + "WHERE empleado_id_empleado = ? ";

            ps = conexion.prepareStatement(queryRenta);
            ps.setInt(1, idEmpleado);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Obtener el salario de referencia
                double salarioReferencia = rs.getDouble("salario_referencia");
                System.out.println("Salario referencia: " + salarioReferencia);

                double salarioMensual = salarioReferencia;
                System.out.println("Salario mensual (estimado): " + salarioMensual);

                // Calcular la deducción por renta según los tramos
                if (salarioMensual <= 941000) {
                    deduccionRenta = 0; // No aplica deducción

                } else if (salarioMensual <= 1381000) {
                    deduccionRenta = (salarioMensual - 941000) * 0.10;

                } else if (salarioMensual <= 2423000) {
                    deduccionRenta = (1381000 - 941000) * 0.10
                            + (salarioMensual - 1381000) * 0.15;

                } else if (salarioMensual <= 4845000) {
                    deduccionRenta = (1381000 - 941000) * 0.10
                            + (2423000 - 1381000) * 0.15
                            + (salarioMensual - 2423000) * 0.20;

                } else {
                    deduccionRenta = (1381000 - 941000) * 0.10
                            + (2423000 - 1381000) * 0.15
                            + (4845000 - 2423000) * 0.20
                            + (salarioMensual - 4845000) * 0.25;
                }

                // Mostrar la deducción calculada
                System.out.println("Deducción de renta mensual: " + deduccionRenta);

                // Convertir la deducción mensual a quincenal
                deduccionRenta = deduccionRenta / 2;
                System.out.println("Deducción de renta quincenal: " + deduccionRenta);
            } else {
                System.out.println("No se encontraron datos para el empleado con ID renta: " + idEmpleado);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Mostrar el error en caso de excepción
        }

        // Retorna la deducción calculada o -1 si no se encontraron datos
        return deduccionRenta > 0 ? deduccionRenta : 0;
    }
//----------------------------------------------------------------------------

    public double[] calcularSalarioQuincenal(int idEmpleado) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        double[] salarios = new double[2]; // Índice 0: Salario bruto, Índice 1: Salario neto

        try {
            // Consulta SQL para obtener la información de la planilla
            String querySalario = "SELECT * FROM planilla "
                    + "WHERE empleado_id_empleado = ? "
                    + "AND periodo = 'Quincena'";

            ps = conexion.prepareStatement(querySalario);
            ps.setInt(1, idEmpleado);
            rs = ps.executeQuery();

            if (rs.next()) {
                double salarioReferencia = rs.getDouble("salario_referencia");
                double pagoIncapacidades = rs.getDouble("pago_incapacidades");
                double pagoVacaciones = rs.getDouble("pago_vacaciones");
                double salarioHorasExtra = rs.getDouble("salario_horas_extra");
                double salarioHorasRegulares = rs.getDouble("salario_horas_regulares");
                double horasExtra = rs.getDouble("horas_extra");
                double horasRegulares = rs.getDouble("horas_regulares");

                double pagoHorasRegulares = horasRegulares * salarioHorasRegulares;
                double pagoHorasExtra = horasExtra * salarioHorasExtra;

                // Calcular salario bruto quincenal
                double salarioBrutoQuincena = pagoHorasExtra + pagoHorasRegulares + pagoVacaciones + pagoIncapacidades;

                // Obtener deducciones
                double deduccionCCSS = rs.getDouble("deducciones_CCSS");
                double deduccionRenta = rs.getDouble("deducciones_impuestos");

                // Calcular salario neto
                double salarioNeto = salarioBrutoQuincena - (deduccionRenta + deduccionCCSS);

                // Si el salario neto es menor que 0, asignar 0
                if (salarioNeto < 0) {
                    salarioNeto = 0;
                }

                // Asignar valores al arreglo
                salarios[0] = salarioBrutoQuincena;
                salarios[1] = salarioNeto;

                // Debug
                System.out.println("Salario Bruto Quincena: " + salarioBrutoQuincena);
                System.out.println("Salario Neto Quincena: " + salarioNeto);

                // Actualizar el salario neto en la base de datos si es menor que 0
                String updateQuery = "UPDATE planilla SET salario_neto = ? "
                        + "WHERE empleado_id_empleado = ? AND periodo = 'Quincena'";

                try (PreparedStatement updatePs = conexion.prepareStatement(updateQuery)) {
                    updatePs.setDouble(1, salarioNeto);
                    updatePs.setInt(2, idEmpleado);
                    updatePs.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("No se encontraron datos para el empleado con ID salario: " + idEmpleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retornar el arreglo con los valores
        return salarios;
    }

//--------------------------------------------------------------------
    public double[] calcularSalarioMensual(int idEmpleado) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        double[] salarios = new double[2]; // Índice 0: Salario bruto, Índice 1: Salario neto

        try {
            String querySalario = "SELECT * FROM planilla "
                    + "WHERE empleado_id_empleado = ? "
                    + "AND periodo = 'Mensual'";

            ps = conexion.prepareStatement(querySalario);
            ps.setInt(1, idEmpleado);
            rs = ps.executeQuery();

            if (rs.next()) {
                double salarioReferencia = rs.getDouble("salario_referencia");
                double pagoIncapacidades = rs.getDouble("pago_incapacidades");
                double pagoVacaciones = rs.getDouble("pago_vacaciones");
                double salarioHorasExtra = rs.getDouble("salario_horas_extra");
                double salarioHorasRegulares = rs.getDouble("salario_horas_regulares");
                double horasExtra = rs.getDouble("horas_extra");
                double horasRegulares = rs.getDouble("horas_regulares");

                double pagoHorasRegulares = horasRegulares * salarioHorasRegulares;
                double pagoHorasExtra = horasExtra * salarioHorasExtra;

                // Calcular salario bruto mensual
                double salarioBrutoMes = pagoHorasExtra + pagoHorasRegulares + pagoVacaciones + pagoIncapacidades;

                // Obtener deducciones
                double deduccionCCSS = rs.getDouble("deducciones_CCSS");
                double deduccionRenta = rs.getDouble("deducciones_impuestos");

                // Calcular salario neto
                double salarioNeto = salarioBrutoMes - deduccionRenta - deduccionCCSS;

                // Si el salario neto es menor que 0, asignar 0
                if (salarioNeto < 0) {
                    salarioNeto = 0;
                }

                // Asignar valores al arreglo
                salarios[0] = salarioBrutoMes;
                salarios[1] = salarioNeto;

                // Debug
                System.out.println("Salario Bruto Mensual: " + salarioBrutoMes);
                System.out.println("Salario Neto Mensual: " + salarioNeto);

                // Actualizar el salario neto en la base de datos si es menor que 0
                String updateQuery = "UPDATE planilla SET salario_neto = ? WHERE empleado_id_empleado = ? AND periodo = 'Mensual'";
                try (PreparedStatement updatePs = conexion.prepareStatement(updateQuery)) {
                    updatePs.setDouble(1, salarioNeto);
                    updatePs.setInt(2, idEmpleado);
                    updatePs.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } else {
                System.out.println("No se encontraron datos para el empleado con ID: " + idEmpleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Retornar el arreglo con los valores
        return salarios;
    }

//--------------------------*****--------------------------------------
//-------------------------------------------------------------------------    
    public void actualizarOCrearReporteQuincenal(int idEmpleado, int mesSeleccionado, int anioSeleccionado) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Consultar horas del 1 al 15
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
                System.out.println("No hay datos para la primera quincena.");
                return;
            }

            double totalHorasNormalesQuincenal = rs.getDouble("total_horas_normales");
            double totalHorasExtrasQuincenal = rs.getDouble("total_horas_extras");

            // Obtener salario por hora
            double[] salarioPorHora = salarioHora(idEmpleado);
            double salarioCargo = salarioPorHora[0];
            double salarioHoraNormal = salarioPorHora[1];
            double salarioHoraExtra = salarioPorHora[2];

//---------------------------------------------------------------------------
            double pagoIncapacidades = obtenerPagoIncapacidades(idEmpleado, anioSeleccionado, mesSeleccionado);
            double deduccionesCCSSQuincena = calcularDeduccionQuincenaCCSS(idEmpleado);
            double pagoVacacionesQuincena = obtenerVacacionesQuincena(idEmpleado, anioSeleccionado, mesSeleccionado);
            double deduccionesRenta = calcularRenta(idEmpleado);
            
            double[] calcularSalarioQuincenal = calcularSalarioQuincenal(idEmpleado);
            double pagoSalarioBruto = calcularSalarioQuincenal[0];
            double pagoSalarioNeto = calcularSalarioQuincenal[1];
        

            System.out.println("incapacidades ** pago: " + pagoIncapacidades);
            System.out.println("vacaciones mes PAGO * " + pagoVacacionesQuincena);
            System.out.println("deducciones CCSS " + deduccionesCCSSQuincena);
            System.out.println("deducciones Renta " + deduccionesRenta);
            System.out.println("salario bruto **: " + pagoSalarioBruto);
            System.out.println("salario neto * " + pagoSalarioNeto);
//--------------------------------------------------------------------            
            // Asignar fecha de pago (1 al 15)
            String mesPagoQuincenal = String.format("%d-%02d-01", anioSeleccionado, mesSeleccionado);
            java.sql.Date dateMesPagoQuincenal = java.sql.Date.valueOf(mesPagoQuincenal);

            // Verificar si ya existe un reporte mensual
            String queryVerificarQuincenal = "SELECT id_planilla FROM planilla "
                    + "WHERE YEAR(mes_pago) = ? AND MONTH(mes_pago) = ? "
                    + "AND empleado_id_empleado = ? AND periodo = 'Quincena'";

            ps = conexion.prepareStatement(queryVerificarQuincenal);
            ps.setInt(1, anioSeleccionado);
            ps.setInt(2, mesSeleccionado);
            ps.setInt(3, idEmpleado);
            rs = ps.executeQuery();

            if (rs.next()) {

                System.out.println("nuevo  actializareporte");
                // Actualizar reporte mensual existente
                int idPlanillaQuincenal = rs.getInt("id_planilla");
                String queryActualizaQuincenal = "UPDATE planilla SET "
                        + "salario_referencia = ?, "
                        + "deducciones_CCSS = ?, "
                        + "deducciones_impuestos = ?, "
                        + "salario_horas_extra = ?, "
                        + "salario_horas_regulares = ?, "
                        + "horas_extra = ?, "
                        + "horas_regulares = ?, "
                        + "pago_incapacidades = ?, "
                        + "pago_vacaciones = ?, "
                        + "salario_bruto = ?, "
                        + "salario_neto = ?, "
                        + "mes_pago = ? "
                        + "WHERE id_planilla = ?";

                ps = conexion.prepareStatement(queryActualizaQuincenal);
                ps.setDouble(1, salarioCargo);
                ps.setDouble(2, deduccionesCCSSQuincena);
                ps.setDouble(3, deduccionesRenta);
                ps.setDouble(4, salarioHoraExtra);
                ps.setDouble(5, salarioHoraNormal);
                ps.setDouble(6, totalHorasExtrasQuincenal);
                ps.setDouble(7, totalHorasNormalesQuincenal);
                ps.setDouble(8, pagoIncapacidades);
                ps.setDouble(9, pagoVacacionesQuincena);
                ps.setDouble(10, pagoSalarioBruto);
                ps.setDouble(11, pagoSalarioNeto);
                ps.setDate(12, dateMesPagoQuincenal);
                ps.setInt(13, idPlanillaQuincenal);

                int filasActualizadas = ps.executeUpdate();
                System.out.println(filasActualizadas > 0 ? "Reporte mensual actualizado correctamente." : "Error al actualizar el reporte mensual.");

                Planilla planilla = new Planilla();
                System.out.println("incapacidades  " + planilla.getPagoIncapacidades());
                System.out.println("vacacciones " + planilla.getPagoVacaciones());

            } else {
                //-----------------------------------------------------------
                System.out.println("incapacidades ** inser: " + pagoIncapacidades);
                System.out.println("vacaciones mes inser * " + pagoVacacionesQuincena);
                System.out.println("deducciones CCSS inser " + deduccionesCCSSQuincena);
                System.out.println("deducciones Renta inser " + deduccionesRenta);
                System.out.println("salario bruto inser " + pagoSalarioBruto);
                System.out.println("salario neto inser " + pagoSalarioNeto);

                //---------------------------------------------------------
                // Crear nuevo reporte mensual
                String queryInsertarQuincenal = "INSERT INTO planilla "
                        + "(empleado_id_empleado, "
                        + "salario_referencia, "
                        + "deducciones_CCSS, "
                        + "deducciones_impuestos, "
                        + "salario_horas_extra, "
                        + "salario_horas_regulares, "
                        + "horas_extra, "
                        + "horas_regulares, "
                        + "pago_incapacidades, "
                        + "pago_vacaciones, "
                        + "salario_bruto , "
                        + "salario_neto , "
                        + "mes_pago, "
                        + "periodo) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?)";

                ps = conexion.prepareStatement(queryInsertarQuincenal);
                ps.setInt(1, idEmpleado);
                ps.setDouble(2, salarioCargo);
                ps.setDouble(3, deduccionesCCSSQuincena);
                ps.setDouble(4, deduccionesRenta);
                ps.setDouble(5, salarioHoraExtra);
                ps.setDouble(6, salarioHoraNormal);
                ps.setDouble(7, totalHorasExtrasQuincenal);
                ps.setDouble(8, totalHorasNormalesQuincenal);
                ps.setDouble(9, pagoIncapacidades);
                ps.setDouble(10, pagoVacacionesQuincena);
                ps.setDouble(11, pagoSalarioBruto);
                ps.setDouble(12, pagoSalarioNeto);
                ps.setDate(13, dateMesPagoQuincenal);
                ps.setString(14, "Quincena");

                int filasInsertadas = ps.executeUpdate();

                System.out.println(filasInsertadas + " filas insertadas. quincena");
                System.out.println(filasInsertadas > 0 ? "Reporte mensual creado correctamente." : "Error al crear el reporte mensual.");

                Planilla planilla = new Planilla();
                System.out.println("incapacidades  " + planilla.getPagoIncapacidades());
                System.out.println("vacacciones " + planilla.getPagoVacaciones());

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//----------------------------------------------------------------------------
//----------------------------------------------------------
//------------------------*****
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
//---------------------------------------------------------------------------
            // Obtener pago por incapacidades
            double pagoIncapacidades = obtenerPagoIncapacidadesMes(idEmpleado, anioSeleccionado, mesSeleccionado);
            double deduccionesCCSSMes = calcularDeduccionMensualCCSS(idEmpleado);
            double pagoVacacionesMes = obtenerVacacionesMensual(idEmpleado, anioSeleccionado, mesSeleccionado);
            double deduccionesRenta = calcularRenta(idEmpleado);

            double[] calcularSalarioMensual = calcularSalarioMensual(idEmpleado);
            double pagoSalarioBruto = calcularSalarioMensual[0];
            double pagoSalarioNeto = calcularSalarioMensual[1];

            System.out.println("incapacidades **: " + pagoIncapacidades);
            System.out.println("vacaciones mes PAGO * " + pagoVacacionesMes);
            System.out.println("deducciones CCSS " + deduccionesCCSSMes);
            System.out.println("deducciones Renta " + deduccionesRenta);
            System.out.println("salario  bruto " + pagoSalarioBruto);
            System.out.println("salario neto " + pagoSalarioNeto);
//--------------------------------------------------------------------            
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
                        + "salario_referencia = ?, "
                        + "deducciones_CCSS = ?, "
                        + "deducciones_impuestos = ?, "
                        + "salario_horas_extra = ?, "
                        + "salario_horas_regulares = ?, "
                        + "horas_extra = ?, "
                        + "horas_regulares = ?, "
                        + "pago_incapacidades = ?, "
                        + "pago_vacaciones = ?, "
                        + "salario_bruto = ?, "
                        + "salario_neto = ?, "
                        + "mes_pago = ? "
                        + "WHERE id_planilla = ?";

                ps = conexion.prepareStatement(queryActualizarMensual);
                ps.setDouble(1, salarioCargo);
                ps.setDouble(2, deduccionesCCSSMes);
                ps.setDouble(3, deduccionesRenta);
                ps.setDouble(4, salarioHoraExtra);
                ps.setDouble(5, salarioHoraNormal);
                ps.setDouble(6, totalHorasExtrasMensual);
                ps.setDouble(7, totalHorasNormalesMensual);
                ps.setDouble(8, pagoIncapacidades);
                ps.setDouble(9, pagoVacacionesMes);
                ps.setDouble(10, pagoSalarioBruto);
                ps.setDouble(11, pagoSalarioNeto);
                ps.setDate(12, dateMesPagoMensual);
                ps.setInt(13, idPlanillaMensual);

                int filasActualizadas = ps.executeUpdate();
                System.out.println(filasActualizadas > 0 ? "Reporte mensual actualizado correctamente." : "Error al actualizar el reporte mensual.");

                Planilla planilla = new Planilla();
                System.out.println("incapacidades  " + planilla.getPagoIncapacidades());
                System.out.println("vacacciones " + planilla.getPagoVacaciones());

            } else {
                // Crear nuevo reporte mensual
                String queryInsertarMensual = "INSERT INTO planilla "
                        + "(empleado_id_empleado, "
                        + "salario_referencia, "
                        + "deducciones_CCSS, "
                        + "deducciones_impuestos, "
                        + "salario_horas_extra, "
                        + "salario_horas_regulares, "
                        + "horas_extra, "
                        + "horas_regulares, "
                        + "pago_incapacidades, "
                        + "pago_vacaciones, "
                        + "salario_bruto, "
                        + "salario_neto, "
                        + "mes_pago, "
                        + "periodo) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, ?)";

                ps = conexion.prepareStatement(queryInsertarMensual);
                ps.setInt(1, idEmpleado);
                ps.setDouble(2, salarioCargo);
                ps.setDouble(3, deduccionesCCSSMes);
                ps.setDouble(4, deduccionesRenta);
                ps.setDouble(5, salarioHoraExtra);
                ps.setDouble(6, salarioHoraNormal);
                ps.setDouble(7, totalHorasExtrasMensual);
                ps.setDouble(8, totalHorasNormalesMensual);
                ps.setDouble(9, pagoIncapacidades);
                ps.setDouble(10, pagoVacacionesMes);
                ps.setDouble(11, pagoSalarioBruto);
                ps.setDouble(12, pagoSalarioNeto);
                ps.setDate(13, dateMesPagoMensual);
                ps.setString(14, "Mensual");

                int filasInsertadas = ps.executeUpdate();

                System.out.println(filasInsertadas + " filas insertadas. MES");
                System.out.println(filasInsertadas > 0 ? "Reporte mensual creado correctamente." : "Error al crear el reporte mensual.");

                Planilla planilla = new Planilla();
                System.out.println("incapacidades  " + planilla.getPagoIncapacidades());
                System.out.println("vacacciones " + planilla.getPagoVacaciones());

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//------------------------------------------------------------------   
//---------------------------------------------------------------------

//--------------------- INGRESAR DATOS A PLANILLA ------------------------------      
//-----------------------------------------------------------------------------
    public void generarPlanillaQuincenalParaTodos(int mesSeleccionado, int anioSeleccionado) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Consultar todos los empleados activos
            String queryEmpleados = "SELECT id_empleado "
                    + "FROM empleado ";

            ps = conexion.prepareStatement(queryEmpleados);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idEmpleado = rs.getInt("id_empleado");

                // Generar o actualizar la planilla quincenal para el empleado actual
                IntStream.range(0, 3).forEach(i -> actualizarOCrearReporteQuincenal(idEmpleado, mesSeleccionado, anioSeleccionado));

            }

            System.out.println("Planilla quincenal generada para todos los empleados.");

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
    //-------------------------------------------------------------------------

    public void generarPlanillaMensualParaTodos(int mesSeleccionado, int anioSeleccionado) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Consultar todos los empleados activos
            String queryEmpleados = "SELECT id_empleado "
                    + "FROM empleado ";

            ps = conexion.prepareStatement(queryEmpleados);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idEmpleado = rs.getInt("id_empleado");

                // Generar o actualizar la planilla quincenal para el empleado actual
                IntStream.range(0, 3).forEach(i -> actualizarOCrearReporteMensual(idEmpleado, mesSeleccionado, anioSeleccionado));

            }

            System.out.println("Planilla quincenal generada para todos los empleados.");

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
//----------------------------------------------------------------
    public List<Planilla> verPlanillaEmpleado(int idEmpleado) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Planilla> planillas = new ArrayList<>();

        try {
            String queryEmpleado = " SELECT "
                    + "    p.id_planilla AS ID_Planilla, "
                    + "    p.mes_pago AS MesPago,"
                    + "    p.periodo AS TipoPeriodo,"
                    + "    p.empleado_id_empleado AS ID_Empleado,"
                    + "    SUM(p.salario_neto) AS SalarioNetoTotal,"
                    + "    COUNT(*) AS TotalRegistros"
                    + " FROM "
                    + "    planilla p"
                    + " WHERE "
                    + "    p.empleado_id_empleado = ? "
                    + " GROUP BY "
                    + "    p.id_planilla, "
                    + "    p.mes_pago, "
                    + "    p.periodo, "
                    + "    p.empleado_id_empleado"
                    + " ORDER BY "
                    + "    p.empleado_id_empleado, "
                    + "    p.mes_pago ";

            ps = conexion.prepareStatement(queryEmpleado);
            ps.setInt(1, idEmpleado);
            rs = ps.executeQuery();

            while (rs.next()) {
                Planilla planilla = new Planilla();

                // Asignar los valores del resultado de la consulta a la Planilla
                planilla.setIdPlanilla(rs.getInt("ID_Planilla"));
                planilla.setMesPago(rs.getDate("MesPago")); 
                planilla.setPeriodo(rs.getString("TipoPeriodo"));
                planilla.setEmpleadoIdEmpleado(rs.getInt("ID_Empleado"));
                planilla.setSalarioNeto(rs.getDouble("SalarioNetoTotal")); 

                planillas.add(planilla);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Imprime el error de la excepción (o puedes usar un logger)
        } finally {
            // Cerrar los recursos
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

        return planillas;
    }
//---------------------------------------------------------------------------
    public Planilla detallePlanilla(int idPlanilla) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Planilla planilla = null; // Objeto Planilla para almacenar los datos

        try {
            String queryPlanilla = "SELECT * FROM planilla WHERE id_planilla = ?";
            ps = conexion.prepareStatement(queryPlanilla);
            ps.setInt(1, idPlanilla);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Crear un objeto Planilla y mapear los datos
                planilla = new Planilla();
                planilla.setIdPlanilla(rs.getInt("id_planilla"));
                planilla.setPeriodo(rs.getString("periodo"));
                planilla.setSalarioReferencia(rs.getDouble("salario_referencia"));
                planilla.setDeduccionesCCSS(rs.getDouble("deducciones_CCSS"));
                planilla.setPagoIncapacidades(rs.getDouble("pago_incapacidades"));
                planilla.setPagoVacaciones(rs.getDouble("pago_vacaciones"));
                planilla.setDeduccionesImpuestos(rs.getDouble("deducciones_impuestos"));
                planilla.setSalarioHorasExtra(rs.getDouble("salario_horas_extra"));
                planilla.setSalarioHorasRegulares(rs.getDouble("salario_horas_regulares"));
                planilla.setHorasExtra(rs.getDouble("horas_extra"));
                planilla.setHorasRegulares(rs.getDouble("horas_regulares"));
                planilla.setSalarioBruto(rs.getDouble("salario_bruto"));
                planilla.setSalarioNeto(rs.getDouble("salario_neto"));
                planilla.setMesPago(rs.getDate("mes_pago"));
                planilla.setEmpleadoIdEmpleado(rs.getInt("empleado_id_empleado"));
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener detalles de la planilla: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                System.err.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
        

        return planilla; // Devuelve el objeto Planilla o null si no se encontró
    }
//---------------------------------------------------------
    
  public void actualizarReporteMensual(int idEmpleado, int mesSeleccionado, int anioSeleccionado) {
    String queryHoras = "SELECT SUM(horas_dia_normal) AS total_horas_normales, "
            + "SUM(horas_dia_extra) AS total_horas_extras "
            + "FROM marcas "
            + "WHERE DAY(fecha_marca) BETWEEN 16 AND 31 "
            + "AND id_empleado = ? "
            + "AND YEAR(fecha_marca) = ? "
            + "AND MONTH(fecha_marca) = ?";
    
    String queryVerificarMensual = "SELECT id_planilla FROM planilla "
            + "WHERE YEAR(mes_pago) = ? AND MONTH(mes_pago) = ? "
            + "AND empleado_id_empleado = ? AND periodo = 'Mensual'";

    String queryActualizarMensual = "UPDATE planilla SET "
            + "salario_referencia = ?, "
            + "deducciones_CCSS = ?, "
            + "deducciones_impuestos = ?, "
            + "salario_horas_extra = ?, "
            + "salario_horas_regulares = ?, "
            + "horas_extra = ?, "
            + "horas_regulares = ?, "
            + "pago_incapacidades = ?, "
            + "pago_vacaciones = ?, "
            + "salario_bruto = ?, "
            + "salario_neto = ?, "
            + "mes_pago = ? "
            + "WHERE id_planilla = ?";

    String queryInsertarMensual = "INSERT INTO planilla "
            + "(empleado_id_empleado, salario_referencia, "
            + "deducciones_CCSS, deducciones_impuestos, salario_horas_extra, "
            + "salario_horas_regulares, horas_extra, horas_regulares, "
            + "pago_incapacidades, pago_vacaciones, salario_bruto, "
            + "salario_neto, mes_pago, periodo) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement psHoras = conexion.prepareStatement(queryHoras);
         PreparedStatement psVerificar = conexion.prepareStatement(queryVerificarMensual)) {

        // Consultar horas del 16 al 31
        psHoras.setInt(1, idEmpleado);
        psHoras.setInt(2, anioSeleccionado);
        psHoras.setInt(3, mesSeleccionado);
        try (ResultSet rsHoras = psHoras.executeQuery()) {
            if (!rsHoras.next()) {
                System.out.println("No hay datos para la segunda quincena.");
                return;
            }

            double totalHorasNormalesMensual = rsHoras.getDouble("total_horas_normales");
            double totalHorasExtrasMensual = rsHoras.getDouble("total_horas_extras");

            // Obtener salario por hora
            double[] salarioPorHora = salarioHora(idEmpleado);
            double salarioCargo = salarioPorHora[0];
            double salarioHoraNormal = salarioPorHora[1];
            double salarioHoraExtra = salarioPorHora[2];

            // Obtener pagos por incapacidades, deducciones, y vacaciones
            double pagoIncapacidades = obtenerPagoIncapacidadesMes(idEmpleado, anioSeleccionado, mesSeleccionado);
            double deduccionesCCSSMes = calcularDeduccionMensualCCSS(idEmpleado);
            double pagoVacacionesMes = obtenerVacacionesMensual(idEmpleado, anioSeleccionado, mesSeleccionado);
            double deduccionesRenta = calcularRenta(idEmpleado);

            double[] calcularSalarioMensual = calcularSalarioMensual(idEmpleado);
            double pagoSalarioBruto = calcularSalarioMensual[0];
            double pagoSalarioNeto = calcularSalarioMensual[1];

            // Asignar fecha de pago (16 al 31)
            String mesPagoMensual = String.format("%d-%02d-16", anioSeleccionado, mesSeleccionado);
            java.sql.Date dateMesPagoMensual = java.sql.Date.valueOf(mesPagoMensual);

            // Verificar si ya existe un reporte mensual
            psVerificar.setInt(1, anioSeleccionado);
            psVerificar.setInt(2, mesSeleccionado);
            psVerificar.setInt(3, idEmpleado);
            try (ResultSet rsVerificar = psVerificar.executeQuery()) {

                if (rsVerificar.next()) {
                    // Actualizar reporte mensual existente
                    int idPlanillaMensual = rsVerificar.getInt("id_planilla");

                    try (PreparedStatement psActualizar = conexion.prepareStatement(queryActualizarMensual)) {
                        psActualizar.setDouble(1, salarioCargo);
                        psActualizar.setDouble(2, deduccionesCCSSMes);
                        psActualizar.setDouble(3, deduccionesRenta);
                        psActualizar.setDouble(4, salarioHoraExtra);
                        psActualizar.setDouble(5, salarioHoraNormal);
                        psActualizar.setDouble(6, totalHorasExtrasMensual);
                        psActualizar.setDouble(7, totalHorasNormalesMensual);
                        psActualizar.setDouble(8, pagoIncapacidades);
                        psActualizar.setDouble(9, pagoVacacionesMes);
                        psActualizar.setDouble(10, pagoSalarioBruto);
                        psActualizar.setDouble(11, pagoSalarioNeto);
                        psActualizar.setDate(12, dateMesPagoMensual);
                        psActualizar.setInt(13, idPlanillaMensual);

                        int filasActualizadas = psActualizar.executeUpdate();
                        System.out.println(filasActualizadas > 0 ? "Reporte mensual actualizado correctamente." : "Error al actualizar el reporte mensual.");
                    }

                } else {
                    // Crear nuevo reporte mensual
                    try (PreparedStatement psInsertar = conexion.prepareStatement(queryInsertarMensual)) {
                        psInsertar.setInt(1, idEmpleado);
                        psInsertar.setDouble(2, salarioCargo);
                        psInsertar.setDouble(3, deduccionesCCSSMes);
                        psInsertar.setDouble(4, deduccionesRenta);
                        psInsertar.setDouble(5, salarioHoraExtra);
                        psInsertar.setDouble(6, salarioHoraNormal);
                        psInsertar.setDouble(7, totalHorasExtrasMensual);
                        psInsertar.setDouble(8, totalHorasNormalesMensual);
                        psInsertar.setDouble(9, pagoIncapacidades);
                        psInsertar.setDouble(10, pagoVacacionesMes);
                        psInsertar.setDouble(11, pagoSalarioBruto);
                        psInsertar.setDouble(12, pagoSalarioNeto);
                        psInsertar.setDate(13, dateMesPagoMensual);
                        psInsertar.setString(14, "Mensual");

                        int filasInsertadas = psInsertar.executeUpdate();
                        System.out.println(filasInsertadas > 0 ? "Reporte mensual creado correctamente." : "Error al crear el reporte mensual.");
                    }
                }
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
  }

//-----------------------
}
