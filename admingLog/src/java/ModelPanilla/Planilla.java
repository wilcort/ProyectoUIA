/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelPanilla;

import java.sql.*;
import Model.Colaborador;
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author Dell
 */
public class Planilla {
    
    private int idPlanilla; 
    private String periodo;
    private double salarioBruto;
    private double deduccionesCCSS;
    private double deduccionesIncapacidades;
    private double deduccionesImpuestos;
    private double salarioHorasExtra;
    private double salarioHorasRegulares;
    private double horasExtra;
    private double horasRegulares;
    private double salarioNeto;
    private Date mesPago;
    private int empleadoIdEmpleado;
    private Colaborador colaborador;

    public Planilla() {
    }

    public Planilla(int idPlanilla, String periodo, double salarioBruto,
            double deduccionesCCSS, double deduccionesIncapacidades,
            double deduccionesImpuestos, double salarioHorasExtra,
            double horasExtra, double horasRegulares,
            double salarioHorasRegulares,
            double salarioNeto, Date mesPago,
            int empleadoIdEmpleado, Colaborador colaborador) {

        this.idPlanilla = idPlanilla;
        this.periodo = periodo;
        this.salarioBruto = salarioBruto;
        this.deduccionesCCSS = deduccionesCCSS;
        this.deduccionesIncapacidades = deduccionesIncapacidades;
        this.deduccionesImpuestos = deduccionesImpuestos;
        this.salarioHorasExtra = salarioHorasExtra;
        this.salarioHorasRegulares = salarioHorasRegulares;
        this.horasExtra = horasExtra;
        this.horasRegulares = horasRegulares;
        this.salarioNeto = salarioNeto;
        this.mesPago = mesPago;
        this.empleadoIdEmpleado = empleadoIdEmpleado;
        this.colaborador = colaborador;
    }

    public int getIdPlanilla() {
        return idPlanilla;
    }

    public void setIdPlanilla(int idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public double getSalarioBruto() {
        return salarioBruto;
    }

    public void setSalarioBruto(double salarioBruto) {
        this.salarioBruto = salarioBruto;
    }

    public double getDeduccionesCCSS() {
        return deduccionesCCSS;
    }

    public void setDeduccionesCCSS(double deduccionesCCSS) {
        this.deduccionesCCSS = deduccionesCCSS;
    }

    public double getDeduccionesIncapacidades() {
        return deduccionesIncapacidades;
    }

    public void setDeduccionesIncapacidades(double deduccionesIncapacidades) {
        this.deduccionesIncapacidades = deduccionesIncapacidades;
    }

    public double getDeduccionesImpuestos() {
        return deduccionesImpuestos;
    }

    public void setDeduccionesImpuestos(double deduccionesImpuestos) {
        this.deduccionesImpuestos = deduccionesImpuestos;
    }

    public double getSalarioHorasExtra() {
        return salarioHorasExtra;
    }

    public void setSalarioHorasExtra(double salarioHorasExtra) {
        this.salarioHorasExtra = salarioHorasExtra;
    }

    public double getSalarioHorasRegulares() {
        return salarioHorasRegulares;
    }

    public void setSalarioHorasRegulares(double salarioHorasRegulares) {
        this.salarioHorasRegulares = salarioHorasRegulares;
    }

    public double getSalarioNeto() {
        return salarioNeto;
    }

    public void setSalarioNeto(double salarioNeto) {
        this.salarioNeto = salarioNeto;
    }

    public Date getMesPago() {
        return mesPago;
    }

    public void setMesPago(Date mesPago) {
        this.mesPago = mesPago;
    }

    public int getEmpleadoIdEmpleado() {
        return empleadoIdEmpleado;
    }

    public void setEmpleadoIdEmpleado(int empleadoIdEmpleado) {
        this.empleadoIdEmpleado = empleadoIdEmpleado;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }
    
    public double getHorasExtra() {
        return salarioHorasExtra;
    }

    public void setHorasExtra(double salarioHorasExtra) {
        this.salarioHorasExtra = salarioHorasExtra;
    }

    public double getHorasRegulares() {
        return salarioHorasRegulares;
    }

    public void setHorasRegulares(double salarioHorasRegulares) {
        this.salarioHorasRegulares = salarioHorasRegulares;
    }

    
    // Método toString (opcional) para mostrar el objeto Planilla de manera legible
    @Override
    public String toString() {
        return "Planilla{"
                + "idPlanilla=" + idPlanilla
                + ", periodo='" + periodo + '\''
                + ", salarioBruto=" + salarioBruto
                + ", deduccionesCCSS=" + deduccionesCCSS
                + ", deduccionesIncapacidades=" + deduccionesIncapacidades
                + ", deduccionesImpuestos=" + deduccionesImpuestos
                + ", salarioHorasExtra=" + salarioHorasExtra
                + ", salarioHorasRegulares=" + salarioHorasRegulares
                + ", horasExtra=" + horasExtra
                + ", horasRegulares=" + horasRegulares
                + ", salarioNeto=" + salarioNeto
                + ", mesPago=" + mesPago
                + ", empleadoIdEmpleado=" + empleadoIdEmpleado
                + ", colaborador=" + colaborador
                + '}';
    }

}
