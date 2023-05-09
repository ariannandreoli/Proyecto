package db.jdbc;		//clase intermediaria con la base de datos

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Logger;

import db.interfaces.DBManager;
import factory.Factory;
import pojo.CentroPokemon;
import pojo.Entrenador;
import pojo.EntrenadorPokemon;
import pojo.Pokemon;
import pojo.Ruta;

public class JDBCManager implements DBManager{
	final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	final String FICHERO_DDL = "./db/ddl.sql";
	final String FICHERO_DML = "./db/dml.sql";
	final String FICHERO_DML_POKEMON = "./db/dml_pokemon.sql";
	final String FICHERO_DML_TIPO = "./db/dml_tipo.sql";
	final String FICHERO_DML_CENTRO = "./db/dml_centro.sql";
	final String FICHERO_DML_RUTA = "./db/dml_ruta.sql";
	final String FICHERO_DML_POKEMON_TIPO = "./db/dml_pokemonTipo.sql";
	
	final String STMT_COUNT = "SELECT count(*) FROM ";
	final String STMT_GET_ENTRENADOR = "SELECT * FROM Entrenador" ;
	private final String STMT_GET_POKEMON_BY_ID = "SELECT * FROM Pokemon WHERE Id=";
	private final String STMT_GET_POKEMON_BY_NOMBRE = "SELECT * FROM Pokemon WHERE Nombre =''";
	private final String STMT_GET_ENTRENADOR_BY_ID = "SELECT * FROM Entrenador WHERE Id='";	
	private final String STMT_GET_POKEMONS = "SELECT * FROM Pokemon;";
	private final String STMT_GET_RUTA_BY_ID = "SELECT * FROM Ruta WHERE Id="; 
	
	private final String PREP_ADD_ENTRENADOR = "INSERT INTO Entrenador (Nombre, Genero) VALUES (?,?);";
	private final String PREP_DELETE_POKEMON = "DELETE FROM Pokemon WHERE Id = ?;";
	private final String PREP_ADD_POKEMON = "INSERT INTO Pokemon (Nombre, Nivel, Habilidad, Genero, RutaP) VALUES (?,?,?,?,?);";
	private final String PREP_EVOLVE_POKEMON = "UPDATE Pokemon SET Nombre=?, Nivel=?, Habilidad=?, Genero=? WHERE Id=?;";
	private final String PREP_LEVEL_UP_POKEMON = "UPDATE Pokemon SET Nivel=? WHERE Id=?;";
	private final String PREP_ADD_CENTRO = "INSERT INTO Centro (Id, Trabajadores, Ciudad) VALUES (?,?,?);";
	private final String PREP_ADD_POKEMON_ENTRENADOR = "INSERT INTO 'Entrenador-Pokemon' (IdPokemon, IdEntrenador, Cantidad) VALUES (?,?,?);";
	private Statement stmt;
	private PreparedStatement prepAddEntrenador;
	private PreparedStatement prepAddPokemon;
	private PreparedStatement prepDeletePokemon;
	private PreparedStatement prepEvolvePokemon;
	private PreparedStatement prepLevelUpPokemon;
	private PreparedStatement prepAddCentro;
	private PreparedStatement prepAddEntrenadorPokemon;
	
	private Connection c;
	
	private final int NUM_ENTRENADOR = 1000;
	
