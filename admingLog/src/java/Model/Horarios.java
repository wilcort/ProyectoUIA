/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;
import java.sql.Time;
import java.util.Set;

public class Horarios {

    private int idHorario;
    private Time horaEntrada;
    private Time horaSalida;
    private Double  horasLaborales;
    private Set<String> diasLaborales;

    // Constructor vacío
    public Horarios() {}

    // Constructor con parámetros
    public Horarios(int idHorario, Time horaEntrada, Time horaSalida, double horasLaborales, Set<String> diasLaborales) {
        this.idHorario = idHorario;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.horasLaborales = horasLaborales;
        this.diasLaborales = diasLaborales;
    }

    // Getters y Setters
    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public Time getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(Time horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public Time getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(Time horaSalida) {
        this.horaSalida = horaSalida;
    }

    public double getHorasLaborales() {
        return horasLaborales;
    }

    public void setHorasLaborales(double horasLaborales) {
        this.horasLaborales = horasLaborales;
    }

    public Set<String> getDiasLaborales() {
        return diasLaborales;
    }

    public void setDiasLaborales(Set<String> diasLaborales) {
        this.diasLaborales = diasLaborales;
    }

    @Override
    public String toString() {
        return "Horarios{" +
                "idHorario=" + idHorario +
                ", horaEntrada=" + horaEntrada +
                ", horaSalida=" + horaSalida +
                ", horasLaborales=" + horasLaborales +
                ", diasLaborales=" + diasLaborales +
                '}';
    }
}