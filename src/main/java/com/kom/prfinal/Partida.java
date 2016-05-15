package com.kom.prfinal;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.kom.prfinal.DAO.DAO;
import com.kom.prfinal.beans.BeanUsuario;
import com.kom.prfinal.beans.Cubilete;
import com.kom.prfinal.beans.Dado;
import com.kom.prfinal.beans.PuntosJugada;
import com.kom.prfinal.excepciones.ErroresAp;

/**
 * @author Jose Manuel Romero Rodriguez.
 * Clase Controladora que recoge las peticiones de la partida.
 */
@Controller
public class Partida {
	/**
	 * Peticion que realiza una tirada de dados.
	 * Se encarga de generar una cubilete de 13 dados y cada dado nuevo que se tira.
	 * Comprueba si con la tirada realizada el jugador a muerto o ganado la partida.
	 * Devuleve un JSON con todos los datos de la tirada del jugador.
     * @return JSON 
	 */
	@RequestMapping(value = "/tirada", method = RequestMethod.POST)
	public @ResponseBody String tirada(HttpServletRequest request) {
		StringBuilder sb;
		String cerebros;
		String disparos;
		String json = null;
		HttpSession sesion = request.getSession();
		ArrayList<String> tirada=new ArrayList<String>();
		Cubilete cubilete = (Cubilete) sesion.getAttribute("cubilete");
		PuntosJugada puntosJugada = (PuntosJugada) sesion.getAttribute("puntosJugada");

		Dado dado1 = (Dado) sesion.getAttribute("dado1");
		Dado dado2 = (Dado) sesion.getAttribute("dado2");
		Dado dado3 = (Dado) sesion.getAttribute("dado3");

		//si no hay cubilete o no tiene 3 dados se crea uno nuevo.
		if(cubilete==null||cubilete.getDados().size()<3){
			cubilete=new Cubilete();
		}
		if(puntosJugada==null){
			puntosJugada=new PuntosJugada();
		}
		//si no existe algun dado(principio de la tirada o existe alguna huella) se sacan del cubilete.
		if (dado1==null){
			dado1=cubilete.getDadoAleatorio(dado1);
		}
		if (dado2==null){
			dado2=cubilete.getDadoAleatorio(dado2);
		}
		if (dado3==null){
			dado3=cubilete.getDadoAleatorio(dado3);
		}
		//se añaden las caras aleatorias a la tirada.
		tirada.add(dado1.getCaraAleatoria());
		tirada.add(dado2.getCaraAleatoria());
		tirada.add(dado3.getCaraAleatoria());

		//comprobar dados
		//dado1
		if(dado1.getCara().equals("v1.gif")||dado1.getCara().equals("a1.gif")||dado1.getCara().equals("r1.gif"))
			puntosJugada.sumaDisparo();

		if(dado1.getCara().equals("v3.gif")||dado1.getCara().equals("a3.gif")||dado1.getCara().equals("r3.gif"))
			puntosJugada.sumaCerebro();

		if(dado1.getCara().equals("v2.gif")||dado1.getCara().equals("a2.gif")||dado1.getCara().equals("r2.gif")){
			sesion.setAttribute("dado1", dado1);
		}
		else{
			sesion.setAttribute("dado1", null);
		}
		//dado2
		if(dado2.getCara().equals("v1.gif")||dado2.getCara().equals("a1.gif")||dado2.getCara().equals("r1.gif")){
			puntosJugada.sumaDisparo();
		}
		if(dado2.getCara().equals("v3.gif")||dado2.getCara().equals("a3.gif")||dado2.getCara().equals("r3.gif")){
			puntosJugada.sumaCerebro();
		}
		if(dado2.getCara().equals("v2.gif")||dado2.getCara().equals("a2.gif")||dado2.getCara().equals("r2.gif")){
			sesion.setAttribute("dado2", dado2);
		}
		else{
			sesion.setAttribute("dado2", null);
		}

		//dado3
		if(dado3.getCara().equals("v1.gif")||dado3.getCara().equals("a1.gif")||dado3.getCara().equals("r1.gif")){
			puntosJugada.sumaDisparo();
		}
		if(dado3.getCara().equals("v3.gif")||dado3.getCara().equals("a3.gif")||dado3.getCara().equals("r3.gif")){
			puntosJugada.sumaCerebro();
		}
		if(dado3.getCara().equals("v2.gif")||dado3.getCara().equals("a2.gif")||dado3.getCara().equals("r2.gif")){
			sesion.setAttribute("dado3", dado3);
		}
		else{
			sesion.setAttribute("dado3", null);
		}

		sb = new StringBuilder();
		sb.append(puntosJugada.getDisparos());
		disparos = sb.toString();
		tirada.add(disparos);

		sb = new StringBuilder();
		sb.append(puntosJugada.getCerebros());
		cerebros = sb.toString();
		tirada.add(cerebros);
		//comprobar si ha ganado con 13 cerebros o a perdido con 3 disparos
		//1 GANADOR
		//2 MUERTO
		//0 NADA

		if(puntosJugada.getDisparos()>2){
			tirada.add("2");
			puntosJugada.setCerebros(0);
		}
		else if(puntosJugada.getCerebros()>12){
			tirada.add("1");
		}
		else{
			tirada.add("0");
		}

		sesion.setAttribute("cubilete", cubilete);
		sesion.setAttribute("puntosJugada", puntosJugada);


		json = new Gson().toJson(tirada);
		return json;

	}


	/**
	 * Peticion para plantarse en una tirada de dados y acumular los cerebros conseguidos.
	 * Guarda los puntos en el usuario y marca activo al siguiente jugador.
	 */
	@RequestMapping(value = "/plantarse", method = RequestMethod.POST)
	public void plantarse(HttpServletRequest request) {
		HttpSession sesion = request.getSession();
		DAO dao = new DAO();
		BeanUsuario usuario = (BeanUsuario) sesion.getAttribute("usuario");
		PuntosJugada puntosJugada = (PuntosJugada) sesion.getAttribute("puntosJugada");

		//actualizar puntos del usuario
		dao.actualizarPuntosUsuario(usuario,puntosJugada.getCerebros());

		//poner la jugada vacia
		sesion.setAttribute("dado1", null);
		sesion.setAttribute("dado2", null);
		sesion.setAttribute("dado3", null);
		sesion.setAttribute("puntosJugada", null);

		//pasar al siguiente jugador
		dao.cambiaEstadoUsuarios(usuario);

	}

	/**
	 * Peticion para una vez finalizada la partida se redirija a la pantalla final con el resultado de la partida.
     * @return vista.jsp
	 */
	@RequestMapping(value = "/finalPartida", method = RequestMethod.GET)
	public String finalPartida(Model model,HttpServletRequest request) {

		HttpSession sesion = request.getSession();
		DAO dao = new DAO();
		ArrayList<BeanUsuario> jugadores=new ArrayList<BeanUsuario>();

		BeanUsuario usuario = (BeanUsuario) sesion.getAttribute("usuario");

		dao.finalPartida(usuario);

		dao.dameJugadores(usuario, jugadores);
		model.addAttribute("jugadores", jugadores );

		return "finalPartida";

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
