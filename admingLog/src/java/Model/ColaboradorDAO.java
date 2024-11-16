package Model;

import ModelEmpleado.Horarios;
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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ColaboradorDAO {

    Connection conexion;

    public ColaboradorDAO() {
        Conexion conex = new Conexion();
        conexion = conex.getConectar();
    }
//-------------------------------------------------------------------------------------//
//------------------------------ LISTAR -----------------------------------------//   

    public List<Colaborador> listarColaboradores() {
        ResultSet rs = null;
        PreparedStatement ps = null;
        List<Colaborador> lista = new ArrayList<>();

        try {
            ps = conexion.prepareStatement("SELECT \n"
                    + "    e.id_empleado,\n"
                    + "    e.num_documento,\n"
                    + "    e.nombre AS empleado_nombre,\n"
                    + "    e.apellido_1,\n"
                    + "    e.apellido_2,\n"
                    + "    e.telefono,\n"
                    + "    e.direccion,\n"
                    + "    e.fecha_contratacion,\n"
                    + "    u.id_usuario,\n"
                    + "    u.nombreUsuario,\n"
                    + "    u.estadoUsuario\n"
                    + "FROM \n"
                    + "    empleado e\n"
                    + "INNER JOIN \n"
                    + "    usuario u ON e.usuario_id_usuario = u.id_usuario ");

            rs = ps.executeQuery();

            while (rs.next()) {
                // Obtener los datos del colaborador usando el alias correcto
                int id_empleado = rs.getInt("id_empleado");
                int num_documento = rs.getInt("num_documento");
                String nombre = rs.getString("empleado_nombre");  // Alias utilizado aquí
                String apellido_1 = rs.getString("apellido_1");
                String apellido_2 = rs.getString("apellido_2");
                int telefono = rs.getInt("telefono");
                String direccion = rs.getString("direccion");
                Date fecha_contratacion = rs.getDate("fecha_contratacion");
                int id_usuario = rs.getInt("id_usuario");

                // Obtener el usuario
                Usuario usuario = new Usuario();
                usuario.setId_usuario(id_usuario);

                // Crear objeto Colaborador y agregarlo a la lista
                Colaborador colaborador = new Colaborador(id_empleado,
                        num_documento, nombre, apellido_1, apellido_2, telefono,
                        direccion, fecha_contratacion, null,
                        usuario, null,null);

                lista.add(colaborador);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar colaboradores: " + e.getMessage());
        } finally {
            // Cerrar CallableStatement y ResultSet, pero no la conexión
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar CallableStatement o ResultSet: " + ex.getMessage());
            }
        }

        return lista;
    }

//-------------------------------------------------------------------------------------//
    //------------------------------ MOSTRAR EMPLEADO -------------------------------------// 
    
    // mostrar
    public Colaborador mostrarEmpleado(int idEmpleado) {
 
        PreparedStatement ps = null;
        ResultSet rs = null;
        Colaborador colaborador = null;
        try {
            
          ps = conexion.prepareStatement("SELECT e.id_empleado, e.num_documento, e.nombre, "
            + "e.apellido_1, e.apellido_2, e.telefono, e.direccion, e.fecha_contratacion, "
            + "u.id_usuario, u.nombreUsuario, u.estadoUsuario, "
            + "c.id_cargo, c.nombre_cargo, c.estado, h.id_horario, h.hora_entrada, h.hora_salida, "
            + "h.horas_laborales, h.dias_laborales "
            + "FROM empleado e "
            + "INNER JOIN usuario u ON e.usuario_id_usuario = u.id_usuario "
            + "LEFT JOIN cargo c ON e.id_cargo = c.id_cargo "
            + "LEFT JOIN horarios h ON e.horarios_id_horario = h.id_horario "
            + "WHERE e.id_empleado = ?"); 
       
            
            

            ps.setInt(1, idEmpleado);
            rs = ps.executeQuery();
            System.out.println(" datos usuario " + idEmpleado);
            while (rs.next()) {

                // Obtener los datos del colaborador
             
                int num_documento = rs.getInt("num_Documento");
                String nombre = rs.getString("nombre");
                String apellido_1 = rs.getString("apellido_1");
                String apellido_2 = rs.getString("apellido_2");
                int telefono = rs.getInt("telefono");
                String direccion = rs.getString("direccion");
                java.sql.Date fecha_Contratacion = rs.getDate("fecha_Contratacion");
                                
                int id_usuario = rs.getInt("id_usuario");              
                String nombreUsuario = rs.getString("nombreUsuario"); ///***
                boolean estadoUsuario = rs.getBoolean("estadoUsuario");
                                       
                // Obtener el usuario
                Usuario usuario = new Usuario();
                usuario.setId_usuario(id_usuario);
                usuario.setNombreUsuario(nombreUsuario); //******
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

// Verificar si 'diasLaboralesString' es null antes de procesar
                Set<String> diasLaborales = new HashSet<>();
                if (diasLaboralesString != null) {
                    diasLaborales = new HashSet<>(Arrays.asList(diasLaboralesString.split(",")));
                }

// Continúa con la creación del objeto 'Horarios'
                Horarios horarios = new Horarios();
                horarios.setHoraEntrada(horaEntrada);
                horarios.setHoraSalida(horaSalida);
                horarios.setHorasLaborales(horasLaborales);
                horarios.setDiasLaborales(diasLaborales);

                colaborador = new Colaborador(idEmpleado, num_documento,
                        nombre, apellido_1, apellido_2, telefono, direccion,
                        fecha_Contratacion, fecha_Contratacion,
                        usuario, cargo, horarios);
                
                System.out.println(" se encontró el colaborador con empleado: " + idEmpleado);              
            }
            if (colaborador == null) {
                System.out.println("No se encontró el colaborador con empleado: " + idEmpleado);
                // Manejar el caso, por ejemplo, lanzar una excepción o retornar un objeto vacío
            } else {
                System.out.println("datos usuario 2 " + colaborador.getUsuario().getId_usuario());
            }
            
            System.out.println(" datos usuario 2 " + colaborador.getUsuario().getId_usuario());
            
        } catch (SQLException ex) {
            Logger.getLogger(ColaboradorDAO.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(ColaboradorDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return colaborador;
    }

    //-------------------------------------------------------------------------------------//
    //------------------------------ INSERTAR EMPLEADO -----------------------------------------//  
    public boolean insertarColaboradores(Usuario usuario, Colaborador colaborador) {
        PreparedStatement psUsuario = null;
        PreparedStatement psEmpleado = null;
        int idUsuarioGenerado = 0;

        try {
            // Insertar usuario
            psUsuario = conexion.prepareStatement("INSERT INTO usuario(nombreUsuario, "
                    + "clave, estadoUsuario) VALUES (?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            psUsuario.setString(1, usuario.getNombreUsuario());
            psUsuario.setString(2, usuario.getClave());
            psUsuario.setBoolean(3, usuario.isEstadoUsuario());

            int filasAfectadasUsuario = psUsuario.executeUpdate();

            if (filasAfectadasUsuario == 1) {
                ResultSet generatedKeysUsuario = psUsuario.getGeneratedKeys();

                if (generatedKeysUsuario.next()) {
                    idUsuarioGenerado = generatedKeysUsuario.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID del usuario generado");
                }
            } else {
                throw new SQLException("No se pudo insertar el usuario");
            }

            // Insertar empleado
            psEmpleado = conexion.prepareStatement("INSERT INTO empleado(num_Documento, nombre, "
                    + "apellido_1, apellido_2, telefono, direccion, fecha_contratacion, "
                    + "usuario_id_usuario, liquidaciones_id_liquidacion,"
                    + "horarios_id_horario) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, NULL, NULL)");

            psEmpleado.setInt(1, colaborador.getNum_documento());
            psEmpleado.setString(2, colaborador.getNombre());
            psEmpleado.setString(3, colaborador.getApellido_1());
            psEmpleado.setString(4, colaborador.getApellido_2());
            psEmpleado.setInt(5, colaborador.getTelefono());
            psEmpleado.setString(6, colaborador.getDireccion());;
            psEmpleado.setDate(7, new java.sql.Date(colaborador.getFecha_contratacion().getTime())); // Convertir Date a java.sql.Date
            psEmpleado.setInt(8, idUsuarioGenerado);

            int filasAfectadasEmpleado = psEmpleado.executeUpdate();

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
                if (psUsuario != null) {
                    psUsuario.close();
                }
                if (psEmpleado != null) {
                    psEmpleado.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar PreparedStatements: " + ex.getMessage());
            }
        }
    }
    //---------------------------- -----------------------------------------// 

//--------------------VALIDAR SI EXISTE EMPLEADO  -----------------------------------------//
    public boolean empleadoExiste(int numDocumento) throws SQLException {
        String query = "SELECT COUNT(*) FROM empleado WHERE num_Documento = ?";
        try (PreparedStatement ps = conexion.prepareStatement(query)) {
            ps.setInt(1, numDocumento);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    //---------------------------------------------------------------------// 
//--------------------------------ELIMINAR EMPLEADO-------------------------------//
    public boolean eliminarEmpleado(int idEmpleado) {
        PreparedStatement ps;

        try {
            ps = conexion.prepareStatement("DELETE  e, u\n"
                    + "FROM empleado e  \n"
                    + "JOIN usuario u \n"
                    + "ON e.usuario_id_usuario = u.id_usuario\n"
                    + "WHERE e.id_empleado= ?");

            ps.setInt(1, idEmpleado);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
//---------------------------------------------------------------------// 
//--------------------------------ACTUALIZAR EMPLEADO-------------------------------//

    public boolean actualizarEmpleado(Colaborador colaborador) {
        PreparedStatement psEmpleado = null;

        try {
            String sql = "UPDATE empleado SET num_documento=?, nombre=?, "
                    + "apellido_1=?, apellido_2=?, telefono=?, direccion=?, fecha_contratacion=?, "
                    + "WHERE id_empleado=?";

            psEmpleado = conexion.prepareStatement(sql);
            psEmpleado.setInt(1, colaborador.getNum_documento());
            psEmpleado.setString(2, colaborador.getNombre());
            psEmpleado.setString(3, colaborador.getApellido_1());
            psEmpleado.setString(4, colaborador.getApellido_2());
            psEmpleado.setInt(5, colaborador.getTelefono());
            psEmpleado.setString(6, colaborador.getDireccion());

            // Verifica si la fecha es nula antes de convertirla
            if (colaborador.getFecha_contratacion() != null) {
                psEmpleado.setDate(7, new java.sql.Date(colaborador.getFecha_contratacion().getTime()));
            } else {
                psEmpleado.setNull(7, java.sql.Types.DATE);
            }

            psEmpleado.setInt(8, colaborador.getId_Empleado());

            int filasAfectadas = psEmpleado.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir el stack trace para más detalles del error
            return false;
        } finally {
            try {
                if (psEmpleado != null) {
                    psEmpleado.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();  // Imprimir el stack trace para más detalles del error
            }
        }
    }

    // Método para actualizar el estado del usuario
    public boolean actualizarUsuario(Usuario usuario) {
        PreparedStatement psActUsuario = null;

        try {
            String sql = "UPDATE usuario SET estadoUsuario = ?"
                    + " WHERE id_usuario = ?";

            psActUsuario = conexion.prepareStatement(sql);
            psActUsuario.setBoolean(1, usuario.isEstadoUsuario());
            psActUsuario.setInt(2, usuario.getId_usuario());

            int filasAfectadas = psActUsuario.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir el stack trace para más detalles del error
            return false;
        } finally {
            try {
                if (psActUsuario != null) {
                    psActUsuario.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();  // Imprimir el stack trace para más detalles del error
            }
        }
    }

    // Método para modificar empleado y usuario
    public boolean modificarEmpleado(Colaborador colaborador) {
        boolean empleadoActualizado = actualizarEmpleado(colaborador);
        boolean usuarioActualizado = actualizarUsuario(colaborador.getUsuario());

        return empleadoActualizado && usuarioActualizado;
    }
    //---------------------------------------------------------------------//   
    

}
