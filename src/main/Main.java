package main;		//lo relacionado con la interfaz 
//QUITAR LA ENTIDAD DE POKEDEX

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;


import db.interfaces.DBManager;
import db.interfaces.UsuariosManager;
import db.jdbc.JDBCManager;
import db.jpa.JPAUsuariosManager;
import factory.Factory;
import logging.MyLogger;
import pojo.CentroPokemon;
import pojo.Entrenador;
import pojo.EntrenadorCentro;
import pojo.EntrenadorPokemon;
import pojo.Pokemon;
import pojo.PokemonTipo;
import pojo.Rol;
import pojo.Ruta;
import pojo.Tipo;
import pojo.Usuario;

public class Main {
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public static DBManager dbman;
	private static UsuariosManager userman;
	public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private final static String[] MENU_PRINCIPAL = {"Salir", "Login", "Registrarse"};
	private final static String[] MENU_CENTRO_POKEMON = {"Salir", "Localizar Pokemon", "Gestionar Entrenador"};
	private final static String[] MENU_ENTRENADOR_LOGGED = {"Salir", "Actualizar Pokemones", "Consultar Pokemones", "Capturar Pokemon"};
	private static final String[] MENU_ACTUALIZAR_POKEMONES = {"Salir","Add Pokemon", "Delete Pokemon"};
	private static final String[] MENU_CONSULTAR_POKEMONES = {"Salir","Ver Pokemon", "Buscar Pokemon"};
	private static final String[] MENU_GESTIONAR_ENTRENADOR_INGRESADO = {"Salir","Evolucionar un Pokemon", "Subir nivel de un Pokemon", "Ver tipo Pokemon"};
	private static Usuario usuario;
	private static int NUM_USUARIOSE = 1000;
	private static int NUM_USUARIOSC = 10;
	
	public static void main(String[] args) {
		MyLogger.setupFromFile();
		userman = new JPAUsuariosManager();
		userman.connect();
		dbman = new JDBCManager();
		dbman.connect();
		if(dbman.countElementsFromTable("Entrenador") == 0) {
			initializeDB();
		}
		System.out.println("¡Bienvenido al mundo Pokemon!");
		int respuesta;
		do {
			respuesta = showMenu(MENU_PRINCIPAL);
			switch(respuesta) {
				case 1 -> login();			
				case 2 -> registrarse();
			}
		} while(respuesta != 0);
		
		marshallingXML();
		unmarshallingXML();
		
		System.out.println("¡Gracias!");
		dbman.disconnect();
		
	}
	
	private static void initializeDB() {
		Factory factory = new Factory();
		MessageDigest md;
		List<Rol> roles = userman.getRoles();
			for(int i = 0; i < NUM_USUARIOSE; i++) {
				//TODO Añadir los USUARIOS a ENTRENADORES en batch
				String pass = "1234";
				byte[] hash = null;
				try {
					md = MessageDigest.getInstance("MD5");
					md.update(pass.getBytes());
					hash = md.digest();
				} catch (NoSuchAlgorithmException e){
					e.printStackTrace();
				}
				Entrenador entrenador = factory.generarEntrenadorAleatorio();
				Usuario usuario = new Usuario(entrenador.getNombre().toUpperCase(), hash, roles.get(0));
				userman.addUsuario(usuario);
				entrenador.setId(usuario.getId());
				dbman.addEntrenador(entrenador);
				
			} for(int i = 0; i < NUM_USUARIOSC; i++) {
				//TODO Añadir los USUARIOS a CENTRO en batch
				String pass = "1234";
				byte[] hash = null;
				try {
					md = MessageDigest.getInstance("MD5");
					md.update(pass.getBytes());
					hash = md.digest();
				} catch (NoSuchAlgorithmException e){
					e.printStackTrace();
				}
				CentroPokemon centro = factory.generarCentroAleatorio();
				Usuario u = new Usuario(centro.getTrabajadores().toUpperCase(), hash, roles.get(1));
				userman.addUsuario(u);
				centro.setId(u.getId());
				dbman.addCentro(centro);
			}
			ArrayList<Pokemon> pokemons = dbman.getPokemons();
			ArrayList<Entrenador> entrenadores = dbman.getEntrenadores();
			for(int i = 0; i < entrenadores.size(); i++) {
				Entrenador entrenador = entrenadores.get(i);
				Pokemon pokemon = pokemons.get(randomInt(pokemons.size()));
				int cantidad = randomInt(4) + 1;
				EntrenadorPokemon ep = new EntrenadorPokemon(entrenador, pokemon, cantidad);
				dbman.addEntrenadorPokemon(ep);
				//TODO Añadir más de un entrenador por pokemon
				}
			
			ArrayList<CentroPokemon> centros = dbman.getCentros();
			for(int i = 0; i < entrenadores.size(); i++) {
				Entrenador entrenador = entrenadores.get(i);
				CentroPokemon c = centros.get(randomInt(centros.size()));
				EntrenadorCentro ec = new EntrenadorCentro (entrenador, c);
				dbman.addEntrenadorCentro(ec);
				//TODO Añadirun entrenador en cada centro 
				}
			
	}
	
