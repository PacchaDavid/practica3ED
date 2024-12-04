package com.practica.controller.dao;

import com.practica.controller.dao.implement.AdapterDao;
import com.practica.controller.dao.implement.JsonFileManager;
import com.practica.controller.exception.DuplicateDataException;
import com.practica.controller.exception.EmptyFieldException;
import com.practica.controller.models.Generador;
import com.practica.controller.tda.list.LinkedList;

public class GeneradorDao extends AdapterDao<Generador> {
    Generador generador;
    
    public GeneradorDao() {
        super(Generador.class);
    }

    // Get & Set ===================================================================================================
    public Generador getGenerador() {
        if(this.generador == null) {
            this.generador = new Generador();
        }
        return this.generador;
    }

    public void setGenerador(Generador generador) {
        this.generador = generador;
    }

    public Generador generadorFromJson(String generador) {
        this.generador = gson.fromJson(generador, Generador.class);
        return this.getGenerador();
    }
    
    // CRUD OPERATIONS ==========================================================================================

    public Generador save() throws Exception {
        if(!isThereAreAllFields()) throw new EmptyFieldException("Campos vacío en GeneradorDao.save()");
        if(existGeneradorModel()) throw new DuplicateDataException("Ya existe un Generador con el Modelo: " + this.getGenerador().getModelo());
        
        new CrudOperationManager().registerNewCrudOperation(this.getGenerador(), "SAVE");

        Integer currentId = JsonFileManager.readAndUpdateCurrentIdOf(className);
        this.getGenerador().setId(currentId);

        return persist(generador);
    }

    public Generador getGeneradorById(Integer id) throws Exception {
        return this.get(id);
    }

    public Generador updateGenerador() throws Exception {
        Integer id = this.getGenerador().getId();
        if(!isThereAreAllFields()) throw new EmptyFieldException("Campo vacío en GeneradorDao.updateGenerador()");
        if(existGeneradorModel(true)) throw new DuplicateDataException("Ya existe un Generador con el Modelo: " + this.getGenerador().getModelo());
        return merge(generador, id);
    }

    public Generador deleteGenerador(Integer id) throws Exception {
        new CrudOperationManager().registerNewCrudOperation(this.get(id), "DELETE");
        new TransaccionDao().cascade(id, className);
        return remove(id);
    }

    // ORDENACIÓN Y BÚSQUEDA ============================================================================

    public Generador[] sort(String attribute, Integer orden, Integer tipoOrdenacion) throws Exception
    {
        LinkedList<Generador> list = listAll();
        switch (tipoOrdenacion) { //quick, merge o shell
            case 0:
                list.quickSort(attribute, orden);
                break;
            case 1:
                list.mergeSort(attribute, orden);
                break;
            case 2:
                list.shellSort(attribute,orden);
                break;
            default:
                System.out.println("USUARIO BURRO!");
                break;
        }
        return list.toArray();
    }

    public Generador[] search(String attribute, String x) throws Exception {
        LinkedList<Generador> list = listAll();
        if(attribute.equalsIgnoreCase("Modelo"))
            return list.binaryLinearSearch(attribute, x).toArray(getGenerador().getClass());  
        else 
            return list.binaryLinearSearch(attribute, Float.parseFloat(x)).toArray(getGenerador().getClass());
    }

    // VALIDADORES ======================================================================================

    public Boolean isThereAreAllFields() {
        if(this.getGenerador().getConsumo() == null) return false;
        if(this.getGenerador().getModelo() == null) return false;
        if(this.getGenerador().getPotencia() == null) return false;
        if(this.getGenerador().getPrecio() == null) return false;
        return true;
    }


    public Boolean existGeneradorModel(Boolean forUpdate) {
        if(!forUpdate)
            return !listAll().binaryLinearSearch("modelo", this.getGenerador().getModelo()).isEmpty();

        LinkedList<Generador> generadores = listAll();
        try {
            generadores.delete(generadores.getIndexOf("id", this.getGenerador().getId()));
        } catch (Exception e) {
            return false;
        }
        return !generadores.binaryLinearSearch("modelo", this.getGenerador().getModelo()).isEmpty();
    }

    public Boolean existGeneradorModel() {
        return existGeneradorModel(false);
    }


}
