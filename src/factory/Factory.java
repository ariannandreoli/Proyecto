package factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Scanner;
import java.util.logging.Logger;

import pojo.Entrenador;

//INSERTA EL ID Y EL GENERO PERO NO LE PONE NOMBRES ALEATORIOS 

public class Factory {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	private final String FICHERO_NOMBRES = "./db/nombres.csv";
	private static int id = 1;
	private String[] nombres;
	
	public Factory() {
		nombres = readFile(FICHERO_NOMBRES);
	}
	
	private String[] readFile(String fileName) {
		String fileContent = "";
		File file = new File(fileName);
		try (Scanner scanner = new Scanner(file)){
			while (scanner.hasNext()) {
				fileContent += scanner.nextLine() + "\n";
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] lineas = fileContent.split("\\r?\\n");
		LOGGER.info("Procesadas " + lineas.length + " lineas del fichero " + fileName);
		return lineas;
	}
	
	public Entrenador generarEntrenadorAleatorio() {
		Entrenador entrenador = new Entrenador();	
		String nombre = nombres[randomInt(nombres.length)];
		entrenador.setNombre(nombre);
		entrenador.setGenero("M");		//como meter el genero aleatorio tambien? 
		id++;
		return entrenador;
	}
	
	
	private static int randomInt(int max) {
		return (int) (Math.random() * max);
	}
}