	private static void login() {
		String nombre = askForText("Indique su nombre:");
		String password = askForText("Indique su contraseña:");
		usuario = userman.checkLogin(nombre.toUpperCase(), password);
		if (usuario == null) {
			System.out.println("Nombre o contraseña incorrectos");
		} else {
			switch(usuario.getRol().getNombre()) {
				case "entrenador" -> menuEntrenador();
				case "centro_pokemon" -> menuCentroPokemon();
			}
		}
	}
	
	private static void registrarse() {
		try {
			String nombre = askForText("Indique su nombre:");
			String pass = askForText("Indique su contraseña:");
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(pass.getBytes());
			byte[] hash = md.digest();
			System.out.println(userman.getRoles());
			int rolId = askForInt("Indique el id del rol:");
			//TODO Asegurarse que el id es válido
			Rol rol = userman.getRolById(rolId);
			Usuario usuario = new Usuario(nombre.toUpperCase(), hash, rol);
			rol.addUsuario(usuario);
			userman.addUsuario(usuario);
			if(rol.getNombre().equals("entrenador")) {
				String genero = askForText("Indique su genero:");
				Entrenador entrenador = new Entrenador(0, nombre.toUpperCase(), genero.toUpperCase());
				dbman.addEntrenador(entrenador);
			} else {
				String trabajador= askForText("Indique su nombre:");
				String ciudad = askForText("Indique su ciudad:");
				CentroPokemon centro = new CentroPokemon(usuario.getId(), trabajador.toUpperCase(), ciudad.toUpperCase() );
				dbman.addCentro(centro);
			}
		} catch(NoSuchAlgorithmException e) {
			LOGGER.warning("Error en el registro\n" + e);
		}
	}
	
	private static void menuEntrenador() {
		System.out.println("Menu entrenador: estas loggeado");
		int respuesta;
		do {
			respuesta = showMenu(MENU_ENTRENADOR_LOGGED);
			switch(respuesta) {
				case 1 -> menuActualizarPokemones();  
				case 2 -> menuConsultarPokemones();
				case 3 -> menuCapturarPokemon();
			}
		} while(respuesta != 0);
	}
		
	
	private static void menuCapturarPokemon() {
		String nombreE = askForText("Indique su nombre: ");		 
		Entrenador e = dbman.getEntrenadorByNombre(nombreE.toUpperCase());
		System.out.println(e);
		verMisPokemons();
		int idP = askForInt("Indique el del pokemon: ");
		//if ()
		//deberiamos chequear si el id que pone es igual al que tiene, si lo es aumenta la cantidad a uno, sino puedes añadir un pokemon nuevo
		Pokemon p = dbman.getPokemonById(idP);
		int cantidad = askForInt("Indique la cantidad de pokemones: ");		 
		EntrenadorPokemon ep = new EntrenadorPokemon (e, p, cantidad);
		System.out.println("Ahora el entrenador " + ep.getEntrenador() + " tiene " + ep.getCantidad() + " de " + ep.getPokemon());
		dbman.addEntrenadorPokemon(ep);
		
	}

	private static void menuConsultarPokemones() {
		int respuesta;
		do {
			respuesta = showMenu(MENU_CONSULTAR_POKEMONES);
			switch(respuesta) {
				case 1 -> verPokemons();
				case 2 -> menuBuscarPokemonByNombre();
			}
		} while(respuesta != 0);
	}
	
	private static void menuBuscarPokemonByNombre() {
		String nombrePokemon = askForText("Indique el nombre del pokemon:");		 
		Pokemon pokemon = dbman.getPokemonByNombre(nombrePokemon.toUpperCase());
		System.out.println(pokemon);
	}

