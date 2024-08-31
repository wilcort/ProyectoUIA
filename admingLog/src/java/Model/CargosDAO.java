/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Configur.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CargosDAO {

    Connection conexion;

    public CargosDAO() {
        Conexion conex = new Conexion();
        conexion = conex.getConectar();
    }

    public List<Cargo> listarCargos() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Cargo> lista = new ArrayList<>();

        try {
            String sql = "SELECT id_cargo, nombre_cargo, estado FROM cargo";
            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                int idCargo = rs.getInt("id_cargo");
                String nombreCargo = rs.getString("nombre_cargo");
                boolean estado = rs.getBoolean("estado");

                Cargo cargoExistente = new Cargo(idCargo, nombreCargo, estado);
                lista.add(cargoExistente);
            }
        } catch (SQLException e) {
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
        return lista;
    }
// --------------------------- buscar cargo ------------------------
    
    public Cargo mostrar_Cargos(int idCargo) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Cargo cargosExistente = null;

        try {
            
            ps = conexion.prepareStatement("SELECT id_cargo, nombre_cargo, estado"
                                        + " FROM cargo WHERE id_cargo = ?");
            ps.setInt(1, idCargo);
            rs = ps.executeQuery();

            while (rs.next()) {
                // Variable por cada elemento de la clase Cargo
                int idcargo = rs.getInt("id_cargo");
                String nombreCargo = rs.getString("nombre_cargo");
                boolean estado = rs.getBoolean("estado");

                cargosExistente = new Cargo(idcargo, nombreCargo, estado);

            }
        } catch (SQLException e) {
            System.out.println("Error al listar cargos: " + e.getMessage());
        } finally {
            // Cerrar PreparedStatement y ResultSet
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
        return cargosExistente;
    }
    
 //------------------------------------------------------------------------------
     public boolean crear_Cargos(Cargo cargo ) {

        PreparedStatement ps = null;
         
        try {
            
            // Generate a new ID if needed
        String sqlSelectMax = "SELECT MAX(id_cargo) FROM cargo";
        ps = conexion.prepareStatement(sqlSelectMax);
        ResultSet rs = ps.executeQuery();
        int newId = 1;
        if (rs.next()) {
            newId = rs.getInt(1) + 1;
        }
            
            
            ps = conexion.prepareStatement ("INSERT INTO cargo(id_cargo, nombre_cargo, estado) "
                    + " VALUES (?, ?, ?)");
                   
            ps.setInt(1, newId);
            ps.setString(2, cargo.getNombreCargo());
            ps.setBoolean(3, cargo.isEstado());
           
            
            int filasAfectadasEmpleado = ps.executeUpdate();

            if (filasAfectadasEmpleado == 1) {
                return true;
                
            } else {
                throw new SQLException("No se pudo insertar el colaborador");
            }

         } catch (SQLException e) {
            System.out.println("Error al insertar colaboradores: " + e.getMessage());
            return false;
        } finally {
            // Cerrar PreparedStatements
            try {
                if (ps != null) {
                    ps.close();
                }
                
            } catch (SQLException ex) {
                System.out.println("Error al cerrar PreparedStatements: " + ex.getMessage());
            }
        }
    }
     
 //------------------------------------------------------------------------------
 
 //--------------------------------------------------------------------------------   
    public boolean modificar_Cargos(Cargo cargo) {
        PreparedStatement ps = null;
        try {
            ps = conexion.prepareStatement("UPDATE cargo SET nombre_cargo = ?,"
                               + " estado = ? WHERE id_cargo = ?");
            ps.setString(1, cargo.getNombreCargo());
            ps.setBoolean(2, cargo.isEstado());
            ps.setInt(3, cargo.getIdCargo());

            int filasAfectadas = ps.executeUpdate();

            return filasAfectadas == 1;
        } catch (SQLException e) {
            System.out.println("Error al modificar cargo: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar PreparedStatement: " + ex.getMessage());
            }
        }
    }


     
}

