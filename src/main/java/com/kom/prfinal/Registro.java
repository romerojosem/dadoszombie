package com.kom.prfinal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kom.prfinal.beans.BeanUsuario;
import com.kom.prfinal.excepciones.ErroresAp;

/**
 * @author Jose Manuel Romero Rodriguez.
 * Clase Controladora que recoge las peticiones de registro y login de usuarios.
 */
@Controller
public class Registro {

	/**
	 * Peticion registrar un usuario.
     * @return JSON 
	 */
	@RequestMapping(value = "/registrar", method = RequestMethod.POST)
	public @ResponseBody String registrar(Model model,HttpServletRequest request) {
		HttpSession sesion = request.getSession();
		String vista,login,pass,nombre,email;

		login= request.getParameter("login");
		pass= request.getParameter("clave");
		nombre= request.getParameter("nombre");
		email= request.getParameter("email");

		BeanUsuario usuario=new BeanUsuario(login,pass,nombre,email);

		if(usuario.validaNuevoLogin()){
			model.addAttribute("usuario",usuario);
			sesion.setAttribute("usuario", usuario);
			vista="cuenta";
		}
		else{

			vista="registro";
		}

		return vista;
	}
	
	/**
	 * Peticion para logueo de un usuario.
     * @return JSON 
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody String login(Model model,HttpServletRequest request) {
		HttpSession sesion = request.getSession();
		String vista,login,pass;

		login= request.getParameter("login");
		pass= request.getParameter("clave");


		BeanUsuario usuario=new BeanUsuario();
		usuario.setLogin(login);
		usuario.setClave(pass);

		if(usuario.validaLogin()){

			model.addAttribute("usuario",usuario);
			sesion.setAttribute("usuario", usuario);

			vista="cuenta";
		}
		else{

			vista="registro";
		}
		
		return vista;
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
