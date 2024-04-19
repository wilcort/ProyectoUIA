package Model;

import Configur.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ColaboradorDAO {

    Connection conexion;

    public ColaboradorDAO() {
        Conexion conex = new Conexion();
        conexion = conex.getConectar();
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
        }
    }

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

        } catch (SQLException e) {
            System.out.println("Error al obtener el cargo por nombre: " + e.getMessage());
        }

        return cargo;
    }

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
}
