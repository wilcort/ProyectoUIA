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
    public List<Vacaciones> mostrarVacaciones(int idEmpleado) {
        List<Vacaciones> vacacionesList = new ArrayList<>();

        // Obtener fecha de contratación
        Date fechaContratacion = obtenerFechaContratacion(idEmpleado);
        if (fechaContratacion != null) {
            // Calcular y guardar vacaciones si es necesario
            calcularYGuardarVacaciones(fechaContratacion, idEmpleado);
        }

        // Código de consulta y retorno de lista de vacaciones
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT v.*, e.nombre, e.apellido_1, e.apellido_2, e.fecha_contratacion "
                    + "FROM vacaciones v "
                    + "JOIN empleado e ON v.empleado_id_empleado = e.id_empleado "
                    + "WHERE v.empleado_id_empleado = ?";

            ps = conexion.prepareStatement(sql);
            ps.setInt(1, idEmpleado);
            rs = ps.executeQuery();

            while (rs.next()) {
                Set<String> estadoSet = new HashSet<>();
                estadoSet.add(rs.getString("estado_solicitud"));

                Colaborador colaborador = new Colaborador();
                colaborador.setNombre(rs.getString("nombre"));
                colaborador.setApellido_1(rs.getString("apellido_1"));
                colaborador.setApellido_2(rs.getString("apellido_2"));
                colaborador.setFecha_contratacion(rs.getDate("fecha_contratacion"));

                Vacaciones vacaciones = new Vacaciones(
                        rs.getInt("id_vacacion"),
                        rs.getDate("fecha_solicitud"),
                        rs.getDate("fecha_inicio"),
                        rs.getDate("fecha_fin"),
                        rs.getInt("dias_vacaciones_solicitados"),
                        rs.getInt("dias_vacaciones_total"),
                         rs.getInt("dias_vacaciones_restantes"),
                        estadoSet,
                        rs.getString("comentario"),
                        rs.getDate("fecha_aprobacion"),
                        rs.getInt("empleado_id_empleado"),
                        colaborador
                );

                vacacionesList.add(vacaciones);
                System.out.println("Empleado: " + colaborador.getNombre() + " " + colaborador.getApellido_1() + " " + colaborador.getApellido_2());
                System.out.println("ID Vacación: " + vacaciones.getIdVacacion());
                System.out.println("Días de Solicitados: " + vacaciones.getDiasVacacionesSolicitados());
                System.out.println("Estado de Solicitud: " + estadoSet);
                System.out.println("---------------------------");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
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
        return vacacionesList;
    }
//---------------------------------

    public Vacaciones calcularYGuardarVacaciones(Date fechaContratacion, int idEmpleado) {
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

        // Crear y devolver el objeto Vacaciones
        Vacaciones vacaciones = new Vacaciones();
        vacaciones.setDiasVacacionesTotal(diasVacacionesTotal);
        vacaciones.setIdEmpleado(idEmpleado);
        return vacaciones;
    } else {
        System.out.println("Ya existe un registro de vacaciones para el empleado con ID: " + idEmpleado);
        return null;
    }
}
//-------------------------------------------------------------
    
 //------------------------------

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
    public int obtenerDiasVacacionesSolicitados(int idEmpleado) {
        String sql = "SELECT dias_vacaciones_solicitados FROM vacaciones WHERE id_empleado = ?";
        int diasVacacionesSolicitados = 0;

        try (PreparedStatement statement = conexion.prepareStatement(sql)) {
            statement.setInt(1, idEmpleado);

            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    diasVacacionesSolicitados = rs.getInt("dias_vacaciones_solicitados");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return diasVacacionesSolicitados;
    }
    
//------------------------------------------------

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
        return false; // Por defecto, retorna false si ocurre un error
    }
    //---------------------------------------------------------------------------
