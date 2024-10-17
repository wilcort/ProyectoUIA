/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelEmpleado;

import Configur.Conexion;
import Model.Cargo;
import Model.Colaborador;
import Model.Usuario;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
import java.util.Date;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Dell
 */
public class EmpleadoDAO {

    Connection conexion;

    public EmpleadoDAO() {
        Conexion conex = new Conexion();
        conexion = conex.getConectar();
    }

//----------------------------------------------------------------------  
// ----------------------- MOSTRAR DATOS DEL EMPLEADO ------------------------
    public Colaborador mostrarEmpleado(int idUsuario) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Colaborador colaborador = null;
        try {
            ps = conexion.prepareStatement("SELECT e.id_empleado, e.num_documento, e.nombre, "
                    + "e.apellido_1, e.apellido_2, e.telefono, e.direccion, e.fecha_contratacion, "
                    + "e.salario_base, u.id_usuario, u.nombreUsuario, u.estadoUsuario, "
                    + "c.id_cargo, c.nombre_cargo, c.estado, h.id_horario, h.hora_entrada, h.hora_salida, "
                    + "h.horas_laborales, h.dias_laborales "
                    + "FROM empleado e "
                    + "INNER JOIN usuario u ON e.usuario_id_usuario = u.id_usuario "
                    + "LEFT JOIN cargo c ON e.id_cargo = c.id_cargo "
                    + "LEFT JOIN horarios h ON e.horarios_id_horario = h.id_horario "
                    + "WHERE u.id_usuario = ?");  // Cambiar esto

            // Establecer el parámetro
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();

            if (rs.next()) {  // Cambiar while por if
                // Obtener los datos del colaborador
                int idEmpleado = rs.getInt("id_empleado");
                int num_documento = rs.getInt("num_documento");  // Cambiar a "num_documento"
                String nombre = rs.getString("nombre");
                String apellido_1 = rs.getString("apellido_1");
                String apellido_2 = rs.getString("apellido_2");
                int telefono = rs.getInt("telefono");
                String direccion = rs.getString("direccion");
                java.sql.Date fecha_Contratacion = rs.getDate("fecha_contratacion");  // Cambiar a "fecha_contratacion"
                BigDecimal salario_Base = rs.getBigDecimal("salario_base");  // Cambiar a "salario_base"

                int id_usuario = rs.getInt("id_usuario");
                String nombreUsuario = rs.getString("nombreUsuario");
                boolean estadoUsuario = rs.getBoolean("estadoUsuario");

                // Obtener el usuario
                Usuario usuario = new Usuario();
                usuario.setId_usuario(id_usuario);
                usuario.setNombreUsuario(nombreUsuario);
                usuario.setEstadoUsuario(estadoUsuario);

                // Datos del cargo
                int idCargo = rs.getInt("id_cargo");
                String nombreCargo = rs.getString("nombre_cargo");
                boolean estadoCargo = rs.getBoolean("estado");

                Cargo cargo = new Cargo();
                cargo.setEstado(estadoCargo);
                cargo.setIdCargo(idCargo);
                cargo.setNombreCargo(nombreCargo);

                // Datos de horario
                int idhorario = rs.getInt("id_horario");
                Time horaEntrada = rs.getTime("hora_entrada");
                Time horaSalida = rs.getTime("hora_salida");
                Double horasLaborales = rs.getDouble("horas_laborales");

                // Obtener el valor de la columna 'diasLaborales' como un String
                String diasLaboralesString = rs.getString("dias_laborales");
                Set<String> diasLaborales = new HashSet<>();
                if (diasLaboralesString != null) {
                    diasLaborales = new HashSet<>(Arrays.asList(diasLaboralesString.split(",")));
                }

                // Crear el objeto Horarios
                Horarios horarios = new Horarios();
                horarios.setHoraEntrada(horaEntrada);
                horarios.setHoraSalida(horaSalida);
                horarios.setHorasLaborales(horasLaborales);
                horarios.setDiasLaborales(diasLaborales);

                // Crear el objeto Colaborador
                colaborador = new Colaborador(idEmpleado, num_documento,
                        nombre, apellido_1, apellido_2, telefono, direccion,
                        fecha_Contratacion, fecha_Contratacion, // ¿esto debería ser fechaSalida?
                        salario_Base, usuario, cargo, horarios);

            } else {
                System.out.println("No se encontró el colaborador con empleado: " + idUsuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return colaborador;
    }

//-----------------------------------------------------------------------------
// --------------------- MARCAS EMPLEADO -----------------------------------
 public boolean marcarEntrada(Marcas marca) {
    return realizarMarca(marca, true, false, false, false);
}

public boolean marcarSalida(Marcas marca) {
    return realizarMarca(marca, false, true, false, false);
}

public boolean marcarEntradaAlmuerzo(Marcas marca) {
    return realizarMarca(marca, false, false, true, false);
}

public boolean marcarSalidaAlmuerzo(Marcas marca) {
    return realizarMarca(marca, false, false, false, true);
}

private boolean realizarMarca(Marcas marca, boolean esEntrada, boolean esSalida, boolean esEntradaAlmuerzo, boolean esSalidaAlmuerzo) {
    PreparedStatement ps = null;

    try {
        

        ps = conexion.prepareStatement("INSERT INTO marcas ("
                + " fecha_marca, hora_entrada, hora_salida, "
                + "hora_entrada_almuerzo, hora_salida_almuerzo, id_empleado) "
                + "VALUES (?, ?, ?, ?, ?, ?)",
                PreparedStatement.RETURN_GENERATED_KEYS);

        // Obtener la fecha de marca
        java.util.Date fechaUtil = marca.getFechaMarca();

        // Verificar si fechaUtil es null
        if (fechaUtil == null) {
            throw new IllegalArgumentException("La fecha de marca no puede ser null");
        }

        // Convertir java.util.Date a java.sql.Date
        java.sql.Date fechaSQL = new java.sql.Date(fechaUtil.getTime());

        // Usar el objeto java.sql.Date en el PreparedStatement
        ps.setDate(1, fechaSQL);
        ps.setTime(2, esEntrada ? marca.getMarcaEntrada() : null);
        ps.setTime(3, esSalida ? marca.getMarcaSalida() : null);
        ps.setTime(4, esEntradaAlmuerzo ? marca.getMarcaEntradaAlmuerzo() : null);
        ps.setTime(5, esSalidaAlmuerzo ? marca.getMarcaSalidaAlmuerzo() : null);
        ps.setInt(6, marca.getIdEmpleado());

        int filasAfectadas = ps.executeUpdate();

        if (filasAfectadas == 1) {
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int nuevoId = generatedKeys.getInt(1);
                    marca.setIdMarca(nuevoId);
                }
            }
            return true;
        } else {
            throw new SQLException("No se pudo insertar la marca");
        }

    } catch (Exception e) {
        e.printStackTrace(); // Cambiado para un mejor diagnóstico
        return false;
    } finally {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error al cerrar PreparedStatements: " + ex.getMessage());
        }
    }
}

