package com.practica.controller.tda.queue;

import java.lang.reflect.Array;

import com.practica.controller.tda.list.Node;

public class Queue<E> {
    private Node<E> first;
    private Node<E> last;
    private Integer size;

    public Queue() {
        this.last = null;
        this.first = this.last;
        this.size = 0;
    }

    public Node<E> getFirst() {
        return first;
    }

    public void setFirst(Node<E> first) {
        this.first = first;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Node<E> getLast() {
        return last;
    }

    public void setLast(Node<E> last) {
        this.last = last;
    }
    
    public Boolean isEmpty() {
        return this.first == null || this.size == 0;
    }

    public void queued(E info) {    
        if(isEmpty()) {
            this.first = new Node<>(info);
            this.size++;
        } else {
            if(this.last == null) {
                this.first.setNext(new Node<>(info));
                this.last = this.first.getNext();
                this.size++;
            } else {
                this.last.setNext(new Node<>(info));
                this.last = this.last.getNext();
                this.size++;
            }
        }
    }

    public E dequeued() throws Exception {
        if(isEmpty()) {
            throw new Exception("Queue is empty");
        } else {
            Node<E> retrNode = this.first;
            this.first = this.first.getNext();
            retrNode.setNext(null);
            this.size--;
            return retrNode.getInfo();
        }
    }

    public E[] toArray() throws Exception {
        if(isEmpty()) throw new Exception("Cannot Queue convert to Array because Queue is empty");
        Class<?> clazz = this.first.getInfo().getClass();
        @SuppressWarnings("unchecked")
        E[] arrayObjects = (E[])Array.newInstance(clazz, this.size);
        Node<E> aux = this.first;
        int count = 0;
        while(count < this.size) {
            arrayObjects[count] = aux.getInfo();
            aux = aux.getNext();
            count++;
        }
        return arrayObjects;
    }

    public void reset() {
        this.first = null;
        this.last = null;
        this.size = 0;
    }

    public void fromArrayToQueue(E[] objects) {
        for(int i = 0; i < objects.length; i++) this.queued(objects[i]);
    }
}

// La huella de tu campo hecho raices Melinaaaaaaa, y vuelven a reír tus ojos tristes melinaaaa ay larararararararayrararaaaaaaa aaaa
// has vuelto melina a a a, alza tus manos hacia Dios que el escuche tu voz la la lai la la la
// joder es cine

