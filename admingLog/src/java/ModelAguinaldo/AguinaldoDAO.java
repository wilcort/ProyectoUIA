/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelAguinaldo;

import Model.Colaborador;
import Configur.Conexion;
import Model.Cargo;
import Model.Colaborador;
import com.mysql.cj.protocol.Resultset;
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

/**
 *
 * @author Dell
 */
public class AguinaldoDAO {
    
    
    Connection conexion;

    public AguinaldoDAO() {
        Conexion conex = new Conexion();
        conexion = conex.getConectar();
    }
//-----------------------------------------------        
       public List<Aguinaldo> calculoAguinaldo(int anio) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Aguinaldo> aguinaldos = new ArrayList<>();

        try {
            String queryAguinaldo = "SELECT "
                    + " empleado_id_empleado, "
                    + " SUM(salario_bruto) AS total_salarios, "
                    + " SUM(salario_bruto) / 12 AS aguinaldo_calculado "
                    + " FROM planilla "
                    + " WHERE ("
                    + " (MONTH(mes_pago) = 12 AND YEAR(mes_pago) = ?) "
                    + " OR (MONTH(mes_pago) <= 11 AND YEAR(mes_pago) = ? + 1)) "
                    + " GROUP BY empleado_id_empleado";

            ps = conexion.prepareStatement(queryAguinaldo);
            ps.setInt(1, anio);
            ps.setInt(2, anio);

            rs = ps.executeQuery();

            while (rs.next()) {
                int idEmpleado = rs.getInt("empleado_id_empleado");
                double totalSalarios = rs.getDouble("total_salarios");
                double aguinaldo = rs.getDouble("aguinaldo_calculado");

                // Crear objeto Aguinaldo
                Aguinaldo aguinaldoObj = new Aguinaldo(idEmpleado, 
                        totalSalarios, 
                        aguinaldo, 
                        anio, 
                        idEmpleado, 
                        null);
                aguinaldos.add(aguinaldoObj);
            }

        } catch (Exception e) {
            System.out.println("Error al calcular los aguinaldos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (Exception ex) {
                System.out.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
        return aguinaldos;
    }
//-----------------------------------------------------
//----------------------------------------------
    public void insertarAguinaldos(List<Aguinaldo> aguinaldos) {
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String queryCheck = "SELECT COUNT(*) FROM aguinaldo WHERE id_empleado = ? AND anno_aguinaldo = ?";
            String queryInsert = "INSERT INTO aguinaldo (total_salarios, aguinaldo, id_empleado, anno_aguinaldo) "
                    + "VALUES (?, ?, ?, ?)";

            for (Aguinaldo aguinaldo : aguinaldos) {
                // Verificar si ya existe un registro
                ps = conexion.prepareStatement(queryCheck);
                ps.setInt(1, aguinaldo.getIdEmpleado());
                ps.setInt(2, aguinaldo.getAnnoAguinaldo());
                rs = ps.executeQuery();

                if (rs.next() && rs.getInt(1) > 0) {
                    // Mensaje si ya existe
                    System.out.println("El aguinaldo para el empleado con ID " + aguinaldo.getIdEmpleado() 
                            + " ya existe para el año " + aguinaldo.getAnnoAguinaldo() + ". Omitiendo.");
                    continue;
                }

                // Insertar si no existe
                ps = conexion.prepareStatement(queryInsert);
                ps.setDouble(1, aguinaldo.getTotalSalarios());
                ps.setDouble(2, aguinaldo.getAguinaldo());
                ps.setInt(3, aguinaldo.getIdEmpleado());
                ps.setInt(4, aguinaldo.getAnnoAguinaldo());

                ps.executeUpdate();
            }

            System.out.println("Aguinaldos procesados correctamente.");
        } catch (Exception e) {
            System.out.println("Error al insertar aguinaldos: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
            } catch (Exception ex) {
                System.out.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
    }

//----------------------------------------------------------------
//----------------------**
    
    public Aguinaldo verAguinaldoEmpleado(int idEmpleado) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Aguinaldo aguinaldo = null;
        Colaborador colaborador = null;

        try {
            String queryLook = "SELECT e.nombre, e.apellido_1, e.apellido_2, "
                    + "a.total_salarios, a.aguinaldo, a.id_aguinaldo "
                    + "FROM empleado e "
                    + "JOIN aguinaldo a "
                    + "ON e.id_empleado = a.id_empleado "
                    + "WHERE e.id_empleado = ?;";

            ps = conexion.prepareStatement(queryLook);
            ps.setInt(1, idEmpleado);
            rs = ps.executeQuery();

            if (rs.next()) {
                
               
                // Crear y poblar objeto Colaborador
                colaborador = new Colaborador();
                
                colaborador.setId_Empleado(idEmpleado);
                colaborador.setNombre(rs.getString("nombre"));
                colaborador.setApellido_1(rs.getString("apellido_1"));
                colaborador.setApellido_2(rs.getString("apellido_2"));

                // Crear y poblar objeto Aguinaldo
               
                aguinaldo= new Aguinaldo();
                
                aguinaldo.setIdAguinaldo(rs.getInt("id_aguinaldo"));
                aguinaldo.setTotalSalarios(rs.getDouble("total_salarios"));
                aguinaldo.setAguinaldo(rs.getDouble("aguinaldo"));
                aguinaldo.setColaborador(colaborador);

                // Mostrar los datos en consola
                System.out.println("Datos del Aguinaldo del Empleado:");
                System.out.println("Nombre: " + colaborador.getNombre() + " "
                        + colaborador.getApellido_1() + " "
                        + colaborador.getApellido_2());
                System.out.println("ID Aguinaldo: " + aguinaldo.getIdAguinaldo());
                System.out.println("Total de Salarios: " + aguinaldo.getTotalSalarios());
                System.out.println("Monto Aguinaldo: " + aguinaldo.getAguinaldo());
            } else {
                System.out.println("No se encontró información del empleado con ID: " + idEmpleado);
            }
        } catch (Exception e) {
            System.out.println("Error al consultar los datos del aguinaldo: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception ex) {
                System.out.println("Error al cerrar recursos: " + ex.getMessage());
            }
        }
        return aguinaldo; // Devuelve el objeto Aguinaldo o null si no existe
    }
//------------------------------------------------------------------ 
    
 
    
//---------------------------------------------------------------
}
