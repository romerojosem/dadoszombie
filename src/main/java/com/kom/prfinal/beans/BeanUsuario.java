/**
 * 
 */
package com.kom.prfinal.beans;


import java.io.*;


import com.kom.prfinal.DAO.DAO;

/**
 * @author Jose Manuel Romero
 * Clase que representa un usuario de la aplicacion
 */
@SuppressWarnings("serial")
public class BeanUsuario implements Serializable {
	/**
	 * Propiedad login del usuario
	 */
	private String login;
	/**
	 * Propiedad clave del usuario
	 */
	private String clave;
	/**
	 * Propiedad nombre del usuario
	 */
	private String nombre;
	/**
	 * Propiedad email del usuario
	 */
	private String email;
	/**
	 * Propiedad codigo(_id Mongo) del usuario
	 */
	private String codUsuario;
	private boolean encontrado = false;
	/**
	 * Propiedad puntos que tendra los cerebros de una partida
	 * o el numero de partidas ganadas,
	 */
	private int puntos=0;
	/**
	 * Propiedad turno asignado al jugador.
	 */
	private int turno=0;
	/**
	 * Propiedad codigo de la partida (_id Mongo) donde esta jugando actualmente.
	 */
	private String idPartida;

	/**
	 * Constructor por defecto sin parametros
	 */
	public BeanUsuario(){

	}

	/**
	 * Constructor con parametros
	 */
	public BeanUsuario(String login, String clave, String nombre, String email)
	{
		this.login = login;
		this.clave = clave;
		this.nombre = nombre;
		this.email = email;
	}

	public int getPuntos() {
		return puntos;
	}

	/**
	 * 
	 * Metodos GET y SET de las propiedades
	 */
	public void setPuntos(int puntos) {
		this.puntos = puntos;
	}

	public int getTurno() {
		return turno;
	}

	public void setTurno(int turno) {
		this.turno = turno;
	}

	public void setIdPartida(String idPartida) {
		this.idPartida = idPartida;
	}

	public String getIdPartida() {
		return idPartida;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLogin() {
		return login;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getClave() {
		return clave;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEncontrado(boolean encontrado) {
		this.encontrado = encontrado;
	}

	public void setCodUsuario(String codUsuario) {
		this.codUsuario=codUsuario;

	} 

	public String getCodUsuario(){
		return codUsuario;
	}

	public boolean isEncontrado() {
		return encontrado;
	}
	
	/**
	 * Metodo que comprueba si el login es correcto y existe en la BD.
	 * Devuelve TRUE o FALSE
	 * @return 
	 */
	public boolean validaLogin() {
		boolean res = true;
		DAO DAO = new DAO();

		if (login==null || login.equals("")){
			res=false;
		}

		else if(clave==null || clave.equals("")){
			res = false;
		}

		if (res==true){

			res=DAO.existeLogin(this);

		}
		return res;

	} 

	/**
	 * Metodo que comprueba los datos son correctos para el registro de un nuevo usuario.
	 * Comprueba que el login no exista en la BD.
	 * Devuelve TRUE o FALSE
	 * @return 
	 */
	public boolean validaNuevoLogin() {
		boolean res = true;

		DAO DAO = new DAO();

		if (login==null || login.equals("")){

			res=false;
		}

		else if(clave==null || clave.equals("")){
			res = false;

		}
		else if(email==null || email.equals("")){
			res = false;

		}
		else if(nombre==null || nombre.equals("")){
			res = false;

		}
		if (res==true){

			res=DAO.LoginEmailValidos(this);
		}

		return res;

	}

	/**
	 * Metodo toString para mostrar los datos de los ganadores.
	 * Si ha conseguido mas de 12 cerebros sera ganador.
	 * @return 
	 */
	public String toString(){
		String cadena="";
		if (this.puntos>12)
			cadena="<h2 style='color:#FF0000;'>GANADOR: "+ this.nombre+"<br>Cerebros Conseguidos: "+this.puntos+"</h2>";
		else		
			cadena="Usuario: "+ this.nombre+"<br>Cerebros Conseguidos: "+this.puntos+"<br>";
		return cadena;


	}
	/**
	 * Metodo toString para mostrar los datos de los jugadores en /micuenta.
	 * @return 
	 */
	public String toStringFinal(){
		String cadena="";

		cadena="Usuario: "+ this.nombre+"<br>Email: "+this.email+"<br>Partidas ganadas: "+this.puntos;

		return cadena;

	}


}