//-------------------- SOLICITUD DE VACACIONES -------------------

    public boolean solicitarVacaciones(int idEmpleado, Vacaciones vacaciones) {

        PreparedStatement ps = null;

        try {
            String sql = "INSERT INTO vacaciones "
                    + "(fecha_solicitud, fecha_inicio, fecha_fin, dias_vacaciones_solicitados, "
                    + "estado_solicitud, empleado_id_empleado) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            ps = conexion.prepareStatement(sql);

            // Configuración de los parámetros
            ps.setDate(1, new java.sql.Date(vacaciones.getFechaSolicitud().getTime()));
            ps.setDate(2, new java.sql.Date(vacaciones.getFechaInicio().getTime()));
            ps.setDate(3, new java.sql.Date(vacaciones.getFechaFin().getTime()));
            ps.setInt(4, vacaciones.getDiasVacacionesSolicitados());
            ps.setString(5, "Pendiente");  // Estado de la solicitud
            ps.setInt(6, idEmpleado);

            // Ejecuta la inserción
            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas == 1) {
                System.out.println("Vacaciones solicitadas correctamente para el empleado ID: " + idEmpleado);
                return true;
            } else {
                throw new SQLException("No se pudo insertar la solicitud de vacaciones.");
            }

        } catch (SQLException e) {
            System.out.println("Error al insertar la solicitud de vacaciones: " + e.getMessage());
            return false;
        } finally {
            // Cerrar PreparedStatement
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar PreparedStatement: " + ex.getMessage());
            }
        }
    }
//---------------------------------------------------------------
// --------------- LISTAR VACACIONES EMPLEADO ---------------------------
    
   public List<Vacaciones> listarVacacionesPendienteEmpeado(int idEmpleado) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Vacaciones> listarVacacionesEmpleado = new ArrayList<>();

        try {
            // Ahora recuperar las solicitudes de vacaciones de empleados que están en estado 'pendiente'
            String sqlVacaciones = "SELECT v.*, e.nombre, e.apellido_1, e.apellido_2, e.fecha_contratacion "
                    + "FROM vacaciones v "
                    + "JOIN empleado e ON v.empleado_id_empleado = e.id_empleado " // Asegúrate de que el JOIN sea correcto
                    + "WHERE v.empleado_id_empleado = ?"; // Filtrar por idEmpleado

            ps = conexion.prepareStatement(sqlVacaciones);
            ps.setInt(1, idEmpleado);
            rs = ps.executeQuery();

            while (rs.next()) {
                // Aquí recuperamos y procesamos los datos de la solicitud de vacaciones como lo haces actualmente
                int idVacacion = rs.getInt("id_vacacion");
                Date fechaSolicitud = rs.getDate("fecha_solicitud");
                Date fechaInicio = rs.getDate("fecha_inicio");
                Date fechaFin = rs.getDate("fecha_fin");
                int diasVacacionesTotal = rs.getInt("dias_vacaciones_total");
                int diasVacacionesSolicitados = rs.getInt("dias_vacaciones_solicitados");
                String estadoSolicitud = rs.getString("estado_solicitud");
                String comentario = rs.getString("comentario");
                Date fechaAprobacion = rs.getDate("fecha_aprobacion");

                // Recuperar datos del colaborador desde el JOIN
                String nombreColaborador = rs.getString("nombre");
                String apellido1Colaborador = rs.getString("apellido_1");
                String apellido2Colaborador = rs.getString("apellido_2");
                Date fechaContratacionColaborador = rs.getDate("fecha_contratacion");

                // Crear objeto Colaborador
                Colaborador colaborador = new Colaborador();
                colaborador.setNombre(nombreColaborador);
                colaborador.setApellido_1(apellido1Colaborador);
                colaborador.setApellido_2(apellido2Colaborador);
                colaborador.setFecha_contratacion(fechaContratacionColaborador);

                // Convertir el String a un Set<String>
                Set<String> estadoSolicitudSet = new HashSet<>(Collections.singleton(estadoSolicitud));

                // Crear el objeto Vacaciones
                Vacaciones vacaciones = new Vacaciones(idVacacion,
                        fechaSolicitud, fechaInicio, fechaFin,
                        diasVacacionesSolicitados,
                        diasVacacionesTotal,
                        0,
                        estadoSolicitudSet, comentario, fechaAprobacion,
                        idEmpleado, colaborador);

                listarVacacionesEmpleado.add(vacaciones);
                System.out.println("Consultando vacaciones para el empleado con ID: " + idEmpleado);
                System.out.println("Número de vacaciones encontradas: " + listarVacacionesEmpleado.size());

            }

        } catch (Exception e) {
            System.out.println("Error al listar vacaciones: " + e.getMessage());
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
        return listarVacacionesEmpleado;
    } 