	@Override
	public void connect() {
		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:./db/pokemonWorld.db");
			stmt = c.createStatement();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		createTables();
		initializeTables();
		LOGGER.info("Inicializada la conexión a la base de datos");
		LOGGER.finest("Este es un mensaje finest");
	}
	
	
	private void initializeTables() {
		try {
			prepAddEntrenador= c.prepareStatement(PREP_ADD_ENTRENADOR);
			prepDeletePokemon = c.prepareStatement(PREP_DELETE_POKEMON);
			prepAddPokemon= c.prepareStatement(PREP_ADD_POKEMON);
			prepEvolvePokemon = c.prepareStatement(PREP_EVOLVE_POKEMON);
			prepLevelUpPokemon= c.prepareStatement(PREP_LEVEL_UP_POKEMON);
			prepAddCentro = c.prepareStatement(PREP_ADD_CENTRO);
			prepAddEntrenadorPokemon = c.prepareStatement(PREP_ADD_POKEMON_ENTRENADOR);
			Factory factory = new Factory();
			
			if(countElementsFromTable("Pokemon") == 0) {
				stmt.executeUpdate(readFile(FICHERO_DML_POKEMON));
				LOGGER.info("Inicializada la tabla Pokemon");
			} 
			else {
				LOGGER.info("La tabla Pokemon ya estaba inicializada");
			} 
			if(countElementsFromTable("Tipo") == 0) {
				stmt.executeUpdate(readFile(FICHERO_DML_TIPO));
				LOGGER.info("Inicializada la tabla Tipo");
			} 
			else {
				LOGGER.info("La tabla Tipo ya estaba inicializada");
			}
			if(countElementsFromTable("Centro") == 0) {
				stmt.executeUpdate(readFile(FICHERO_DML_CENTRO));
				LOGGER.info("Inicializada la tabla Centro");
			} 
			else {
				LOGGER.info("La tabla Centro ya estaba inicializada");
			}
			if(countElementsFromTable("Ruta") == 0) {
				stmt.executeUpdate(readFile(FICHERO_DML_RUTA));
				LOGGER.info("Inicializada la tabla Ruta");
			} 
			else {
				LOGGER.info("La tabla Ruta ya estaba inicializada");
			}
			if(countElementsFromTable("PokemonTipo") == 0) {
				stmt.executeUpdate(readFile(FICHERO_DML_POKEMON_TIPO));
				LOGGER.info("Inicializada la tabla PokemonTipo");
			} 
			else {
				LOGGER.info("La tabla PokemonTipo ya estaba inicializada");
			}
			if(countElementsFromTable("Entrenador") == 0) {
				for(int i = 0; i < NUM_ENTRENADOR; i++) {
					//TODO Añadir los entrenadores en batch
					Entrenador entrenador = factory.generarEntrenadorAleatorio();
					addEntrenador(entrenador);
				}
				ArrayList<Pokemon> pokemons = getPokemons();
				ArrayList<Entrenador> entrenadores = getEntrenadores();
				for(int i = 0; i < entrenadores.size(); i++) {
					Entrenador entrenador = entrenadores.get(i);
					Pokemon pokemon = pokemons.get(randomInt(pokemons.size()));
					/*Pokemon pokemon = pokemons.get(i);
					Entrenador entrenador = entrenadores.get(randomInt(pokemons.size()));*/
					int cantidad = randomInt(4) + 1;
					EntrenadorPokemon ep = new EntrenadorPokemon(entrenador, pokemon, cantidad);
					addEntrenadorPokemon(ep);
					//TODO Añadir más de un producto por pedido
					}
				LOGGER.info("Inicializadas las tablas Entrenadores, Centro Pokemon , Pokemones, Pokedex, Ruta, Tipo");
			} 
			else {
				LOGGER.info("La tabla Entrenadores ya estaba inicializada");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LOGGER.info("Inicializada la base de datos");
	}
			
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


	private ArrayList<Pokemon> getPokemons() {
		ArrayList<Pokemon> pokemons = new ArrayList<>();
		try (Statement stmt = c.createStatement()){
			ResultSet rs = stmt.executeQuery(STMT_GET_POKEMONS);
			while(rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				int nivel = rs.getInt("Nivel");
				String habilidad = rs.getString("Habilidad");
				String genero = rs.getString("Genero");
				Pokemon pokemon = new Pokemon(id, nombre, nivel, habilidad, genero);
				int idRuta = rs.getInt("RutaP");
				Ruta ruta = getRutaById(idRuta);
				pokemon.setRutaP(ruta);
				pokemons.add(pokemon);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pokemons;
	}


	private Ruta getRutaById(int idRuta) {
		Ruta ruta = null;
		try {
			ResultSet rs = stmt.executeQuery(STMT_GET_RUTA_BY_ID + idRuta);
			if(rs.next()) {
				int id = rs.getInt("Id");
				int nombre = rs.getInt("Nombre");
				ruta = new Ruta(id, nombre);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ruta;
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
	public ArrayList<Entrenador> getEntrenadores() {
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

	@Override
	public boolean addEntrenador(Entrenador entrenador) {
		try {
			ResultSet rs = stmt.executeQuery(STMT_GET_ENTRENADOR_BY_ID + entrenador.getId()+ "';");
			if(rs.next()) {
				return false;
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			prepAddEntrenador.setString(1, entrenador.getNombre());
			prepAddEntrenador.setString(2, entrenador.getGenero());
			prepAddEntrenador.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public Pokemon getPokemonByNombre(String nombrePokemon) {
	    Pokemon pokemon = null;
	    try {
	        PreparedStatement pstmt = c.prepareStatement("SELECT * FROM Pokemon WHERE Nombre = ?");
	        pstmt.setString(1, nombrePokemon);
	        ResultSet rs = pstmt.executeQuery();
	        if (rs.next()) {
	            int id = rs.getInt("Id");
	            String nombre = rs.getString("Nombre");
	            int nivel = rs.getInt("Nivel");
	            String habilidad = rs.getString("Habilidad");
	            String genero = rs.getString("Genero");
	            //int rutaP = rs.getInt("RutaP");
	            pokemon = new Pokemon(id, nombre, nivel, habilidad, genero);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return pokemon;
	}


	@Override
	public int releasePokemon (int id) {		
		int result = 0;
		try {
			prepDeletePokemon.setInt(1, id);
			result = prepDeletePokemon.executeUpdate();
			if(result == 1) {
				LOGGER.info("Pokemon con id " + id + " eliminado con éxito");
			} else {
				LOGGER.info("No existe el pokemon con id " + id);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
		//return pokemon.getNombre();
	}


	@Override
	public boolean addPokemon(Pokemon pokemon) {
		try {
			prepAddPokemon.setString(1, pokemon.getNombre());
			prepAddPokemon.setInt(2, pokemon.getNivel());
			prepAddPokemon.setString(3, pokemon.getHabilidad());
			prepAddPokemon.setString(4, pokemon.getGenero());
			//prepAddPokemon.setInt(5, pokemon.getRutaP().getId());
			prepAddPokemon.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return true;
	}

	@Override
	public ArrayList<Pokemon> getPokemonByOrder(int offset, int limit, int idLimit) {
		String sql = "SELECT * FROM Pokemon WHERE Id < " + idLimit + " ORDER BY Id ASC LIMIT " + limit + " OFFSET " + offset + ";";		ArrayList<Pokemon> pokemons = new ArrayList<>();
		try (Statement stmt = c.createStatement()){
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				int nivel = rs.getInt("Nivel");
				String habilidad = rs.getString("Habilidad");
				String genero = rs.getString("Genero");
				//int rutaP = rs.getInt("RutaP");
				Pokemon pokemon = new Pokemon(id,  nombre,  nivel, habilidad,  genero);
				pokemons.add(pokemon);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pokemons;
		}

	@Override
	public Pokemon getPokemonById(int idPokemon) {
		Pokemon pokemon = null;
		try {
			ResultSet rs = stmt.executeQuery(STMT_GET_POKEMON_BY_ID + idPokemon);
			if(rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				int nivel = rs.getInt("Nivel");
				String habilidad = rs.getString("Habilidad");
				String genero = rs.getString("Genero");
				//int rutaP = rs.getInt("RutaP");
				pokemon = new Pokemon(id, nombre, nivel, habilidad, genero);
				int idRuta = rs.getInt("RutaP");
				Ruta ruta = getRutaById(idRuta);
				pokemon.setRutaP(ruta);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pokemon;
	}
	
	
	@Override
	public void evolvePokemon (Pokemon pokemon) {
		try {
			prepEvolvePokemon.setString(1, pokemon.getNombre());
			prepEvolvePokemon.setInt(2, pokemon.getNivel());
			prepEvolvePokemon.setString(3, pokemon.getHabilidad());
			prepEvolvePokemon.setString(4, pokemon.getGenero());
			//quiero la ruta se mantenga igual
			prepEvolvePokemon.setInt(5, pokemon.getId());
			prepEvolvePokemon.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	


	@Override
	public ArrayList<Pokemon> getListPokemonByNombre(String nombreP) {
		ArrayList<Pokemon> pokemons = new ArrayList<>();
		try {
			ResultSet rs = stmt.executeQuery(STMT_GET_POKEMON_BY_NOMBRE + nombreP + ";");
			while(rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				int nivel = rs.getInt("Nivel");
				String habilidad = rs.getString("Habilidad");
				String genero = rs.getString("Genero");
				Pokemon pokemon = new Pokemon(id, nombre, nivel, habilidad, genero);
				int idRuta = rs.getInt("RutaP");
				Ruta ruta = getRutaById(idRuta);
				pokemon.setRutaP(ruta);
				pokemons.add(pokemon);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pokemons;
	}


	@Override
	public void levelUp(Pokemon pokemon) {
		LOGGER.info(pokemon.toString());
		try {
			prepLevelUpPokemon.setInt(1, pokemon.getNivel());
			prepLevelUpPokemon.setInt(2, pokemon.getId());
			prepLevelUpPokemon.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	@Override
	public void addCentro(CentroPokemon centro) {
		try {
			prepAddCentro.setInt(1, centro.getId());
			prepAddCentro.setString(2, centro.getTrabajadores());
			prepAddCentro.setString(3, centro.getCiudad());
			prepAddCentro.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
	
	@Override
	public boolean addEntrenadorPokemon (EntrenadorPokemon ep) {
		boolean exito = false;
		try {
			prepAddEntrenadorPokemon.setInt(1, ep.getPokemon().getId());
			prepAddEntrenadorPokemon.setInt(2, ep.getEntrenador().getId());
			prepAddEntrenadorPokemon.setInt(3, ep.getCantidad());
			int resultado = prepAddEntrenadorPokemon.executeUpdate();
			if (resultado == 1) {
				exito = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exito;
	}
	
	
	
	private static int randomInt(int max) {
		return (int) (Math.random() * max);
	}

	
	
}