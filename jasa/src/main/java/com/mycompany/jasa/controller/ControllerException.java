package com.mycompany.jasa.controller;

/** Exception dengan pesan yang aman ditampilkan oleh View. */
public class ControllerException extends RuntimeException {

    public ControllerException(String message) {
        super(message);
    }

    public ControllerException(String message, Throwable cause) {
        super(message, cause);
    }
}
