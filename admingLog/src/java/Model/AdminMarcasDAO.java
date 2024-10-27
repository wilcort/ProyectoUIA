/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import Configur.Conexion;
import java.sql.Connection;
import java.sql.Time;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

/**
 *
 * @author Dell
 */
public class AdminMarcasDAO {
    
     Connection conexion;

    public AdminMarcasDAO() {
        Conexion conex = new Conexion();
        conexion = conex.getConectar();
    }
   
   
}
