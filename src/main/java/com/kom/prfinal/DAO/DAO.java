package com.kom.prfinal.DAO;

import static java.util.Arrays.asList;
import java.util.ArrayList;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import com.kom.prfinal.beans.*;
import com.kom.prfinal.excepciones.*;
import com.mongodb.*;
import com.mongodb.client.*;

/**
 * @author Jose Manuel Romero
 * Clase DAO para las peticiones a MongoDB
 */
public class DAO {
	/**
	 * Cliente par la conexion a MongoDB
	 */
	private MongoClient mongoClient;
	/**
	 * Base de datos MongoDB
	 */
	private MongoDatabase database;

	/**
	 * Constructor por defecto
	 */
	public DAO() {

	}

	/**
	 * Metodo que cierra la conexion con MongoDB
	 */
	public void cerrarConexion() {
		try{
			mongoClient.close();
		} catch (MongoException e) {
			throw new ErrorMongo("Un humano ha matado a nuestro hermano zombie MongoDB.");
		}
	}

	/**
	 * Metodo que recibe un usario y comprueba si existe en la BD
	 * @param usuario
	 * @return
	 */
	public boolean existeLogin(BeanUsuario usuario) {
		boolean encontrado = false;

		try{
			this.mongoClient=new MongoClient();
			this.database = mongoClient.getDatabase("PF08");

			MongoCollection<Document> collection = database.getCollection("Usuarios");

			Document query = new Document("pass", usuario.getClave()).append("login", usuario.getLogin());

			FindIterable<Document> cursor = collection.find(query);

			for (Document doc : cursor) {

				usuario.setEmail( doc.getString("email"));
				usuario.setNombre( doc.getString("nombre"));

				encontrado=true;
			}

		} catch (MongoException e) {
			throw new ErrorMongo("Un humano ha matado a nuestro hermano zombie MongoDB.");
		}finally {
			cerrarConexion();
		}
		return encontrado;
	}

	/**
	 * Metodo que comprueba si existe el login o el email del usuario recibido.
	 * Si no existe lo inserta en la BD
	 * @param usuario
	 * @return
	 */
	public boolean LoginEmailValidos(BeanUsuario usuario) {
		boolean encontrado = false;
		try{
			this.mongoClient=new MongoClient();
			this.database = mongoClient.getDatabase("PF08");

			MongoCollection<Document> collection = database.getCollection("Usuarios");

			Document query=new Document("$or", asList(new Document("login", usuario.getLogin()),new Document("email", usuario.getEmail())));

			FindIterable<Document> cursor = collection.find(query);
			for (Document doc : cursor) {
				usuario.setEmail( doc.getString("email"));
				usuario.setNombre( doc.getString("nombre"));

				encontrado=true;
			}

			if (encontrado==false){

				//Inserto al usuario en la BD
				collection.insertOne(
						new Document("login",usuario.getLogin())
						.append("pass", usuario.getClave())
						.append("email", usuario.getClave())
						.append("puntos", 0)
						.append("nombre", usuario.getNombre()));

			}
		} catch (MongoException e) {
			throw new ErrorMongo("Un humano ha matado a nuestro hermano zombie MongoDB.");
		}finally {
			cerrarConexion();
		}
		return !encontrado;
	}

	/**
	 * Devueve las partidas con estado 0 (listas para unirse a ellas)
	 * @return
	 */
	public ArrayList<BeanPartida> damePartidas() {
		BeanPartida partida = null;
		ArrayList<BeanPartida> partidas = new ArrayList<BeanPartida>();

		try{
			this.mongoClient=new MongoClient();
			this.database = mongoClient.getDatabase("PF08");

			MongoCollection<Document> collection = database.getCollection("Partidas");
			Document query = new Document("estado", 0);

			FindIterable<Document> cursor = collection.find(query);
			for (Document doc : cursor) {
				partida = new BeanPartida();
				partida.setJugadores((int) doc.getInteger("njugadores"));
				partida.setNombre( doc.getString("Nombre"));
				partida.setId( doc.getObjectId("_id").toString());

				partidas.add(partida);

			}
		} catch (MongoException e) {
			throw new ErrorMongo("Un humano ha matado a nuestro hermano zombie MongoDB.");
		}finally {
			cerrarConexion();
		}
		return partidas;
	}

