
package Model;

public class Cargo {
    private int idCargo;
    private String nombreCargo;
    private boolean estado;

    // Constructor por defecto
    public Cargo() {
    }

    // Constructor con parámetros
    public Cargo(int idCargo, String nombreCargo, boolean estado) {
        this.idCargo = idCargo;
        this.nombreCargo = nombreCargo;
        this.estado = estado;
    }

    // Métodos getter y setter para idCargo
    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }

    // Métodos getter y setter para nombreCargo
    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    // Métodos getter y setter para estado
    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}