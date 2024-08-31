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
            
            ps = conexion.prepareStatement(" SELECT \n"
                    + "e.num_documento,\n"
                    + "e.nombre,\n"
                    + "e.apellido_1,\n"
                    + "e.apellido_2,\n"
                    + "e.telefono,\n"
                    + "e.direccion,\n"
                    + "e.fecha_contratacion,\n"
                    + "e.salario_base,\n"
                    + "u.id_usuario,\n"
                    + "u.estado,\n"
                    + "c.nombre_cargo\n"
                    + "FROM \n"
                    + "empleado e\n"
                    + "    INNER JOIN \n"
                    + "        usuario u ON e.usuario_id_usuario = u.id_usuario\n"
                    + "    INNER JOIN \n"
                    + "        cargo c ON u.id_cargo = c.id_cargo;");
           
            rs = ps.executeQuery();

            while (rs.next()) {
                // Obtener los datos del colaborador
                int num_documento = rs.getInt("num_documento");
                String nombre = rs.getString("nombre");
                String apellido_1 = rs.getString("apellido_1");
                String apellido_2 = rs.getString("apellido_2");
                int telefono = rs.getInt("telefono");
                String direccion = rs.getString("direccion");
                Date fecha_contratacion = rs.getDate("fecha_contratacion");
                BigDecimal salario_base = rs.getBigDecimal("salario_base");
                int id_usuario = rs.getInt("id_usuario");

                // Obtener el usuario
                Usuario usuario = new Usuario();
                usuario.setId_usuario(id_usuario);

                // Crear objeto Colaborador y agregarlo a la lista
                
                Colaborador colaborador = new Colaborador(id_usuario, num_documento, 
                        nombre, apellido_1, apellido_2, telefono, direccion,
                        fecha_contratacion, salario_base, usuario);

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
//------------------------------ OBTENER LISTA CARGO -----------------------------------------//   
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

                System.out.println("cargo uno " + nombre_cargo);

            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el cargo por nombre: " + e.getMessage());
        }

        return cargo;
    }

    //-------------------------------------------------------------------------------------//
//------------------------------ INSERTAR -----------------------------------------// 
    
