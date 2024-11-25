/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelAguinaldo;

import Model.Colaborador;
import java.util.Date;

/**
 *
 * @author Dell
 */
public class Aguinaldo {

    // Atributos
    private int idAguinaldo;            // ID del aguinaldo
    private double totalSalarios;       // Total de salarios durante el período
    private double aguinaldo;           // Aguinaldo calculado
    private int annoAguinaldo;
    private int idEmpleado;
    private Colaborador colaborador;
     
    public Aguinaldo() {
    }

    // Constructor con todos los campos
    public Aguinaldo(int idAguinaldo, 
            double totalSalarios, 
            double aguinaldo, 
            int annoAguinaldo, 
            int idEmpleado,
            Colaborador colaborador) {
        
        this.idAguinaldo = idAguinaldo;
        this.totalSalarios = totalSalarios;
        this.aguinaldo = aguinaldo;
        this.annoAguinaldo = annoAguinaldo;
        this.idEmpleado = idEmpleado;
        this.colaborador = colaborador;
    }

    public int getIdAguinaldo() {
        return idAguinaldo;
    }

    public void setIdAguinaldo(int idAguinaldo) {
        this.idAguinaldo = idAguinaldo;
    }

    public double getTotalSalarios() {
        return totalSalarios;
    }

    public void setTotalSalarios(double totalSalarios) {
        this.totalSalarios = totalSalarios;
    }

    public double getAguinaldo() {
        return aguinaldo;
    }

    public void setAguinaldo(double aguinaldo) {
        this.aguinaldo = aguinaldo;
    }

    public int getAnnoAguinaldo() {
        return annoAguinaldo;
    }

    public void setAnnoAguinaldo(int annoAguinaldo) {
        this.annoAguinaldo = annoAguinaldo;
    }
    
    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    

    // Método toString() para mostrar los datos de la clase
    @Override
    public String toString() {
        return "Aguinaldo{"
                + "idAguinaldo=" + idAguinaldo
                + ", totalSalarios=" + totalSalarios
                + ", aguinaldo=" + aguinaldo
                + ", annoAguinaldo=" + annoAguinaldo 
                + ", idEmpleado=" + idEmpleado
                + ", colaborador=" + colaborador
                + '}';
    }
}
