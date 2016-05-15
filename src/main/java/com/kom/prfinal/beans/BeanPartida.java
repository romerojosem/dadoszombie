package com.kom.prfinal.beans;

import java.io.Serializable;

/**
 * @author Jose Manuel Romero
 * Clase que representa una partida
 */
@SuppressWarnings("serial")
public class BeanPartida implements Serializable{
	/**
	 * Propiedad del creador de la partida
	 */
	String nombre;
	/**
	 * Propiedad numero de jugadores
	 */
	int jugadores;
	/**
	 * Propiedad con el estado de la partida
	 * 0 creada y esperando jugadores
	 * 1 jugando
	 * 3 finalizada
	 */
	int estado;
	/**
	 * Propiedad con la id de la partida (_id MongoDB)
	 */
	String id;


	/**
	 * Constructor por defecto sin parametros
	 */
	public BeanPartida(){

	}

	/** 
	 * Metodos GET Y SET de las propiedades
	 */
	public int getJugadores() {
		return jugadores;
	}

	public void setJugadores(int jugadores) {
		this.jugadores = jugadores;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setId(String id){
		this.id=id;
	}

	public String getId(){
		return id;
	}

}