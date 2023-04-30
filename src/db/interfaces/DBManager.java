package db.interfaces;

import java.util.ArrayList;
import pojo.Entrenador;
import pojo.Pokemon;


public interface DBManager {

	void connect();

	void disconnect();

	int countElementsFromTable (String tableName);

	ArrayList<Entrenador> getEntrenador();

	boolean addEntrenador(Entrenador entrenador);
	
	Pokemon getPokemonById(int id);

	//ArrayList<Pokemon> getPokemonNombre(String nombre);
	
	Pokemon getPokemonByNombre (String nombre);
	
	int releasePokemon(int id);

	void addPokemon(Pokemon pokemon);

	boolean addImagenProducto(Pokemon pokemon);

	ArrayList<Pokemon> getPokemonByOrder(int offset, int limit);


}
