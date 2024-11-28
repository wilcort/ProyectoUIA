/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelPanilla;


import ModelLiquidacion.LiquidacionDAO;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Scanner;
import java.sql.Date;

/**
 *
 * @author Dell
 */
public class pruebareporte {
    
    public static void main(String[] args) {
        // Crear un objeto Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        try {
            // Solicitar al usuario que ingrese la fecha de finalización del contrato
            System.out.println("Ingrese la fecha de finalización del contrato (formato: yyyy-MM-dd):");
            String fechaInput = scanner.nextLine();

            // Convertir la fecha a formato Date
            Date fechaFinalizacion = Date.valueOf(fechaInput);

            // Solicitar al usuario que ingrese el id del empleado
            System.out.println("Ingrese id empleado:");
            String idEmpleadoStr = scanner.nextLine();
            int idEmpleado = Integer.parseInt(idEmpleadoStr);

            // Solicitar mes y año para el reporte
            System.out.println("Ingrese mes (número):");
            int mesSeleccionado = Integer.parseInt(scanner.nextLine());

            System.out.println("Ingrese año (número):");
            int anioSeleccionado = Integer.parseInt(scanner.nextLine());

            // Crear el DAO y generar el reporte
            LiquidacionDAO liquidacionDAO = new LiquidacionDAO();
            liquidacionDAO.generarReporteQuincenal(idEmpleado, mesSeleccionado, anioSeleccionado);
           
        } catch (Exception e) {
            // Manejar errores si la fecha es inválida
            System.err.println("Error: " + e.getMessage());
        } finally {
            // Cerrar el scanner
            scanner.close();
        }
    }
    
}
