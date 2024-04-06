
package Configur;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {

    private final String basedeDatos = "tienda";
    private final String servidor = "jdbc:mysql://localhost/" + basedeDatos;
    private final String usuario = "root";
    private final String password = "123456";

    public Connection getConectar() {

        Connection conectar = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conectar = DriverManager.getConnection(servidor, usuario, password);
            System.out.println("CONEXION");

        } catch (Exception e) {

            System.out.println("ERROR CONEXION" + e.getMessage());

        }

        return conectar;

    }

    public static void cerrarConexion(Connection conexion) {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("CONEXIÓN CERRADA");
            } catch (SQLException e) {
                System.out.println("ERROR: No se pudo cerrar la conexión");
            }
        }
    }
    
}
