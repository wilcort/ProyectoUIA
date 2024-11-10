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
    
     private int idPlanilla; // id_planilla
    private String periodo; // periodo
    private double salarioBruto; // salario_bruto
    private double deduccionesCCSS; // deducciones_CCSS
    private double deduccionesIncapacidades; // deducciones_incapacidades
    private double deduccionesImpuestos; // deducciones_impuestos
    private double salarioHorasExtra; // salario_horas_extra
    private double salarioHorasRegulares; // salario_horas_regulares
    private double salarioNeto; // salario_neto
    private Date fechaPago; // fecha_pago
    private int empleadoIdEmpleado; // empleado_id_empleado

    public Planilla(int idPlanilla, String periodo, double salarioBruto, 
            double deduccionesCCSS, double deduccionesIncapacidades, 
            double deduccionesImpuestos, double salarioHorasExtra, 
            double salarioHorasRegulares, double salarioNeto, Date fechaPago, 
            int empleadoIdEmpleado) {
        
        this.idPlanilla = idPlanilla;
        this.periodo = periodo;
        this.salarioBruto = salarioBruto;
        this.deduccionesCCSS = deduccionesCCSS;
        this.deduccionesIncapacidades = deduccionesIncapacidades;
        this.deduccionesImpuestos = deduccionesImpuestos;
        this.salarioHorasExtra = salarioHorasExtra;
        this.salarioHorasRegulares = salarioHorasRegulares;
        this.salarioNeto = salarioNeto;
        this.fechaPago = fechaPago;
        this.empleadoIdEmpleado = empleadoIdEmpleado;
    }

    public Planilla() {
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

    public Date getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(Date fechaPago) {
        this.fechaPago = fechaPago;
    }

    public int getEmpleadoIdEmpleado() {
        return empleadoIdEmpleado;
    }

    public void setEmpleadoIdEmpleado(int empleadoIdEmpleado) {
        this.empleadoIdEmpleado = empleadoIdEmpleado;
    }
   
    // Método toString (opcional) para mostrar el objeto Planilla de manera legible
    @Override
    public String toString() {
        return "Planilla{" +
                "idPlanilla=" + idPlanilla +
                ", periodo='" + periodo + '\'' +
                ", salarioBruto=" + salarioBruto +
                ", deduccionesCCSS=" + deduccionesCCSS +
                ", deduccionesIncapacidades=" + deduccionesIncapacidades +
                ", deduccionesImpuestos=" + deduccionesImpuestos +
                ", salarioHorasExtra=" + salarioHorasExtra +
                ", salarioHorasRegulares=" + salarioHorasRegulares +
                ", salarioNeto=" + salarioNeto +
                ", fechaPago=" + fechaPago +
                ", empleadoIdEmpleado=" + empleadoIdEmpleado +
                '}';
    } 

}
