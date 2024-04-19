package Model;

import Configur.Conexion;
import static Configur.Conexion.cerrarConexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
<<<<<<< HEAD
import java.util.List;
=======
import Model.Usuario;
import java.sql.Statement;
import javax.servlet.RequestDispatcher;



>>>>>>> 37196b40e8dbddc60c6412cf3a08212e5bbf284e

public class ColaboradorDAO {

    Connection conexion;

    public ColaboradorDAO() {
        Conexion conex = new Conexion();
        conexion = conex.getConectar();
    }
    
      public void cerrarConexion() {
        Conexion.cerrarConexion(conexion);
    }

    public List<Colaborador> listarColaboradores() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Colaborador> lista = new ArrayList<>();

        try {
            ps = conexion.prepareStatement("SELECT e.num_documento, e.nombre, "
                    + "e.apellido_1, e.apellido_2, e.telefono, e.direccion, u.id_usuario\n"
                    + "FROM empleado e\n"
                    + "INNER JOIN usuario u ON e.id_usuario = u.id_usuario;");
            rs = ps.executeQuery();

            while (rs.next()) {
                int num_documento = rs.getInt("num_documento");
                String nombre = rs.getString("nombre");
                String apellido_1 = rs.getString("apellido_1");
                String apellido_2 = rs.getString("apellido_2");
                int telefono = rs.getInt("telefono");
                String direccion = rs.getString("direccion");
                int id_usuario = rs.getInt("id_usuario");

                Usuario usuario = new Usuario();
                usuario.setId_usuario(id_usuario);

                Colaborador colaborador = new Colaborador(num_documento, nombre,
                        apellido_1, apellido_2, telefono, direccion, usuario);

                lista.add(colaborador);
            }
            
            return lista;
        } catch (Exception e) {
            System.out.println("Error al listar colaboradores: " + e.getMessage());
            return null;
<<<<<<< HEAD
        } finally {
            // Cerrar PreparedStatement y ResultSet, pero no la conexión
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
=======
        }finally {
            cerrarConexion();
>>>>>>> 37196b40e8dbddc60c6412cf3a08212e5bbf284e
        }
    }
    
<<<<<<< HEAD
  
=======
    public void cerrarConexion() {
        Conexion.cerrarConexion(conexion);
    }
    
>>>>>>> 37196b40e8dbddc60c6412cf3a08212e5bbf284e

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
                String nombre = rs.getString("nombre_Cargo");
                boolean estado = rs.getBoolean("estado");

                cargo = new Cargo(id_Cargo, nombreCargo, estado);
                
            }

<<<<<<< HEAD
        } catch (SQLException e) {
            System.out.println("Error al obtener el cargo por nombre: " + e.getMessage());
=======
            return colaborador;
        } catch (Exception e) {

            System.out.println("colaborador no encontrado " + e.getMessage());
            return null;
        }finally {
            cerrarConexion();
>>>>>>> 37196b40e8dbddc60c6412cf3a08212e5bbf284e
        }

        return cargo;
    }
    

<<<<<<< HEAD
=======
    //-----------------------------------------------------------------------//
        
            /* Cuando utilizas Statement.RETURN_GENERATED_KEYS, le estás indicando al 
        PreparedStatement que, después de ejecutar una operación de inserción, quieres 
        recuperar las claves generadas automáticamente por la base de datos. Esto es útil 
        cuando necesitas obtener el valor de una columna autoincremental que se genera 
        automáticamente, como en el caso del id_usuario en tu tabla usuario.
        Después de ejecutar la operación de inserción, puedes usar el método getGeneratedKeys() 
        del PreparedStatement para obtener un ResultSet que contiene las claves generadas 
        automáticamente. Luego puedes extraer el valor de la clave autoincremental del ResultSet y 
        utilizarlo según sea necesario, como lo hacemos en el código proporcionado para obtener el 
        id_usuario generado después de insertar un nuevo usuario.*/
    