//--------------------VALIDAR SI EXISTE EMPLEADO-----------------------------------------//
    
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
    
    
 //------------------------------------------------------------------------------------//   
    public boolean insertarColaboradores(Usuario usuario, Colaborador colaborador) {
        PreparedStatement psUsuario = null;
        PreparedStatement psEmpleado = null;
        int idUsuarioGenerado = 0;

        try {
            // Insertar usuario
            psUsuario = conexion.prepareStatement("INSERT INTO usuario(nombreUsuario, "
                    + "clave, estado, id_cargo) VALUES (?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            psUsuario.setString(1, usuario.getNombreUsuario());
            psUsuario.setString(2, usuario.getClave());
            psUsuario.setBoolean(3, usuario.isEstado());
            psUsuario.setInt(4, usuario.getCargo().getIdCargo());
            
            
            System.out.println(" cargo insertar " + usuario.getNombreUsuario());
            System.out.println(" cargo insertar " + usuario.getCargo().getNombreCargo());
            System.out.println(" cargo insertar " + usuario.getCargo().isEstado());

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
                    + "salario_base, usuario_id_usuario, liquidaciones_id_liquidacion,horarios_id_horario) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, NULL, NULL)");

            psEmpleado.setInt(1, colaborador.getNum_Documento());
            psEmpleado.setString(2, colaborador.getNombre());
            psEmpleado.setString(3, colaborador.getApellido_1());
            psEmpleado.setString(4, colaborador.getApellido_2());
            psEmpleado.setInt(5, colaborador.getTelefono());
            psEmpleado.setString(6, colaborador.getDireccion());;
            psEmpleado.setDate(7, new java.sql.Date(colaborador.getFecha_Contratacion().getTime())); // Convertir Date a java.sql.Date
            psEmpleado.setBigDecimal(8, colaborador.getSalario_Base());
            psEmpleado.setInt(9, idUsuarioGenerado);
            
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

    //-------------------------------------------------------------------------------------//
//------------------------------ MOSTRAR -----------------------------------------//   
    public Colaborador mostrarEmpleado(int idUsuario) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Colaborador colaborador = null;

        try {
            ps = conexion.prepareStatement("SELECT e.id_empleado,e.num_documento, e.nombre, e.apellido_1, "
                    + "e.apellido_2, e.telefono, e.direccion, e.fecha_contratacion, e.salario_Base,"
                    + "u.id_usuario, u.nombreUsuario, u.estado, "
                    + "c.nombre_cargo FROM empleado as e "
                    + "INNER JOIN usuario as u ON e.usuario_id_usuario = u.id_usuario "
                    + "INNER JOIN cargo as c ON u.id_cargo = c.id_cargo "
                    + "WHERE e.usuario_id_usuario = ?");

            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();

            while (rs.next()) {

                // Obtener los datos del colaborador
                int id_empleado = rs.getInt("id_Empleado");
                int num_documento = rs.getInt("num_Documento");
                String nombre = rs.getString("nombre");
                String apellido_1 = rs.getString("apellido_1");
                String apellido_2 = rs.getString("apellido_2");
                int telefono = rs.getInt("telefono");
                String direccion = rs.getString("direccion");
                java.sql.Date fecha_Contratacion = rs.getDate("fecha_Contratacion");
                BigDecimal salario_Base = rs.getBigDecimal("salario_Base");
                int id_usuario = rs.getInt("id_usuario");
                String nombreUsuario = rs.getString("u.nombreUsuario"); ///***
                boolean estado = rs.getBoolean("estado");
                String nombre_cargo = rs.getString("nombre_cargo"); // Obtener el nombre del cargo

                // Obtener el usuario
                Usuario usuario = new Usuario();
                usuario.setId_usuario(id_usuario);
                usuario.setNombreUsuario(nombreUsuario); //******
                usuario.setEstado(estado);

                // Obtener el cargo
                Cargo cargo = new Cargo();
                cargo.setNombreCargo(nombre_cargo);

                usuario.setCargo(cargo);
                
                
                colaborador = new Colaborador(id_usuario, num_documento,
                        nombre, apellido_1, apellido_2, telefono, direccion, 
                        fecha_Contratacion, salario_Base, usuario);
                
                System.out.println("cargo " + colaborador.getUsuario().getNombreUsuario());
                System.out.println("cargo " + colaborador.getUsuario().isEstado());
                System.out.println("cargo " + colaborador.getUsuario().getCargo().getNombreCargo());
                System.out.println("cargo " + colaborador.getUsuario().getCargo().isEstado());

            }
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
    
    public Colaborador mostrarDatosEmpleado(int idUsuario) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Colaborador colaborador = null;

        try {
            ps = conexion.prepareStatement("SELECT * FROM empleado WHERE id_usuario = ?");
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();

            while (rs.next()) {
                // Obtener los datos del colaborador
                int num_documento = rs.getInt("num_documento");
                String nombre = rs.getString("nombre");
                String apellido_1 = rs.getString("apellido_1");
                String apellido_2 = rs.getString("apellido_2");
                int telefono = rs.getInt("telefono");
                String direccion = rs.getString("direccion");
                java.sql.Date fechaContratacion = rs.getDate("fechaContratacion");
                BigDecimal salarioBase = rs.getBigDecimal("salarioBase");
                int id_usuario = rs.getInt("id_usuario");
                
     
                colaborador = new Colaborador(id_usuario, num_documento,
                        nombre, apellido_1, apellido_2, telefono, direccion, 
                        fechaContratacion, salarioBase, null);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Agregar manejo de errores
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Agregar manejo de errores
            }
        }

        return colaborador;
    }
 //----------------------------------------------------------------------------
    
    public Usuario mostrarDatosUsuario(int idUsuario) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Usuario usuario = null; // Definir la variable aquí

        try {
            ps = conexion.prepareStatement("SELECT * FROM usuario WHERE id_usuario = ?");
            ps.setInt(1, idUsuario);
            rs = ps.executeQuery();

            if (rs.next()) { // Usar if si esperas un solo registro
                // Obtener los datos del usuario
                int id_usuario = rs.getInt("id_usuario");
                String nombreUsuario = rs.getString("nombreUsuario");
                String clave = rs.getString("clave");
                boolean estado = rs.getBoolean("estado");
                int id_cargo = rs.getInt("id_cargo");

                // Crear y configurar el objeto Usuario
                usuario = new Usuario();
                usuario.setId_usuario(id_usuario);
                usuario.setNombreUsuario(nombreUsuario);
                usuario.setClave(clave); // Asegúrate de tener un setter para 'clave'
                usuario.setEstado(estado);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Registrar el error
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace(); // Registrar el error
            }
        }

        return usuario; // Ahora esta variable está accesible aquí
    }
    
    
//------------------------------ ELIMINAR -----------------------------------------//  
    public boolean eliminarEmpleado(int id_usuario) {
        PreparedStatement ps;

        try {
            ps = conexion.prepareStatement("DELETE  empleado, usuario\n"
                    + "FROM empleado \n"
                    + "JOIN usuario  \n"
                    + "ON empleado.usuario_id_usuario = usuario.id_usuario\n"
                    + "WHERE empleado.usuario_id_usuario = ?");

            ps.setInt(1, id_usuario);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

//-------------------------------------------------------------------------------------//
//------------------------------ ACTUALIZAR -----------------------------------------//   
    // Método para actualizar los datos del empleado
    public boolean actualizarEmpleado(Colaborador colaborador) {
        PreparedStatement psEmpleado = null;

        try {
            String sql = "UPDATE empleado SET num_documento=?, nombre=?, "
                    + "apellido_1=?, apellido_2=?, telefono=?, direccion=?, fecha_contratacion=?"
                    + "fecha_salida=?, salario_base=?"
                    + "WHERE id_usuario=?";

            psEmpleado = conexion.prepareStatement(sql);
             psEmpleado.setInt(1, colaborador.getNum_Documento());
            psEmpleado.setString(2, colaborador.getNombre());
            psEmpleado.setString(3, colaborador.getApellido_1());
            psEmpleado.setString(4, colaborador.getApellido_2());
            psEmpleado.setInt(5, colaborador.getTelefono());
            psEmpleado.setString(6, colaborador.getDireccion());;
            psEmpleado.setDate(7, new java.sql.Date(colaborador.getFecha_Contratacion().getTime())); // Convertir Date a java.sql.Date
            psEmpleado.setBigDecimal(8, colaborador.getSalario_Base());
            psEmpleado.setInt(9, colaborador.getUsuario().getId_usuario());
            
            // psEmpleado.setInt(7, colaborador.getUsuario().getId_usuario());
            
           
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
            String sql = "UPDATE usuario SET estado = ?"
                    + " WHERE id_usuario = ?";

            psActUsuario = conexion.prepareStatement(sql);
            psActUsuario.setBoolean(1, usuario.isEstado());
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

    // Método para actualizar el cargo del usuario
    public boolean actualizarCargo(Usuario usuario) {
        PreparedStatement psActualizarCargo = null;

        try {
            
             if (usuario.getCargo() == null) {
                System.out.println("El cargo del usuario es nulo. No se puede actualizar.");
                return false;
            }
             
            String sql = "UPDATE usuario SET id_cargo = ? WHERE id_usuario = ?";

            psActualizarCargo = conexion.prepareStatement(sql);
            psActualizarCargo.setInt(1, usuario.getCargo().getIdCargo()); // Asignar el nuevo id_cargo
            psActualizarCargo.setInt(2, usuario.getId_usuario());
            
            System.out.println(" cargo update " + usuario.getCargo());
            
            int filasAfectadas = psActualizarCargo.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();  // Imprimir el stack trace para más detalles del error
            return false;
        } finally {
            try {
                if (psActualizarCargo != null) {
                    psActualizarCargo.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();  // Imprimir el stack trace para más detalles del error
            }
        }
    }

    // Método para modificar empleado y usuario
    public boolean modificarEmpleado(Usuario usuario, Colaborador colaborador) {
        boolean empleadoActualizado = actualizarEmpleado(colaborador);
        boolean usuarioActualizado = actualizarUsuario(usuario);


         return empleadoActualizado && usuarioActualizado;
    }

//-------------------------------------------------------------------------------------//
   
}