	/**
	 * Metodo que crea una partida con el usuario recibido.
	 * Si ese jugador no tiene otra partida activa(estado 0) se crea.
	 * @param usuario
	 * @return
	 */
	public boolean creaPartida(BeanUsuario usuario) {
		boolean encontrado=false;

		try{
			this.mongoClient=new MongoClient();
			this.database = mongoClient.getDatabase("PF08");

			MongoCollection<Document> collection = database.getCollection("Partidas");

			Document query = new Document("estado", 0).append("Nombre", usuario.getLogin());

			FindIterable<Document> cursor = collection.find(query);
			for (Document doc : cursor) {

				encontrado=true;
			}

			if (encontrado==false){

				//Inserto la partida en la BD
				collection.insertOne(
						new Document("Nombre",usuario.getLogin())
						.append("estado", 0)
						.append("njugadores", 0));
			}
		} catch (MongoException e) {
			throw new ErrorMongo("Un humano ha matado a nuestro hermano zombie MongoDB.");
		}finally {
			cerrarConexion();
		}
		return !encontrado;
	}

	/**
	 * Metodo que recibiendo un usuario y un idPartida une al jugador en esa partida
	 * si no estuviese ya unido a ella.
	 * @param usuario
	 * @param idPartida
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public boolean UnirsePartida(BeanUsuario usuario, String idPartida) {
		boolean encontrado=false;
		ArrayList<Document> jugadores=new ArrayList<Document>();

		try{
			this.mongoClient=new MongoClient();
			this.database = mongoClient.getDatabase("PF08");
			MongoCollection<Document> collection = database.getCollection("Partidas");

			Document query = new Document("_id", new ObjectId(idPartida)).append("jugadores.login",usuario.getLogin());

			FindIterable<Document> cursor = collection.find(query);

			for (Document doc : cursor) {

				encontrado=true;
			}

			if (encontrado==false){
				//miro si hay menos de 4 jugadores en la partida
				query = new Document("_id", new ObjectId(idPartida));
				cursor = collection.find(query);

				for (Document doc : cursor) {
					jugadores= (ArrayList<Document>) doc.get("jugadores");
				}
				if(jugadores==null||jugadores.size()<4){

					//aumento el numero de jugadores de la partida
					collection.updateOne(new Document("_id", new ObjectId(idPartida)),
							new Document("$inc", new Document("njugadores",1)));

					//Inserto el usuario en la partida en la BD
					collection.updateOne(new Document("_id", new ObjectId(idPartida)),
							new Document("$push", new Document("jugadores", new Document("login",usuario.getLogin())
									.append("cerebros", 0)
									.append("estado", 0))));
				}
				else
					encontrado=true;
			}
		} catch (MongoException e) {
			throw new ErrorMongo("Un humano ha matado a nuestro hermano zombie MongoDB.");
		}finally {
			cerrarConexion();
		}
		return !encontrado;
	}

	/**
	 * Metodo que recibe un usuario y un ArrayList de usuarios que lo rellena con los jugadores de la partida
	 * del usuario recibido.
	 * @param usuario
	 * @param usuarios
	 */
	public void dameJugadores(BeanUsuario usuario,ArrayList<BeanUsuario> usuarios) {
		BeanUsuario usuarioP = null;

		int turno=1;

		try{
			this.mongoClient=new MongoClient();
			this.database = mongoClient.getDatabase("PF08");

			MongoCollection<Document> collection = database.getCollection("Partidas");

			Document query = new Document("_id", new ObjectId(usuario.getIdPartida()));
			FindIterable<Document> cursor = collection.find(query);

			for (Document doc : cursor) {
				@SuppressWarnings("unchecked")
				ArrayList<Document> jugadores= (ArrayList<Document>) doc.get("jugadores");
				for (Document jugador : jugadores) {
					usuarioP = new BeanUsuario();
					usuarioP.setNombre( jugador.getString("login"));
					usuarioP.setPuntos((int) jugador.getInteger("cerebros"));
					usuarioP.setTurno(turno);
					usuarios.add(usuarioP);
					turno++;
				}
			}

		} catch (MongoException e) {
			throw new ErrorMongo("Un humano ha matado a nuestro hermano zombie MongoDB.");
		}finally {
			cerrarConexion();
		}

	}

