/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Dell
 */
import ModelEmpleado.Horarios;
import java.math.BigDecimal;
import java.util.Date;

public class Colaborador {

    private int id_Empleado;
    private int num_documento;
    private String nombre;
    private String apellido_1;
    private String apellido_2;
    private int telefono;
    private String direccion;
    private Date fecha_contratacion;
    private Date fecha_salida;
    private BigDecimal salario_base;
    private Usuario usuario;
    private Cargo cargo; // Nuevo campo para el id_cargo
    private Horarios horarios;

    //constructor
    public Colaborador(int id_Empleado, int num_documento,
            String nombre, String apellido_1, String apellido_2,
            int telefono, String direccion, Date fecha_contratacion,
            Date fecha_salida, BigDecimal salario_base, Usuario usuario,
            Cargo cargo, Horarios horarios) {

        this.id_Empleado = id_Empleado;
        this.num_documento = num_documento;
        this.nombre = nombre;
        this.apellido_1 = apellido_1;
        this.apellido_2 = apellido_2;
        this.telefono = telefono;
        this.direccion = direccion;
        this.fecha_contratacion = fecha_contratacion;
        this.fecha_salida = fecha_salida;
        this.salario_base = salario_base;
        this.usuario = usuario;
        this.cargo = cargo;
        this.horarios = horarios;

    }

    public Colaborador() {
    }
    
    

    public int getId_Empleado() {
        return id_Empleado;
    }

    public void setId_Empleado(int id_Empleado) {
        this.id_Empleado = id_Empleado;
    }

    public int getNum_documento() {
        return num_documento;
    }

    public void setNum_documento(int num_documento) {
        this.num_documento = num_documento;
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

    public Date getFecha_contratacion() {
        return fecha_contratacion;
    }

    public void setFecha_contratacion(Date fecha_contratacion) {
        this.fecha_contratacion = fecha_contratacion;
    }

    public Date getFecha_salida() {
        return fecha_salida;
    }

    public void setFecha_salida(Date fecha_salida) {
        this.fecha_salida = fecha_salida;
    }

    public BigDecimal getSalario_base() {
        return salario_base;
    }

    public void setSalario_base(BigDecimal salario_base) {
        this.salario_base = salario_base;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }

    public Horarios getHorarios() {
        return horarios;
    }

    public void setHorarios(Horarios horarios) {
        this.horarios = horarios;
    }

}

   