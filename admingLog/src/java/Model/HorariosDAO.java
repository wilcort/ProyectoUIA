/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import ModelEmpleado.Horarios;
import Configur.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.Time;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author Dell
 */
public class HorariosDAO {

    Connection conexion;

    public HorariosDAO() {
        Conexion conex = new Conexion();
        conexion = conex.getConectar();
    }

    public List<Horarios> listarHorarios() {

        PreparedStatement ps = null; // ejecutar consultas -  prevenir ataques de inyección SQL, ya que los 
        //parámetros se envían al servidor de base de datos por separado de la consulta SQL.
        ResultSet rs = null;        // Permite iterar sobre los resultados de la consulta y acceder a 
        //los datos recuperados de la base de datos.
        List<Horarios> listaHorario = new ArrayList<>();

        try {
            String sql = "SELECT id_horario, hora_entrada, hora_salida,"
                    + "horas_laborales,dias_laborales FROM horarios";

            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {

                int idHorario = rs.getInt("id_horario");
                Time horaEntrada = rs.getTime("hora_entrada");
                Time horaSalida = rs.getTime("hora_salida");
                double horasLaborales = rs.getDouble("horas_laborales");
                String diasLaboralesStr = rs.getString("dias_laborales");

                // Convertir la cadena a Set<String>
                Set<String> diasLaborales = new HashSet<>();
                if (diasLaboralesStr != null && !diasLaboralesStr.isEmpty()) {
                    String[] diasArray = diasLaboralesStr.split(",");
                    for (String dia : diasArray) {
                        diasLaborales.add(dia.trim());
                    }
                }

                // Crear el objeto Horarios y añadirlo a la lista
                Horarios horario = new Horarios(idHorario, horaEntrada, horaSalida,
                        horasLaborales, diasLaborales);

                listaHorario.add(horario);
            }

        }catch (SQLException e) {
            System.out.println("Error al listar cargos: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar PreparedStatement o ResultSet: " + ex.getMessage());
            }
        }
        return listaHorario;
    }
 // -------------------- MOSTRAR HORARIO---------------   
    
       public Horarios mostrar_Horario(int idHorario) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Horarios horarioExistente = null;

        try {
            ps = conexion.prepareStatement("SELECT id_horario, hora_entrada, "
                    + "hora_salida, horas_laborales, dias_laborales FROM horarios "
                    + "WHERE id_horario = ?");
            ps.setInt(1, idHorario);
            rs = ps.executeQuery();

            if (rs.next()) {
                int id_horario = rs.getInt("id_horario");
                Time hora_entrada = rs.getTime("hora_entrada");
                Time hora_salida = rs.getTime("hora_salida");
                double horas_laborales = rs.getDouble("horas_laborales");
                String diasLaboralesStr = rs.getString("dias_laborales");

                Set<String> diasLaborales = new HashSet<>();
                if (diasLaboralesStr != null && !diasLaboralesStr.isEmpty()) {
                    String[] diasArray = diasLaboralesStr.split(",");
                    for (String dia : diasArray) {
                        diasLaborales.add(dia.trim());
                    }
                }
                // Crear el objeto Horarios y añadirlo a la lista

                horarioExistente = new Horarios(id_horario, hora_entrada,
                        hora_salida, horas_laborales, diasLaborales);
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar horario: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar PreparedStatement o ResultSet: " + ex.getMessage());
            }
        }
        return horarioExistente;
    }

  //------------------------------------
    
    public boolean crear_Horario(Horarios horario) {
        PreparedStatement ps = null;

        try {
            // Convertir Set<String> a String separado por comas
            String diasLaboralesStr = String.join(",", horario.getDiasLaborales());

            // Preparar la consulta
        ps = conexion.prepareStatement(
                "INSERT INTO horarios(hora_entrada, hora_salida, "
                         + "dias_laborales) VALUES (?, ?, ?)",
                         PreparedStatement.RETURN_GENERATED_KEYS
        );

        // Establecer los parámetros de la consulta
        ps.setTime(1, horario.getHoraEntrada());
        ps.setTime(2, horario.getHoraSalida());
        ps.setString(3, diasLaboralesStr);  // Aquí convertimos el Set a String

        // Ejecutar la consulta
        int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 1) {
                // Recuperar la clave generada automáticamente (si es necesario)
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int nuevoId = generatedKeys.getInt(1);
                        horario.setIdHorario(nuevoId);
                    }
                }               
                return true;
            } else {
                throw new SQLException("No se pudo insertar el horario");
            }

        } catch (SQLException e) {
            // Manejar la excepción
            System.out.println("Error al insertar colaboradores: " + e.getMessage());
            return false;
        } finally {
            // Cerrar PreparedStatement
           try {
                if (ps != null) {
                    ps.close();
                }
                
            } catch (SQLException ex) {
                System.out.println("Error al cerrar PreparedStatements: " + ex.getMessage());
            }
        }
    }
      
//------------------------------------------------
    public boolean eliminarHorario(int idHorario) {
        PreparedStatement ps;

        try {
            ps = conexion.prepareStatement("DELETE FROM horarios WHERE id_horario = ?");

            ps.setInt(1, idHorario);
            ps.execute();
            return true;
            
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
    
    //------------------------------------------------
}
