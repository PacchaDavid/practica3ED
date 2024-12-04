package com.practica.controller.exception;

public class DuplicateDataException extends Exception {
    public DuplicateDataException() {}
    public DuplicateDataException(String msg) {
        super(msg);
    }
}
