
package Model;

public class Cargo {

    private int idCargo;
    private String nombreCargo;
    private boolean estado;
    private double salario;

    // Constructor por defecto
    public Cargo() {
    }

    // Constructor con parámetros

    public Cargo(int idCargo, String nombreCargo, boolean estado, double salario) {
        this.idCargo = idCargo;
        this.nombreCargo = nombreCargo;
        this.estado = estado;
        this.salario = salario;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }
    
    
}