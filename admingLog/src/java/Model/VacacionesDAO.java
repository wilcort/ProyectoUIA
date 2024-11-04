/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;


import Configur.Conexion;
import Model.Colaborador;
import ModelEmpleado.Vacaciones;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.sql.*;
import java.util.Calendar;
import java.util.Date;


public class VacacionesDAO {
    Connection conexion;

    public VacacionesDAO() {
        Conexion conex = new Conexion();
        conexion = conex.getConectar();
    }
  
  //----------------------------------------------------------------------  
//------------- CALCAULAR DIAS VACACIONES ---------------

    public Vacaciones mostrarVacaciones(int idEmpleado) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vacaciones vacaciones = null; // Inicializamos Vacaciones como null

        try {
            ps = conexion.prepareStatement("SELECT "
                    + "    e.nombre, "
                    + "    e.apellido_1, "
                    + "    e.apellido_2, "
                    + "    e.fecha_contratacion, "
                    + "    v.* "
                    + "FROM "
                    + "    vacaciones v "
                    + "INNER JOIN "
                    + "    empleado e ON v.empleado_id_empleado = e.id_empleado "
                    + "WHERE v.empleado_id_empleado = ?");

            ps.setInt(1, idEmpleado);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Obtener información del empleado
                String nombre = rs.getString("nombre");
                String apellido1 = rs.getString("apellido_1");
                String apellido2 = rs.getString("apellido_2");
                Date fechaContratacion = rs.getDate("fecha_contratacion");

                // Crear el objeto Colaborador
                Colaborador colaborador = new Colaborador(
                        idEmpleado,
                        idEmpleado,
                        nombre,
                        apellido1,
                        apellido2,
                        idEmpleado,
                        null,
                        fechaContratacion,
                        null,
                        null,
                        null,
                        null,
                        null
                );
                colaborador.setNombre(nombre);
                colaborador.setApellido_1(apellido1);
                colaborador.setApellido_2(apellido2);
                colaborador.setFecha_contratacion(fechaContratacion);

                // Establecer el objeto Colaborador en Vacaciones
                vacaciones.setColaborador(colaborador);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Muestra la excepción en caso de error
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return vacaciones;
    }
//---------------------------------
    
   public void calcularYGuardarVacaciones(Date fechaContratacion, int idEmpleado) {
    // Obtener la fecha actual
    Calendar fechaActual = Calendar.getInstance();

    // Convertir fecha de contratación a Calendar
    Calendar fechaContratacionCal = Calendar.getInstance();
    fechaContratacionCal.setTime(fechaContratacion);

    // Calcular los años trabajados
    int añosTrabajados = fechaActual.get(Calendar.YEAR) - fechaContratacionCal.get(Calendar.YEAR);
    if (fechaActual.get(Calendar.DAY_OF_YEAR) < fechaContratacionCal.get(Calendar.DAY_OF_YEAR)) {
        añosTrabajados--; // Ajuste si no ha cumplido el año
    }

    // Calcular los días de vacaciones
    int diasVacacionesBase = 12; // Días de vacaciones base
    int diasVacacionesPorAño = 2; // Días adicionales por cada año trabajado

    // Total de días de vacaciones
    int diasVacacionesTotal = diasVacacionesBase + (añosTrabajados * diasVacacionesPorAño);

    // Verificar si ya existe un registro de vacaciones para el empleado
    if (!existeVacacionesPorEmpleado(idEmpleado)) {
        // Insertar información de vacaciones en la base de datos
        insertarVacacionesEnDB(idEmpleado, diasVacacionesTotal);
    } else {
        System.out.println("Ya existe un registro de vacaciones para el empleado con ID: " + idEmpleado);
        // Aquí puedes agregar lógica adicional si lo deseas, como actualizar el registro existente
    }
}

    private void insertarVacacionesEnDB(int idEmpleado, int diasVacacionesTotal) {
        PreparedStatement ps = null;

        try {
            String sql = "INSERT INTO vacaciones (dias_vacaciones_total,"
                    + " estado_solicitud, empleado_id_empleado) VALUES (?, ?, ?)";
            ps = conexion.prepareStatement(sql); // Asegúrate de tener la conexión inicializada
            ps.setInt(1, diasVacacionesTotal);
            ps.setString(2, "consulta"); // Cambia a un estado significativo si es necesario
            ps.setInt(3, idEmpleado);
            ps.executeUpdate();
            System.out.println("Vacaciones guardadas correctamente para el empleado ID: " + idEmpleado);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Cerrar el PreparedStatement
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
 //-------------------------------------
    
    public Date obtenerFechaContratacion(int idEmpleado) {
    Date fechaContratacion = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        String sql = "SELECT fecha_contratacion FROM empleado WHERE id_empleado = ?";
        ps = conexion.prepareStatement(sql);
        ps.setInt(1, idEmpleado);
        rs = ps.executeQuery();

        if (rs.next()) {
            fechaContratacion = rs.getDate("fecha_contratacion");
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
    return fechaContratacion;
}
//-------------------
// Método para verificar si ya existen vacaciones para un empleado
private boolean existeVacacionesPorEmpleado(int idEmpleado) {
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        String sql = "SELECT COUNT(*) FROM vacaciones WHERE "
                + "empleado_id_empleado = ? AND estado_solicitud = 'consulta'";
        
        ps = conexion.prepareStatement(sql);
        ps.setInt(1, idEmpleado);
        rs = ps.executeQuery();

        if (rs.next()) {
            return rs.getInt(1) > 0; // Retorna true si hay registros, false si no
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Cerrar recursos
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    return false; // Por defecto, retorna false si ocurre un error
}
 //----------------------------------   
}
