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
import pojo.CentroPokemon;
import pojo.Entrenador;
import pojo.EntrenadorCentro;
import pojo.EntrenadorPokemon;
import pojo.Pokemon;
import pojo.PokemonTipo;
import pojo.Ruta;
import pojo.Tipo;
import pojo.Usuario;

public class JDBCManager implements DBManager {
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
	private final String STMT_GET_ENTRENADOR_BY_ID = "SELECT * FROM Entrenador WHERE Id=";	
	private final String STMT_GET_POKEMONS = "SELECT * FROM Pokemon;";
	private final String STMT_GET_POKEMON_TIPO_BY_POKEMONID = "SELECT * FROM PokemonTipo WHERE IdPokemon=";
	private final String STMT_GET_RUTA_BY_ID = "SELECT * FROM Ruta WHERE Id="; 
	private final String STMT_GET_USUARIO_BY_ID= "SELECT * FROM Usuario WHERE Id='";
	private final String STMT_GET_TIPOS =  "SELECT * FROM Tipo;";
	private final String STMT_GET_CENTROS =  "SELECT * FROM Centro;";
	private final String STMT_GET_TIPO_BY_ID= "SELECT * FROM Tipo WHERE Id=";
	private final String STMT_GET_ENTRENADORPOKEMON_BY_ID_ENTRENADOR = "SELECT * FROM 'Entrenador-Pokemon' WHERE IdEntrenador = ? AND IdPokemon = ?";
	
	private final String PREP_ADD_ENTRENADOR = "INSERT INTO Entrenador (Nombre, Genero) VALUES (?,?);";
	private final String PREP_DELETE_POKEMON = "DELETE FROM Pokemon WHERE Id = ?;";
	private final String PREP_ADD_POKEMON = "INSERT INTO Pokemon (Nombre, Nivel, Habilidad, Genero, RutaP) VALUES (?,?,?,?,?);";
	private final String PREP_LEVEL_UP_POKEMON = "UPDATE Pokemon SET Nivel=? WHERE Id=?;";
	private final String PREP_ADD_CENTRO = "INSERT INTO Centro (Id, Trabajadores, Ciudad) VALUES (?,?,?);";
	private final String PREP_ADD_POKEMON_ENTRENADOR = "INSERT INTO 'Entrenador-Pokemon' (IdPokemon, IdEntrenador, Cantidad) VALUES (?,?,?);";
	private final String PREP_ADD_ENTRENADOR_CENTRO = "INSERT INTO 'Entrenador-Centro' (IdEntrenador, IdCentro) VALUES (?,?);";
	private final String PREP_SET_CANTIDAD= "UPDATE 'Entrenador-Pokemon' SET Cantidad=? WHERE IdEntrenador = ? AND IdPokemon = ?;";
	
