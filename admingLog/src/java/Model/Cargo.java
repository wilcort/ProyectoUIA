
package Model;


public class Cargo {
    
    private int id_cargo;
    private String nombreCargo;
    private boolean estado;

    public Cargo(int id_cargo, String nombreCargo, boolean estado) {
        this.id_cargo = id_cargo;
        this.nombreCargo = nombreCargo;
        this.estado = estado;
    }

    
    

    public int getId_cargo() {
        return id_cargo;
    }

    public void setId_cargo(int id_cargo) {
        this.id_cargo = id_cargo;
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
    
    
}