//------------------------------------------------------------------
//------------- LISTAR VACACIONES DE TODOS LOS EMPLEADOS -----------------------
 //******--------------
   
   /*
   public List<Vacaciones> listarVacacionesEmpleados() {
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<Vacaciones> listarVacacionesEmpleado = new ArrayList<>();

    try {
        // Consulta con LEFT JOIN para incluir todos los empleados, incluso si no tienen solicitudes de vacaciones
        String sql = "SELECT e.id_empleado, e.nombre, e.apellido_1, e.apellido_2, e.fecha_contratacion, "
                   + "v.id_vacacion, v.fecha_solicitud, v.fecha_inicio, v.fecha_fin, "
                   + "COALESCE(v.dias_vacaciones_total, 0) AS dias_vacaciones_total, "
                   + "COALESCE(v.dias_vacaciones_solicitados, 0) AS dias_vacaciones_solicitados, "
                   + "COALESCE(v.dias_vacaciones_restantes, 0) AS dias_vacaciones_restantes, "
                   + "COALESCE(v.estado_solicitud, 'consulta') AS estado_solicitud, "
                   + "v.comentario, v.fecha_aprobacion "
                   + "FROM empleado e "
                   + "LEFT JOIN vacaciones v ON e.id_empleado = v.empleado_id_empleado "
                   + "AND (v.estado_solicitud = 'consulta' OR v.estado_solicitud IS NULL)";
        
        ps = conexion.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            // Datos del empleado
            int idEmpleado = rs.getInt("id_empleado");
            String nombre = rs.getString("nombre");
            String apellido1 = rs.getString("apellido_1");
            String apellido2 = rs.getString("apellido_2");
            Date fechaContratacion = rs.getDate("fecha_contratacion");

            // Datos de vacaciones (si existen)
            int idVacacion = rs.getInt("id_vacacion");
            Date fechaSolicitud = rs.getDate("fecha_solicitud");
            Date fechaInicio = rs.getDate("fecha_inicio");
            Date fechaFin = rs.getDate("fecha_fin");
            int diasVacacionesTotal = rs.getInt("dias_vacaciones_total");
            int diasVacacionesSolicitados = rs.getInt("dias_vacaciones_solicitados");
            int diasVacacionesRestantes = rs.getInt("dias_vacaciones_restantes");
            String estadoSolicitud = rs.getString("estado_solicitud");
            String comentario = rs.getString("comentario");
            Date fechaAprobacion = rs.getDate("fecha_aprobacion");

            // Crear el objeto Colaborador y Vacaciones
            Colaborador colaborador = new Colaborador();
            colaborador.setNombre(nombre);
            colaborador.setApellido_1(apellido1);
            colaborador.setApellido_2(apellido2);
            colaborador.setFecha_contratacion(fechaContratacion);

            Set<String> estadoSolicitudSet = new HashSet<>(Collections.singleton(estadoSolicitud));

            Vacaciones vacaciones = new Vacaciones(idVacacion, fechaSolicitud, fechaInicio, fechaFin,
                    diasVacacionesSolicitados, diasVacacionesTotal, diasVacacionesRestantes,
                    estadoSolicitudSet, comentario, fechaAprobacion, idEmpleado, colaborador);

            listarVacacionesEmpleado.add(vacaciones);
        }
    } catch (Exception e) {
        System.out.println("Error al listar vacaciones: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al cerrar PreparedStatement o ResultSet: " + ex.getMessage());
        }
    }
    return listarVacacionesEmpleado;
}
*/
 
 public List<Vacaciones> listarVacacionesEmpleados() {
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<Vacaciones> listarVacacionesEmpleado = new ArrayList<>();

    try {
        // Consulta con LEFT JOIN para incluir todos los empleados, incluso si no tienen solicitudes de vacaciones
        String sql = "SELECT e.id_empleado, e.nombre, e.apellido_1, e.apellido_2, e.fecha_contratacion, "
                   + "v.id_vacacion, v.fecha_solicitud, v.fecha_inicio, v.fecha_fin, "
                   + "COALESCE(v.dias_vacaciones_total, 0) AS dias_vacaciones_total, "
                   + "COALESCE(v.dias_vacaciones_solicitados, 0) AS dias_vacaciones_solicitados, "
                   + "COALESCE(v.dias_vacaciones_restantes, 0) AS dias_vacaciones_restantes, "
                   + "COALESCE(v.estado_solicitud, 'consulta') AS estado_solicitud, "
                   + "v.comentario, v.fecha_aprobacion "
                   + "FROM empleado e "
                   + "LEFT JOIN vacaciones v ON e.id_empleado = v.empleado_id_empleado "
                   + "AND (v.estado_solicitud = 'consulta' OR v.estado_solicitud IS NULL)";

        ps = conexion.prepareStatement(sql);
        rs = ps.executeQuery();

        while (rs.next()) {
            // Datos del empleado
            int idEmpleado = rs.getInt("id_empleado");
            String nombre = rs.getString("nombre");
            String apellido1 = rs.getString("apellido_1");
            String apellido2 = rs.getString("apellido_2");
            Date fechaContratacion = rs.getDate("fecha_contratacion");

            // Llamada al método para calcular y guardar las vacaciones (si no existen)
            Vacaciones vacacionesCalculadas = calcularYGuardarVacaciones(fechaContratacion, idEmpleado);

            // Datos de vacaciones (si existen en la base de datos)
            int idVacacion = rs.getInt("id_vacacion");
            Date fechaSolicitud = rs.getDate("fecha_solicitud");
            Date fechaInicio = rs.getDate("fecha_inicio");
            Date fechaFin = rs.getDate("fecha_fin");
            int diasVacacionesTotal = rs.getInt("dias_vacaciones_total");
            int diasVacacionesSolicitados = rs.getInt("dias_vacaciones_solicitados");
            int diasVacacionesRestantes = rs.getInt("dias_vacaciones_restantes");
            String estadoSolicitud = rs.getString("estado_solicitud");
            String comentario = rs.getString("comentario");
            Date fechaAprobacion = rs.getDate("fecha_aprobacion");

            // Crear el objeto Colaborador y Vacaciones
            Colaborador colaborador = new Colaborador();
            colaborador.setNombre(nombre);
            colaborador.setApellido_1(apellido1);
            colaborador.setApellido_2(apellido2);
            colaborador.setFecha_contratacion(fechaContratacion);

            Set<String> estadoSolicitudSet = new HashSet<>(Collections.singleton(estadoSolicitud));

            // Si las vacaciones fueron calculadas correctamente, actualizar las vacaciones
            if (vacacionesCalculadas != null) {
                diasVacacionesTotal = vacacionesCalculadas.getDiasVacacionesTotal();
            }

            Vacaciones vacaciones = new Vacaciones(idVacacion, fechaSolicitud, fechaInicio, fechaFin,
                    diasVacacionesSolicitados, diasVacacionesTotal, diasVacacionesRestantes,
                    estadoSolicitudSet, comentario, fechaAprobacion, idEmpleado, colaborador);

            listarVacacionesEmpleado.add(vacaciones);
        }
    } catch (Exception e) {
        System.out.println("Error al listar vacaciones: " + e.getMessage());
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (SQLException ex) {
            System.out.println("Error al cerrar PreparedStatement o ResultSet: " + ex.getMessage());
        }
    }
    return listarVacacionesEmpleado;
}

   
   
   
//---------------------------------------------------------------------
    // Método para obtener el saldo actual de vacaciones restantes
