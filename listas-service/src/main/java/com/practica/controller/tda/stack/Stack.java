package com.practica.controller.tda.stack;

import java.lang.reflect.Array;

import com.practica.controller.tda.list.Node;

public class Stack<E> {
    private Node<E> top;
    private Integer size;

    public Stack() {
        this.top = null;
        this.size = 0;
    }

    public Node<E> gettop() {
        return top;
    }
    public void settop(Node<E> top) {
        this.top = top;
    }
    public Integer getSize() {
        return size;
    }
    public void setSize(Integer size) {
        this.size = size;
    }

    public Boolean isEmpty() {
        return this.top == null || this.size == 0;
    }

    public void push(E info) {
        if(isEmpty()) {
            this.top = new Node<>(info);
            this.size++;
        } else {
            Node<E> newNode = new Node<>(info,this.top);
            this.top = newNode;
            this.size++;
        }
    }

    public E pop() throws Exception { 
        if(isEmpty()) {
            throw new Exception("Stack is empty");
        } else {
            Node<E> returnNode = this.top;
            this.top = this.top.getNext();
            returnNode.setNext(null);
            this.size--;
            return returnNode.getInfo();
        }
    } 

    public E[] toArray() throws Exception {
        if(isEmpty()) throw new Exception("Error, Stack is empty");
        Class<?> _class = this.top.getInfo().getClass();
        @SuppressWarnings("unchecked")
        E[] arrayObjects = (E[]) Array.newInstance(_class, this.size);
        Node<E> aux = this.top;
        Integer i = 0; 
        while (i < this.size) {
            arrayObjects[i] = aux.getInfo();
            aux = aux.getNext();
            i++;
        }
        return arrayObjects;
    }

    public void reset() {
        this.top = null;
        this.size = 0;
    }

    public void fromArrayToStack(E[] array) {
        reset();
        for(int i = 0; i < array.length; i++) this.push(array[i]);
    }
}