	private static void verPokemons() {		
		String respuesta = "";
		int offset = 0;
		int limit = 20;
		int size = limit;
		int idLimit = Integer.MAX_VALUE;
		do {
			System.out.println("Mostrando del " + offset + " al " + (offset + limit));
			ArrayList<Pokemon>pokemons = dbman.getPokemonByOrder(offset, limit, idLimit);
			size = pokemons.size();
			for (int i = 0; i < size; i++) {
				System.out.println(pokemons.get(i));
			}
			respuesta = askForText("Pulse Enter para continuar o 0 para salir");
			offset += limit;
		} while(respuesta.equals("") && size == limit);
	}
	
	private static void verMisPokemons() {
		int id=askForInt("Inserta tu id de entrenador: ");
		ArrayList<Integer>pokemons = dbman.getPokemonByEntrenador(id);
		for (int i = 0; i < pokemons.size(); i++) {
			System.out.println(pokemons.get(i));
			}
			pokemons.contains(id);
	}

	private static void menuActualizarPokemones() {
		System.out.println("Actualizando Pokedex");
			int respuesta;
			do {
				respuesta = showMenu(MENU_ACTUALIZAR_POKEMONES);
				switch(respuesta) {
					case 1 -> menuAddPokemon();
					case 2 -> menuRealeasePokemon();
				}
			} while(respuesta != 0);
		}


	private static void menuRealeasePokemon() {		
		verPokemons();
		int id = askForInt("Inserta el id del pokemon que quiere eliminar: ");
		int result = dbman.releasePokemon(id);
		if(result == 1) {
			System.out.println("El pokemon con id:  '" + id + "' se ha dejado ir con éxito");
		} else {
			System.out.println("No se ha podido soltar el pokemon con id:  '" + id + "'");
			LOGGER.warning("No se ha podido soltar el pokemon con id: " + id);
		}
	}

	private static void menuAddPokemon() {		
	    String nombre = askForText("Indique el nombre del pokemon:");
	    int nivel = askForInt("Indique el nivel del pokemon:");
	    String habilidad = askForText("Indique la habilidad del pokemon:");
	    String genero = askForText("Indique el genero del pokemon:");
	    int rutaId = askForInt("Indique el id de la ruta:");
	    Ruta ruta = new Ruta(rutaId, rutaId); 
	    Pokemon pokemon = new Pokemon(-1, nombre.toUpperCase(), nivel , habilidad.toUpperCase(), genero.toUpperCase(), ruta);
	    dbman.addPokemon(pokemon);
	}
	
	private static void menuCentroPokemon() {
		System.out.println("Menú Centro Pokemon");
		int respuesta;
		do {
			respuesta = showMenu(MENU_CENTRO_POKEMON);
			switch(respuesta) {
				case 1 -> localizarPokemon();
				case 2 -> gestionarEntrenador();	
				
			}
		} while(respuesta != 0);
	}
	
	private static void localizarPokemon() {
		System.out.println("El pokemon que selecione sera localizado: ");
		String nombrePokemon = askForText("Indique el nombre del pokemon:");
		Pokemon pokemon = dbman.getPokemonByNombre(nombrePokemon.toUpperCase());
		if (pokemon != null) {
		System.out.println("Se va a localizar a: " + pokemon.getNombre());
		System.out.println("Se encuentra en la ruta: " + pokemon.getRutaP());
    	} else {
        System.out.println("No se ha encontrado el pokemon " + nombrePokemon);
    	}
	}
	
	private static void gestionarEntrenador() {
		System.out.println("Login del entrenador en el centro pokemon: ");
		String nombre = askForText("Indique su nombre:");
		String password = askForText("Indique su contraseña:");
		usuario = userman.checkLogin(nombre.toUpperCase(), password);
		if (usuario == null) {
			System.out.println("Nombre o contraseña incorrectos");
		} else {
			switch(usuario.getRol().getNombre()) {
				case "entrenador" -> menuPokemonDelEntrenador();
			}
		}
	}

	private static void menuPokemonDelEntrenador() {
		System.out.println("Menú Entrenador dentro del Centro Pokemon, ESTA LOGGEADO: ");
		int respuesta;
		do {
			respuesta = showMenu(MENU_GESTIONAR_ENTRENADOR_INGRESADO);
			switch(respuesta) {
				case 1 -> crearXml();
				case 2 -> subirMiNivel();
				case 3 -> verTipoPokemon();
			}
		} while(respuesta != 0);
	}


