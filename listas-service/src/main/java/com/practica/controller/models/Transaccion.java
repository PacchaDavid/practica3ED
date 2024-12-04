package com.practica.controller.models;

import com.practica.controller.models.enumeration.UsoGenerador;

public class Transaccion {
    private Integer id;
    private String numeroTransaccion;
    private Integer familiaId;
    private Integer generadorId;
    private UsoGenerador usoGenerador;
    private Integer cantidadGeneradores;
    private Float precioFinal;

    public Transaccion() {}

    public Integer getFamiliaId() {
        return familiaId;
    }  
    
    public void setFamiliaId(Integer familiaId) {
        this.familiaId = familiaId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGeneradorId() {
        return generadorId;
    }

    public void setGeneradorId(Integer generadorId) {
        this.generadorId = generadorId;
    }

    public Integer getCantidadGeneradores() {
        return cantidadGeneradores;
    }

    public void setCantidadGeneradores(Integer cantidadGeneradores) {
        this.cantidadGeneradores = cantidadGeneradores;
    }
    
    public Float getPrecioFinal() {
        return precioFinal;
    }

    public void setPrecioFinal(Float precioFinal) {
        this.precioFinal = precioFinal;
    }

    public UsoGenerador getUsoGenerador() {
        return usoGenerador;
    }

    public void setUsoGenerador(UsoGenerador usoGenerador) {
        this.usoGenerador = usoGenerador;
    }

    public String getNumeroTransaccion() {
        return numeroTransaccion;
    }

    public void setNumeroTransaccion(String numeroTransaccion) {
        this.numeroTransaccion = numeroTransaccion;
    }
}
