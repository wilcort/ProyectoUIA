/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Dell
 */
import java.math.BigDecimal;
import java.util.Date;

public class Colaborador {

    private int id_Empleado;
    private int num_Documento;
    private String nombre;
    private String apellido_1;
    private String apellido_2;
    private int telefono;
    private String direccion;
    private Date fecha_Contratacion;
    private BigDecimal salario_Base;
    private Usuario usuario;

    // Constructor
    public Colaborador(int id_Empleado, int num_Documento, String nombre, String apellido_1, String apellido_2, int telefono, String direccion, Date fecha_Contratacion, BigDecimal salario_Base, Usuario usuario) {
        this.id_Empleado = id_Empleado;
        this.num_Documento = num_Documento;
        this.nombre = nombre;
        this.apellido_1 = apellido_1;
        this.apellido_2 = apellido_2;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fecha_Contratacion = fecha_Contratacion;
        this.salario_Base = salario_Base;
        this.usuario = usuario;
    }

    public int getId_Empleado() {
        return id_Empleado;
    }

    public void setId_Empleado(int id_Empleado) {
        this.id_Empleado = id_Empleado;
    }

    public int getNum_Documento() {
        return num_Documento;
    }

    public void setNum_Documento(int num_Documento) {
        this.num_Documento = num_Documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_1() {
        return apellido_1;
    }

    public void setApellido_1(String apellido_1) {
        this.apellido_1 = apellido_1;
    }

    public String getApellido_2() {
        return apellido_2;
    }

    public void setApellido_2(String apellido_2) {
        this.apellido_2 = apellido_2;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getFecha_Contratacion() {
        return fecha_Contratacion;
    }

    public void setFecha_Contratacion(Date fechaContratacion) {
        this.fecha_Contratacion = fechaContratacion;
    }

    public BigDecimal getSalario_Base() {
        return salario_Base;
    }

    public void setSalario_Base(BigDecimal salario_Base) {
        this.salario_Base = salario_Base;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
