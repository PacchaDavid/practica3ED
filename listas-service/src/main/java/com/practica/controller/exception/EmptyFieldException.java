package com.practica.controller.exception;

public class EmptyFieldException extends Exception {
    public EmptyFieldException() {}

    public EmptyFieldException(String msg) {
        super(msg);
    }
}
