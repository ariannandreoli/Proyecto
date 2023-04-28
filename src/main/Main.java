package main;		//lo relacionado con la interfaz 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;

import db.interfaces.DBManager;
import db.jdbc.JDBCManager;
import logging.MyLogger;
import pojo.Categoria;
import pojo.Entrenador;
import pojo.Producto;

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
					case 3 -> menuUpdatePokemon();
					case 4 -> menuAddImagenPokemon();
				}
			} while(respuesta != 0);
		}

	private static void actualizarMenu() {
		System.out.println("Menú Empleado");
		int respuesta;
		do {
			respuesta = showMenu(MENU_ACTUALIAR_MENU);
			switch(respuesta) {
				case 1 -> menuAddProducto();
				case 2 -> menuDeleteProducto();
				case 3 -> menuUpdateProducto();
				case 4 -> menuAddImagenProducto();
			}
		} while(respuesta != 0);
	}

	private static void menuUpdateProducto() {
		Categoria categoria = selectCategoria();
		ArrayList<Producto> productos = dbman.getProductosByCategoria(categoria);
		Producto producto = selectProducto(productos);
		if (producto != null) {
			System.out.println("El nombre actual del producto es " + producto.getNombre());
			String nuevoNombre = askForText("Indique el nuevo nombre del producto:");
			if(!nuevoNombre.equals("")) {
				producto.setNombre(nuevoNombre);
			}
			System.out.println("El precio actual del producto es " + producto.getPrecio());
			float nuevoPrecio = askForFloat("Indique el nuevo precio del producto:");
			if(nuevoPrecio != 0) {
				producto.setPrecio(nuevoPrecio);
			}
			dbman.updateProducto(producto);
		}
	}

	private static void menuDeleteProducto() {
		Categoria categoria = selectCategoria();
		ArrayList<Producto> productos = dbman.getProductosByCategoria(categoria);
		Producto producto = selectProducto(productos);
		int result = dbman.deleteProducto(producto);
		if(result == 1) {
			System.out.println("El producto '" + producto.getNombre() + "' se ha eliminado con éxito");
		} else {
			System.out.println("No se ha podido eliminar el producto '" + producto + "'");
			LOGGER.warning("No se ha podido eliminar: " + producto);
		}
	}

	private static void menuAddProducto() {
		Categoria categoria = selectCategoria();
		String nombre = askForText("Indique el nombre del producto:");
		float precio = askForFloat("Indique el precio del producto:");
		Producto producto = new Producto(-1, nombre, precio, categoria, null);
		dbman.addProducto(producto);
	}
	

	private static Object menuActualizarDescripcion() {
		// TODO Auto-generated method stub
		return null;
	}

	private static Object menuAddPokemon() {
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

