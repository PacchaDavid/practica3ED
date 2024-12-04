package com.practica.controller.tda.list;

public class Node<E> {
    // Atributos
    private E info;
    private Node<E> next;

    public Node() {}
    
    public Node(E info) {
        this.info = info;
    }

    public Node(E info, Node<E> next) {
        this(info);
        this.next = next;
    }

    // Getters & Setters
    public E getInfo() {
        return info;
    }

    public void setInfo(E info) {
        this.info = info;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

}
