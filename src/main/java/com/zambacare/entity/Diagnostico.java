package com.zambacare.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Diagnostico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clienteId;
    private LocalDate fecha;
    private String patronCapilar;
    private String porosidad;
    private String densidad;
    private String grosor;
    private String problemasIdentificados;
    private String habitos;
    @Column(length = 1200)
    private String observaciones;
    @Column(length = 1200)
    private String recomendacion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getPatronCapilar() { return patronCapilar; }
    public void setPatronCapilar(String patronCapilar) { this.patronCapilar = patronCapilar; }
    public String getPorosidad() { return porosidad; }
    public void setPorosidad(String porosidad) { this.porosidad = porosidad; }
    public String getDensidad() { return densidad; }
    public void setDensidad(String densidad) { this.densidad = densidad; }
    public String getGrosor() { return grosor; }
    public void setGrosor(String grosor) { this.grosor = grosor; }
    public String getProblemasIdentificados() { return problemasIdentificados; }
    public void setProblemasIdentificados(String problemasIdentificados) { this.problemasIdentificados = problemasIdentificados; }
    public String getHabitos() { return habitos; }
    public void setHabitos(String habitos) { this.habitos = habitos; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public String getRecomendacion() { return recomendacion; }
    public void setRecomendacion(String recomendacion) { this.recomendacion = recomendacion; }
}
