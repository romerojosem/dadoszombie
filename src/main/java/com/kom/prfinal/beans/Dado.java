package com.kom.prfinal.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
/**
 * @author Jose Manuel Romero
 * Clase que representa un dado
 */
@SuppressWarnings("serial")
public class Dado implements Serializable{
	/**
	 * Propiedad cara del dado.
	 */
	String cara;
	/**
	 * Propiedad con el contenedor de caras del dado, tendra el nombre de la imagen a mostrar.
	 */
	ArrayList<String> caras;
	
	
	/**
	 * Constructor por defecto.
	 */
	public Dado()
	{
		this.caras=new ArrayList<String>();
		cara="";
	}
	
	/**
	 * Metodo que recibe un tipo de dado(color) y devuelve un dado con las caras rellenas de ese tipo,
	 * con sus correspondientes disparos, cerebros y huellas.
	 * @param tipo
	 */
	public Dado(String tipo){
		this.caras=new ArrayList<String>();
		cara="";
		
		if(tipo.equals("verde")){
			this.caras.add("v3.gif");
			this.caras.add("v3.gif");
			this.caras.add("v3.gif");
			this.caras.add("v2.gif");
			this.caras.add("v2.gif");
			this.caras.add("v1.gif");
		}
		
		if(tipo.equals("amarillo")){
			this.caras.add("a3.gif");
			this.caras.add("a3.gif");
			this.caras.add("a2.gif");
			this.caras.add("a2.gif");
			this.caras.add("a1.gif");
			this.caras.add("a1.gif");
		}
		
		if(tipo.equals("rojo")){
			this.caras.add("r1.gif");
			this.caras.add("r2.gif");
			this.caras.add("r2.gif");
			this.caras.add("r1.gif");
			this.caras.add("r1.gif");
			this.caras.add("r1.gif");
		}
		
	}
	
	public void addCara(String cara){
		this.caras.add(cara);
	}
	
	public String getCara(){
		return this.cara;
	}	
	
	/**
	 * Metodo que devuelve una cara aleatoris del dado.
	 * @return
	 */
	public String getCaraAleatoria(){
        Random aleatorio = new Random();
        int valor;
        String cara;

        valor = aleatorio.nextInt(5-0+1)+0;

        cara=caras.get(valor);
        this.cara=cara;
		return cara;
		
	}
	

}

