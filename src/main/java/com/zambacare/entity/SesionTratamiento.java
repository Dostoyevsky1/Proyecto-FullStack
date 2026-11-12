package com.zambacare.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class SesionTratamiento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clienteId;
    private Long planTratamientoId;
    private LocalDate fecha;
    private String servicio;
    private String especialista;
    private Integer duracionMinutos;
    private String productosUtilizados;
    @Column(length = 900)
    private String resultado;
    @Column(length = 900)
    private String observaciones;
    @Column(length = 900)
    private String recomendaciones;
    private LocalDate proximoSeguimiento;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }
    public Long getPlanTratamientoId() { return planTratamientoId; }
    public void setPlanTratamientoId(Long planTratamientoId) { this.planTratamientoId = planTratamientoId; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getServicio() { return servicio; }
    public void setServicio(String servicio) { this.servicio = servicio; }
    public String getEspecialista() { return especialista; }
    public void setEspecialista(String especialista) { this.especialista = especialista; }
    public Integer getDuracionMinutos() { return duracionMinutos; }
    public void setDuracionMinutos(Integer duracionMinutos) { this.duracionMinutos = duracionMinutos; }
    public String getProductosUtilizados() { return productosUtilizados; }
    public void setProductosUtilizados(String productosUtilizados) { this.productosUtilizados = productosUtilizados; }
    public String getResultado() { return resultado; }
    public void setResultado(String resultado) { this.resultado = resultado; }
    public String getObservaciones() { return observaciones; }
    public void setObservaciones(String observaciones) { this.observaciones = observaciones; }
    public String getRecomendaciones() { return recomendaciones; }
    public void setRecomendaciones(String recomendaciones) { this.recomendaciones = recomendaciones; }
    public LocalDate getProximoSeguimiento() { return proximoSeguimiento; }
    public void setProximoSeguimiento(LocalDate proximoSeguimiento) { this.proximoSeguimiento = proximoSeguimiento; }
}
