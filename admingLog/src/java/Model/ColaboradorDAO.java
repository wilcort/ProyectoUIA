package Model;

import Configur.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
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
        CallableStatement cs = null;
        ResultSet rs = null;
        List<Colaborador> lista = new ArrayList<>();

        try {
            // Llamar al procedimiento almacenado
            cs = conexion.prepareCall("{call ObtenerDatosUsuarios()}");
            rs = cs.executeQuery();

            while (rs.next()) {
                // Obtener los datos del colaborador
                int num_documento = rs.getInt("num_documento");
                String nombre = rs.getString("nombre");
                String apellido_1 = rs.getString("apellido_1");
                String apellido_2 = rs.getString("apellido_2");
                int telefono = rs.getInt("telefono");
                String direccion = rs.getString("direccion");
                int id_usuario = rs.getInt("id_usuario");
                                

                // Obtener el usuario
                Usuario usuario = new Usuario();
                usuario.setId_usuario(id_usuario);


                // Crear objeto Colaborador y agregarlo a la lista
                Colaborador colaborador = new Colaborador(num_documento, nombre,
                        apellido_1, apellido_2, telefono, direccion, usuario);

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
                if (cs != null) {
                    cs.close();
                }
            } catch (SQLException ex) {
                System.out.println("Error al cerrar CallableStatement o ResultSet: " + ex.getMessage());
            }
        }

        return lista;
    }
    
    //-------------------------------------------------------------------------------------//
//------------------------------ OBTENER CARGO -----------------------------------------//   

    public Cargo obtenerCargoPorNombre(String nombreCargo) {
        PreparedStatement ps;
        ResultSet rs;
        Cargo cargo = null;

        try {
            ps = conexion.prepareStatement("SELECT * FROM cargo WHERE nombre_cargo = ?");
            ps.setString (1,nombreCargo);
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
    
    public boolean insertarColaboradores(Usuario usuario, Colaborador colaborador) {
        PreparedStatement psUsuario = null;
        PreparedStatement psEmpleado = null;
        int idUsuarioGenerado = 0;

        try {
            // Insertar usuario
            psUsuario = conexion.prepareStatement("INSERT INTO usuario(nombre, "
                    + "clave, estado, id_cargo) VALUES (?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            psUsuario.setString(1, usuario.getNombreUsuario());
            psUsuario.setString(2, usuario.getClave());
            psUsuario.setBoolean(3, usuario.isEstado());
            psUsuario.setInt(4, usuario.getCargo().getIdCargo());

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
            psEmpleado = conexion.prepareStatement("INSERT INTO empleado(num_documento, nombre, "
                    + "apellido_1, apellido_2, telefono, direccion, id_usuario) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?)");

            psEmpleado.setInt(1, colaborador.getNum_documento());
            psEmpleado.setString(2, colaborador.getNombre());
            psEmpleado.setString(3, colaborador.getApellido_1());
            psEmpleado.setString(4, colaborador.getApellido_2());
            psEmpleado.setInt(5, colaborador.getTelefono());
            psEmpleado.setString(6, colaborador.getDireccion());
            psEmpleado.setInt(7, idUsuarioGenerado);

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
            ps = conexion.prepareStatement("SELECT e.num_documento, e.nombre, e.apellido_1, "
                    + "e.apellido_2, e.telefono, e.direccion,\n"
                    + "u.id_usuario, u.nombre, u.estado,\n"
                    + "c.nombre_cargo FROM empleado as e\n"
                    + "INNER JOIN usuario as u ON e.id_usuario = u.id_usuario\n"
                    + "INNER JOIN cargo as c ON u.id_cargo = c.id_cargo\n"
                    + "WHERE e.id_usuario = ?");

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
                int id_usuario = rs.getInt("id_usuario");
                String nombreUsuario = rs.getString("nombre");
                boolean estado = rs.getBoolean("estado");
                String nombre_cargo = rs.getString("nombre_cargo"); // Obtener el nombre del cargo

                // Obtener el usuario
                Usuario usuario = new Usuario();
                usuario.setId_usuario(id_usuario);
                usuario.setNombreUsuario(nombre);
                usuario.setEstado(estado);

                // Obtener el cargo
                Cargo cargo = new Cargo();
                cargo.setNombreCargo(nombre_cargo);

                usuario.setCargo(cargo);

                // Crear el colaborador
                colaborador = new Colaborador(num_documento, nombre, apellido_1, apellido_2, telefono, direccion, usuario);
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
//------------------------------ ELIMINAR -----------------------------------------//  
     
    public boolean eliminarEmpleado(int id_usuario) {
        PreparedStatement ps;

        try {
            ps = conexion.prepareStatement("DELETE  empleado, usuario\n" 
                                            +"FROM empleado\n" 
                                            +"JOIN usuario \n" 
                                            +"ON empleado.id_usuario = usuario.id_usuario\n" 
                                            +"WHERE empleado.id_usuario = ?");

            ps.setInt(1, id_usuario);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }
}
