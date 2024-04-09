
package Model;

import Configur.Conexion;
import java.sql.Connection;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import Model.Usuario;
import java.sql.Statement;




public class ColaboradorDAO {

    //DAO = Data Access Object //
    Connection conexion;
    private Usuario usuario;

    public ColaboradorDAO() {

        Conexion conex = new Conexion();
        conexion = conex.getConectar();

    }

    public List<Colaborador> listarColaboradores() {

        // PreparedStatement = interfaz en Java que representa una declaración SQL precompilada //
        // resulset =  interfaz en Java que representa el conjunto de resultados de una consulta SQL//
        PreparedStatement ps;
        ResultSet rs;

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
                int usuarioId = rs.getInt("id_usuario");

                Colaborador colaborador = new Colaborador(num_documento, nombre,
                        apellido_1, apellido_2, telefono, direccion, usuario);

                lista.add(colaborador);
            }

            return lista;
        } catch (Exception e) {

            System.out.println("Error al listar colaboradores: " + e.getMessage());
            return null;
        }

    }

    //-----------------------------------------//
    public Colaborador mostrarColaboradores(int _id) {

        // PreparedStatement = interfaz en Java que representa una declaración SQL precompilada //
        // resulset =  interfaz en Java que representa el conjunto de resultados de una consulta SQL//
        PreparedStatement ps;
        ResultSet rs;
        Colaborador colaborador = null;

        try {

            ps = conexion.prepareStatement("SELECT num_documento, nombre, "
                    + "apellido_1, apellido_2, telefono, direccion, id_usuario\n"
                    + "FROM empleado \n"
                    + "WHERE id_usuario = ?;");

            ps.setInt(1, _id);
            rs = ps.executeQuery();

            while (rs.next()) {

                int num_documento = rs.getInt("num_documento");
                String nombre = rs.getString("nombre");
                String apellido_1 = rs.getString("apellido_1");
                String apellido_2 = rs.getString("apellido_2");
                int telefono = rs.getInt("telefono");
                String direccion = rs.getString("direccion");
                int usuarioId = rs.getInt("id_usuario");

                colaborador = new Colaborador(num_documento, nombre,
                        apellido_1, apellido_2, telefono, direccion, usuario);

            }

            return colaborador;
        } catch (Exception e) {

            System.out.println("colaborador no encontrado " + e.getMessage());
            return null;
        }
    }

    //-----------------------------------------------------------------------//
    public boolean insertar(Colaborador colaborador) {

        PreparedStatement ps;

        try {

            ps = conexion.prepareStatement("INSERT INTO empleado(num_documento, nombre, "
                    + "apellido_1, apellido_2, telefono, direccion, id_usuario) "
                    + "VALUES (?,?,?,?,?,?,?)");

            ps.setInt(1, colaborador.getNum_documento());
            ps.setString(2, colaborador.getNombre());
            ps.setString(3, colaborador.getApellido_1());
            ps.setString(4, colaborador.getApellido_2());
            ps.setInt(5, colaborador.getTelefono());
            ps.setString(6, colaborador.getDireccion());
            ps.setInt(7, colaborador.getUsuario().getId_usuario());

            ps.execute();

            return true;
        } catch (Exception e) {

            System.out.println("Error insercción" + e.getMessage());
            return false;
        }
       
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
