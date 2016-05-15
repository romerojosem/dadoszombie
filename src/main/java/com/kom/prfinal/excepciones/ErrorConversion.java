package com.kom.prfinal.excepciones;


@SuppressWarnings("serial")
public class ErrorConversion extends ErroresAp {
    public ErrorConversion(String msg) {
        super(msg, CodErroresAp.CODERR_CONV);
    }
}
