package main;		//lo relacionado con la interfaz 

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
	private static final String[] MENU_ACTUALIZAR_POKEMONES = {"Salir","Add Pokemon", "Delete Pokemon"};
	private static final String[] MENU_CONSULTAR_POKEMONES = {"Salir","Ver Pokemon", "Buscar Pokemon"};
	private static final String[] MENU_GESTIONAR_ENTRENADOR_INGRESADO = null;
	
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
		//verificar que el nombre YY el genero estan en la tabla para hacer el login
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
		String nombrePokemon = askForText("Indique el nombre del pokemon:");		//me devuelve null en vez del pokemon
		Pokemon pokemon = dbman.getPokemonNombre(nombrePokemon);
		System.out.println(pokemon);
	}

	private static void verPokemons() {
		String respuesta = "";
		int offset = 0;
		int limit = 20;
		int size = limit;
		do {
			System.out.println("Mostrando del " + offset + " al " + (offset + limit));
			ArrayList<Pokemon>pokemons = dbman.getPokemonByOrder(offset, limit);
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


	
	private static byte[] readFile(String file) {
        ByteArrayOutputStream bos = null;
        try {
            File f = new File(file);
            try (FileInputStream fis = new FileInputStream(f)) {
				byte[] buffer = new byte[1024];
				bos = new ByteArrayOutputStream();
				for (int len; (len = fis.read(buffer)) != -1;) {
				    bos.write(buffer, 0, len);
				}
			}
        } catch (FileNotFoundException e) {
        	LOGGER.severe("Nombre de fichero erroneo");
        } catch (IOException e) {
        	LOGGER.severe("Error al leer la imagen " + file + "\n" + e.getMessage());
        }
        return bos != null ? bos.toByteArray() : null;
    }
	

	private static void menuRealeasePokemon() {
		ArrayList<Pokemon> pokemones = dbman.getPokemonByNombre(); //se sale con 0 solamente si no ve todos los pokemones
		Pokemon pokemon = selectPokemon(pokemones);
		int result = dbman.releasePokemon(pokemon);
		if(result == 1) {
			System.out.println("El pokemon '" + pokemon.getNombre() + "' se ha dejado ir con éxito");
		} else {
			System.out.println("No se ha podido soltar el pokemon '" + pokemon + "'");
			LOGGER.warning("No se ha podido soltar: " + pokemon);
		}
	}

	private static void menuAddPokemon() {
		String nombre = askForText("Indique el nombre del pokemon:");
		int nivel = askForInt("Indique el nivel del pokemon:");
		String habilidad = askForText("Indique la habilidad del pokemon:");
		String genero = askForText("Indique el genero del pokemon:");
		String rutaP = askForText("Indique la ruta del pokemon:");
		//int id,String nombre, int nivel, String habilidad, String genero, String rutaP
		Pokemon pokemon = new Pokemon ( -1, nombre, nivel , habilidad , genero , rutaP);		//hay que agregar los verdaderos valores del constructor
		dbman.addPokemon(pokemon);
	}
	

	private static void menuRegistro() {
		String nombre = askForText("Indique su nombre:");
		String genero = askForText("Indique su genero:");
		Entrenador entrenador = new Entrenador(0, nombre, genero);
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
				case 2 -> gestionarEntrenador();
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
		int respuesta;
		do {
			respuesta = showMenu(MENU_GESTIONAR_ENTRENADOR);
			switch(respuesta) {
				case 1 -> menuPokemonDelEntrenador();
			}
		} while(respuesta != 0);
	}


	private static void menuPokemonDelEntrenador() {		//hacer un get de los pokemones del entrenador y que muestre caso para cada pokemon que tiene
		System.out.println("Menú Entrenador dentro del Centro Pokemon");
		int respuesta;
		do {
			respuesta = showMenu(MENU_GESTIONAR_ENTRENADOR_INGRESADO);
			switch(respuesta) {
				case 1 -> evolucionaPokemon();
				case 2 -> consutarMiNivel();
			}
		} while(respuesta != 0);
	}


	private static Object consutarMiNivel() {
		// TODO Auto-generated method stub
		return null;
	}

	private static void evolucionaPokemon() {
		System.out.println("El pokemon que selecione sera evolucionado y agregado en la tabla: ");
		String nombrePokemon = askForText("Indique el nombre del pokemon:");		//me devuelve null en vez del pokemon
		Pokemon pokemon = dbman.getPokemonNombre(nombrePokemon);
		System.out.println("Se va a evolucionar a: " + pokemon);
		//COMO HACEMOS ESTO?
	}

	private static void localizarPokemon() {
		System.out.println("El pokemon que selecione sera localizado");
		String nombrePokemon = askForText("Indique el nombre del pokemon:");		//me devuelve null en vez del pokemon
		Pokemon pokemon = dbman.getPokemonNombre(nombrePokemon);
		System.out.println("Se va a buscara a: " + pokemon);
		//COMO HACEMOS ESTO?
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

