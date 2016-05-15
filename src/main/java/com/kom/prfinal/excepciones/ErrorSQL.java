package com.kom.prfinal.excepciones;



@SuppressWarnings("serial")
public class ErrorSQL extends ErroresAp {
    public ErrorSQL(String msg) {
        super(msg, CodErroresAp.CODERR_SQL);
    }
}
