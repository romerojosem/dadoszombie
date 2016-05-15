package com.kom.prfinal.excepciones;



@SuppressWarnings("serial")
public class ErrorMongo extends ErroresAp {
	
    public ErrorMongo(String msg) {
    	super(msg, CodErroresAp.CODERR_MONGO);
    	
        
    }
}