	/**
	 * Metodo que finaliza las partidas donde estuviese el usuario jugando.
	 * @param usuario
	 */
	public void terminarPartidas(BeanUsuario usuario) {

		try{
			this.mongoClient=new MongoClient();
			this.database = mongoClient.getDatabase("PF08");
			MongoCollection<Document> collection = database.getCollection("Partidas");

			//Actualizp el usuario en la partida en la BD
			collection.updateMany(new Document("jugadores.login",usuario.getLogin()),
					new Document("$set", new Document("jugadores.$.estado",3)));
		} catch (MongoException e) {
			throw new ErrorMongo("Un humano ha matado a nuestro hermano zombie MongoDB.");
		}finally {
			cerrarConexion();
		}

	}

	/**
	 * Metodo para poner la partida en estado 1(jugando) y al primer usuario de la partida lo pone listo
	 * para jugar (estado 1)
	 * @param usuario
	 * @param i
	 */
	public void cambiaEstadoUsuario(BeanUsuario usuario, int i) {
		try{
			this.mongoClient=new MongoClient();
			this.database = mongoClient.getDatabase("PF08");
			MongoCollection<Document> collection = database.getCollection("Partidas");

			//Actualizo el usuario en la partida en la BD
			collection.updateOne(new Document("_id", new ObjectId(usuario.getIdPartida())),
					new Document("$set", new Document("jugadores.0.estado",1)));
			//Actualizo la partida para jugar
			collection.updateMany(new Document("_id", new ObjectId(usuario.getIdPartida())),
					new Document("$set", new Document("estado",1)));
		} catch (MongoException e) {
			throw new ErrorMongo("Un humano ha matado a nuestro hermano zombie MongoDB.");
		}finally {
			cerrarConexion();
		}

	}

	/**
	 * Metodo que devuelve si la partida esta en estado 1(jugando) o no.
	 * @param usuario
	 * @return
	 */
	public boolean estadoPartida(BeanUsuario usuario) {
		boolean encontrado=false;
		try{
			this.mongoClient=new MongoClient();
			this.database = mongoClient.getDatabase("PF08");
			MongoCollection<Document> collection = database.getCollection("Partidas");

			Document query = new Document("_id", new ObjectId(usuario.getIdPartida())).append("estado",1);
			FindIterable<Document> cursor = collection.find(query);
			for (Document doc : cursor) {

				encontrado=true;
			}


		} catch (MongoException e) {
			throw new ErrorMongo("Un humano ha matado a nuestro hermano zombie MongoDB.");
		}finally {
			cerrarConexion();
		}

		return encontrado;
	}

	/**
	 * Metodo que devuelve el jugado con estado 1(jugando) de la partida del jugador recibido.
	 * @param usuario
	 * @return
	 */
	public BeanUsuario dameJugadorActivo(BeanUsuario usuario) {
		BeanUsuario usuarioP = null;

		try{
			this.mongoClient=new MongoClient();
			this.database = mongoClient.getDatabase("PF08");

			MongoCollection<Document> collection = database.getCollection("Partidas");

			Document query = new Document("_id", new ObjectId(usuario.getIdPartida())).append("jugadores.estado",1);

			FindIterable<Document> cursor = collection.find(query);

			for (Document doc : cursor) {
				@SuppressWarnings("unchecked")
				ArrayList<Document> jugadores= (ArrayList<Document>) doc.get("jugadores");
				for (Document jugador : jugadores) {
					if(jugador.getInteger("estado")==1){
						usuarioP = new BeanUsuario();
						usuarioP.setNombre( jugador.getString("login"));
						usuarioP.setLogin( usuario.getLogin());
					}

				}
			}

		} catch (MongoException e) {
			throw new ErrorMongo("Un humano ha matado a nuestro hermano zombie MongoDB.");
		}finally {
			cerrarConexion();
		}
		if (usuarioP==null){
			//no hay ningun usuario jugando
		}
		return usuarioP;
	}

