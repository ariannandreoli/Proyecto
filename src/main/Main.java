package main;		//lo relacionado con la interfaz 
//QUITAR LA ENTIDAD DE POKEDEX

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;

import db.interfaces.DBManager;
import db.jdbc.JDBCManager;
import logging.MyLogger;
import pojo.Entrenador;
import pojo.Pokemon;

public class Main {
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public static DBManager dbman;
	public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private final static String[] MENU_PRINCIPAL = {"Salir", "Menú Entrenador", "Menú Centro Pokemon"};
	private final static String[] MENU_CENTRO_POKEMON = {"Salir", "Localizar Pokemon", "Gestionar Entrenador"};
	private final static String[] MENU_ENTRENADOR = {"Salir", "Registrarse", "Log in"};
	private final static String[] MENU_ENTRENADOR_LOGGED = {"Salir", "Actualizar Pokemones", "Consultar Pokemones", "Capturar Pokemon"};
	private static final String[] MENU_GESTIONAR_ENTRENADOR = {"Salir", "Ingresar con ID"};
	private static final String[] MENU_GESTIONAR_ENTRENADOR_ID= {"Salir", "Mostrar Pokemon del entrenador"};
	private static final String[] MENU_ACTUALIZAR_POKEMONES = {"Salir","Add Pokemon", "Delete Pokemon"};
	private static final String[] MENU_CONSULTAR_POKEMONES = {"Salir","Ver Pokemon", "Buscar Pokemon"};
	private static final String[] MENU_GESTIONAR_ENTRENADOR_INGRESADO = {"Salir","Evolucionar un Pokemon", "Subir nivel de un Pokemon"};
	
	
	public static void main(String[] args) {
		MyLogger.setupFromFile();
		dbman = new JDBCManager();
		dbman.connect();
		System.out.println("¡Bienvenido al mundo Pokemon!");
		int respuesta;
		do {
			respuesta = showMenu(MENU_PRINCIPAL);
			switch(respuesta) {
				case 1 -> menuEntrenador();
				case 2 -> menuCentroPokemon();
			}
		} while(respuesta != 0);
		System.out.println("¡Gracias!");
		dbman.disconnect();
	}
	
	private static void menuEntrenador() {
		System.out.println("Menú Entrenador");
		int respuesta;
		do {
			respuesta = showMenu(MENU_ENTRENADOR);
			switch(respuesta) {
				case 1 -> menuRegistro();  
				case 2 -> menuLogin();
			}
		} while(respuesta != 0);
	}
	
