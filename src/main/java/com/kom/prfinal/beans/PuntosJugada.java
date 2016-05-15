package com.kom.prfinal.beans;

/**
 * @author Jose Manuel Romero
 * Clase que representa una jugada, donde se guardan los cerebros y disparos conseguidos.
 */
public class PuntosJugada {
	/**
	 * Propiedad numero de disparos
	 */
	int disparos;
	/**
	 * Propiedad numero de cerebros
	 */
	int cerebros;
	
	/**
	 * Constructor por defecto
	 */
	public PuntosJugada(){
		disparos=0;
		cerebros=0;
	}

	/**
	 * Metodos SET y GET de las propiedades
	 */
	public int getDisparos() {
		return disparos;
	}

	public void setDisparos(int disparos) {
		this.disparos = disparos;
	}

	public int getCerebros() {
		return cerebros;
	}
 
	public void setCerebros(int cerebros) {
		this.cerebros = cerebros;
	}

	/**
	 * Suma un disparo
	 */
	public void sumaDisparo(){
		this.disparos++;
	}
	
	/**
	 * Suma un cerebro
	 */
	public void sumaCerebro(){
		this.cerebros++;
	}
}
