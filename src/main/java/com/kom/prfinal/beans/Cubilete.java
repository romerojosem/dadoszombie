package com.kom.prfinal.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * @author Jose Manuel Romero
 * Clase que representa un cubilete con los dados correspondientes
 */
@SuppressWarnings("serial")
public class Cubilete implements Serializable{
	/**
	 * Propiedad con el contenedor de dados.
	 */
	ArrayList<Dado> dados;

	/**
	 * Constructor por defecto.
	 * Rellena el cubilete con los 13 dados que se usan en el juego.
	 */
	public Cubilete()
	{
		this.dados=new ArrayList<Dado>();
		dados.add(new Dado("verde"));
		dados.add(new Dado("verde"));
		dados.add(new Dado("verde"));
		dados.add(new Dado("verde"));
		dados.add(new Dado("verde"));
		dados.add(new Dado("verde"));

		dados.add(new Dado("amarillo"));
		dados.add(new Dado("amarillo"));
		dados.add(new Dado("amarillo"));
		dados.add(new Dado("amarillo"));

		dados.add(new Dado("rojo"));
		dados.add(new Dado("rojo"));
		dados.add(new Dado("rojo"));
	}

	/**
	 * Metodo que recibe un objeto dado y lo convierte en un dado aleatorio que exista dentro del cubilete.
	 * Si no hubiese dados devuelve null.
	 * @param dado
	 * @return
	 */
	public Dado getDadoAleatorio(Dado dado){
		Random rand = new Random();
		int x;

		if (dados.size()>0){
			x = rand.nextInt(dados.size());
			dado=dados.get(x);
			dados.remove(x);
		}
		else
			dado=null;
		return dado;

	}
	
	
	/**
	 * Metodo get del contenedor de dados.
	 * @return
	 */
	public ArrayList<Dado> getDados(){
		return this.dados;
		
	}

}

