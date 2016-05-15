package com.kom.prfinal.excepciones;


@SuppressWarnings("serial")
public class ErrorES extends ErroresAp {
    public ErrorES(String msg) {
        super(msg, CodErroresAp.CODERR_ES);
    }
}
