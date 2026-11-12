package com.zambacare.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class EvolucionCapilar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clienteId;
    private LocalDate fecha;
    private String imagenAntes;
    private String imagenDespues;
    private Integer hidratacion;
    private Integer definicion;
    private Integer frizz;
    private Integer elasticidad;
    @Column(length = 900)
    private String observaciones;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getImagenAntes() { return imagenAntes; }
    public void setImagenAntes(String imagenAntes) { this.imagenAntes = imagenAntes; }
    public String getImagenDespues() { return imagenDespues; }
    public void setImagenDespues(String imagenDespues) { this.imagenDespues = imagenDespues; }
    public Integer getHidratacion() { return hidratacion; }
    public void setHidratacion(Integer hidratacion) { this.hidratacion = hidratacion; }
    public Integer getDefinicion() { return definicion; }
    public void setDefinicion(Integer definicion) { this.definicion = definicion; }
    public Integer getFrizz() { return frizz; }
    public void setFrizz(Integer frizz) { this.frizz = frizz; }
    public Integer getElasticidad() { return elasticidad; }
    public void setElasticidad(Integer elasticidad) { this.elasticidad = elasticidad; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
}
