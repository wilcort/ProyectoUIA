/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelIncapacidad;

import java.io.InputStream;

/**
 *
 * @author Dell
 */
public class IncapacidadEnfComun {
    private int idIncapacidad;
    private String detalle;
    private InputStream documento;


    // Constructor
    

    public IncapacidadEnfComun(int idIncapacidad, String detalle, InputStream documento) {
        this.idIncapacidad = idIncapacidad;
        this.detalle = detalle;
        this.documento = documento;
    }

    public IncapacidadEnfComun() {
    }

    public InputStream getDocumento() {
        return documento;
    }

    public void setDocumento(InputStream documento) {
        this.documento = documento;
    }
    
    // Getters y Setters
    public int getIdIncapacidad() {
        return idIncapacidad;
    }

    public void setIdIncapacidad(int idIncapacidad) {
        this.idIncapacidad = idIncapacidad;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

 
    // Método toString() para imprimir el objeto
    @Override
    public String toString() {
        return "IncapacidadEnfermedadComun{" +
                "idIncapacidad=" + idIncapacidad +
                ", detalle='" + detalle + '\'' +
                ", documento=" + documento + '\'' +
                '}';
    }
}
