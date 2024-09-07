
package Model;


import Configur.Conexion;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.PreparedStatement;


public class UsuarioDAO {
    
    public Usuario identificar (Usuario user) {
    
        Usuario usu = null;
        Conexion con = null;
        Connection cn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;
        
        String sql = "SELECT u.id_usuario, c.nombre_cargo FROM usuario u "
                   + "INNER JOIN cargo c "
                   + "ON u.id_cargo = c.id_cargo "
                   + "WHERE u.estadoUsuario = 1 AND "
                   + "U.nombreUsuario = ? AND u.clave = ?";
                   
        
        try {
            con = new Conexion();
            cn = con.getConectar();
            pst = cn.prepareStatement(sql);
            pst.setString(1, user.getNombreUsuario());
            pst.setString(2, user.getClave());
            rs = pst.executeQuery();
            
            if(rs.next() == true){
                
                usu = new Usuario();
                usu.setId_usuario(rs.getInt("id_usuario"));
                usu.setNombreUsuario(user.getNombreUsuario());
                usu.setCargo(new Cargo());
                usu.getCargo().setNombreCargo(rs.getString("nombre_cargo"));
                usu.setEstadoUsuario(true);
                
            }
        } catch (Exception e) {
            System.out.println("ERROR" + e.getMessage());
        } finally{
             try {
                if(rs != null) rs.close();
                if(pst != null) pst.close();
                if(cn != null) cn.close();
            } catch (Exception e) {
                System.out.println("ERROR AL CERRAR RECURSOS: " + e.getMessage());
            }      
         }       
        return usu;    
    }
   
}
