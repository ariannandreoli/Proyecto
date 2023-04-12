package db.jdbc;		//clase intermediaria con la base de datos

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import db.interfaces.DBManager;
import pojo.Entrenador;

public class JDBCManager implements DBManager{
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	final String FICHERO_DDL = "./db/ddl.sql";
	final String FICHERO_DML = "./db/dml.sql";
	
	final String STMT_COUNT = "SELECT count(*) FROM ";
	final String STMT_GET_ENTRENADOR = "SELECT * FROM Entrenador;";
	
	private Statement stmt;
	private PreparedStatement prepCount;
	private Connection c;
	
	@Override
	public void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/pokemon.db");
			stmt = c.createStatement();
			//prepCount = c.prepareStatement(STMT_COUNT);
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createTables();
		//initializeTables();
		LOGGER.info("Inicializada la conexión a la base de datos");
		LOGGER.finest("Este es un mensaje finest");
	}
	
//	private void initializeTables() {
//		try {
//			if(countElementsFromTable("Categorias") == 0) {
//				stmt.executeUpdate(readFile(FICHERO_DML));
//				LOGGER.info("Inicializada la tabla Categorias");
//			} else {
//				LOGGER.info("La tabla Categorias ya estaba inicializada");
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		LOGGER.info("Inicializada la base de datos");
//	}

	private void createTables() {
		try {
			stmt.executeUpdate(readFile(FICHERO_DDL));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info("Creadas las tablas de la base de datos");
	}
	
	private String readFile(String fileName) {
		String fileContent = "";
		File file = new File(fileName);
		try (Scanner scanner = new Scanner(file)){
			while (scanner.hasNext()) {
				fileContent += scanner.nextLine();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fileContent;
	}
	
	@Override
	public int countElementsFromTable(String tableName) {
		int count = 0;
		try {
			ResultSet rs = stmt.executeQuery(STMT_COUNT + tableName + ";");
			count = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public ArrayList<Entrenador> getEntrenador() {
		ArrayList<Entrenador> entrenador = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(STMT_GET_ENTRENADOR);
			while(rs.next()) {
				Entrenador entren = new Entrenador(rs.getInt("Id"), rs.getString("Nombre"), rs.getString("Genero"));
				entrenador.add(entren);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entrenador;
	}

	@Override
	public void disconnect() {
		try {
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}