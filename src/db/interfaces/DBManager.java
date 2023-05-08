package db.interfaces;

import java.util.ArrayList;

import pojo.CentroPokemon;
import pojo.Entrenador;
import pojo.Pokemon;


public interface DBManager {

	void connect();

	void disconnect();

	int countElementsFromTable (String tableName);

	ArrayList<Entrenador> getEntrenador();

	boolean addEntrenador(Entrenador entrenador);
	
	Pokemon getPokemonById(int id);

	ArrayList<Pokemon> getListPokemonByNombre(String nombre);
	
	Pokemon getPokemonByNombre (String nombre);
	
	int releasePokemon(int id);

	void addPokemon(Pokemon pokemon);

	ArrayList<Pokemon> getPokemonByOrder(int inicio, int fin, int idLimit);

	void evolvePokemon(Pokemon pokemon);

	void levelUp(Pokemon pokemon);
	
	void addCentro(CentroPokemon centro);


}
