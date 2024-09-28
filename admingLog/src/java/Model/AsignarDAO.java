/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Configur.Conexion;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Time;
import java.util.Set;
import java.util.HashSet;

public class AsignarDAO {

    Connection conexion;

    public AsignarDAO() {
        Conexion conex = new Conexion();
        conexion = conex.getConectar();
    }

    //------------------------------- CARGOS --------------------------------------// 
    //------------------------------ OBTENER LISTA CARGO ----------------------//   
    public List<Cargo> listarCargos() {
        List<Cargo> listaCargos = new ArrayList<>();
        String sql = "SELECT * FROM cargo";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id_Cargo = rs.getInt("id_cargo");
                String nombre_cargo = rs.getString("nombre_cargo");
                boolean estado = rs.getBoolean("estado");
                Cargo cargo = new Cargo(id_Cargo, nombre_cargo, estado);
                listaCargos.add(cargo);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar cargos: " + e.getMessage());
        }
        return listaCargos;
    }

    //-------------------------------------------------------------------------------------//
//------------------------------ OBTENER CARGO POR id -----------------------------------------//   
    public Cargo obtenerCargoPorId(int idCargo) {
        Cargo cargo = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement("SELECT * FROM cargo WHERE id_Cargo = ?");
            
            ps.setInt(1, idCargo);
            rs = ps.executeQuery();
            if (rs.next()) {
                cargo = new Cargo();
                cargo.setIdCargo(rs.getInt("id_Cargo"));
                cargo.setNombreCargo(rs.getString("nombre_Cargo"));
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
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return cargo;
    }

    //-------------------------------------------------------------------------------------//
//------------------------------ OBTENER CARGO por nombre-----------------------------------------//   
    public Cargo obtenerCargoPorNombre(String nombreCargo) {
        PreparedStatement ps;
        ResultSet rs;
        Cargo cargo = null;

        try {
            ps = conexion.prepareStatement("SELECT * FROM cargo WHERE nombre_cargo = ?");
            ps.setString(1, nombreCargo);
            rs = ps.executeQuery();

            if (rs.next()) {
                int id_Cargo = rs.getInt("id_Cargo");
                String nombre_cargo = rs.getString("nombre_Cargo");
                boolean estado = rs.getBoolean("estado");

                cargo = new Cargo(id_Cargo, nombre_cargo, estado);

            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el cargo por nombre: " + e.getMessage());
        }

        return cargo;
    }

    //---------------------------------------------------------------------//  
    public boolean asignarCargo(Colaborador colaborador, Cargo cargo) {

        PreparedStatement ps = null;

        try {
             ps = conexion.prepareStatement("UPDATE empleado SET id_cargo = ?"
                     + " WHERE id_empleado = ?");
             
            ps.setInt(1, cargo.getIdCargo());
            ps.setInt(2, colaborador.getId_Empleado());
         

            int filaAfectada = ps.executeUpdate();

            if (filaAfectada == 1) {
                return true;
            } else {
                throw new SQLException("No se pudo insertar el cargo");
            }
        } catch (Exception e) {
            System.out.println("Error al insertar colaboradores: " + e.getMessage());
            return false;
        } finally {
            // Cerrar PreparedStatements
            try {
                if (ps != null) {
                    ps.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar PreparedStatements: " + ex.getMessage());
            }
        }

    }
//----------------------------- HORARIOS --------------------------------------------------
//-------------------------------------------------------------------------------
public boolean asignarHorario(Colaborador colaborador, Horarios horario) {

        PreparedStatement ps = null;

        try {
            ps = conexion.prepareStatement("UPDATE empleado SET horarios_id_horario = ?"
                    + " WHERE id_empleado = ?");

            ps.setInt(1, horario.getIdHorario());
            ps.setInt(2, colaborador.getId_Empleado());

            int filaAfectada = ps.executeUpdate();

            if (filaAfectada == 1) {
                return true;
            } else {
                throw new SQLException("No se pudo insertar el horario");
            }
        } catch (Exception e) {
            System.out.println("Error al insertar colaboradores: " + e.getMessage());
            return false;
        } finally {
            // Cerrar PreparedStatements
            try {
                if (ps != null) {
                    ps.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar PreparedStatements: " + ex.getMessage());
            }
        }

    }
//--------------------------------------------------

    public List<Horarios> listarHorarios() {
        List<Horarios> listaHorario = new ArrayList<>();
        String sql = "SELECT * FROM horarios";

        try {
            PreparedStatement ps = conexion.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id_Horario = rs.getInt("id_horario");
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

                Horarios horario = new Horarios(id_Horario, horaEntrada,
                        horaSalida, horasLaborales, diasLaborales);

                listaHorario.add(horario);
            }
            
        } catch (SQLException e) {
            System.out.println("Error al listar cargos: " + e.getMessage());
        }
        return listaHorario;
    }

//------------------------------------------------------------
//------------------------------------------------------------------------

    public Horarios obtenerHorarioPorId(int idHorario) {
        Horarios horario = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = conexion.prepareStatement("SELECT * FROM horarios WHERE id_horario = ?");

            ps.setInt(1, idHorario);
            rs = ps.executeQuery();

            if (rs.next()) {
                horario = new Horarios();

                horario.setIdHorario(rs.getInt("id_horatio"));
                horario.setHoraEntrada(rs.getTime("hora_entrada"));
                horario.setHoraSalida(rs.getTime("hora_salida"));
                horario.setHorasLaborales(rs.getDouble("horas_laborales"));

                // Suponiendo que los días laborales son almacenados como un string
                // separado por comas en la base de datos
                String diasLaboralesString = rs.getString("dias_laborales");
                Set<String> diasLaboralesSet = new HashSet<>();

                if (diasLaboralesString != null && !diasLaboralesString.isEmpty()) {
                    String[] diasArray = diasLaboralesString.split(",");
// Divide el string por comas
                    for (String dia : diasArray) {
                        diasLaboralesSet.add(dia.trim()); // Agrega cada día al conjunto
                    }
                }

                horario.setDiasLaborales(diasLaboralesSet);
// Establece el conjunto de días laborales

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
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return horario;
    }



//-----------------------------------------------------------------------    
    
}