	private static void crearXml() {
		System.out.println("Se creara el fichero pokemon.xml con esta informacion: ");
		marshallingXML();
		
		System.out.println("Con el fichero pokemon.xml obtenemos el siguiente pokemon: ");
		unmarshallingXML();

	}

	private static void subirMiNivel() {	//sube de nivel al pokemon de la tabla completa ya que todos los entrenadores tienen pokemones con iguales cualidades 
		
		String nombreE = askForText("Indique su nombre: ");		 
		Entrenador e = dbman.getEntrenadorByNombre(nombreE.toUpperCase());
		System.out.println(e);
		System.out.println("Se mostraran sus pokemones, elija cual quiere subir de nivel: ");
		verMisPokemons();
		int id = askForInt("Inserta el id del pokemon que tiene y que va a subir de nivel: ");
		Pokemon pokemon = dbman.getPokemonById(id);
		if (pokemon != null)
			System.out.println("El pokemon actual es " + pokemon.getNombre());
			int nuevoNivel = askForInt("Indique el nuevo nivel del pokemon :");
			if(nuevoNivel != 0) {
				pokemon.setNivel(nuevoNivel);
			}
			dbman.levelUp(pokemon);
		}

	
	private static void verTipoPokemon() {
		String nombreE = askForText("Indique su nombre: ");		 
		Entrenador e = dbman.getEntrenadorByNombre(nombreE.toUpperCase());
		System.out.println(e);
		System.out.println("Se mostraran sus pokemones, elija de cual quiere ver su tipo: ");
		verMisPokemons();
		int idP = askForInt("Indique el Id del pokemon que quiere ver su tipo: ");
		ArrayList<Integer> tipos = dbman.getTipoByPokemon(idP);
		for (int i = 0; i < tipos.size(); i++) {
			System.out.println("Sus tipos son: " + tipos.get(i));
			}
		int idT = askForInt("Indique el Id del tipo para ver sus caracteristicas: ");
		Tipo t = dbman.getTipoById(idT);
		System.out.println("El pokemon es de tipo: " + t);
	}


	
	private static int askForInt(String text) {
		int resultado = Integer.parseInt(askForText(text));
		//TODO Comprobar que se puede convertir a int
		return resultado;
	}
	
	private static String askForText(String text) {
		System.out.println(text);
		String resultado = "";
		try {
			resultado = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultado;
	}
	

	private static int showMenu(String[] menu) {
		int respuesta = -1;
		while(respuesta >= menu.length || respuesta < 0) {
			System.out.println("Elija la opción:");
			for(int i = 1; i < menu.length; i++) {
				System.out.println(i + ". " + menu[i]);
			}
			System.out.println("0. " + menu[0]);
			System.out.println("Introduzca la opción: ");
			try {
				respuesta = Integer.parseInt(reader.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			} catch (NumberFormatException e) {
				LOGGER.warning("Error al parsear el input del usuario: " + respuesta);
				System.out.println("Por favor introduzca un número válido");
			}
		}
		return respuesta;
	}
	
	
	private static int randomInt(int max) {
		return (int) (Math.random() * max);
	}
	

	
private static void unmarshallingXML() {
		
		try {
			// Creamos el JAXBContext
			JAXBContext jaxbC = JAXBContext.newInstance(Pokemon.class);
			// Creamos el JAXBMarshaller
			Unmarshaller jaxbU = jaxbC.createUnmarshaller();
			// Leyendo un fichero
			File XMLfile = new File("./src/xml/pokemons.xml");
			// Creando el objeto
			Pokemon p = (Pokemon) jaxbU.unmarshal(XMLfile);
			// Escribiendo por pantalla el objeto
			System.out.println(p);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private static void marshallingXML() {
		List<Pokemon> pokemons = dbman.getPokemons();
		Pokemon p = pokemons.get(1);
		List<PokemonTipo> pt = dbman.getPokemonTipoByPokemon(p);
		p.setPokemonTipos(pt);
		
		try {
			JAXBContext jaxbC = JAXBContext.newInstance(Pokemon.class);
			// Creamos el JAXBMarshaller
			Marshaller jaxbM = jaxbC.createMarshaller();
			// Formateo bonito
			jaxbM.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
			// Escribiendo en un fichero
			File XMLfile = new File("./src/xml/pokemons.xml");
			jaxbM.marshal(p, XMLfile);
			// Escribiendo por pantalla
			jaxbM.marshal(p, System.out);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
}

