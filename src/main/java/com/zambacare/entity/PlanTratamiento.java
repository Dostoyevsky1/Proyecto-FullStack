package com.zambacare.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class PlanTratamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clienteId;
    private String nombre;
    @Column(length = 800)
    private String objetivo;
    private Integer duracionEstimadaMinutos;
    private Integer totalSesiones;
    private Integer sesionesCompletadas;
    private LocalDate fechaInicio;
    private LocalDate proximaSesion;
    private String estado;

    public int getPorcentaje() {
        if (totalSesiones == null || totalSesiones == 0 || sesionesCompletadas == null) return 0;
        return Math.min(100, Math.round(sesionesCompletadas * 100f / totalSesiones));
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getObjetivo() { return objetivo; }
    public void setObjetivo(String objetivo) { this.objetivo = objetivo; }
    public Integer getDuracionEstimadaMinutos() { return duracionEstimadaMinutos; }
    public void setDuracionEstimadaMinutos(Integer duracionEstimadaMinutos) { this.duracionEstimadaMinutos = duracionEstimadaMinutos; }
    public Integer getTotalSesiones() { return totalSesiones; }
    public void setTotalSesiones(Integer totalSesiones) { this.totalSesiones = totalSesiones; }
    public Integer getSesionesCompletadas() { return sesionesCompletadas; }
    public void setSesionesCompletadas(Integer sesionesCompletadas) { this.sesionesCompletadas = sesionesCompletadas; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getProximaSesion() { return proximaSesion; }
    public void setProximaSesion(LocalDate proximaSesion) { this.proximaSesion = proximaSesion; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}
