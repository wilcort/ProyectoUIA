/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelEmpleado;

import Configur.Conexion;
import Model.Colaborador;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                        idEmpleado,
                        null
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
            String sql = "SELECT v.*, e.nombre, e.apellido_1, e.apellido_2,fecha_contratacion "
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
                
                
               
                 // Crear el objeto Colaborador
                Colaborador colaborador = new Colaborador();
                colaborador.setNombre(rs.getString("nombre"));
                colaborador.setApellido_1(rs.getString("apellido_1"));
                colaborador.setApellido_2(rs.getString("apellido_2"));
                colaborador.setFecha_contratacion(rs.getDate("fecha_contratacion")); // Obtener fecha_contratacion del ResultSet


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
                        rs.getInt("empleado_id_empleado"),                       
                        colaborador
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
    
//----------------------------------------------------------------
//--------------- SOLICITAR VACACIONES  ------------------------
    
    public boolean solicitarVacaciones(int idEmpleado, Vacaciones vacaciones) {
        PreparedStatement ps = null;
        boolean isUpdated = false;

        try {
            
            ps = conexion.prepareCall("{CALL gestionarVacaciones(?, ?, ?, ?, ?, ?, ?)}");

            // Establecer parámetros
            ps.setInt(1, idEmpleado);
            ps.setDate(2, new java.sql.Date(vacaciones.getFechaSolicitud().getTime()));
            ps.setDate(3, new java.sql.Date(vacaciones.getFechaInicio().getTime()));
            ps.setDate(4, new java.sql.Date(vacaciones.getFechaFin().getTime()));
            ps.setInt(5, vacaciones.getDiasVacacionesSolicitados());
            ps.setString(6, vacaciones.getEstadoSolicitud().iterator().next());
            ps.setString(7, vacaciones.getComentario());

            // Ejecutar el procedimiento
            int rowsAffected = ps.executeUpdate();
            
         

            // Comprobar si hubo actualización
            isUpdated = rowsAffected > 0;
            if (isUpdated) {
                System.out.println("Solicitud de vacaciones registrada o actualizada con éxito.");
            } else {
                System.out.println("No se realizó ningún cambio en la solicitud.");
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
//-----------------------------------------------------------------
  public boolean enviarNotificacion(int idEmpleado, String tipo, String mensaje) {
    PreparedStatement ps = null;
    boolean notificacionEnviada = false;

    try {
        String sql = "INSERT INTO notificaciones (tipo_notificacion,"
                + " mensaje, fecha_creacion, leida, empleado_id_empleado)"
                + " VALUES (?, ?, NOW(), 0, ?)";
        
        ps = conexion.prepareStatement(sql);
        ps.setString(1, tipo);
        ps.setString(2, mensaje);
        ps.setInt(3, idEmpleado);

        int rowsAffected = ps.executeUpdate();
        notificacionEnviada = rowsAffected > 0;

        if (notificacionEnviada) {
            System.out.println("Notificación enviada al administrador con éxito.");
        } else {
            System.out.println("No se pudo enviar la notificación.");
        }
    } catch (SQLException e) {
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

    return notificacionEnviada;
}
  

//------------ ADMINISTRADOR MANEJO DE VACACIONES --------------------------
//---------------------------------------------------------------------
//---------------------------------------------------------------
    //-------
  
  public List<Vacaciones> listarVacacionesEmpleado() {
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<Vacaciones> listarVacacionesEmpleado = new ArrayList<>();

    try {
        // Primero, obtener todos los empleados
        String sqlEmpleados = "SELECT id_empleado FROM empleado";
        ps = conexion.prepareStatement(sqlEmpleados);
        rs = ps.executeQuery();

        while (rs.next()) {
            int idEmpleado = rs.getInt("id_empleado");

            // Llamar al procedimiento almacenado para calcular las vacaciones
            Vacaciones vacaciones = calcular(idEmpleado);
            if (vacaciones != null) {
                // Agregar el objeto Vacaciones a la lista
                listarVacacionesEmpleado.add(vacaciones);
            }
        }

        // Ahora recuperar las solicitudes de vacaciones de empleados que están en estado 'consulta'
        String sqlVacaciones = "SELECT v.*, e.nombre, e.apellido_1, e.apellido_2, e.fecha_contratacion "
                             + "FROM vacaciones v "
                             + "JOIN empleado e ON v.empleado_id_empleado = e.id_empleado "
                             + "WHERE v.estado_solicitud = 'consulta'"; // Solo solicitudes en estado 'consulta'

        ps = conexion.prepareStatement(sqlVacaciones);
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
            int idEmpleado = rs.getInt("empleado_id_empleado");

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
                    estadoSolicitudSet, comentario, fechaAprobacion,
                    idEmpleado, colaborador);

            listarVacacionesEmpleado.add(vacaciones);
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
//---------------------------------------------------------------------
//---------------------------------------------------------------
   public List<Vacaciones> listarVacacionesPendiente(int idEmpleado) {
    PreparedStatement ps = null; 
    ResultSet rs = null; 
    List<Vacaciones> listarVacacionesEmpleado = new ArrayList<>();

    try {
        // Ahora recuperar las solicitudes de vacaciones de empleados que están en estado 'pendiente'
        String sqlVacaciones = "SELECT v.*, e.nombre, e.apellido_1, e.apellido_2, e.fecha_contratacion "
                             + "FROM vacaciones v "
                             + "JOIN empleado e ON v.empleado_id_empleado = e.id_empleado " // Asegúrate de que el JOIN sea correcto
                             + "WHERE v.estado_solicitud = 'pendiente' AND v.empleado_id_empleado = ?"; // Filtrar por idEmpleado

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
                    estadoSolicitudSet, comentario, fechaAprobacion,
                    idEmpleado, colaborador);

            listarVacacionesEmpleado.add(vacaciones);
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
//--------------------------------------------------------------------
//------------------------- APROVAR VACACIONES----------------------------------
   
    public boolean aprobar_Vacaciones(int idVacacionAprobar, Vacaciones vacaciones) {
        PreparedStatement ps = null;
        boolean result = false;

        try {
            // Obtiene los días de vacaciones totales y solicitados
            int diasVacacionesTotal = vacaciones.getDiasVacacionesTotal();
            int diasVacacionesSolicitados = vacaciones.getDiasVacacionesSolicitados();

            // Calcular días restantes
            int diasVacacionesRestantes = diasVacacionesTotal - diasVacacionesSolicitados;

            // 1. Actualizar el registro específico a "Aprobada"
            ps = conexion.prepareStatement("UPDATE vacaciones SET "
                    + "dias_vacaciones_total = ?, "
                    + "estado_solicitud = ?, "
                    + "comentario = ?, "
                    + "fecha_aprobacion = ? "
                    + "WHERE id_vacacion = ?");

            ps.setInt(1, diasVacacionesRestantes);
            ps.setString(2, "Aprobada");
            ps.setString(3, "Felices vacaciones");
            ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
            ps.setInt(5, idVacacionAprobar);

            // Ejecuta la actualización para el registro que se va a aprobar
            int filasActualizadas = ps.executeUpdate();
            result = filasActualizadas > 0;
            

        } catch (SQLException e) {
            e.printStackTrace();
        } 

        return result;
    }
//--------------------------------------------------------------------
//--------------------------------------------------------------------
  
 public List<Integer> obtenerIdVacacionConsulta(int idEmpleado) {
     
    List<Integer> idsVacacionesConsulta = new ArrayList<>();
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        String sql = "SELECT id_vacacion FROM vacaciones"
                + " WHERE estado_solicitud = 'consulta' AND empleado_id_empleado = ?";
        
        ps = conexion.prepareStatement(sql);
        ps.setInt(1, idEmpleado);

        rs = ps.executeQuery();

        while (rs.next()) {
            int idVacacion = rs.getInt("id_vacacion");      
            idsVacacionesConsulta.add(idVacacion);
            
            System.out.println("conaulta "+ idsVacacionesConsulta);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        // Cierre de recursos
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return idsVacacionesConsulta;
} 

//--------------------------------------------------------------------
     public Vacaciones obtenerVacacionPorId(int idVacacion) {
        Vacaciones vacacion = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            String sql = "SELECT dias_vacaciones_total, dias_vacaciones_solicitados, "
                    + "comentario, fecha_aprobacion FROM vacaciones WHERE id_vacacion = ?";
            
            ps = conexion.prepareStatement(sql);
            ps.setInt(1, idVacacion);

            rs = ps.executeQuery();
            if (rs.next()) {
                vacacion = new Vacaciones();
                vacacion.setDiasVacacionesTotal(rs.getInt("dias_vacaciones_total"));
                vacacion.setDiasVacacionesSolicitados(rs.getInt("dias_vacaciones_solicitados"));
                vacacion.setComentario(rs.getString("comentario"));
                vacacion.setFechaAprobacion(rs.getDate("fecha_aprobacion"));
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
        return vacacion;
    }
 
//----------------------------- ACTUALIZAR VACACIONES -----------------
 
    public boolean actualizaVacacionDesdeAprobada(int idVacacionConsulta, Vacaciones vacacionAprobada) {
        PreparedStatement ps = null;
        boolean result = false;
        
            // Obtiene los días de vacaciones totales y solicitados
            int diasVacacionesTotal = vacacionAprobada.getDiasVacacionesTotal();
            int diasVacacionesSolicitados = vacacionAprobada.getDiasVacacionesSolicitados();     
            
            // Calcular días restantes
            int diasRestantes = diasVacacionesTotal - diasVacacionesSolicitados;
            System.out.println(" toal " + diasRestantes);
            
        try {
            String sql = "UPDATE vacaciones SET "
                    + "dias_vacaciones_total = ?, "
                    + "comentario = ?, "
                    + "fecha_aprobacion = ? "
                    + "WHERE id_vacacion = ? AND estado_solicitud = 'consulta'";

            ps = conexion.prepareStatement(sql);

            // Usamos los datos de la vacación aprobada
            ps.setInt(1, diasRestantes);
            ps.setString(2, "Vacaciones Actualizadas");
            ps.setDate(3, new java.sql.Date(vacacionAprobada.getFechaAprobacion().getTime()));
            ps.setInt(4, idVacacionConsulta);

            int filasActualizadas = ps.executeUpdate();
            result = filasActualizadas > 0;

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

        return result;
    }

//------------------------- DENEGAR VACACIONES----------------------------------

//--------------------------------------------------------------
}
