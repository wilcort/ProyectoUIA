package ModelIncapacidad;

import java.io.InputStream;
import java.sql.Blob;

public class IncapacidadMaternidad {

    private int idIncapacidad;
    private String detalle;
    private int semanasDeGestacion;
    private InputStream documento;

    public IncapacidadMaternidad(int idIncapacidad, String detalle, int semanasDeGestacion, InputStream documento) {
        this.idIncapacidad = idIncapacidad;
        this.detalle = detalle;
        this.semanasDeGestacion = semanasDeGestacion;
        this.documento = documento;
    }

   

    public IncapacidadMaternidad() {
    }

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

    public int getSemanasDeGestacion() {
        return semanasDeGestacion;
    }

    public void setSemanasDeGestacion(int semanasDeGestacion) {
        this.semanasDeGestacion = semanasDeGestacion;
    }

    public InputStream getDocumento() {
        return documento;
    }

    public void setDocumento(InputStream documento) {
        this.documento = documento;
    }

    

    // Método toString() para imprimir el objeto
    @Override
    public String toString() {
        return "IncapacidadMaternidad{"
                + ", idIncapacidad=" + idIncapacidad
                + ", detalle=" + detalle
                + ", semanasDeGestacion=" + semanasDeGestacion
                + ", documento" + documento
                + '}';
    }
}
