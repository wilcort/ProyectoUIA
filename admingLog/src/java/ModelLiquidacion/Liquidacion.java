/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelLiquidacion;

import java.util.Date;

/**
 *
 * @author Dell
 */
public class Liquidacion {

    // Atributos
    private int idLiquidacion;
    private Date fechaLiquidacion;
    private String motivo;
    private double montoCesantias;
    private double montoAguinaldo;
    private double montoVacaciones;
    private double montoTotal;
    private String estadoPago; // Enum values: "Pendiente", "Pagado"
    private double preaviso;
    private int empleadoIdEmpleado;

    public Liquidacion() {
    }

    public Liquidacion(int idLiquidacion, Date fechaLiquidacion,
            String motivo, double montoCesantias,
            double montoAguinaldo, double montoVacaciones,
            double montoTotal, String estadoPago,
            double preaviso, int empleadoIdEmpleado) {

        this.idLiquidacion = idLiquidacion;
        this.fechaLiquidacion = fechaLiquidacion;
        this.motivo = motivo;
        this.montoCesantias = montoCesantias;
        this.montoAguinaldo = montoAguinaldo;
        this.montoVacaciones = montoVacaciones;
        this.montoTotal = montoTotal;
        this.estadoPago = estadoPago;
        this.preaviso = preaviso;
        this.empleadoIdEmpleado = empleadoIdEmpleado;
    }

    public int getIdLiquidacion() {
        return idLiquidacion;
    }

    public void setIdLiquidacion(int idLiquidacion) {
        this.idLiquidacion = idLiquidacion;
    }

    public Date getFechaLiquidacion() {
        return fechaLiquidacion;
    }

    public void setFechaLiquidacion(Date fechaLiquidacion) {
        this.fechaLiquidacion = fechaLiquidacion;
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

    public double getPreaviso() {
        return preaviso;
    }

    public void setPreaviso(double preaviso) {
        this.preaviso = preaviso;
    }

    public int getEmpleadoIdEmpleado() {
        return empleadoIdEmpleado;
    }

    public void setEmpleadoIdEmpleado(int empleadoIdEmpleado) {
        this.empleadoIdEmpleado = empleadoIdEmpleado;
    }

    // Método toString
    @Override
    public String toString() {
        return "Liquidacion{"
                + "idLiquidacion=" + idLiquidacion
                + ", fechaLiquidacion=" + fechaLiquidacion
                + ", motivo='" + motivo + '\''
                + ", montoCesantias=" + montoCesantias
                + ", montoAguinaldo=" + montoAguinaldo
                + ", montoVacaciones=" + montoVacaciones
                + ", montoTotal=" + montoTotal
                + ", estadoPago='" + estadoPago + '\''
                + ", preaviso=" + preaviso
                + ", empleadoIdEmpleado=" + empleadoIdEmpleado
                + '}';

    }
}
