package com.practica.controller.dao.implement;

import com.practica.controller.tda.list.LinkedList;

public interface InterfazDao<T> {
    public T persist(T object) throws Exception;
    public LinkedList<T> listAll() throws Exception;
    public T get(Integer id) throws Exception;  
    public T merge(T object, Integer id) throws Exception;
    public T remove(Integer id) throws Exception;
}
