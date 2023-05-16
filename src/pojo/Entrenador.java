package pojo;

import java.util.Objects;

public class Entrenador {

	private int id;
	private String nombre;
	private String genero;

	
	public Entrenador(int id, String nombre, String genero) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.genero= genero;
	}
	
	public Entrenador() {
		super();
	}
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getGenero() {
		return genero;
	}
	public void setGenero(String genero) {
		this.genero = genero;
	}

	
	@Override
	public int hashCode() {
		return Objects.hash(genero, id, nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entrenador other = (Entrenador) obj;
		return Objects.equals(genero, other.genero) && id == other.id && Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "Entrenador [id=" + id + ", nombre=" + nombre + ", genero=" + genero + "]";
	}
	
	
	
}
