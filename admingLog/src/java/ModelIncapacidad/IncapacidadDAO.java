/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelIncapacidad;

import Configur.Conexion;
import Model.Colaborador;
import ModelEmpleado.Vacaciones;
import java.io.InputStream;
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

/**
 *
 * @author Dell
 */
public class IncapacidadDAO {

    Connection conexion;

    public IncapacidadDAO() {
        Conexion conex = new Conexion();
        conexion = conex.getConectar();
    }
//-----------------------------------------------------------
//------------------- SOLICITAR INCAPACIDADES ---------------

    public Integer solicitarIncapacidad(Incapacidades incapacidad, int idEmpleado) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer idIncapacidadGenerado = null;

        try {
            String sql = "INSERT INTO incapacidades (motivo,"
                    + " fecha_inicio, fecha_fin, "
                    + "dias_incapacidad, estado,"
                    + " empleado_id_empleado)"
                    + " VALUES (?, ?, ?, ?, ?, ?)";

            ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setString(1, incapacidad.getMotivo());
            ps.setDate(2, new java.sql.Date(incapacidad.getFechaInicio().getTime()));
            ps.setDate(3, new java.sql.Date(incapacidad.getFechaFin().getTime()));
            ps.setInt(4, incapacidad.getDiasIncapacidad());
            ps.setString(5, "Pendiente");
            ps.setInt(6, idEmpleado);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    idIncapacidadGenerado = rs.getInt(1);
                    incapacidad.setIdIncapacidad(idIncapacidadGenerado);
                    System.out.println("ID de la incapacidad insertada: " + idIncapacidadGenerado);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return idIncapacidadGenerado;
    }

//-----------------------------------------------------------------------------
//------------------------------- SUBTABLAS INCAPACIDADES ------------------
//--------------------------------------------------------------------------
    public boolean insertarIncapacidadEnfermedadComun(IncapacidadEnfComun incapacidad, int idIncapacidad) {
        PreparedStatement ps = null;

        try {
            // La consulta SQL para insertar en la tabla
            String sql = "INSERT INTO incapacidad_enfermedad_comun "
                    + "(id_incapacidad, detalle , documento) VALUES (?, ?, ?)";

            // Crear un PreparedStatement para ejecutar la consulta
            ps = conexion.prepareStatement(sql);

            ps.setInt(1, idIncapacidad);
            ps.setString(2, incapacidad.getDetalle());
            ps.setBlob(3, incapacidad.getDocumento());

            System.out.println("idincapacidad" + idIncapacidad);
            System.out.println("detalle" + incapacidad.getDetalle());

            // Ejecutar la consulta de inserción
            int filasAfectadas = ps.executeUpdate();
            System.out.println("enviado");
            // Retornar verdadero si la inserción fue exitosa
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar incapacidad: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }
    }

//---------------------------------------------------------------
//---------------   --------------
    public boolean insertarIncapacidadMaternidad(IncapacidadMaternidad incapacidad, int idIncapacidad) {
        PreparedStatement ps = null;
        try {
            // La consulta SQL para insertar en la tabla incapacidad_maternidad
            String sql = "INSERT INTO incapacidad_maternidad "
                    + "(id_incapacidad, detalle, semanas_de_gestacion, documento) "
                    + "VALUES (?, ?, ?, ?)";

            // Crear un PreparedStatement para ejecutar la consulta
            ps = conexion.prepareStatement(sql);

            // Establecer los valores en el PreparedStatement
            ps.setInt(1, idIncapacidad);
            ps.setString(2, incapacidad.getDetalle());
            ps.setInt(3, incapacidad.getSemanasDeGestacion());
            ps.setBlob(4, incapacidad.getDocumento());; // Usa el InputStream para el campo BLOB

            System.out.println("idincapacidad" + idIncapacidad);
            System.out.println("detalle" + incapacidad.getDetalle());
            System.out.println("semanas" + incapacidad.getSemanasDeGestacion());

            // Ejecutar la consulta de inserción
            int filasAfectadas = ps.executeUpdate();
            System.out.println("enviado");
            // Retornar verdadero si la inserción fue exitosa
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar incapacidad de maternidad: " + e.getMessage());
            return false;
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }
    }
//---------------------------------------------------------------------****

