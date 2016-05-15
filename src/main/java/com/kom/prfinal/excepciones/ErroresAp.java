package com.kom.prfinal.excepciones;


@SuppressWarnings("serial")
public class ErroresAp extends RuntimeException {
    /**
     * Mensaje asociado al error de conversión
     */
    private String msg;

    /**
     * Código de error asociado a la excepción
     */
    private int coderr;

    /**
     * Constructor que recibe el mensaje de la excepción.
     * @param msg
     */
    public ErroresAp(String msg, int coderr) {

    	
        this.msg = msg;
        this.coderr = coderr;

    }

    /**
     * Setter de msg (mensaje de la excepción)
     * @param msg Mensaje asociado a la excepción
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Getter de msg (mensaje de la excepción)
     * @return El mensaje asociado a la excepción
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Setter de coderr (código de error)
     * @param coderr Código de error
     */
    public void setCoderr(int coderr) {
        this.coderr = coderr;
    }

    /**
     * Getter de coderr (código de error)
     * @return
     */
    public int getCoderr() {
        return coderr;
    }
}
