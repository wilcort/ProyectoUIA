/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelEmpleado;

import Configur.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Dell
 */
public class VacacionesDAO {
    Connection conexion;

    public VacacionesDAO() {
        Conexion conex = new Conexion();
        conexion = conex.getConectar();
    }

//----------------------------------------------------------------------  
//------------- CALCAULAR DIAS VACACIONES ---------------
    
    public Vacaciones calcular(int idEmpleado) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vacaciones vacaciones = null; // Inicializamos Vacaciones como null

        try {
            // Preparar la llamada al procedimiento almacenado
            ps = conexion.prepareCall("{CALL calcularVacaciones(?)}");
            ps.setInt(1, idEmpleado);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Obtener los datos del colaborador
                String nombre = rs.getString("nombre");
                String apellido_1 = rs.getString("apellido_1");
                String apellido_2 = rs.getString("apellido_2");
                java.sql.Date fechaContratacion = rs.getDate("fecha_contratacion");
                int diasVacacionesTotal = rs.getInt("dias_vacaciones_total"); // Asegúrate de que sea un int

                // Crear la instancia de Vacaciones con los datos obtenidos
                vacaciones = new Vacaciones(
                        0, 
                        null, 
                        null, 
                        null, 
                        diasVacacionesTotal,
                        0,
                        null, 
                        null, 
                        null, 
                        idEmpleado
                );

                // Imprimir o devolver los datos obtenidos
                System.out.println("Empleado: " + nombre + " " + apellido_1 + " " + apellido_2);
                System.out.println("Fecha de Contratación: " + fechaContratacion);
                System.out.println("Días de Vacaciones Total: " + diasVacacionesTotal);
            } else {
                System.out.println("No se encontraron datos para el empleado con ID: " + idEmpleado);
            }

        } catch (SQLException ex) {
            ex.printStackTrace(); // Manejo de excepciones
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return vacaciones; // Retorna el objeto Vacaciones
    }
//-----------------------------------------------------------
//------------------ MOSTRAR DAOS VACACIONES ---------------------
    
    public List<Vacaciones> obtenerTodasVacaciones(int idEmpleado) {
        List<Vacaciones> vacacionesList = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            // Unir la tabla vacaciones con la tabla empleado
            String sql = "SELECT v.*, e.nombre, e.apellido_1, e.apellido_2 "
                    + "FROM vacaciones v "
                    + "JOIN empleado e ON v.empleado_id_empleado = e.id_empleado "
                    + "WHERE v.empleado_id_empleado = ?";
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, idEmpleado);
            rs = ps.executeQuery();

            while (rs.next()) {
                // Crear un objeto Vacaciones para cada fila
                Set<String> estadoSet = new HashSet<>(); // Crear un nuevo Set
                String estadoSolicitud = rs.getString("estado_solicitud");
                estadoSet.add(estadoSolicitud); // Agregar el estado al Set

                // Crear la instancia de Vacaciones
                Vacaciones vacaciones = new Vacaciones(
                        rs.getInt("id_vacacion"),
                        rs.getDate("fecha_solicitud"),
                        rs.getDate("fecha_inicio"),
                        rs.getDate("fecha_fin"),
                        rs.getInt("dias_vacaciones_total"),
                        rs.getInt("dias_vacaciones_solicitados"),
                        estadoSet, // Pasar el Set en lugar de un String
                        rs.getString("comentario"),
                        rs.getDate("fecha_aprobacion"),
                        rs.getInt("empleado_id_empleado")
                );

                // Agregar a la lista de vacaciones
                vacacionesList.add(vacaciones);

                // Obtener el nombre del empleado
                String nombreEmpleado = rs.getString("nombre");
                String apellido1 = rs.getString("apellido_1");
                String apellido2 = rs.getString("apellido_2");

                // Imprimir en consola los datos de las vacaciones y del empleado
                System.out.println("Empleado: " + nombreEmpleado + " " + apellido1 + " " + apellido2);
                System.out.println("ID Vacación: " + vacaciones.getIdVacacion());
                System.out.println("Días de Vacaciones Total: " + vacaciones.getDiasVacacionesTotal());
                System.out.println("Estado de Solicitud: " + estadoSolicitud);
                System.out.println("---------------------------");
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Manejo de excepciones
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return vacacionesList; // Retorna la lista de vacaciones
    }
//-----------------------------------------------------------
//------------------ SOLICITAR  VACACIONES ---------------------
    
   public boolean solicitarVacaciones(int idEmpleado, Vacaciones vacaciones) {
    PreparedStatement ps = null;
    boolean isUpdated = false;

    try {
        ps = conexion.prepareStatement(
                "UPDATE vacaciones SET "
                + "fecha_solicitud = ?, "
                + "fecha_inicio = ?, "
                + "fecha_fin = ?, "
                + "dias_vacaciones_solicitados = ?, "
                + "estado_solicitud = ?, "
                + "comentario = ? "
                + "WHERE empleado_id_empleado = ?"
        );

        // Convertir fechaSolicitud a java.sql.Date
        ps.setDate(1, new java.sql.Date(vacaciones.getFechaSolicitud().getTime()));
        ps.setDate(2, new java.sql.Date(vacaciones.getFechaInicio().getTime()));
        ps.setDate(3, new java.sql.Date(vacaciones.getFechaFin().getTime()));

        ps.setInt(4, vacaciones.getDiasVacacionesSolicitados());

        // Convertir el Set<String> a un String
        String estado = vacaciones.getEstadoSolicitud().iterator().next();
        ps.setString(5, estado);

        ps.setString(6, vacaciones.getComentario());
        ps.setInt(7, idEmpleado);

        // Ejecutar la actualización
         System.out.println(ps.toString());
        int rowsAffected = ps.executeUpdate();
        isUpdated = (rowsAffected > 0);

        if (isUpdated) {
            System.out.println("CAMBIO REALIZADO");
        } else {
            System.out.println("NO SE REALIZÓ NINGÚN CAMBIO");
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    return isUpdated;
}


//----------------------------------------------
}