//---------------------------------------------------------------------
   
// --------------------- OBTENER EL ID EMPLEADO -----------------------------------
    public Integer obtenerIdEmpleado(int idUsuario) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer idEmpleado = null;

        try {
            ps = conexion.prepareStatement("SELECT e.id_empleado FROM empleado e "
                    + "INNER JOIN usuario u ON e.usuario_id_usuario = u.id_usuario "
                    + "WHERE u.id_usuario = ?");

            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();

            if (rs.next()) {
                idEmpleado = rs.getInt("id_empleado");

            }
        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return idEmpleado;
    }

//----------------------------------------------------------------------------
    public Marcas obtenerMarcaPorFecha(int idEmpleado, Date fecha) {
        Marcas marca = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conexion.prepareStatement("SELECT * FROM marcas "
                    + "WHERE id_empleado = ? AND fecha_marca = ?");

            ps.setInt(1, idEmpleado);
            ps.setDate(2, new java.sql.Date(fecha.getTime()));

            rs = ps.executeQuery();

            if (rs.next()) {
                marca = new Marcas();
                marca.setFechaMarca(rs.getDate("fecha_marca"));
                marca.setMarcaEntrada(rs.getTime("hora_entrada"));
                marca.setMarcaSalida(rs.getTime("hora_salida"));
                marca.setMarcaSalidaAlmuerzo(rs.getTime("hora_salida_almuerzo"));
                marca.setMarcaEntradaAlmuerzo(rs.getTime("hora_entrada_almuerzo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return marca;
    }
    //----------------------------------------------------------------------------
// --------------------- OBTENER MARCAS EMPLEADO POR DIA -----------------------------------

    public List<Marcas> obtenerMarcasPorDia(int idEmpleado, Date fechaMarca) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Marcas> marcasList = new ArrayList<>();

        try {
            ps = conexion.prepareStatement("SELECT m.id_marca, m.fecha_marca, m.hora_entrada, "
                    + "m.hora_salida, m.hora_entrada_almuerzo, m.hora_salida_almuerzo "
                    + "FROM marcas m "
                    + "WHERE m.id_empleado = ? "
                    + "AND m.fecha_marca = ?");

            // Setear los parámetros
            ps.setInt(1, idEmpleado);
            ps.setDate(2, new java.sql.Date(fechaMarca.getTime()));

            rs = ps.executeQuery();

            while (rs.next()) {
                // Obtener los valores de las columnas
                int idMarca = rs.getInt("id_marca");
                Date fechaMarcaResult = rs.getDate("fecha_marca");
                Time horaEntrada = rs.getTime("hora_entrada");
                Time horaSalida = rs.getTime("hora_salida");
                Time horaEntradaAlmuerzo = rs.getTime("hora_entrada_almuerzo");
                Time horaSalidaAlmuerzo = rs.getTime("hora_salida_almuerzo");

                // Crear objeto Marcas
                Marcas marca = new Marcas(idMarca, fechaMarcaResult, horaEntrada, horaSalida,
                        horaSalidaAlmuerzo, horaEntradaAlmuerzo, idEmpleado);

                // Añadir el objeto a la lista
                marcasList.add(marca);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(EmpleadoDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return marcasList;
    }

//--------------------------------------------
    
public List<Marcas> obtenerTodasLasMarcas(int idEmpleado) {
    List<Marcas> marcas = new ArrayList<>();
    
    String sql = "SELECT id_marca, fecha_marca, hora_entrada, hora_salida, "
                 + "hora_entrada_almuerzo, hora_salida_almuerzo "
                 + "FROM marcas WHERE id_empleado = ?";
    
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setInt(1, idEmpleado); // Setear el ID del empleado
        ResultSet rs = ps.executeQuery();
        
        while (rs.next()) {
            Marcas marca = new Marcas();
            marca.setIdMarca(rs.getInt("id_marca"));
            marca.setFechaMarca(rs.getDate("fecha_marca"));
            marca.setMarcaEntrada(rs.getTime("hora_entrada"));
            marca.setMarcaSalida(rs.getTime("hora_salida"));
            marca.setMarcaEntradaAlmuerzo(rs.getTime("hora_entrada_almuerzo"));
            marca.setMarcaSalidaAlmuerzo(rs.getTime("hora_salida_almuerzo"));

            marcas.add(marca);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return marcas;
}

//---
public boolean verificarMarca(String fecha) {
    boolean existeMarca = false;
    String query = "SELECT COUNT(*) FROM marcas WHERE fecha_marca = ?";

    try (PreparedStatement ps = conexion.prepareStatement(query)){
        ps.setString(1, fecha);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            existeMarca = rs.getInt(1) > 0; // Si el conteo es mayor que 0, existe una marca
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return existeMarca;
}

//-------------------------------------------------
}