    public List<Incapacidades> listarIncapEmpleado(int idEmpleado) {
        List<Incapacidades> listarIncapEmpleado = new ArrayList<>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sqlIncapacidad = "SELECT i.*, m.detalle AS detalle_maternidad, m.semanas_de_gestacion, m.documento AS documento_maternidad, "
                    + "e.detalle AS detalle_enfermedad_comun, e.documento AS documento_enfermedad_comun "
                    + "FROM incapacidades i "
                    + "LEFT JOIN incapacidad_maternidad m ON i.id_incapacidad = m.id_incapacidad "
                    + "LEFT JOIN incapacidad_enfermedad_comun e ON i.id_incapacidad = e.id_incapacidad "
                    + "WHERE i.empleado_id_empleado = ?";

            ps = conexion.prepareStatement(sqlIncapacidad);
            ps.setInt(1, idEmpleado);
            rs = ps.executeQuery();

            while (rs.next()) {
                Incapacidades incapacidad = new Incapacidades();
                incapacidad.setIdIncapacidad(rs.getInt("id_incapacidad"));
                incapacidad.setMotivo(rs.getString("motivo"));
                incapacidad.setFechaInicio(rs.getDate("fecha_inicio"));
                incapacidad.setFechaFin(rs.getDate("fecha_fin"));
                incapacidad.setDiasIncapacidad(rs.getInt("dias_incapacidad"));
                incapacidad.setEstado(rs.getString("estado"));
                incapacidad.setEmpleadoIdEmpleado(rs.getInt("empleado_id_empleado"));

                // Datos de maternidad y enfermedad común
                String detalleMaternidad = rs.getString("detalle_maternidad");
                InputStream documentoMaternidad = rs.getBinaryStream("documento_maternidad");

                String detalleEnfermedadComun = rs.getString("detalle_enfermedad_comun");
                InputStream documentoEnfermedadComun = rs.getBinaryStream("documento_enfermedad_comun");

                listarIncapEmpleado.add(incapacidad);
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return listarIncapEmpleado;
    }
//-------------------------------------------------------//
//--------------------  PARTE ADMINISTRATIVA --------------------

    public List<Incapacidades> listarSolicitudesIncap(int idEmpleado) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Incapacidades> listarIncapEmpleado = new ArrayList<>();

        try {
            String sqlIncapacidad = "SELECT i.*, m.*, c.*, e.*"
                    + " FROM incapacidades i"
                    + " LEFT JOIN incapacidad_maternidad m ON i.id_incapacidad = m.id_incapacidad"
                    + " LEFT JOIN incapacidad_enfermedad_comun c ON i.id_incapacidad = c.id_incapacidad "
                    + " LEFT JOIN empleado e ON i.empleado_id_empleado = ?";

            ps = conexion.prepareStatement(sqlIncapacidad);
            ps.setInt(1, idEmpleado);
            rs = ps.executeQuery();

            while (rs.next()) {
                Incapacidades incapacidad = new Incapacidades();

                // Datos de incapacidad
                incapacidad.setIdIncapacidad(rs.getInt("id_incapacidad"));
                incapacidad.setMotivo(rs.getString("motivo"));
                incapacidad.setFechaInicio(rs.getDate("fecha_inicio"));
                incapacidad.setFechaFin(rs.getDate("fecha_fin"));
                incapacidad.setDiasIncapacidad(rs.getInt("dias_incapacidad"));
                incapacidad.setEstado(rs.getString("estado"));
                incapacidad.setEmpleadoIdEmpleado(rs.getInt("empleado_id_empleado"));

                // Establecer IncapacidadMaternidad
                IncapacidadMaternidad maternidad = new IncapacidadMaternidad();
                maternidad.setDetalle(rs.getString("detalle_maternidad"));
                maternidad.setDocumento(rs.getBinaryStream("documento_maternidad"));
                incapacidad.setIncapacidadMaternidad(maternidad);

                // Establecer IncapacidadEnfComun
                IncapacidadEnfComun comun = new IncapacidadEnfComun();
                comun.setDetalle(rs.getString("detalle_enfermedad_comun"));
                comun.setDocumento(rs.getBinaryStream("documento_enfermedad_comun"));
                incapacidad.setIncapacidadEnfComun(comun);

                listarIncapEmpleado.add(incapacidad);
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
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return listarIncapEmpleado;
    }
//-------------------------------------------------------------------------------
//------- LISTAR TODAS LAS SOLICITUDES DE INCAPACIDAD --
public List<Colaborador> listarEmpleados() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Colaborador> listaEmpleados = new ArrayList<>();

        try {
            // Consulta para obtener todos los empleados
            String sql = "SELECT id_empleado, nombre, apellido_1, apellido_2 "
                    + "FROM empleado";  

            ps = conexion.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                // Datos del empleado
                int idEmpleado = rs.getInt("id_empleado");
                String nombre = rs.getString("nombre");
                String apellido1 = rs.getString("apellido_1");
                String apellido2 = rs.getString("apellido_2");

                // Crear el objeto colaborador (empleado)
                Colaborador colaborador = new Colaborador();
                colaborador.setId_Empleado(idEmpleado);
                colaborador.setNombre(nombre);
                colaborador.setApellido_1(apellido1);
                colaborador.setApellido_2(apellido2);

                // Añadir el colaborador a la lista
                listaEmpleados.add(colaborador);
            }
        } catch (Exception e) {
            System.out.println("Error al listar empleados: " + e.getMessage());
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
        return listaEmpleados;
    }
//--------------------------------------------
//----------------------- DOCUMENTOS------------------------------

// Método para recuperar el documento de una incapacidad por su ID
    public InputStream obtenerDocumento(int idIncapacidad) throws SQLException {
        
        InputStream documento = null;
        String query = "SELECT documento FROM incapacidad_enfermedad_comun WHERE id_incapacidad = ? " +
                   "UNION " +
                   "SELECT documento FROM incapacidad_maternidad WHERE id_incapacidad = ?";

        try (PreparedStatement stmt = conexion.prepareStatement(query)) {
             stmt.setInt(1, idIncapacidad);
             stmt.setInt(2, idIncapacidad); 

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    documento = rs.getBinaryStream("documento"); // Recupera el documento como InputStream
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error al recuperar el documento", e);
        }

        return documento;
    }

//------------------------------------------------------
//----------------------- APROBAR INCAPACIDADES -------------------
    public boolean incapacidadAprobada(int idIncapacidad, Incapacidades incapacidad) {

        PreparedStatement ps = null;
        boolean result = false;

        try {
            String sql = "UPDATE incapacidades SET "
                    + "estado = 'Aprobada' "
                    + "WHERE id_incapacidad = ?";
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, idIncapacidad);

            int filasActualizadas = ps.executeUpdate();
            result = filasActualizadas > 0;
            System.out.println(" enviado " +result );
            if (!result) {
                System.out.println("No se encontró una incapacidad en estado 'Pendiente' con el ID proporcionado.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar la incapacidad: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }

        return result;
    }
 //---------------------------------------------------------------
 //---------------------- DENEGER INCAPACIDADES ------------------------- 
    public boolean incapacidadDenegada(int idIncapacidad) {
        PreparedStatement ps = null;
        boolean result = false;

        try {
            String sql = "UPDATE incapacidades "
                    + "SET estado = 'Rechazada' WHERE id_incapacidad = ?";
            
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, idIncapacidad);

            int filasActualizadas = ps.executeUpdate();
            result = filasActualizadas > 0;
            System.out.println("Solicitud denegada: " + result);

            if (!result) {
                System.out.println("No se encontró una incapacidad en estado 'Pendiente' con el ID proporcionado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar la incapacidad: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar PreparedStatement: " + e.getMessage());
            }
        }

        return result;
    }

//-------------------------------------------------------
}