	/**
	 * Metodo para poner activo(estado 1 - jugando) al siguiente jugador de la partida del usuario recibido.
	 * @param usuario
	 */
	public void cambiaEstadoUsuarios(BeanUsuario usuario) {
		int posicion=0;
		int x=0;
		BeanUsuario usuarioNuevo=new BeanUsuario();
		try{
			this.mongoClient=new MongoClient();
			this.database = mongoClient.getDatabase("PF08");

			MongoCollection<Document> collection = database.getCollection("Partidas");

			Document query = new Document("_id", new ObjectId(usuario.getIdPartida()));

			FindIterable<Document> cursor = collection.find(query);

			for (Document doc : cursor) {
				@SuppressWarnings("unchecked")
				ArrayList<Document> jugadores= (ArrayList<Document>) doc.get("jugadores");
				for (Document jugador : jugadores) {

					if(jugador.getInteger("estado")==1){
						posicion=x;
						//actualizar estado
						siguienteJugador(usuario,0,posicion);
					}
					x++;
				}
				if (posicion+1==jugadores.size()){
					//si es el ultimo actualizo el estado del primero
					usuarioNuevo.setIdPartida(usuario.getIdPartida());
					usuarioNuevo.setLogin(jugadores.get(0).getString("login"));
					siguienteJugador(usuario,1,0);
				}
				else{
					//actualizo el siguiente
					usuarioNuevo.setIdPartida(usuario.getIdPartida());
					usuarioNuevo.setLogin(jugadores.get(posicion+1).getString("login"));
					siguienteJugador(usuario,1,posicion+1);

				}
			}


		} catch (MongoException e) {
			throw new ErrorMongo("Un humano ha matado a nuestro hermano zombie MongoDB.");
		}finally {
			cerrarConexion();
		}


	}

	public void siguienteJugador(BeanUsuario usuario, int i,int pos) {
		try{
			this.mongoClient=new MongoClient();
			this.database = mongoClient.getDatabase("PF08");
			MongoCollection<Document> collection = database.getCollection("Partidas");

			//Actualizo el usuario en la partida en la BD
			collection.updateOne(new Document("_id", new ObjectId(usuario.getIdPartida())),
					new Document("$set", new Document("jugadores."+pos+".estado",i)));

		} catch (MongoException e) {
			throw new ErrorMongo("Un humano ha matado a nuestro hermano zombie MongoDB.");
		}finally {
			cerrarConexion();
		}

	}

	/**
	 * Metodo que busca al jugador que esta activo en la partida para actualizar los cerebros conseguidos.
	 * @param usuario
	 * @param cerebros
	 */
	public void actualizarPuntosUsuario(BeanUsuario usuario, int cerebros) {
		int posicion=0;
		try{
			this.mongoClient=new MongoClient();
			this.database = mongoClient.getDatabase("PF08");

			MongoCollection<Document> collection = database.getCollection("Partidas");

			Document query = new Document("_id", new ObjectId(usuario.getIdPartida()));

			FindIterable<Document> cursor = collection.find(query);

			for (Document doc : cursor) {
				@SuppressWarnings("unchecked")
				ArrayList<Document> jugadores= (ArrayList<Document>) doc.get("jugadores");
				for (Document jugador : jugadores) {

					if(jugador.getInteger("estado")==1){
						actualizaCerebros(usuario,cerebros,posicion);
					}
					posicion++;

				}
			}


		} catch (MongoException e) {
			throw new ErrorMongo("Un humano ha matado a nuestro hermano zombie MongoDB.");
		}finally {
			cerrarConexion();
		}

	}

	/**
	 * Metodo que actualiza el numero de cerebros conseguidos por el jugador. 
	 */
	private void actualizaCerebros(BeanUsuario usuario, int cerebros,int pos) {
		try{
			this.mongoClient=new MongoClient();
			this.database = mongoClient.getDatabase("PF08");
			MongoCollection<Document> collection = database.getCollection("Partidas");

			//Actualizo el usuario en la partida en la BD
			collection.updateOne(new Document("_id", new ObjectId(usuario.getIdPartida())),
					new Document("$inc", new Document("jugadores."+pos+".cerebros",cerebros)));

		} catch (MongoException e) {
			throw new ErrorMongo("Un humano ha matado a nuestro hermano zombie MongoDB.");
		}finally {
			cerrarConexion();
		}

	}

	/**
	 * Metodo que devuelve si el usuario es el creador de la partida.
	 * @param usuario
	 * @return
	 */
	public boolean usuarioCreador(BeanUsuario usuario){
		boolean encontrado=false;

		try{
			this.mongoClient=new MongoClient();
			this.database = mongoClient.getDatabase("PF08");
			MongoCollection<Document> collection = database.getCollection("Partidas");

			Document query = new Document("_id", new ObjectId(usuario.getIdPartida())).append("Nombre",usuario.getLogin());

			FindIterable<Document> cursor = collection.find(query);

			for (Document doc : cursor) {

				encontrado=true;
			}


		} catch (MongoException e) {
			throw new ErrorMongo("Un humano ha matado a nuestro hermano zombie MongoDB.");
		}finally {
			cerrarConexion();
		}
		return encontrado;

	}

