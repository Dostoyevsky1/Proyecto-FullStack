package com.zambacare.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

@Entity
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 80)
    private String nombres;

    @NotBlank
    @Size(max = 80)
    private String apellidos;

    @Size(max = 12)
    private String dni;

    private LocalDate fechaNacimiento;

    @Size(max = 20)
    private String telefono;

    @Email
    @Size(max = 120)
    private String correo;

    @Size(max = 180)
    private String direccion;

    @Size(max = 10)
    private String tipoCabello;

    @Size(max = 20)
    private String textura;

    @Size(max = 20)
    private String porosidad;

    @Size(max = 20)
    private String densidad;

    @Size(max = 20)
    private String grosor;

    @Size(max = 120)
    private String estadoCapilar;

    @Size(max = 300)
    private String alergias;

    @Size(max = 600)
    private String observaciones;

    private LocalDate fechaRegistro;

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
    }

    public String getIniciales() {
        String primera = nombres == null || nombres.isBlank() ? "" : nombres.substring(0, 1);
        String segunda = apellidos == null || apellidos.isBlank() ? "" : apellidos.substring(0, 1);
        return (primera + segunda).toUpperCase();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public String getDni() { return dni; }
    public void setDni(String dni) { this.dni = dni; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
    public String getTipoCabello() { return tipoCabello; }
    public void setTipoCabello(String tipoCabello) { this.tipoCabello = tipoCabello; }
    public String getTextura() { return textura; }
    public void setTextura(String textura) { this.textura = textura; }
    public String getPorosidad() { return porosidad; }
    public void setPorosidad(String porosidad) { this.porosidad = porosidad; }
    public String getDensidad() { return densidad; }
    public void setDensidad(String densidad) { this.densidad = densidad; }
    public String getGrosor() { return grosor; }
    public void setGrosor(String grosor) { this.grosor = grosor; }
    public String getEstadoCapilar() { return estadoCapilar; }
    public void setEstadoCapilar(String estadoCapilar) { this.estadoCapilar = estadoCapilar; }
    public String getAlergias() { return alergias; }
    public void setAlergias(String alergias) { this.alergias = alergias; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) { this.fechaRegistro = fechaRegistro; }
}
