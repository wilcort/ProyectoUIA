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
import java.util.Date;


public class PlanillaDAO {
    Connection conexion;

    public PlanillaDAO() {
        Conexion conex = new Conexion();
        conexion = conex.getConectar();
    }
//---------------------------------------   
    public void salarioHora(int idEmpleado) {
        PreparedStatement ps = null;
        ResultSet rs = null;

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
                String nombreEmpleado = rs.getString("nombre_empleado");
                String apellido1 = rs.getString("apellido_1");
                String apellido2 = rs.getString("apellido_2");
                String nombreCargo = rs.getString("nombre_cargo");
                double salarioCargo = rs.getDouble("salario_cargo");

                // Calcula el salario por hora
                double salarioHora = (salarioCargo / 30) / 8;
                double salarioHoraExtra = ((salarioCargo / 30) / 8)*1.5;

                // Muestra los datos en consola
                System.out.println("Empleado: " + nombreEmpleado + " " + apellido1 + " " + apellido2);
                System.out.println("Cargo: " + nombreCargo);
                System.out.println("Salario: " + salarioCargo);
                System.out.println("Salario por hora: " + salarioHora);
                System.out.println("Salario por extra: " + salarioHoraExtra);

                // Llamamos al método para insertar los datos en la tabla planilla
                Planilla planilla = new Planilla(
                        0, 
                        null, 
                        salarioCargo, 
                        0, 
                        0, 
                        0, 
                        salarioHoraExtra, 
                        salarioHora, 
                        0,
                        0,
                        0,
                        null, 
                        idEmpleado,
                        null);
                       
                
                planilla.setSalarioBruto(salarioCargo);
                planilla.setSalarioHorasExtra(salarioHoraExtra);
                planilla.setSalarioHorasRegulares(salarioHora);
                ingresarDatos(idEmpleado, planilla);
            } else {
                System.out.println("No se encontró un empleado con ese ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cierra los recursos en el bloque `finally` para evitar fugas
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
    public void horasExtraNormaQuincena(int idEmpleado) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Consulta modificada para agrupar las horas por mes
            String query = "SELECT MONTH(m.fecha_marca) AS mes, SUM(m.horas_dia) AS total_horas_trabajadas "
                    + "FROM marcas m "
                    + "WHERE m.id_empleado = ? "
                    + "AND DAY(m.fecha_marca) BETWEEN 1 AND 15 "
                    + "GROUP BY mes";

            ps = conexion.prepareStatement(query);
            ps.setInt(1, idEmpleado);
            rs = ps.executeQuery();

            // Itera sobre el resultado y muestra las horas trabajadas por mes
            while (rs.next()) {
                int mes = rs.getInt("mes");  // Obtiene el mes
                double totalHorasTrabajadas = rs.getDouble("total_horas_trabajadas");  // Obtiene las horas trabajadas totales por mes
 

                // Imprime el total de horas trabajadas por mes
                System.out.println("Mes: " + mes + " | Total horas trabajadas quincena: " + totalHorasTrabajadas);
                
                double[] resultado = calcularHorasNormalesYExtras(totalHorasTrabajadas);

                double horasNormales = resultado[0];
                double horasExtras = resultado[1];

                System.out.println("Horas Normales: " + horasNormales);
                System.out.println("Horas Extras: " + horasExtras);
            
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
//------------------------------------------------------------
     public void horasExtraNormaMensual(int idEmpleado) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Consulta modificada para agrupar las horas por mes
            String query = "SELECT MONTH(m.fecha_marca) AS mes, SUM(m.horas_dia) AS total_horas_trabajadas "
                    + "FROM marcas m "
                    + "WHERE m.id_empleado = ? "
                    + "AND DAY(m.fecha_marca) BETWEEN 16 AND 31 "
                    + "GROUP BY mes";

            ps = conexion.prepareStatement(query);
            ps.setInt(1, idEmpleado);
            rs = ps.executeQuery();

            // Itera sobre el resultado y muestra las horas trabajadas por mes
            while (rs.next()) {
                int mes = rs.getInt("mes");  // Obtiene el mes
                double totalHorasTrabajadas = rs.getDouble("total_horas_trabajadas");  // Obtiene las horas trabajadas totales por mes

                // Imprime el total de horas trabajadas por mes
                System.out.println("Mes: " + mes + " | Total horas trabajadas mensual: " + totalHorasTrabajadas);
            
                double[] resultado = calcularHorasNormalesYExtras(totalHorasTrabajadas);

                double horasNormales = resultado[0];
                double horasExtras = resultado[1];

                System.out.println("Horas Normales: " + horasNormales);
                System.out.println("Horas Extras: " + horasExtras);
            
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
//--------------------------------------------------------------------------
    public double[] calcularHorasNormalesYExtras(double totalHorasTrabajadas) {
        double horasNormales = 0;
        double horasExtras = 0;

        if (totalHorasTrabajadas <= 120) {
            horasNormales = totalHorasTrabajadas;
            horasExtras = 0;
        } else {
            horasNormales = 120;
            horasExtras = totalHorasTrabajadas - 120;
        }

        // Devuelve las horas normales y extras como un array
        return new double[]{horasNormales, horasExtras};
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
                    + "deducciones_incapacidades, "
                    + "deducciones_impuestos, "
                    + "salario_horas_extra, "
                    + "salario_horas_regulares, "
                    + "salario_neto, "
                    + "mes_pago, "
                    + "empleado_id_empleado) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            ps = conexion.prepareStatement(query);

            ps.setString(1, planilla.getPeriodo());
            ps.setDouble(2, planilla.getSalarioBruto());
            ps.setDouble(3, planilla.getDeduccionesCCSS());
            ps.setDouble(4, planilla.getDeduccionesIncapacidades());
            ps.setDouble(5, planilla.getDeduccionesImpuestos());
            ps.setDouble(6, planilla.getSalarioHorasExtra());
            ps.setDouble(7, planilla.getSalarioHorasRegulares());
            ps.setDouble(8, planilla.getSalarioNeto());

            // Si la fechaPago es null, no la pasamos
            if (planilla.getMesPago() != null) {
                ps.setDate(9, new java.sql.Date(planilla.getMesPago().getTime()));
            } else {
                ps.setNull(9, java.sql.Types.DATE);  // Aquí usamos setNull para permitir que la fecha sea null en la base de datos
            }

            ps.setInt(10, idEmpleado);

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
