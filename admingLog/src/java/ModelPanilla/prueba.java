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

        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Generar Reporte de Planilla ===");
        System.out.println("1. Generar reporte quincenal");
        System.out.println("2. Generar reporte mensual");
        System.out.print("Selecciona una opción (1 o 2): ");
        int opcion = scanner.nextInt();

        if (opcion < 1 || opcion > 2) {
            System.out.println("Opción inválida. Por favor selecciona 1 o 2.");
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
            // Llamar al método correspondiente según la opción seleccionada
            if (opcion == 1) {
                planillaDAO.actualizarOCrearReporteQuincenal(idEmpleado, mesSeleccionado, anioSeleccionado);
            } else if (opcion == 2) {
                planillaDAO.actualizarOCrearReporteMensual(idEmpleado, mesSeleccionado, anioSeleccionado);
            }
        }

        scanner.close();
    }
}
// Llamar al método salarioHora con el ID ingresado
// planillaDAO.salarioHora(idEmpleado);
// planillaDAO.horasExtraNormaQuincena(idEmpleado);
//planillaDAO.horasExtraNormaMensuales(idEmpleado);
        //planillaDAO.incapacidadCalculoQuincena(idEmpleado);
