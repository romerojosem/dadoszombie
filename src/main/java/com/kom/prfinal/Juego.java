
package com.kom.prfinal;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kom.prfinal.DAO.DAO;
import com.kom.prfinal.beans.BeanPartida;
import com.kom.prfinal.beans.BeanUsuario;
import com.kom.prfinal.excepciones.ErroresAp;

/**
 * @author Jose Manuel Romero Rodriguez.
 * Clase Controladora que recoge las peticiones referentes al juego de la aplicacion
 */
@Controller
public class Juego {
	/**
	 * Peticion para la solicitud de las partidas disponibles
     * @return JSON 
	 */
	@RequestMapping(value = "/partidas", method = RequestMethod.POST)
	public @ResponseBody String partidas() {

		DAO dao = new DAO();
		ArrayList<BeanPartida> partidas=new ArrayList<BeanPartida>();
		String json = null;

		partidas=dao.damePartidas();

		json = new Gson().toJson(partidas);


		return json;
	}

	/**
	 * Peticion para crear una nueva partida, primero comprueba que el usuario no tenga creada una partida activa,
	 * y despues crea una partida, devuelve si se ha podido crear la partida o no para mostrarle el mensaje al usuario.
     * @return JSON 
	 */
	@RequestMapping(value = "/partidas/crear", method = RequestMethod.POST)
	public @ResponseBody String crearPartida(HttpServletRequest request) {
		HttpSession sesion = request.getSession();
		BeanUsuario usuario;
		String resultado="";

		usuario = (BeanUsuario) sesion.getAttribute("usuario");
		DAO dao = new DAO();

		if(dao.creaPartida(usuario)){
			resultado="Partida Creada Correctamente.";
		}
		else{
			resultado="Solo se puede crear una partida por jugador.";
		}

		return resultado;
	}

	/**
	 * Peticion para unirse a una partida activa, primero comprueba que el usuario no este ya jugando en esa partida
	 * y que no este completa, despues se une al usuario en la partida, devuelve "OK" si se ha podido unir
	 * o un mensaje de error.
     * @return JSON 
	 */
	@RequestMapping(value = "/partidas/unirse", method = RequestMethod.POST)
	public @ResponseBody String unirsePartida(HttpServletRequest request) {
		HttpSession sesion = request.getSession();
		BeanUsuario usuario;
		String resultado="";
		String idPartida;

		usuario = (BeanUsuario) sesion.getAttribute("usuario");
		idPartida = request.getParameter("idPartida");


		DAO dao = new DAO();

		if(dao.UnirsePartida(usuario,idPartida)){
			resultado="OK";
			//si se ha unido bien guardo la idPartida dentro del usuario.
			usuario.setIdPartida(idPartida);
		}
		else{
			resultado="Ya esta jugando en esa partida o esta completa.";
		}

		return resultado;
	}

	/**
	 * Peticion que devuelve un listado de los jugadores disponibles en una partida.
	 * El primer registro del ArrayList indica si el usuario de la sesion es el creador de la partida o no.
     * @return JSON 
	 */
	@RequestMapping(value = "/jugadores", method = RequestMethod.POST)
	public @ResponseBody String jugadores(HttpServletRequest request) {
		BeanUsuario nuevoUsuario;
		DAO dao = new DAO();
		ArrayList<BeanUsuario> jugadores=new ArrayList<BeanUsuario>();
		String json = null;

		HttpSession sesion = request.getSession();
		BeanUsuario usuario = (BeanUsuario) sesion.getAttribute("usuario");


		if(dao.estadoPartida(usuario)){
			return "OK";
		}
		else{

			//miro el creador de la partida
			if(dao.usuarioCreador(usuario)){
				nuevoUsuario=new BeanUsuario();
				nuevoUsuario.setNombre("1");
				jugadores.add(nuevoUsuario);
			}
			else{
				nuevoUsuario=new BeanUsuario();
				nuevoUsuario.setNombre("0");
				jugadores.add(nuevoUsuario);
			}
			dao.dameJugadores(usuario,jugadores);
			json = new Gson().toJson(jugadores);
			return json;
		}



	}
	
	/**
	 * Peticion para crear entrar en la partida.
     * @return vista.jsp 
	 */
	@RequestMapping(value = "/partida", method = RequestMethod.GET)
	public String partida(HttpServletRequest request) {
		HttpSession sesion = request.getSession();

		String vista;
		BeanUsuario usuario;

		usuario = (BeanUsuario) sesion.getAttribute("usuario");
		if(usuario==null){
			vista="registro";
		}
		else{

			vista="partida";
		}
		return vista;
	}

	/**
	 * Peticion para comenzar una partida, primero comprueba que haya al menos 2 usuarios en la partida activa,
	 * y despues marca la partida y al primer usuario como jugando.
     * @return JSON 
	 */
	@RequestMapping(value = "/comenzar", method = RequestMethod.POST)
	public @ResponseBody String comenzar(HttpServletRequest request) {
		HttpSession sesion = request.getSession();
		DAO dao = new DAO();
		String vista;
		BeanUsuario usuario;
		ArrayList<BeanUsuario> jugadores=new ArrayList<BeanUsuario>();

		usuario = (BeanUsuario) sesion.getAttribute("usuario");
		//miro si hay al menos 2 usuarios en la partida
		dao.dameJugadores(usuario,jugadores);
		if(jugadores.size()>1){
			//	poner el usuario primero como activo para que empiece a jugar
			dao.cambiaEstadoUsuario(usuario,1);
			vista="OK";
		}
		else
			vista="NO";

		return vista;
	}

	/**
	 * Peticion para enviar a la pagina de la partida nueva para jugar.
     * @return JSON 
	 */
	@RequestMapping(value = "/jugada", method = RequestMethod.GET)
	public String jugada(HttpServletRequest request) {
		HttpSession sesion = request.getSession();

		String vista;
		BeanUsuario usuario;

		usuario = (BeanUsuario) sesion.getAttribute("usuario");
		if(usuario==null){
			vista="registro";
		}
		else{

			vista="jugada";
		}
		return vista;
	}

	/**
	 * Peticion para listar los jugadores y sus puntos actuales de la partida,
	 * primero comprueba que la partida no haya terminado.
	 * Si la partida ha terminado devuelve un ArrayList nulo. 
     * @return JSON 
	 */
	@RequestMapping(value = "/listaJugadores", method = RequestMethod.POST)
	public @ResponseBody String listaJugadores(HttpServletRequest request) {

		DAO dao = new DAO();

		ArrayList<BeanUsuario> jugadores=new ArrayList<BeanUsuario>();
		String json = null;
		BeanUsuario jugadorActivo;
		HttpSession sesion = request.getSession();
		BeanUsuario usuario = (BeanUsuario) sesion.getAttribute("usuario");
		
		//miro si la partida ha finalizado.
		if(dao.partidaFinalizada(usuario)){
			jugadores.add(null);
		}
		else{
			jugadorActivo=dao.dameJugadorActivo(usuario);
			jugadores.add(jugadorActivo);
			dao.dameJugadores(usuario,jugadores);
		}
		json = new Gson().toJson(jugadores);

		return json;

	}


	/**
	 * Control de excepciones, recoge una excepcion y redirecciona a la web de error con un mensaje.
	 */
	@ExceptionHandler(Exception.class)
	public ModelAndView handleAllException(Exception ex) {
		ModelAndView model = new ModelAndView("errorPage");
		ErroresAp err=new ErroresAp("Los zombies se han vuelto locos.",6);
		model.addObject("errMsg", err.getMsg());

		return model;

	}

}
