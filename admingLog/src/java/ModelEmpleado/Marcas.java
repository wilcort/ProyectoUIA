/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelEmpleado;

import java.util.Date;
import java.sql.Time;

/**
 *
 * @author Dell
 */
public class Marcas {
    
    private int idMarca;
    private Date fechaMarca;
    private Time marcaEntrada;
    private Time marcaSalida;
    private Time marcaSalidaAlmuerzo;
    private Time marcaEntradaAlmuerzo;
    private int idEmpleado;
    private Double horasDia;
    private Double horasTabajadasDia;
    
    public Marcas() {
    // Constructor vacío
}

    public Marcas(int idMarca, Date fechaMarca, Time marcaEntrada, Time marcaSalida, Time marcaSalidaAlmuerzo, Time marcaEntradaAlmuerzo, int idEmpleado, Double horasDia, Double horasTabajadasDia) {
        this.idMarca = idMarca;
        this.fechaMarca = fechaMarca;
        this.marcaEntrada = marcaEntrada;
        this.marcaSalida = marcaSalida;
        this.marcaSalidaAlmuerzo = marcaSalidaAlmuerzo;
        this.marcaEntradaAlmuerzo = marcaEntradaAlmuerzo;
        this.idEmpleado = idEmpleado;
        this.horasDia = horasDia;
        this.horasTabajadasDia = horasTabajadasDia;
    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public Date getFechaMarca() {
        return fechaMarca;
    }

    public void setFechaMarca(Date fechaMarca) {
        this.fechaMarca = fechaMarca;
    }

    public Time getMarcaEntrada() {
        return marcaEntrada;
    }

    public void setMarcaEntrada(Time marcaEntrada) {
        this.marcaEntrada = marcaEntrada;
    }

    public Time getMarcaSalida() {
        return marcaSalida;
    }

    public void setMarcaSalida(Time marcaSalida) {
        this.marcaSalida = marcaSalida;
    }

    public Time getMarcaSalidaAlmuerzo() {
        return marcaSalidaAlmuerzo;
    }

    public void setMarcaSalidaAlmuerzo(Time marcaSalidaAlmuerzo) {
        this.marcaSalidaAlmuerzo = marcaSalidaAlmuerzo;
    }

    public Time getMarcaEntradaAlmuerzo() {
        return marcaEntradaAlmuerzo;
    }

    public void setMarcaEntradaAlmuerzo(Time marcaEntradaAlmuerzo) {
        this.marcaEntradaAlmuerzo = marcaEntradaAlmuerzo;
    }

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public Double getHorasDia() {
        return horasDia;
    }

    public void setHorasDia(Double horasDia) {
        this.horasDia = horasDia;
    }

    public Double getHorasTabajadasDia() {
        return horasTabajadasDia;
    }

    public void setHorasTabajadasDia(Double horasTabajadasDia) {
        this.horasTabajadasDia = horasTabajadasDia;
    }

    
    @Override
    public String toString() {
        return "Marcas{"
                + "idMarca=" + idMarca
                + ", fechaMarca=" + fechaMarca
                + ", marcaEntrada=" + marcaEntrada
                + ", marcaSalida=" + marcaSalida
                + ", marcaEntradaAlmuerzo=" + marcaEntradaAlmuerzo
                + ", marcaSalidaAlmuerzo=" + marcaSalidaAlmuerzo
                + ", horasDia=" + horasDia
                + ", horasTabajadasDia=" + horasTabajadasDia 
                + '}';
    }
}