package com.zambacare.bean;

import java.math.BigDecimal;

public class PlanMembresiaBean {
    private final String codigo;
    private final String nombre;
    private final BigDecimal precio;
    private final String descripcion;

    public PlanMembresiaBean(String codigo, String nombre, BigDecimal precio, String descripcion) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public BigDecimal getPrecio() { return precio; }
    public String getDescripcion() { return descripcion; }
}
