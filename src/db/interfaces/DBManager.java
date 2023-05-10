package db.interfaces;

import java.util.ArrayList;

import pojo.CentroPokemon;
import pojo.Entrenador;
import pojo.EntrenadorPokemon;
import pojo.Pokemon;
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

	boolean addEntrenadorPokemon(EntrenadorPokemon ep);

	ArrayList<Entrenador> getEntrenadores();

	boolean addUsuario(Usuario u);


}
