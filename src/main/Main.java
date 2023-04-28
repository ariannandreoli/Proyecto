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
	private final static String[] MENU_ENTRENADOR_LOGGED = {"Salir", "Actualizar Pokedex", "Consultar Pokedex", "Capturar Pokemon"};
	private static final String[] MENU_GESTIONAR_ENTRENADOR = {"Salir", "Ingresar con ID"};
	private static final String[] MENU_ACTUALIZAR_POKEDEX = {"Salir","Add Pokemon", "Actualizar Pokedex"};
	
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
				case 1 -> menuRegistro();  //queremos que se registren y hagan login po que directamente entren sin verificacion? 
				case 2 -> menuLogin();
			}
		} while(respuesta != 0);
	}
	
	private static void menuLogin() {
		String nombre = askForText("Indique su nombre:");
		String genero = askForText("Indique su genero:");
		System.out.println("Estas loggeado");
		int respuesta;
		do {
			respuesta = showMenu(MENU_ENTRENADOR_LOGGED);
			switch(respuesta) {
				case 1 -> actualizarPokedex();  //queremos que se registren y hagan login po que directamente entren sin verificacion? 
				case 2 -> consultarPokedex();
				case 3 -> capturarPokemon();
			}
		} while(respuesta != 0);
	}
		
	private static Object capturarPokemon() {
		// TODO Auto-generated method stub
		return null;
	}

	private static void consultarPokedex() {
		
	}
	
	private static void actualizarPokedex() {
		System.out.println("Actualizando Pokedex");
			int respuesta;
			do {
				respuesta = showMenu(MENU_ACTUALIZAR_POKEDEX);
				switch(respuesta) {
					case 1 -> menuAddPokemon();
					case 2 -> menuDeletePokemon();
					case 3 -> menuAddImagenPokemon();
				}
			} while(respuesta != 0);
		}



		private static Object menuAddImagenPokemon() {
		/*//Categoria categoria = selectCategoria();
		ArrayList<Pokemon> pokemons = dbman.getPokemonByNombre();
		Pokemon pokemon = selectPokemon(pokemons);
		System.out.println("Indique el nombre del archivo:");
		String ruta = PATH_IMAGES;
		try {
			ruta += reader.readLine();
			LOGGER.info(ruta);
			byte[] imagen = readFile(ruta);
			//TODO Error si la imagen es null
			pokemon.setImagen(imagen);
			boolean exito = dbman.addImagenProducto(pokemon);
			if (exito) {
				System.out.println("La imagen se ha añadido con éxito");
			} else {
				System.out.println("Ha habido un error al añadir la imagen");
			}
		} catch (IOException e) {
			LOGGER.warning("Error al añadir imagen " + ruta + "\n" + e.toString());
			System.out.println("Ha habido un error al añadir la imagen");
		}*/
			return null;	
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
	

	private static void menuDeletePokemon() {
		//Categoria categoria = selectCategoria();
		ArrayList<Pokemon> pokemons = dbman.getPokemonByNombre();
		Pokemon pokemon = selectPokemon(pokemons);
		int result = dbman.deletePokemon(pokemon);
		if(result == 1) {
			System.out.println("El pokemon '" + pokemon.getNombre() + "' se ha eliminado con éxito");
		} else {
			System.out.println("No se ha podido eliminar el pokemon '" + pokemon + "'");
			LOGGER.warning("No se ha podido eliminar: " + pokemon);
		}
	}

	private static void menuAddPokemon() {
		//Categoria categoria = selectCategoria();
		String nombre = askForText("Indique el nombre del pokemon:");
		//int id,String nombre, int nivel, String habilidad, String genero, int rutaP
		Pokemon pokemon = new Pokemon (-1, nombre, 0 , "" , "" , 0);		//hay que agregar los verdaderos valores del constructor
		dbman.addPokemon(pokemon);
	}
	

	private static Object menuActualizarDescripcion() {
		// TODO Auto-generated method stub
		return null;
	}

	private static void menuRegistro() {
		String nombre = askForText("Indique su nombre:");
		String genero = askForText("Indique su genero:");
		Entrenador entrenador = new Entrenador(0, nombre, genero);
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


	private static Object menuPokemonDelEntrenador() {		//hacer un get de los pokemones del entrenador y que muestre caso para cada pokemon que tiene
		// TODO Auto-generated method stub					//Luego crear un menu donde seleccionando el pokemon se evolucione, cpnsulte nivel o salga
		return null;
	}


	private static Object localizarPokemon() {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static Pokemon selectPokemon(ArrayList<Pokemon> pokemons) {
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

