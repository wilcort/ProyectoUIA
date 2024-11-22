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
import java.util.stream.IntStream;

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

        Scanner scanner = new Scanner(System.in);
        //   System.out.print("Ingresa el ID del empleado: ");
        //   int idEmpleado = scanner.nextInt();

        System.out.println("=== Generar Reporte de Planilla ===");
        System.out.println("1. Generar reporte quincenal");
        System.out.println("2. Generar reporte mensual");
        System.out.println("3. Reporte Vacaciones");
        System.out.print("Selecciona una opción (1 al 3): ");
        int opcion = scanner.nextInt();

        if (opcion < 1 || opcion > 3) {
            System.out.println("Opción inválida. Por favor selecciona 1, 2 o 3.");
            return;
        }

        // Solicitar los datos necesarios al usuario
        System.out.print("Ingresa el ID del empleado: ");
        int idEmpleado = scanner.nextInt();

        System.out.print("Ingresa el mes del reporte (1-12): ");
        int mesSeleccionado = scanner.nextInt();

        System.out.print("Ingresa el año del reporte (por ejemplo, 2024): ");
        int anioSeleccionado = scanner.nextInt();

        // Validar entrada
        if (mesSeleccionado < 1 || mesSeleccionado > 12) {
            System.out.println("El mes ingresado no es válido. Debe estar entre 1 y 12.");
        } else if (anioSeleccionado < 2000 || anioSeleccionado > 2100) {
            System.out.println("El año ingresado no es válido. Debe estar entre 2000 y 2100.");
        } else {
            switch (opcion) {
                case 1:
                    IntStream.range(0, 2).forEach(i -> planillaDAO.actualizarOCrearReporteQuincenal(idEmpleado, mesSeleccionado, anioSeleccionado));
                    break;
                case 2:
                    IntStream.range(0, 2).forEach(i ->  planillaDAO.actualizarOCrearReporteMensual(idEmpleado, mesSeleccionado, anioSeleccionado));
                    break;
                case 3:
                    System.out.println("Ejecutando Reporte de Vacaciones...");
                    double vacacionesPago = planillaDAO.obtenerVacacionesMensual(idEmpleado, anioSeleccionado, mesSeleccionado);

                    break;
                default:
                    System.out.println("Opción desconocida.");
                    break;
            }
        }

        scanner.close();
        //   planillaDAO.generarPlanillaQuincenalParaTodos(11,2024);
    }
}
// Llamar al método salarioHora con el ID ingresado
// planillaDAO.salarioHora(idEmpleado);
// planillaDAO.horasExtraNormaQuincena(idEmpleado);
//planillaDAO.horasExtraNormaMensuales(idEmpleado);
        //planillaDAO.incapacidadCalculoQuincena(idEmpleado);
