
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
        this.usuario = usuario;

    }

    public List<Colaborador> listarColaboradores()  {

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

            System.out.println(e.toString());
            return null;
        }

    }

}
