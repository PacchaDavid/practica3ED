package com.practica.controller.dao.services;

import java.util.HashMap;

import com.practica.controller.dao.FamiliaDao;
import com.practica.controller.models.Familia;
import com.practica.controller.models.enumeration.Canton;
import com.practica.controller.models.enumeration.NivelSocioeconomico;
import com.practica.controller.tda.list.LinkedList;

public class FamiliaServices {
    FamiliaDao obj;

    public FamiliaServices() {
        this.obj = new FamiliaDao();
    }

    // GET & SET ======================================================================

    public Familia getFamilia() {
        return this.obj.getFamilia();
    }

    public void setFamilia(Familia familia) {
        this.obj.setFamilia(familia);
    } 

    public Familia familiaFromJson(String json) {
        return this.obj.familiaFromJson(json);
    }

    // Métodos CRUD ==========================================================

    public LinkedList<Familia> listAll() throws Exception {
        return this.obj.listAll();
    }

    public Familia[] getAllObjects() {
        return this.obj.getArray();
    }

    public Familia save() throws Exception {
        return this.obj.save();
    }

    public Familia save(String json) throws Exception {
        this.familiaFromJson(json);
        return this.save();
    }

    public Familia updateFamilia(String json) throws Exception {
        this.familiaFromJson(json);
        return this.obj.updateFamilia();
    }

    public Familia deleteFamilia(Integer id) throws Exception {
        return this.obj.deleteFamilia(id);
    }

    public Familia getFamiliaById(Integer id) throws Exception {
        return this.obj.getFamiliaById(id);
    }

    // ENUMERACIONES ================================================================

    public NivelSocioeconomico[] nivelesSocioeconomicos() {
        return NivelSocioeconomico.values();
    }

    public Canton[] cantones() {
        return Canton.values();
    }

    public HashMap<String, Object> enumeraciones() {
        HashMap<String,Object> enums = new HashMap<>();
        enums.put("cantones", cantones());
        enums.put("nivelesSocioeconomicos", nivelesSocioeconomicos());
        return enums;
    } 

    public String[] attributes() {
        String[] attr = {"CodigoFamilia","IngresosMensuales","NroIntegrantes","ApellidosRepresentantes"};
        return attr;
    }

    /// ORDENACIÓN Y BÚSQUEDA =============================================================================
    
    public Familia[] sort(String attribute, Integer orden, Integer typeSort) throws Exception {
        return this.obj.sort(attribute, orden, typeSort);
    }

    public Familia[] search(String attribute, String x) throws Exception {
        return this.obj.search(attribute, x);
    }





    
}