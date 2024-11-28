/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelLiquidacion;

import Configur.Conexion;
import Model.Cargo;
import Model.Colaborador;
import ModelAguinaldo.Aguinaldo;
import ModelPanilla.PlanillaDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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


public class LiquidacionDAO {
    
    Connection conexion;
    Aguinaldo aguinaldo = null;
    Colaborador colaborador = null;
    PlanillaDAO planillaDAO = null;

    public LiquidacionDAO() {
        Conexion conex = new Conexion();
        this.planillaDAO = new PlanillaDAO();
        conexion = conex.getConectar();
    }
 //----------------------------------------------------------
    public static long calcularDiasDesdeDiciembre(LocalDate fechaFin) {
        // Obtener el 1 de diciembre del año anterior
        LocalDate inicioPeriodo = LocalDate.of(fechaFin.getYear() - 1, 12, 1);

        // Validar que la fecha final es posterior al inicio del periodo
        if (fechaFin.isBefore(inicioPeriodo)) {
            throw new IllegalArgumentException("La fecha proporcionada debe ser posterior al 1 de diciembre del año anterior.");
        }

        // Calcular días entre el inicio del periodo y la fecha final
        return ChronoUnit.DAYS.between(inicioPeriodo, fechaFin) + 1; // +1 para incluir el día actual
    }
  //------------------------------------------------------
     public double calcularSalarioBruto(int idEmpleado, LocalDate fechaFinContrato) {
        // Método para obtener el salario bruto (lo que ya tenías)
        PreparedStatement ps = null;
        ResultSet rs = null;
        double totalSalarios = 0;

        try {
            // Consulta SQL corregida con espacios adecuados
            String query = "SELECT SUM(salario_bruto) AS total_salarios "
                    + "FROM planilla "
                    + "WHERE mes_pago >= STR_TO_DATE(CONCAT(YEAR(CURDATE())-1, '-12-01'), '%Y-%m-%d') "
                    + "AND mes_pago <= ? "
                    + "AND empleado_id_empleado = ?";

            ps = conexion.prepareStatement(query);

            // Convertir LocalDate a java.sql.Date
            java.sql.Date sqlDate = java.sql.Date.valueOf(fechaFinContrato);

            // Establecer el parámetro para la fecha de fin de contrato
            ps.setDate(1, sqlDate);  // Fecha de fin de contrato

            // Establecer el parámetro para el ID del empleado
            ps.setInt(2, idEmpleado);  // ID del empleado

            rs = ps.executeQuery();

            if (rs.next()) {
                totalSalarios = rs.getDouble("total_salarios");
                System.out.println("Total de salarios: " + totalSalarios);
                // Realiza el cálculo del aguinaldo aquí si es necesario
                // Ejemplo de cálculo:
                
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir el error para depuración
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();  // Imprimir el error para depuración
            }
        }
        return totalSalarios;
    }
 //----------------------------------------------------------
     public void calcularAguinaldo(LocalDate fechaFin, int idEmpleado) {
        // Calcular los días desde el 1 de diciembre del año anterior hasta la fecha de finalización
        double dias = calcularDiasDesdeDiciembre(fechaFin);

        // Obtener el salario bruto
        double salarioBruto = calcularSalarioBruto(idEmpleado, fechaFin);

        // Verificar que los valores son válidos antes de calcular el aguinaldo
        if (salarioBruto > 0 && dias > 0) {
            // Calcular el aguinaldo total
            double aguinaldoTotal = (salarioBruto / 365) * dias;

            // Mostrar el resultado
            System.out.println("Aguinaldo total calculado: " + aguinaldoTotal);
            System.out.println("Días transcurridos desde diciembre del año anterior: " + dias);
        } else {
            System.out.println("No se pudo calcular el aguinaldo debido a valores inválidos.");
        }
    }
  //-------------------------------------------------------------------------------------
  //---------------------- VACACIONES ----------------------------------------
 //----------------------------------------------------------------------------------
    public double calcularDiasVacaciones(int idEmpleado) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        double totalDiasRestantes = 0;

        try {
            // Consulta SQL corregida con espacios adecuados
            String query = "SELECT "
                    + "v.dias_vacaciones_total, "
                    + "p.salario_horas_regulares "
                    + "FROM vacaciones v "
                    + "INNER JOIN planilla p "
                    + "ON v.empleado_id_empleado = p.empleado_id_empleado "
                    + "WHERE v.empleado_id_empleado = ?";

            ps = conexion.prepareStatement(query);
            ps.setInt(1, idEmpleado);

            rs = ps.executeQuery();

            if (rs.next()) {
                // Obtener los valores de días de vacaciones y salario por hora regular
                int diasVacaciones = rs.getInt("dias_vacaciones_total");
                double salarioHoraRegular = rs.getDouble("salario_horas_regulares");

                // Calcular el total de días restantes multiplicando los días de vacaciones por el salario por hora
                totalDiasRestantes = (diasVacaciones * (salarioHoraRegular * 8));
                
                System.out.println("dias restantes de vacaciones: " + diasVacaciones);
                System.out.println("pago por hora: " + salarioHoraRegular);
                System.out.println("total a pagar: " + totalDiasRestantes);
            }

        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir el error para depuración
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();  // Imprimir el error para depuración
            }
        }
        return totalDiasRestantes;
    }
//-----------------------------------------------------
   
    
    
    
//-----------------------------------------------------------------------------------------  
    public void generarReportePorContrato(int idEmpleado, String fechaFinalizacionContrato) {
        try {
            // Parsear la fecha de finalización del contrato
            LocalDate fechaFinalizacion = LocalDate.parse(fechaFinalizacionContrato);

            // Imprimir la fecha ingresada
            System.out.println("Fecha ingresada: " + fechaFinalizacion);

            // Obtener el mes y año de la fecha ingresada
            int mesIngresado = fechaFinalizacion.getMonthValue();
            int anioIngresado = fechaFinalizacion.getYear();

            // Obtener la fecha actual
            LocalDate fechaActual = LocalDate.now();

            // Calcular los días restantes desde la fecha actual hasta la fecha ingresada
            long diasRestantes = ChronoUnit.DAYS.between(fechaActual, fechaFinalizacion);

            // Obtener el día de la fecha ingresada
            int diaFinalizacion = fechaFinalizacion.getDayOfMonth();
          

            if (diaFinalizacion >= 1 && diaFinalizacion <= 15) {
                System.out.println("La fecha está en el rango del 1 al 15.");

                System.out.println("Generando reporte quincenal para el empleado con ID: " + idEmpleado);

            } else if (diaFinalizacion >= 16 && diaFinalizacion <= 31) {
                System.out.println("La fecha está en el rango del 16 al 31.");
                
                planillaDAO.actualizarOCrearReporteMensual(idEmpleado, mesIngresado, anioIngresado);


                System.out.println("Generando reporte mensual para el empleado con ID: " + idEmpleado);

            } else {
                System.out.println("Error: Fecha fuera de rango.");
            }
        } catch (Exception e) {
            System.err.println("Error al procesar el reporte: " + e.getMessage());
        }
    }


//--------------------------------------------------------
 
//-----------------------------------------------
}