/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelLiquidacion;

import Model.Colaborador;
import java.util.Date;

/**
 *
 * @author Dell
 */
public class Liquidacion {

    // Atributos
    private int idLiquidacion;
    private Date fechaFinContrato;
    private String motivo;
    private double montoCesantias;
    private double montoAguinaldo;
    private double montoVacaciones;
    private double responsabilidadPatronal;
    private double preaviso;
    private double montoTotal;
    private String estadoPago; // Enum values: "Pendiente", "Pagado"
    private int empleadoIdEmpleado;
    private Colaborador  colaborador;
    
    
        public Liquidacion() {
    }

    public Liquidacion(int idLiquidacion, 
            Date fechaFinContrato, String motivo,
            double montoCesantias, double montoAguinaldo,
            double montoVacaciones, double responsabilidadPatronal,
            double preaviso, double montoTotal, String estadoPago,
            int empleadoIdEmpleado, Colaborador colaborador) {
        
        
        this.idLiquidacion = idLiquidacion;
        this.fechaFinContrato = fechaFinContrato;
        this.motivo = motivo;
        this.montoCesantias = montoCesantias;
        this.montoAguinaldo = montoAguinaldo;
        this.montoVacaciones = montoVacaciones;
        this.responsabilidadPatronal = responsabilidadPatronal;
        this.preaviso = preaviso;
        this.montoTotal = montoTotal;
        this.estadoPago = estadoPago;
        this.empleadoIdEmpleado = empleadoIdEmpleado;
        this.colaborador = colaborador;
    }

    public int getIdLiquidacion() {
        return idLiquidacion;
    }

    public void setIdLiquidacion(int idLiquidacion) {
        this.idLiquidacion = idLiquidacion;
    }

    public Date getFechaFinContrato() {
        return fechaFinContrato;
    }

    public void setFechaFinContrato(Date fechaLiquidacion) {
        this.fechaFinContrato = fechaFinContrato;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public double getMontoCesantias() {
        return montoCesantias;
    }

    public void setMontoCesantias(double montoCesantias) {
        this.montoCesantias = montoCesantias;
    }

    public double getMontoAguinaldo() {
        return montoAguinaldo;
    }

    public void setMontoAguinaldo(double montoAguinaldo) {
        this.montoAguinaldo = montoAguinaldo;
    }

    public double getMontoVacaciones() {
        return montoVacaciones;
    }

    public void setMontoVacaciones(double montoVacaciones) {
        this.montoVacaciones = montoVacaciones;
    }

    public double getResponsabilidadPatronal() {
        return responsabilidadPatronal;
    }

    public void setResponsabilidadPatronal(double responsabilidadPatronal) {
        this.responsabilidadPatronal = responsabilidadPatronal;
    }

    public double getPreaviso() {
        return preaviso;
    }

    public void setPreaviso(double preaviso) {
        this.preaviso = preaviso;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public void setMontoTotal(double montoTotal) {
        this.montoTotal = montoTotal;
    }

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
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

    // Método toString
    @Override
    public String toString() {
        return "Liquidacion{"
                + "idLiquidacion=" + idLiquidacion
                + ", fechaFinContrato=" + fechaFinContrato
                + ", motivo='" + motivo 
                + ", montoCesantias=" + montoCesantias
                + ", montoAguinaldo=" + montoAguinaldo
                + ", montoVacaciones=" + montoVacaciones
                + ", responsabilidadPatronal=" + responsabilidadPatronal
                + ", preaviso=" + preaviso
                + ", montoTotal=" + montoTotal
                + ", estadoPago='" + estadoPago 
                + ", empleadoIdEmpleado=" + empleadoIdEmpleado
                + '}';

    }
}
