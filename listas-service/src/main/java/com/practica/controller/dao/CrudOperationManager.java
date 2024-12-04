package com.practica.controller.dao;

import java.lang.reflect.Method;

import java.time.LocalDateTime;

import com.practica.controller.dao.implement.AdapterDao;
import com.practica.controller.dao.implement.JsonFileManager;
import com.practica.controller.models.OperacionCRUD;
import com.practica.controller.tda.list.LinkedList;

public class CrudOperationManager extends AdapterDao<OperacionCRUD> {
    OperacionCRUD operacionCRUD;
    
    public CrudOperationManager() {
        super(OperacionCRUD.class);
    }

    // Get & Set ============================================================================
    
    public OperacionCRUD getOperacionCRUD() {
        if(this.operacionCRUD == null) {
            this.operacionCRUD = new OperacionCRUD();
        }
        return this.operacionCRUD;
    }

    public OperacionCRUD[] getAllObjects() throws Exception {
        return listAll().toArray(this.getOperacionCRUD().getClass());
    }

    public void setCRUDOperation(Object object, String operationMethod) throws Exception {
        final String modelClass = object.getClass().getSimpleName(); 
        final String date = currentDate();
        final String time = currentTime();
        Method method = object.getClass().getMethod("getId");
        final Integer objectId = (Integer)method.invoke(object);
        this.operacionCRUD = new OperacionCRUD().buildOperacionCRUD(objectId, modelClass, operationMethod, date, time);
    }

    public String currentDate() {
        final LocalDateTime dateTime = LocalDateTime.now();

        final int day = dateTime.getDayOfMonth();
        final int month = dateTime.getMonthValue();
        final int year = dateTime.getYear();

        // Build Date  
        StringBuilder date = new StringBuilder();
        date.append(day).append("-").append(month).append("-").append(year);
        return date.toString();
    }

    public String currentTime() {
        final LocalDateTime dateTime = LocalDateTime.now();
        final int hour = dateTime.getHour();
        final int minute = dateTime.getMinute();
        final int second = dateTime.getSecond();

         // Build Time
         StringBuilder date = new StringBuilder();
         date.append(hour).append(":").append(minute).append(":").append(second);
 
         return date.toString();
    }

    // Utiliza dos métodos para guardar una operación crud en formato json
    public void registerNewCrudOperation(Object object, String operationMethod) throws Exception {
        setCRUDOperation(object, operationMethod);
        save();
    }
    
    public OperacionCRUD save() throws Exception {
        Integer currentId = JsonFileManager.readAndUpdateCurrentIdOf(className);
        this.getOperacionCRUD().setId(currentId);
        return persist(operacionCRUD);
    }

    public OperacionCRUD getOperationCrudById(Integer id) throws Exception {
        return this.get(id);
    }

    public OperacionCRUD deleteOperationCrud(Integer id) throws Exception {
        return remove(id);
    }

    public String[] attributes() {
        String[] attr = {"NombreClase","Operacion","Fecha","Hora"};
        return attr;
    }



     public OperacionCRUD[] sort(String attribute, Integer orden, Integer tipoOrdenacion) throws Exception
    {
        LinkedList<OperacionCRUD> list = listAll();
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
        return list.toArray(this.getOperacionCRUD().getClass());
    }


    public OperacionCRUD[] search(String attribute, String x) throws Exception {
        LinkedList<OperacionCRUD> list = listAll();
        return list.binaryLinearSearch(attribute, x).toArray(this.getOperacionCRUD().getClass());  
    }
}
