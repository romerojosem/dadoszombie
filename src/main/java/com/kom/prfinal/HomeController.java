package com.kom.prfinal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.kom.prfinal.DAO.DAO;
import com.kom.prfinal.beans.*;
import com.kom.prfinal.excepciones.ErroresAp;

/**
 * @author Jose Manuel Romero Rodriguez.
 * Clase Controladora que recoge las peticiones del menu principal de la aplicacion
 */
@Controller
public class HomeController {


	/**
	 * Peticion de la pagina principal
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Model model) {
		return "index";
	}

	/**
	 * Peticion de la pagina de reglas
	 */
	@RequestMapping(value = "/reglas", method = RequestMethod.GET)
	public String reglas(Model model) {

		return "reglas";
	}

	/**
	 * Peticion de la pagina de cuenta del usuario
	 */
	@RequestMapping(value = "/micuenta", method = RequestMethod.GET)
	public String micuenta(Model model,HttpServletRequest request) {

		HttpSession sesion = request.getSession();
		DAO dao=new DAO();
		String vista;
		BeanUsuario usuariosession;
		BeanUsuario usuario;

		usuariosession = (BeanUsuario) sesion.getAttribute("usuario");
		if(usuariosession==null){
			vista="registro";
		}
		else{
			usuario=dao.dameUsuario(usuariosession.getLogin());
			model.addAttribute("usuario", usuario );
			vista="cuenta";
		}
		return vista;
	}



	/**
	 * Peticion de la pagina de Partidas
	 */
	@RequestMapping(value = "/juego", method = RequestMethod.GET)

	public String juego(Model model,HttpServletRequest request) {
		HttpSession sesion = request.getSession();

		String vista;
		BeanUsuario usuario;

		usuario = (BeanUsuario) sesion.getAttribute("usuario");
		if(usuario==null){
			vista="registro";
		}
		else{
			model.addAttribute("usuario", usuario );

			vista="juego";
		}
		return vista;
	}

	/**
	 * Peticion para salir.
	 */
	@RequestMapping(value = "/salir", method = RequestMethod.GET)
	public String salir(HttpServletRequest request) {
		HttpSession sesion = request.getSession();
		//terminar todas las partidas donde este jugando el usuario
		DAO dao = new DAO();
		BeanUsuario usuario = (BeanUsuario) sesion.getAttribute("usuario");

		if(usuario!=null){
			dao.terminarPartidas(usuario);
			sesion.invalidate();			
		}
		return "index";
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
