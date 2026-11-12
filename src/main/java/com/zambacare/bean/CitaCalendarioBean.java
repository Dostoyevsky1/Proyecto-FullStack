package com.zambacare.bean;

import com.zambacare.entity.Cita;
import java.time.LocalDate;
import java.time.LocalTime;

public class CitaCalendarioBean {
    private final Cita cita;
    private final String clienteNombre;
    private final int dia;
    private final int posicion;
    private final int altura;
    private final int izquierda;
    private final int ancho;

    public CitaCalendarioBean(Cita cita, String clienteNombre, int dia, int posicion, int altura, int izquierda, int ancho) {
        this.cita = cita;
        this.clienteNombre = clienteNombre;
        this.dia = dia;
        this.posicion = posicion;
        this.altura = altura;
        this.izquierda = izquierda;
        this.ancho = ancho;
    }

    public Long getId() { return cita.getId(); }
    public Long getClienteId() { return cita.getClienteId(); }
    public String getClienteNombre() { return clienteNombre; }
    public LocalDate getFecha() { return cita.getFecha(); }
    public LocalTime getHoraInicio() { return cita.getHoraInicio(); }
    public LocalTime getHoraFin() { return cita.getHoraFin(); }
    public String getServicio() { return cita.getServicio(); }
    public String getEspecialista() { return cita.getEspecialista(); }
    public String getEstado() { return cita.getEstado(); }
    public String getObservaciones() { return cita.getObservaciones(); }
    public int getDia() { return dia; }
    public int getPosicion() { return posicion; }
    public int getAltura() { return altura; }
    public int getIzquierda() { return izquierda; }
    public int getAncho() { return ancho; }
}