	private static void menuLogin() {
		//verificar que el nombre Y el genero estan en la tabla para hacer el login
		String nombre = askForText("Indique su nombre:");
		String genero = askForText("Indique su genero:");
		System.out.println("Estas loggeado");
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
		
	private static Object menuCapturarPokemon() {
		// TODO Auto-generated method stub
		return null;
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
		String nombrePokemon = askForText("Indique el nombre del pokemon:");		//ME DEVUELVE NULL SI ELIJO UN NOMBRE VALIDO 
		Pokemon pokemon = dbman.getPokemonByNombre(nombrePokemon);
		System.out.println(pokemon);
	}

	private static void verPokemons() {		//NO ME DEJA SALIR SI SELECCIONO 0
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
		} while(respuesta.equals("") || size < limit);
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

	

	private static void menuRealeasePokemon() {				//elimina un pokemon de la tabla PERO DA UN ERROR BIEN RARO
		verPokemons();
		int id = askForInt("Inserta el id del pokemon que quiere eliminar: ");
		Pokemon pokemon = dbman.getPokemonById(id); 
		int result = dbman.releasePokemon(id);
		if(result == 1) {
			System.out.println("El pokemon con id:  '" + id + "' se ha dejado ir con éxito");
		} else {
			System.out.println("No se ha podido soltar el pokemon con id:  '" + id + "'");
			LOGGER.warning("No se ha podido soltar el pokemon con id: " + id);
		}
	}

	private static void menuAddPokemon() {					//agrega un pokemon a la tabla
		String nombre = askForText("Indique el nombre del pokemon:");
		int nivel = askForInt("Indique el nivel del pokemon:");
		String habilidad = askForText("Indique la habilidad del pokemon:");
		String genero = askForText("Indique el genero del pokemon:");
		int rutaP = askForInt("Indique la ruta del pokemon:");
		Pokemon pokemon = new Pokemon ( -1, nombre, nivel , habilidad , genero , rutaP);		//hay que agregar los verdaderos valores del constructor
		dbman.addPokemon(pokemon);
	}
	

	private static void menuRegistro() {
		String nombre = askForText("Indique su nombre:");
		String genero = askForText("Indique su genero:");
		Entrenador entrenador = new Entrenador(0, nombre.toUpperCase(), genero.toUpperCase());
		System.out.println("¡Esta registrado, por favor haga login!");
		dbman.addEntrenador(entrenador);
	}
	
	
	private static void menuCentroPokemon() {
		System.out.println("Menú Centro Pokemon");
		int respuesta;
		do {
			respuesta = showMenu(MENU_CENTRO_POKEMON);
			switch(respuesta) {
				case 1 -> localizarPokemon();
				case 2 -> gestionarEntrenador();		//hacer un login desde el centro
			}
		} while(respuesta != 0);
	}
	
	
	private static void gestionarEntrenador() {
		System.out.println("Menú Entrenador dentro del Centro Pokemon");
		int respuesta;
		do {
			respuesta = showMenu(MENU_GESTIONAR_ENTRENADOR);
			switch(respuesta) {
				case 1 -> menuIdEntrenador();
			}
		} while(respuesta != 0);
	}


	private static void menuIdEntrenador() {
		System.out.println("Menú Entrenador dentro del Centro Pokemon");
		System.out.println("Va a tener que seleccionar el id para loggearse: ");
		int respuesta;
		do {
			respuesta = showMenu(MENU_GESTIONAR_ENTRENADOR_ID);
			switch(respuesta) {
				case 1 -> menuPokemonDelEntrenador();
			}
		} while(respuesta != 0);
	}

	private static void menuPokemonDelEntrenador() {		//hacer un get de los pokemones del entrenador y que muestre caso para cada pokemon que tiene
		System.out.println("Menú Entrenador dentro del Centro Pokemon, se loggeo con su id: ");
		int respuesta;
		do {
			respuesta = showMenu(MENU_GESTIONAR_ENTRENADOR_INGRESADO);
			switch(respuesta) {
				case 1 -> evolucionarPokemon();
				case 2 -> subirMiNivel();
			}
		} while(respuesta != 0);
	}


	private static void subirMiNivel() {		//DA PROBLEMAS
		verPokemons();
		int id = askForInt("Inserta el id del pokemon que quiere subir de nivel: ");
		Pokemon pokemon = dbman.getPokemonById(id);
		if (pokemon != null)
			System.out.println("El pokemon actual es " + pokemon.getNombre());
			int nuevoNivel = askForInt("Indique el nuevo nivel del pokemon :");
			if(nuevoNivel != 0) {
				pokemon.setNivel(nuevoNivel);
			}
			dbman.levelUp(pokemon);
		}

	private static void evolucionarPokemon() {		
		System.out.println("El pokemon que selecione sera evolucionado y agregado en la tabla: ");
		String nombrePokemon = askForText("Indique el nombre del pokemon:");
		ArrayList<Pokemon> pokemons = dbman.getListPokemonByNombre(nombrePokemon); //que hago aqui 
		Pokemon pokemon = selectPokemon(pokemons);
		if (pokemon != null)
			System.out.println("El pokemon actual es " + pokemon.getNombre());
			String nuevoNombre = askForText("Indique la evolucion del pokemon :");
		if(!nuevoNombre.equals("")) {
			pokemon.setNombre(nuevoNombre);
		}
		System.out.println("El nivel actual del pokemon es " + pokemon.getNivel());	//deberia mantener el mismo nivel
		int nuevoNivel = askForInt("Indique el nuevo nivel del pokemon:");
		if(nuevoNivel != 0) {
			pokemon.setNivel(nuevoNivel);
		}
		System.out.println("La habilidad actual del pokemon es " + pokemon.getHabilidad());
		String nuevaHabilidad = askForText("Indique la nueva habilidad del pokemon:");
		if(!nuevaHabilidad.equals("")) {
			pokemon.setHabilidad(nuevaHabilidad);
		}
		System.out.println("El genero actual del pokemon es " + pokemon.getGenero());
		String nuevoGenero = askForText("Indique la nueva habilidad del pokemon:");
		if(!nuevoGenero.equals("")) {
			pokemon.setGenero(nuevoGenero);
		}
		System.out.println("La ruta actual del pokemon es " + pokemon.getRutaP() + " donde se encontrara la evolucion tambien."); //debe mantener la misma ruta

		dbman.evolvePokemon(pokemon);
		
	}

	private static void localizarPokemon() {
		System.out.println("El pokemon que selecione sera evolucionado y agregado en la tabla: ");
		String nombrePokemon = askForText("Indique el nombre del pokemon:");		//me devuelve null en vez del pokemon
		Pokemon pokemon = dbman.getPokemonByNombre(nombrePokemon);
		System.out.println("Se va a evolucionar a: " + pokemon);
		//ESTO NO TIENE SENTIDO PORQUE EL CENTRO NO PUEDE ACCEDER A LAS RUTAS
		//QUE TAL SI LO PODEMOS SUBIR DE NIVEL AL POKEMON? 

	}
			
	
	private static Pokemon selectPokemon(ArrayList<Pokemon> pokemons) {		//no sirve porque dice que el arraylist esta vacio 
		String[] opciones = new String[pokemons.size() + 1];
		opciones[0] = "Cancelar";
		for(int i = 0; i < pokemons.size(); i++) {
			opciones[i + 1] = pokemons.get(i).getNombre();
		}
		int numPokemon = showMenu(opciones);
		if(numPokemon == 0) {
			return null;
		}
		return pokemons.get(numPokemon - 1);
	}

	private static float askForFloat(String text) {
		float resultado = Float.parseFloat(askForText(text));
		//TODO Comprobar que se puede convertir a float
		return resultado;
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
	
	private static Entrenador selectEntrenador() {
		System.out.println("Elija el Entrenador del pokemon:");
		ArrayList<Entrenador> entrenadores = dbman.getEntrenador();
		String[] opciones = new String[entrenadores.size() + 1];
		opciones[0] = "Cancelar";
		for(int i = 0; i < entrenadores.size(); i++) {
			opciones[i + 1] = entrenadores.get(i).getNombre();
		}
		int numEntrenador = showMenu(opciones);
		return entrenadores.get(numEntrenador - 1);
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
}