private Integer obtenerDiasVacacionesRestantes(int idEmpleado) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT dias_vacaciones_restantes FROM vacaciones WHERE empleado_id_empleado = ?";
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, idEmpleado);
            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("dias_vacaciones_restantes");
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los días de vacaciones restantes: " + e.getMessage());
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
        return null; // No hay saldo registrado
    }
//-----------------------------------------------------
//----------------------- APROBAR VACACIONES -------------------

    public boolean actualizaVacacionAprobar(int idVacacionConsulta, Vacaciones vacacionAprobada)
    {
        PreparedStatement ps = null;
        boolean result = false;

        // Calcula días restantes
        int diasRestantes = vacacionAprobada.getDiasVacacionesTotal() - vacacionAprobada.getDiasVacacionesSolicitados();

        try {
            String sql = "UPDATE vacaciones SET "
            + "dias_vacaciones_total = ?, "
            + "dias_vacaciones_restantes = ?, "
            + "comentario = ?, "
            + "fecha_aprobacion = ?, "
            + "estado_solicitud = 'aprobada' "
            + "WHERE id_vacacion = ?";


            ps = conexion.prepareStatement(sql);

            // Configura los parámetros de la consulta
            ps.setInt(1, vacacionAprobada.getDiasVacacionesTotal());
            ps.setInt(2, diasRestantes); 
            ps.setString(3, "Vacaciones Actualizadas");
            ps.setDate(4, new java.sql.Date(vacacionAprobada.getFechaAprobacion().getTime()));
            ps.setInt(5, idVacacionConsulta);

            int filasActualizadas = ps.executeUpdate();
            result = filasActualizadas > 0;
             
            
            if (!result) {
                System.out.println("No se encontró una vacación en estado 'consulta' con el ID proporcionado.");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar las vacaciones: " + e.getMessage());
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
 //--------------------------------------------------------------
    public boolean actualizaVacacionDenegar(int idVacacionConsulta, Vacaciones vacacionDenegada) {
    PreparedStatement ps = null;
    boolean result = false;

    try {
        String sql = "UPDATE vacaciones SET "
            + "comentario = 'Vacaciones Denegadas', "
            + "estado_solicitud = 'Rechazada' "
            + "WHERE id_vacacion = ?";

        ps = conexion.prepareStatement(sql);

        // Configura los parámetros de la consulta
         ps.setInt(1, idVacacionConsulta); 
         
        // Ejecuta la actualización
        int filasActualizadas = ps.executeUpdate();
        result = filasActualizadas > 0;

        if (!result) {
            System.out.println("No se encontró una vacación en estado 'consulta' con el ID proporcionado.");
        }

    } catch (SQLException e) {
        System.out.println("Error al denegar las vacaciones: " + e.getMessage());
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

//-----------------------------------------------------------------
    public boolean actualizarVacacionesConsulta(Vacaciones vacacion) {
        PreparedStatement ps = null;
        boolean result = false;

        try {
            // SQL para actualizar las vacaciones en estado 'consulta'
            String sql = "UPDATE vacaciones SET "
                    + "dias_vacaciones_total = ?, "
                    + "dias_vacaciones_restantes = ?, "
                    + "comentario = ?, "
                    + "fecha_aprobacion = ?"
                    + "WHERE id_vacacion = ? AND estado_solicitud = 'consulta'";

            ps = conexion.prepareStatement(sql);

            // Establecer los parámetros del PreparedStatement con los valores de la vacación
            ps.setInt(1, vacacion.getDiasVacacionesTotal());
            ps.setInt(2, 0);
            ps.setString(3, "VACACIONES ACTUALIZADAS");
            ps.setDate(4, new java.sql.Date(vacacion.getFechaAprobacion().getTime()));
            ps.setInt(5, vacacion.getIdVacacion()); 
            
            // Ejecutar la actualización
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Vacación actualizada correctamente.");
                result = true;
            } else {
                System.out.println("No se encontró la vacación en estado 'consulta' para actualizar.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar la vacación: " + e.getMessage());
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return result;
    }

//-----------------------------------------------------------------
    public int obtenerIdVacacionEnConsulta(int idEmpleado) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int idVacacion = -1;

        try {
            // Consulta para obtener el id de la vacación en estado "consulta" para el empleado dado
            String sql = "SELECT * FROM vacaciones "
                    + "WHERE empleado_id_empleado = ? AND estado_solicitud = 'consulta'";

            ps = conexion.prepareStatement(sql);
            ps.setInt(1, idEmpleado);

            rs = ps.executeQuery();

            if (rs.next()) {
                idVacacion = rs.getInt("id_vacacion");
                System.out.println("ID de la vacación en estado 'consulta' para el empleado: " + idVacacion);
            } else {
                System.out.println("No se encontró ninguna vacación en estado 'consulta' para el empleado con ID proporcionado.");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el ID de la vacación en estado 'consulta': " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return idVacacion;
    }
 //---------------------------------------------------------------
    public Vacaciones mostrarVacacionesAprobado(int idVacacion) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vacaciones vacaciones = null;
        try {
            // Modificar la consulta para filtrar por el estado 'Aprobada'
            String sql = "SELECT * FROM vacaciones "
                    + "WHERE id_vacacion = ? ";
            
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, idVacacion);
            rs = ps.executeQuery();

            if (rs.next()) {
                vacaciones = new Vacaciones();
                vacaciones.setIdVacacion(rs.getInt("id_vacacion"));
                vacaciones.setIdEmpleado(rs.getInt("empleado_id_empleado"));
                vacaciones.setDiasVacacionesSolicitados(rs.getInt("dias_vacaciones_solicitados"));
                // Puedes continuar con el resto de los campos de la vacación según sea necesario
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
        return vacaciones;
    }
//-----------------------------------------------------
    
    public Vacaciones mostrarVacacionesConsulta(int idEmpleado) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Vacaciones vacaciones = null;
        try {
            // Modificar la consulta para filtrar por el estado 'Aprobada'
            String sql = "SELECT * FROM vacaciones "
                    + "WHERE empleado_id_empleado = ? AND estado_solicitud = 'Consulta'";
            
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, idEmpleado);
            rs = ps.executeQuery();

            if (rs.next()) {
                vacaciones = new Vacaciones();
                vacaciones.setIdVacacion(rs.getInt("id_vacacion"));
                vacaciones.setIdEmpleado(rs.getInt("empleado_id_empleado"));
                vacaciones.setDiasVacacionesTotal(rs.getInt("dias_vacaciones_total"));
                
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
        return vacaciones;
    }
    
//------------------------------------------------------------------ 
 /*   public boolean actualizaVacacionConsulta(Vacaciones vacacionAprobada) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean result = false;

        int idempleadoCon = vacacionAprobada.getIdEmpleado();

        // Llamar al método obtenerIdVacacionEnConsulta para obtener el ID de la vacación
        int idVacacionconsulta = obtenerIdVacacionEnConsulta(idempleadoCon);
        int idSolicitud = vacacionAprobada.getIdVacacion();  // Vacación en estado 'Aprobada'

        System.out.println("id vacacion consulta: " + idVacacionconsulta);
        System.out.println("id vacacion solicitud: " + idSolicitud);

        try {
            // Primero, obtener los días vacación_total de la vacación en estado 'consulta'
            String sqlObtenerDiasTotal = "SELECT dias_vacaciones_total "
                    + "FROM vacaciones WHERE id_vacacion = ?";
            ps = conexion.prepareStatement(sqlObtenerDiasTotal);
            ps.setInt(1, idVacacionconsulta);  // Asignamos el valor de idVacacionconsulta a la consulta
            rs = ps.executeQuery();

            int diasVacacionesTotalConsulta = 0;
            if (rs.next()) {
                diasVacacionesTotalConsulta = rs.getInt("dias_vacaciones_total");
                System.out.println("Total días vacaciones consulta: " + diasVacacionesTotalConsulta);
            } else {
                System.out.println("No se encontró la vacación en estado 'consulta'.");
                return false;
            }

            // Ahora, obtener los días solicitados de la vacación en estado 'Aprobada'
            String sqlObtenerDiasSolicitados = "SELECT dias_vacaciones_solicitados "
                    + "FROM vacaciones WHERE id_vacacion = ?";
            ps = conexion.prepareStatement(sqlObtenerDiasSolicitados);
            ps.setInt(1, idSolicitud);  // Vacación 'Aprobada' con el idSolicitud
            rs = ps.executeQuery();

            int diasVacacionesSolicitados = 0;
            if (rs.next()) {
                diasVacacionesSolicitados = rs.getInt("dias_vacaciones_solicitados");
                System.out.println("Días solicitados en vacación aprobada: " + diasVacacionesSolicitados);
            } else {
                System.out.println("No se encontró la vacación aprobada.");
                return false;
            }

            // Restar los días solicitados de la vacación en estado 'Aprobada' de los días vacacionales en la vacación en estado 'consulta'
            int nuevoDiasVacacionesTotal = diasVacacionesTotalConsulta - diasVacacionesSolicitados;
            System.out.println("Nuevo total de días vacaciones para vacación en estado consulta: " + nuevoDiasVacacionesTotal);

            // Actualizar el valor de dias_vacaciones_total en la vacación en estado 'consulta'
            String sqlActualizarVacacion = "UPDATE vacaciones SET dias_vacaciones_total = ? "
                    + "WHERE id_vacacion = ?";
            
            ps = conexion.prepareStatement(sqlActualizarVacacion);
            ps.setInt(1, nuevoDiasVacacionesTotal);
            ps.setInt(2, idVacacionconsulta);
            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Vacación actualizada correctamente.");
                result = true;
            } else {
                System.out.println("No se pudo actualizar la vacación en estado 'consulta'.");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar las vacaciones: " + e.getMessage());
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }

        return result;
    }
*/

//--------------------------------------------------------------
}
