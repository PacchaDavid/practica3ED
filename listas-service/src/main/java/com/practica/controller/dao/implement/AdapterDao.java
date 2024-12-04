package com.practica.controller.dao.implement;

import com.practica.controller.exception.IdNotFoundException;
import com.practica.controller.tda.list.LinkedList;

import java.lang.reflect.Array;
import java.lang.reflect.Type;

import com.google.gson.Gson;

@SuppressWarnings("unchecked")
public abstract class AdapterDao<T> implements InterfazDao<T> {
    
    private Class<?> modelClass;
    protected String jsonFileName;
    protected String className;
    protected Gson gson = new Gson(); 

    public AdapterDao() {}

    public AdapterDao(Class<?> modelClass) {
        this.modelClass = modelClass;
        this.className = modelClass.getSimpleName();
        this.jsonFileName =  className + ".json";
    }

    // Método para Obtener el array de objetos =====================================================

    public T[] getArray() {
        try {
            Type arrayType = Array.newInstance(modelClass, 0).getClass();
            T[] objs = gson.fromJson(JsonFileManager.readJsonFile(jsonFileName), arrayType);
            if(objs != null) 
                return objs;
            T[] array = (T[]) Array.newInstance(modelClass, 0);
            return array;
        } catch (Exception e) {
            System.out.println("getArray() dice: " + e.getMessage());
            T[] array = (T[]) Array.newInstance(modelClass, 0);
            return array;
        }
    }

    // Métodos de interfaz Dao ===================================================================

    public LinkedList<T> listAll() {
        return new LinkedList<T>().fromArrayToLinkedList(getArray());
    }

    /* Se asume que los modelos tienen un atributo id para poder hacer operaciones de CRUD */

    public T persist(T object) throws Exception {
        LinkedList<T> objects = listAll();
        objects.add(object);
        JsonFileManager.writeObjectInJsonFile(objects.toArray(),jsonFileName);
        return object;
    }

    public T get(Integer id) throws Exception {
        LinkedList<T> list = listAll();
        try {
            return list.binarySearch("id", id);
        } catch (Exception e) {
            throw new IdNotFoundException("Error en " + className + "Dao(): No existe " + className + " con el id: " + id);
        }
    }

    public T merge(T object, Integer id) throws Exception {
        LinkedList<T> list = listAll();
        list.update(object, list.getIndexOf("id", id));
        JsonFileManager.writeObjectInJsonFile(list.toArray(),jsonFileName);
        return object;
    }

    public T remove(Integer id) throws Exception {
        LinkedList<T> list = listAll();
        Integer index = list.getIndexOf("id", id);
        T object = list.get(index);
        list.delete(index);
        JsonFileManager.writeObjectInJsonFile(list.toArray(modelClass), jsonFileName);
        return object;
    } 

}