>>>>>>> 37196b40e8dbddc60c6412cf3a08212e5bbf284e
    public boolean insertarColaboradores(Cargo cargo, Usuario usuario, Colaborador colaborador) {
        PreparedStatement psCargo = null;
        PreparedStatement psUsuario = null;
        PreparedStatement psEmpleado = null;
<<<<<<< HEAD
        int idUsuarioGenerado = 0;

        try {
            // Insertar usuario
            psUsuario = conexion.prepareStatement("INSERT INTO usuario(nombre, "
                    + "clave, estado, id_cargo) VALUES (?, ?, ?, ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS);

            psUsuario.setString(1, usuario.getNombreUsuario());
            psUsuario.setString(2, usuario.getClave());
            psUsuario.setBoolean(3, usuario.isEstado());
            psUsuario.setInt(4, cargo.getId_cargo());

            int filasAfectadasUsuario = psUsuario.executeUpdate();

            if (filasAfectadasUsuario == 1) {
                ResultSet generatedKeysUsuario = psUsuario.getGeneratedKeys();

                if (generatedKeysUsuario.next()) {
                    idUsuarioGenerado = generatedKeysUsuario.getInt(1);
                } else {
                    throw new SQLException("No se pudo obtener el ID del usuario generado");
                }
            } else {
                throw new SQLException("No se pudo insertar el usuario correctamente");
            }

            // Insertar empleado
            psEmpleado = conexion.prepareStatement("INSERT INTO empleado(num_documento, "
                    + "nombre, apellido_1, apellido_2, telefono, direccion, id_usuario)"
                    + " VALUES (?, ?, ?, ?, ?, ?, ?)");

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
            }

        } catch (Exception ex) {
            System.out.println("Error al insertar colaboradores: " + ex.getMessage());
        } finally {
            try {
                if (psUsuario != null) {
                    psUsuario.close();
                }
                if (psEmpleado != null) {
                    psEmpleado.close();
                }
                
            } catch (SQLException ex) {
                System.out.println("Error al cerrar PreparedStatement: " + ex.getMessage());
            }
        }

        return true;
    }
}
=======
        RequestDispatcher dispatcher = null;
        int idCargoGenerado = 0;
        int idUsuarioGenerado = 0;
        int idEmpleadoGenerado = 0;

        try {
            // Insertar cargo
            psCargo = conexion.prepareStatement("INSERT INTO cargo(nombre_cargo, estado)"
                    + " VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);

            psCargo.setString(1, cargo.getNombreCargo());
            psCargo.setBoolean(2, cargo.isEstado());
            int filasAfectadasCargo = psCargo.executeUpdate();

            // Verificar si se insertó correctamente el cargo
            if (filasAfectadasCargo == 1) {
                System.out.println("Cargo insertado correctamente");
                // Obtener el id_cargo generado
                ResultSet generatedKeysCargo = psCargo.getGeneratedKeys();

                if (generatedKeysCargo.next()) {
                    idCargoGenerado = generatedKeysCargo.getInt(1);
                    System.out.println("ID del cargo generado: " + idCargoGenerado);

                } else {
                    System.out.println("No se pudo obtener el ID del cargo generado");
                }
            //------------------------------------------------------------------

            // Insertar usuario
                psUsuario = conexion.prepareStatement("INSERT INTO usuario(nombre, "
                        + "clave, estado, id_cargo) VALUES (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS);

                psUsuario.setString(1, usuario.getNombreUsuario());
                psUsuario.setString(2, usuario.getClave());
                psUsuario.setBoolean(3, usuario.isEstado());
                psUsuario.setInt(4, idCargoGenerado); // Establecer el id_cargo generado como clave foránea
                int filasAfectadasUsuario = psUsuario.executeUpdate();

                // Verificar si se insertó correctamente el usuario
                if (filasAfectadasUsuario == 1) {
                    System.out.println("usuario insertado correctamente");
                    // Obtener el id_cargo generado
                    ResultSet generatedKeysUsuario = psCargo.getGeneratedKeys();

                    if (generatedKeysUsuario.next()) {
                        idUsuarioGenerado = generatedKeysUsuario.getInt(1);
                        System.out.println("ID del cargo generado: " + idUsuarioGenerado);

                    } else {
                        System.out.println("No se pudo obtener el ID del usuario generado");
                    }
                }
            //----------------------------------------
                
            // Insertar colaborador
                psEmpleado = conexion.prepareStatement("INSERT INTO empleado(num_documento, "
                        + "nombre, apellido_1, apellido_2, telefono, direccion, id_usuario)"
                        + " VALUES (?, ?, ?, ?, ?, ?, ?)");

                psEmpleado.setInt(1, colaborador.getNum_documento());
                psEmpleado.setString(2, colaborador.getNombre());
                psEmpleado.setString(3, colaborador.getApellido_1());
                psEmpleado.setString(4, colaborador.getApellido_2());
                psEmpleado.setInt(5, colaborador.getTelefono());
                psEmpleado.setString(6, colaborador.getDireccion());
                psEmpleado.setInt(7, idUsuarioGenerado);
                
                int filasAfectadasEmpleado = psEmpleado.executeUpdate();
                
                // Verificar si se insertó correctamente el empleado
                if (filasAfectadasEmpleado == 1) {
                    System.out.println("Empleado insertado correctamente");

                    // Obtener el id_empleado generado
                    ResultSet generatedKeysEmpleado = psEmpleado.getGeneratedKeys();
                    
                    if (generatedKeysEmpleado.next()) {
                        idEmpleadoGenerado = generatedKeysEmpleado.getInt(1);
                        System.out.println("ID del empleado generado: " + idUsuarioGenerado);

                    }else {
                        System.out.println("No se pudo obtener el ID del usuario generado");
                    }
                   
                }
                
               
            

            }
        } catch (SQLException e) {
            System.out.println("Error al insertar el cargo: " + e.getMessage());
        } finally {
            // Cerrar PreparedStatement
            if (psCargo != null) {
                try {
                    psCargo.close();
                } catch (SQLException ex) {
                    System.out.println("Error al cerrar PreparedStatement: " + ex.getMessage());
                }
            }
        }
        cerrarConexion();
        return true;
       
    }

     //-----------------------------------------//
    public boolean actualizar(Colaborador colaborador) {

        PreparedStatement ps;

        try {

            ps = conexion.prepareStatement("UPDATE empleado SET num_documento = ?, nombre = ?, "
                    + "apellido_1 = ?, apellido_2 = ?, telefono = ?, direccion = ?, id_usuario = ?) "
                    + "WHERE id=?");

            ps.setInt(1, colaborador.getNum_documento());
            ps.setString(2, colaborador.getNombre());
            ps.setString(3, colaborador.getApellido_1());
            ps.setString(4, colaborador.getApellido_2());
            ps.setInt(5, colaborador.getTelefono());
            ps.setString(6, colaborador.getDireccion());
            ps.setInt(7, colaborador.getUsuario().getId_usuario());
            // Establecer el ID del empleado (si tienes un método getId(), úsalo aquí)
            ps.setInt(8, colaborador.getUsuario().getId_usuario());

            ps.execute();

            return true;
        } catch (Exception e) {

            System.out.println("Error insercción" + e.getMessage());
            return false;
        }

    }
    
     //-----------------------------------------//
    public boolean eliminar(int _id) {

        PreparedStatement ps;

        try {

            ps = conexion.prepareStatement("DELETE FROM empleado WHERE id=?");

            ps.setInt(1, _id);
            ps.execute();

            return true;
        } catch (Exception e) {

            System.out.println("Error insercción" + e.getMessage());
            return false;
        }

    }
 
}
>>>>>>> 37196b40e8dbddc60c6412cf3a08212e5bbf284e
