/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ModelEmpleado;

import java.util.Date;
import java.util.Set;

/**
 *
 * @author Dell
 */
public class Vacaciones {

    private int idVacacion;
    private Date fechaSolicitud;
    private Date fechaInicio;
    private Date fechaFin;
    private int diasVacacionesTotal;
    private int diasVacacionesSolicitados;
    private Set<String> estadoSolicitud;
    private String comentario;
    private Date fechaAprobacion;
    private int idEmpleado;

    public Vacaciones() {
    }
    
    

    public Vacaciones(int idVacacion, Date fechaSolicitud, Date fechaInicio, Date fechaFin, int diasVacacionesTotal, int diasVacacionesSolicitados, Set<String> estadoSolicitud, String comentario, Date fechaAprobacion, int empleadoId) {
        this.idVacacion = idVacacion;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.diasVacacionesTotal = diasVacacionesTotal;
        this.diasVacacionesSolicitados = diasVacacionesSolicitados;
        this.estadoSolicitud = estadoSolicitud;
        this.comentario = comentario;
        this.fechaAprobacion = fechaAprobacion;
        this.idEmpleado = idEmpleado;
    }

    public int getIdVacacion() {
        return idVacacion;
    }

    public void setIdVacacion(int idVacacion) {
        this.idVacacion = idVacacion;
    }

    public Date getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(Date fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
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

    public int getDiasVacacionesTotal() {
        return diasVacacionesTotal;
    }

    public void setDiasVacacionesTotal(int diasVacacionesTotal) {
        this.diasVacacionesTotal = diasVacacionesTotal;
    }

    public int getDiasVacacionesSolicitados() {
        return diasVacacionesSolicitados;
    }

    public void setDiasVacacionesSolicitados(int diasVacacionesSolicitados) {
        this.diasVacacionesSolicitados = diasVacacionesSolicitados;
    }

    public Set<String> getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(Set<String> estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFechaAprobacion() {
        return fechaAprobacion;
    }

    public void setFechaAprobacion(Date fechaAprobacion) {
        this.fechaAprobacion = fechaAprobacion;
    }

    public int getidEmpleado() {
        return idEmpleado;
    }

    public void setEmpleadoId(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    
    
    
   
    @Override
    public String toString() {
        return "Vacaciones{"
                + "idVacacion=" + idVacacion
                + ", fechaSolicitud=" + fechaSolicitud
                + ", fechaInicio=" + fechaInicio
                + ", fechaFin=" + fechaFin
                + ", diasVacacionesTotal=" + diasVacacionesTotal
                +", diasVacacionesSolicitados=" + diasVacacionesSolicitados
                + ", estadoSolicitud=" + estadoSolicitud
                + ", comentario='" + comentario + '\''
                + ", fechaAprobacion=" + fechaAprobacion
                + ", idEmpleado=" + idEmpleado
                + '}';
    }
}
