package com.practica.controller.dao.services;

import com.practica.controller.dao.TransaccionDao;
import com.practica.controller.models.Transaccion;
import com.practica.controller.models.enumeration.UsoGenerador;
import com.practica.controller.tda.list.LinkedList;

public class TransaccionServices {
    TransaccionDao obj;

    public TransaccionServices() {
        this.obj = new TransaccionDao();
    }
    
    public LinkedList<Transaccion> listAll() throws Exception {
        return this.obj.listAll();
    }

    // Get & Set ======================================================
    
    public Transaccion getTransaccion() {
        return this.obj.getTransaccion();
    }

    public void setTransaccion(Transaccion transaccion) {
        this.obj.setTransaccion(transaccion);
    } 

    public void transaccionFromJson(String json) throws Exception {
        this.obj.transaccionFromJson(json);
    }


    // Métodos CRUD para el Resource ============================================================

    public Transaccion[] getAllObjects() {
        return this.obj.getArray();
    }

    public Transaccion save() throws Exception {
        return this.obj.save();
    }

    public Transaccion save(String json) throws Exception {
        this.obj.transaccionFromJson(json);
        return this.obj.save();
    }

    public Transaccion updateTransaccion() throws Exception {
        return this.obj.updateTransaccion(getTransaccion());
    }

    public Transaccion updateTransaccion(String json) throws Exception {
        this.transaccionFromJson(json);
        return updateTransaccion();
    }

    public Transaccion deleteTransaccion(Integer id) throws Exception {
        return this.obj.deleteTransaccion(id);
    }

    public Transaccion getTransaccionById(Integer id) throws Exception {
        return this.obj.getTransaccionById(id);
    }

    // UTILIDADES ======================================================================================

    public UsoGenerador[] enumerations() {
        return UsoGenerador.values();
    }

    public String[] attributes() {
        String[] attr = {"NumeroTransaccion","CantidadGeneradores","PrecioFinal"};
        return attr;
    }


    // ORDENACIÓN Y BÚSQUEDA

    public Transaccion[] sort(String attribute,Integer orden, Integer typeSort) throws Exception {
        return this.obj.sort(attribute, orden, typeSort);
    }
}