package ModelIncapacidad;
import java.sql.*;
import Model.Colaborador;
import java.util.Date;

public class Incapacidades {

    private int idIncapacidad;
    private String motivo;
    private Date fechaInicio;
    private Date fechaFin;
    private int diasIncapacidad;
    private String estado;
    private int empleadoIdEmpleado;
    private Colaborador colaborador;
    private Blob documentoMaternidad;
    private Blob documentoEnfermedad;

    // Agrega los atributos de tipo IncapacidadMaternidad y IncapacidadEnfComun
    private IncapacidadMaternidad incapacidadMaternidad;
    private IncapacidadEnfComun incapacidadEnfComun;

    // Constructor completo
    public Incapacidades(int idIncapacidad, String motivo, Date fechaInicio, Date fechaFin, int diasIncapacidad,
            String estado, int empleadoIdEmpleado, Colaborador colaborador,
            IncapacidadMaternidad incapacidadMaternidad, IncapacidadEnfComun incapacidadEnfComun) {
        this.idIncapacidad = idIncapacidad;
        this.motivo = motivo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.diasIncapacidad = diasIncapacidad;
        this.estado = estado;
        this.empleadoIdEmpleado = empleadoIdEmpleado;
        this.colaborador = colaborador;
        this.incapacidadMaternidad = incapacidadMaternidad;
        this.incapacidadEnfComun = incapacidadEnfComun;
    }

    // Constructor vacío
    public Incapacidades() {
    }

    public Blob getDocumentoMaternidad() {
        return documentoMaternidad;
    }

    public void setDocumentoMaternidad(Blob documentoMaternidad) {
        this.documentoMaternidad = documentoMaternidad;
    }

    public Blob getDocumentoEnfermedad() {
        return documentoEnfermedad;
    }

    public void setDocumentoEnfermedad(Blob documentoEnfermedad) {
        this.documentoEnfermedad = documentoEnfermedad;
    }

    // Getters y setters
    public int getIdIncapacidad() {
        return idIncapacidad;
    }

    public void setIdIncapacidad(int idIncapacidad) {
        this.idIncapacidad = idIncapacidad;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public int getDiasIncapacidad() {
        return diasIncapacidad;
    }

    public void setDiasIncapacidad(int diasIncapacidad) {
        this.diasIncapacidad = diasIncapacidad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public IncapacidadMaternidad getIncapacidadMaternidad() {
        return incapacidadMaternidad;
    }

    public void setIncapacidadMaternidad(IncapacidadMaternidad incapacidadMaternidad) {
        this.incapacidadMaternidad = incapacidadMaternidad;
    }

    public IncapacidadEnfComun getIncapacidadEnfComun() {
        return incapacidadEnfComun;
    }

    public void setIncapacidadEnfComun(IncapacidadEnfComun incapacidadEnfComun) {
        this.incapacidadEnfComun = incapacidadEnfComun;
    }

    // Método toString para mostrar el objeto de forma legible
    @Override
    public String toString() {
        return "Incapacidades{"
                + "idIncapacidad=" + idIncapacidad
                + ", motivo='" + motivo + '\''
                + ", fechaInicio=" + fechaInicio
                + ", fechaFin=" + fechaFin
                + ", diasIncapacidad=" + diasIncapacidad
                + ", estado='" + estado + '\''
                + ", empleadoIdEmpleado=" + empleadoIdEmpleado
                + ", colaborador=" + colaborador
                + ", incapacidadMaternidad=" + incapacidadMaternidad
                + ", incapacidadEnfComun=" + incapacidadEnfComun
                + '}';
    }
}
