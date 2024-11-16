/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelPanilla;

import Configur.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.CallableStatement;
import java.util.Scanner;

/**
 *
 * @author Dell
 */
public class prueba {

    Connection conexion;

    public prueba() {
        Conexion conex = new Conexion();
        conexion = conex.getConectar();
    }

    public static void main(String[] args) {
        // Crear una instancia de la clase PlanillaDAO
        PlanillaDAO planillaDAO = new PlanillaDAO();

        // Crear un Scanner para tomar la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        // Pedir al usuario que ingrese el ID de empleado
        System.out.print("Ingresa el ID del empleado: ");
        int idEmpleado = scanner.nextInt();  // Leer el número de empleado

        // Llamar al método salarioHora con el ID ingresado
       // planillaDAO.salarioHora(idEmpleado);
        planillaDAO.horasExtraNormaQuincena(idEmpleado);
        planillaDAO.horasExtraNormaMensual(idEmpleado);

        // Cerrar el scanner después de usarlo
        scanner.close();
    }
}