	private Statement stmt;
	private PreparedStatement prepAddEntrenador;
	private PreparedStatement prepAddPokemon;
	private PreparedStatement prepDeletePokemon;
	private PreparedStatement prepLevelUpPokemon;
	private PreparedStatement prepAddCentro;
	private PreparedStatement prepAddEntrenadorPokemon;
	private PreparedStatement prepAddEntrenadorCentro;
	private PreparedStatement prepSetCantidad;
	private Connection c;

	
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
		//LOGGER.finest("Este es un mensaje finest");
	}
	
	
	private void initializeTables() {
		try {
			prepAddEntrenador= c.prepareStatement(PREP_ADD_ENTRENADOR);
			prepDeletePokemon = c.prepareStatement(PREP_DELETE_POKEMON);
			prepAddPokemon= c.prepareStatement(PREP_ADD_POKEMON);
			prepLevelUpPokemon= c.prepareStatement(PREP_LEVEL_UP_POKEMON);
			prepAddCentro = c.prepareStatement(PREP_ADD_CENTRO);
			prepAddEntrenadorPokemon = c.prepareStatement(PREP_ADD_POKEMON_ENTRENADOR);
			prepAddEntrenadorCentro = c.prepareStatement(PREP_ADD_ENTRENADOR_CENTRO);
			prepSetCantidad = c.prepareStatement(PREP_SET_CANTIDAD);
			
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
			} LOGGER.info("Inicializadas las tablas Centro Pokemon , Pokemon, PokemonTipo, Ruta, Tipo");
			
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
	
	@Override
	public ArrayList<Pokemon> getPokemons() {
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

	@Override
	public ArrayList<Tipo> getTipos() {
		ArrayList<Tipo> tipos = new ArrayList<>();
		try (Statement stmt = c.createStatement()){
			ResultSet rs = stmt.executeQuery(STMT_GET_TIPOS);
			while(rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				Tipo tipo = new Tipo (id, nombre);
				tipos.add(tipo);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tipos;
	}
	

	@Override
	public ArrayList<CentroPokemon> getCentros() {
		ArrayList<CentroPokemon> centros = new ArrayList<>();
		try (Statement stmt = c.createStatement()){
			ResultSet rs = stmt.executeQuery(STMT_GET_CENTROS);
			while(rs.next()) {
				int id = rs.getInt("Id");
				String ciudad = rs.getString("Ciudad");
				String trabajadores = rs.getString("Trabajadores");
				CentroPokemon centro = new CentroPokemon(id, trabajadores, ciudad);
				centros.add(centro);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return centros;
	}
	
	
	@Override
	public ArrayList<PokemonTipo> getPokemonTipoByPokemon (Pokemon p) {
		ArrayList<PokemonTipo> pts = new ArrayList<>();
		try (Statement stmt = c.createStatement()){
			ResultSet rs = stmt.executeQuery(STMT_GET_POKEMON_TIPO_BY_POKEMONID + p.getId());
			while(rs.next()) {
				int idPokemon = rs.getInt("idPokemon");
				p = getPokemonById(idPokemon);
				int idTipo = rs.getInt("idTipo");
				Tipo t = getTipoById(idTipo);
				PokemonTipo pokemonTipo = new PokemonTipo(p, t);
				pokemonTipo.setPokemon(p);
				pokemonTipo.setTipo(t);
				pts.add(pokemonTipo);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return pts;
	}
	
	
	
	@Override
	public Ruta getRutaById(int idRuta) {
		Ruta ruta = null;
		try {
			ResultSet rs = stmt.executeQuery(STMT_GET_RUTA_BY_ID + idRuta);
			if(rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				ruta = new Ruta(id, nombre);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ruta;
	}

	@Override
	public Entrenador getEntrenadorById(int idEntrenador) {
		Entrenador en = null;
		try {
			ResultSet rs = stmt.executeQuery(STMT_GET_ENTRENADOR_BY_ID + idEntrenador);
			if(rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				String genero = rs.getString("Genero");
				en = new Entrenador(id, nombre, genero);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return en;
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
			ResultSet rs = stmt.executeQuery(STMT_GET_ENTRENADOR_BY_ID + entrenador.getId()+ ";");
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
	public boolean addUsuario(Usuario u) {
		try {
			ResultSet rs = stmt.executeQuery(STMT_GET_USUARIO_BY_ID + u.getId()+ "';");
			if(rs.next()) {
				return false;
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			prepAddEntrenador.setString(1, u.getNombre());
			prepAddEntrenador.setBytes(2, u.getPassword());
			prepAddEntrenador.setInt(3, u.getRol().getId());
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
	            int rutaP = rs.getInt("RutaP");
				Ruta r= new Ruta (rutaP, ("Ruta " + rutaP));
				pokemon = new Pokemon(id,  nombre,  nivel, habilidad,  genero, r);
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
	}


	@Override
	public boolean addPokemon(Pokemon pokemon) {
		try {
			prepAddPokemon.setString(1, pokemon.getNombre());
			prepAddPokemon.setInt(2, pokemon.getNivel());
			prepAddPokemon.setString(3, pokemon.getHabilidad());
			prepAddPokemon.setString(4, pokemon.getGenero());
			prepAddPokemon.setInt(5, pokemon.getRutaP().getId());
			prepAddPokemon.executeUpdate();
			LOGGER.info("Pokemon: " + pokemon + " añadido con éxito");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return true;
	}

	@Override
	public ArrayList<Pokemon> getPokemonByOrder(int offset, int limit, int idLimit) {
		String sql = "SELECT * FROM Pokemon WHERE Id < " + idLimit + " ORDER BY Id ASC LIMIT " + limit + " OFFSET " + offset + ";";		
		ArrayList<Pokemon> pokemons = new ArrayList<>();
		try (Statement stmt = c.createStatement()){
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				int nivel = rs.getInt("Nivel");
				String habilidad = rs.getString("Habilidad");
				String genero = rs.getString("Genero");
				int rutaP = rs.getInt("RutaP");
				Ruta r= new Ruta (rutaP, ("Ruta " + rutaP));
				Pokemon pokemon = new Pokemon(id,  nombre,  nivel, habilidad,  genero, r);
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
	public Tipo getTipoById (int idT) {
		Tipo t= null;
		try {
			ResultSet rs = stmt.executeQuery(STMT_GET_TIPO_BY_ID + idT);
			if(rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				t = new Tipo(id, nombre);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	
	@Override
	public Entrenador getEntrenadorByNombre (String nombreE) {
		Entrenador entrenador = null;
		try {
			PreparedStatement pstmt = c.prepareStatement("SELECT * FROM Entrenador WHERE Nombre = ?");
	        pstmt.setString(1, nombreE);
	        ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				int id = rs.getInt("Id");
				String nombre = rs.getString("Nombre");
				String genero = rs.getString("Genero");
				entrenador = new Entrenador (id, nombre, genero);
			}			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return entrenador;
	}
	
	
	@Override
	public ArrayList<Integer> getPokemonByEntrenador(int identrenador) {
		String sql = "SELECT IdPokemon FROM 'Entrenador-Pokemon' WHERE IdEntrenador ="+identrenador;
		ArrayList<Integer> pokemonsid = new ArrayList<>();
		try (Statement stmt = c.createStatement()){
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
		        int pokemonId = rs.getInt("IdPokemon");
	            pokemonsid.add(pokemonId);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return pokemonsid;
	}
	
	@Override
	public ArrayList<Integer> getTipoByPokemon(int idPokemon) {
		String sql = "SELECT IdTipo FROM 'PokemonTipo' WHERE IdPokemon ="+ idPokemon;
		ArrayList<Integer> tiposId = new ArrayList<>();
		try (Statement stmt = c.createStatement()){
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()) {
		        int idTipo = rs.getInt("IdTipo");
		        tiposId.add(idTipo);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
		return tiposId;
	}
	


	@Override
	public ArrayList<Pokemon> getListPokemonByNombre (String nombreP) {
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
				//LOGGER.info("Agregado " + ep + "con exito");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exito;
	}
	
	@Override
	public boolean addEntrenadorCentro (EntrenadorCentro ec) {
		boolean exito = false;
		try {
			prepAddEntrenadorCentro.setInt(1, ec.getEntrenador().getId());
			prepAddEntrenadorCentro.setInt(2, ec.getCentro().getId());
			int resultado = prepAddEntrenadorCentro.executeUpdate();
			if (resultado == 1) {
				exito = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return exito;
	}

	public void setCantidad(EntrenadorPokemon ep) {
		try {
			int cantidadNueva = ep.getCantidad()+1;
			prepSetCantidad.setInt(1, cantidadNueva);
			prepSetCantidad.setInt(2, ep.getEntrenador().getId());
			prepSetCantidad.setInt(3, ep.getPokemon().getId());
			prepSetCantidad.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public EntrenadorPokemon getEntrenadorPokemon(Entrenador e1, Pokemon p) {
	    EntrenadorPokemon ep = null;
	    try {
	        PreparedStatement prepSetEntrenadorPokemonByIds = c.prepareStatement(STMT_GET_ENTRENADORPOKEMON_BY_ID_ENTRENADOR);
	        prepSetEntrenadorPokemonByIds.setInt(1, e1.getId());
	        prepSetEntrenadorPokemonByIds.setInt(2, p.getId());
	        ResultSet rs = prepSetEntrenadorPokemonByIds.executeQuery();

	        if (rs.next()) {
	            int idE = rs.getInt("IdEntrenador");
	            Entrenador ent = getEntrenadorById(idE);
	            int idP = rs.getInt("IdPokemon");
	            Pokemon pok = getPokemonById(idP);
	            int c = rs.getInt("Cantidad");
	            
	            ep = new EntrenadorPokemon(ent, pok, c);
	        }

	        rs.close();
	        prepSetEntrenadorPokemonByIds.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return ep;
	}


	
}