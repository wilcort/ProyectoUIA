/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelLiquidacion;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author Dell
 */
public class prueba {

    public static void main(String[] args) {

        LiquidacionDAO liquidacionDAO = new LiquidacionDAO();

        // Crear un objeto Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);

        // Solicitar al usuario que ingrese la fecha de finalización del contrato
        System.out.println("Ingrese la fecha de finalización del contrato (formato: yyyy-MM-dd):");
        String fechaInput = scanner.nextLine();

        System.out.println("Ingrese id empleado:");
        String idEmpleadoStr = scanner.nextLine();

        try {

            int idEmpleado = Integer.parseInt(idEmpleadoStr);

            // Verificar si la fecha ingresada es válida
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(fechaInput, formatter); // Validación de formato

            // Definir el formato de la fecha
            //    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            // Convertir la entrada del usuario a un objeto LocalDate
            
            LocalDate fechaFin = LocalDate.parse(fechaInput, formatter);

            // Extraer mes y año de la fecha ingresada
               int mesSeleccionado = fechaFin.getMonthValue();
              int anioSeleccionado = fechaFin.getYear();
              
            // Llamar al método para generar la planilla
           liquidacionDAO.obtenerSalarioBrutoPromedio(idEmpleado);
            
           liquidacionDAO.calculoCesantias(idEmpleado,fechaInput);
        } catch (Exception e) {
            // Manejar errores si la fecha es inválida o el ID no es un número
            System.err.println("Error: Entrada no válida. " + e.getMessage());
        }
    }
}

// Calcular los días transcurridos desde el 1 de diciembre del año anterior
// long dias = liquidacionDAO.calcularDiasDesdeDiciembre(fechaFin);
// double salarioAnual = liquidacionDAO.calcularSalarioBruto(Integer.parseInt(idEmpleado), fechaFin);
// Mostrar el resultado
// System.out.println("Días transcurridos desde diciembre del año anterior: " + dias);
//  System.out.println("Salario Anual del Empleado: " + salarioAnual);
// Calcular el aguinaldo
// liquidacionDAO.calcularAguinaldo(fechaFin,idEmpleado);
           // liquidacionDAO.calcularDiasVacaciones(idEmpleado);
