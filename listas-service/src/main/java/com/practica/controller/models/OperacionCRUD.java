package com.practica.controller.models;

public class OperacionCRUD {
    private Integer id;
    private Integer idObjeto;
    private String nombreClase;
    private String operacion;
    private String fecha;
    private String hora;


    public OperacionCRUD() {}

    public OperacionCRUD buildOperacionCRUD(Integer idObjeto, String nombreClase, String operacion, String fecha, String hora) {
        this.idObjeto = idObjeto;
        this.nombreClase = nombreClase;
        this.operacion = operacion;
        this.fecha = fecha;
        this.hora = hora;
        return this;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombreClase() {
        return nombreClase;
    }

    public void setNombreClase(String nombreClase) {
        this.nombreClase = nombreClase;
    }

    public String getOperacion() {
        return operacion;
    }

    public void setOperacion(String operacion) {
        this.operacion = operacion;
    }

    public Integer getObjectId() {
        return idObjeto;
    }

    public void setObjectId(Integer idObjeto) {
        this.idObjeto = idObjeto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
