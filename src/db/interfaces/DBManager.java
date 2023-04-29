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

	ArrayList<Pokemon> getPokemonByNombre();
	
	Pokemon getPokemonNombre (String nombre);
	
	int releasePokemon(Pokemon pokemon);

	void addPokemon(Pokemon pokemon);

	boolean addImagenProducto(Pokemon pokemon);

	ArrayList<Pokemon> getPokemonByOrder(int offset, int limit);


}
