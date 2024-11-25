/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelAguinaldo;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Dell
 */
public class Prueba {

     public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AguinaldoDAO aguinaldoDAO = new AguinaldoDAO();
        
        // Solicitar ID del empleado y año al usuario
            System.out.println("Ingrese el ID del empleado:");
           int idEmpleado = scanner.nextInt();
        
     /*   try {

            System.out.println("Ingrese el año base (por ejemplo, 2023):");
            int anio = scanner.nextInt();
                      

            // Crear una instancia de AguinaldoDAO
            AguinaldoDAO aguinaldoDAO = new AguinaldoDAO();

           
              // Llamar al método para calcular los aguinaldos
            List<Aguinaldo> aguinaldos = aguinaldoDAO.calculoAguinaldo(anio);
            
             // Insertar los aguinaldos en la base de datos
            if (!aguinaldos.isEmpty()) {
                aguinaldoDAO.insertarAguinaldos(aguinaldos);
            } else {
                System.out.println("No se encontraron registros para calcular aguinaldos.");
            }

        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }*/
       
        // List<Aguinaldo> aguinaldosCalculados = aguinaldoDAO.calculoAguinaldo(anioSeleccionado);

            // Insertar los aguinaldos calculados en la base de datos
          //  aguinaldoDAO.insertarAguinaldos(aguinaldosCalculados);
          
          aguinaldoDAO.verAguinaldoEmpleado(idEmpleado);
    }

}