	/**
	 * Metodo que marca la partida como finalizada(estado 3) y si el jugador ha conseguido mas de 12 cerebros
	 * le suma un punto a sus partidas ganadas.
	 * @param usuario
	 */
	public void finalPartida(BeanUsuario usuario) {
		try{
			this.mongoClient=new MongoClient();
			this.database = mongoClient.getDatabase("PF08");
			MongoCollection<Document> collection = database.getCollection("Partidas");

			//Actualizo la partida para finalziar
			collection.updateOne(new Document("_id", new ObjectId(usuario.getIdPartida())),
					new Document("$set", new Document("estado",3)));
			//marco al jugador ganador un punto
			Document query = new Document("_id", new ObjectId(usuario.getIdPartida()));

			FindIterable<Document> cursor = collection.find(query);

			for (Document doc : cursor) {
				@SuppressWarnings("unchecked")
				ArrayList<Document> jugadores= (ArrayList<Document>) doc.get("jugadores");
				for (Document jugador : jugadores) {
					if(jugador.getInteger("cerebros")>12){
						if (usuario.getLogin().equals(jugador.getString("login")))
							actualizaPuntosUsuario(jugador.getString("login"));
					}

				}
			}

		} catch (MongoException e) {
			throw new ErrorMongo("Un humano ha matado a nuestro hermano zombie MongoDB.");
		}finally {
			cerrarConexion();
		}

	}

	/**
	 * Metodo que actualiza el numero de partidas ganadas del usuario.
	 * @param login
	 */
	private void actualizaPuntosUsuario(String login) {
		try{
			this.mongoClient=new MongoClient();
			this.database = mongoClient.getDatabase("PF08");
			MongoCollection<Document> collection = database.getCollection("Usuarios");

			//Actualizo el usuario en la BD
			collection.updateOne(new Document("login",login),
					new Document("$inc", new Document("puntos",1)));

		} catch (MongoException e) {
			throw new ErrorMongo("Un humano ha matado a nuestro hermano zombie MongoDB.");
		}finally {
			cerrarConexion();
		}

	}

	/**
	 * Metodo que mira si la partida del usuario esta finalizada.
	 * @param usuario
	 * @return
	 */
	public boolean partidaFinalizada(BeanUsuario usuario) {
		boolean encontrado=false;
		try{
			this.mongoClient=new MongoClient();
			this.database = mongoClient.getDatabase("PF08");
			MongoCollection<Document> collection = database.getCollection("Partidas");

			Document query = new Document("_id", new ObjectId(usuario.getIdPartida())).append("estado",3);

			FindIterable<Document> cursor = collection.find(query);
			for (Document doc : cursor) {

				encontrado=true;
			}


		} catch (MongoException e) {
			throw new ErrorMongo("Un humano ha matado a nuestro hermano zombie MongoDB.");
		}finally {
			cerrarConexion();
		}

		return encontrado;
	}

	/**
	 * Metodo que busca un usuario en la BD y lo devuelve.
	 * @param login
	 * @return
	 */
	public BeanUsuario dameUsuario(String login) {
		BeanUsuario usuarioP = null;

		try{
			this.mongoClient=new MongoClient();
			this.database = mongoClient.getDatabase("PF08");

			MongoCollection<Document> collection = database.getCollection("Usuarios");

			Document query = new Document("login",login);

			FindIterable<Document> cursor = collection.find(query);

			for (Document doc : cursor) {
				usuarioP = new BeanUsuario();
				usuarioP.setNombre( doc.getString("nombre"));
				usuarioP.setEmail( doc.getString("email"));
				usuarioP.setPuntos((int) doc.getInteger("puntos"));
			}

		} catch (MongoException e) {
			throw new ErrorMongo("Un humano ha matado a nuestro hermano zombie MongoDB.");


		}finally {
			cerrarConexion();
		}

		return usuarioP;
	}

	@ExceptionHandler(ErrorMongo.class)
	public ModelAndView handleAllException(ErrorMongo ex) {
		ModelAndView model = new ModelAndView("errorPage");
		model.addObject("errMsg", ex.getMsg());

		return model;

	}
	
}
