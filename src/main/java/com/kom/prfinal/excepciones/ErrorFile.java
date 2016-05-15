package com.kom.prfinal.excepciones;


@SuppressWarnings("serial")
public class ErrorFile extends ErroresAp {
    public ErrorFile(String msg) {
        super(msg, CodErroresAp.CODERR_FILE);
    }
}
