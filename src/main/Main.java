package main;		//lo relacionado con la interfaz 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Logger;

import db.interfaces.DBManager;
import db.jdbc.JDBCManager;
import logging.MyLogger;
import pojo.Entrenador;

public class Main {
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	public static DBManager dbman;
	public static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	private final static String[] MENU_PRINCIPAL = {"Salir", "Menú Entrenador", "Menú Centro Pokemon"};
	private final static String[] MENU_CENTRO_POKEMON = {"Salir", "Localizar Pokemon", "Gestionar Entrenador"};
	private final static String[] MENU_ENTRENADOR = {"Salir", "Registrarse", "Log in"};
	
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
		String nombre = askForText("Indique su nombre:");
		String genero = askForText("Indique su genero:");
		System.out.printf("Entrenador: ", nombre, " bienvenido."); 
		
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

