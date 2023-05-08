package db.interfaces;

import java.util.List;

import pojo.Rol;
import pojo.Usuario;

public interface UsuariosManager {

	void connect();
	void disconnect();
	void addRol(Rol rol);
	List<Rol> getRoles();
	Rol getRolById(int rolId);
	void addUsuario(Usuario usuario);
	Usuario checkLogin(String nombre, String pass);
}

