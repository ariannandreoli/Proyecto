package db.interfaces;

import java.util.ArrayList;

import pojo.CentroPokemon;
import pojo.Entrenador;
import pojo.EntrenadorCentro;
import pojo.EntrenadorPokemon;
import pojo.Pokemon;
import pojo.PokemonTipo;
import pojo.Ruta;
import pojo.Tipo;
import pojo.Usuario;


public interface DBManager {

	void connect();

	void disconnect();

	int countElementsFromTable (String tableName);

	boolean addEntrenador(Entrenador entrenador);
	
	Pokemon getPokemonById(int id);

	ArrayList<Pokemon> getListPokemonByNombre(String nombre);
	
	Pokemon getPokemonByNombre (String nombre);
	
	int releasePokemon(int id);

	boolean addPokemon(Pokemon pokemon);

	ArrayList<Pokemon> getPokemonByOrder(int inicio, int fin, int idLimit);

	void evolvePokemon(Pokemon pokemon);

	void levelUp(Pokemon pokemon);
	
	void addCentro(CentroPokemon centro);

	ArrayList<Entrenador> getEntrenadores();

	boolean addUsuario(Usuario u);

	ArrayList<Pokemon> getPokemons();

	Ruta getRutaById(int idRuta);

	Entrenador getEntrenadorByNombre(String nombreE);

	ArrayList<Integer> getPokemonByEntrenador(int identrenador);
	
	boolean addEntrenadorPokemon(EntrenadorPokemon ep);

	ArrayList<Integer> getTipoByPokemon(int idPokemon);

	Tipo getTipoById(int idT);

	ArrayList<Tipo> getTipos();

	ArrayList<PokemonTipo> getPokemonTipoByPokemon(Pokemon p);

	ArrayList<CentroPokemon> getCentros();

	boolean addEntrenadorCentro(EntrenadorCentro ec);

	Entrenador getEntrenadorById(int idEntrenador);
	
	EntrenadorPokemon getEntrenadorPokemon(Entrenador e1, Pokemon p);
	
	void setCantidad(EntrenadorPokemon ep);
	


}
