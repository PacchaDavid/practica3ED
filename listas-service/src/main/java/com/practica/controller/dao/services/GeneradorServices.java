package com.practica.controller.dao.services;

import com.practica.controller.dao.GeneradorDao;
import com.practica.controller.models.Generador;
import com.practica.controller.tda.list.LinkedList;

public class GeneradorServices {
    GeneradorDao obj;

    public GeneradorServices() {
        this.obj = new GeneradorDao();
    }
    
    public LinkedList<Generador> listAll() throws Exception {
        return this.obj.listAll();
    }

    // Get & Set ================================================================
    
    public Generador getGenerador() {
        return this.obj.getGenerador();
    }

    public void setGenerador(Generador generador) {
        this.obj.setGenerador(generador);
    } 

    public void generadorFromJson(String json) throws Exception {
        this.obj.generadorFromJson(json);
    }

    // Métodos CRUD ===============================================================

    public Generador[] getAllObjects() {
        return this.obj.getArray();
    }

    public Generador save() throws Exception {
        return obj.save();
    }

    public Generador save(String json) throws Exception {
        this.generadorFromJson(json);
        return save();
    }

    public Generador updateGenerador() throws Exception {
        return this.obj.updateGenerador();
    }

    public Generador updateGenerador(String json) throws Exception {
        this.generadorFromJson(json);
        return this.obj.updateGenerador();
    }

    public Generador deleteGenerador(Integer id) throws Exception {
        return this.obj.deleteGenerador(id);
    }

    public Generador getGeneradorById(Integer id) throws Exception {
        return this.obj.getGeneradorById(id);
    }

    // UTILIDADES ======================================================================================================
   
    public String[] attributes() {
        String[] attr = {"Modelo","Precio","Consumo","Potencia"};
        return attr;
    }

    // Ordenación y búsqueda =============================================================================================

    public Generador[] sort(String attribute,Integer orden, Integer typeSort) throws Exception {
        return this.obj.sort(attribute, orden, typeSort);
    }

    public Generador[] search(String attribute, String x) throws Exception {
        return this.obj.search(attribute, x);
    }

}