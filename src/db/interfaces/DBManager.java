package db.interfaces;

import java.util.ArrayList;
import pojo.Entrenador;


public interface DBManager {

	void connect();

	void disconnect();

	int countElementsFromTable(String tableName);

	ArrayList<Entrenador> getEntrenador();
}
