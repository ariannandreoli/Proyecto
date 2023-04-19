package pojo;

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
	public String toString() {
		return "Entrenador [id=" + id + ", nombre=" + nombre + ", genero=" + genero + "]";
	}
	
	
	
}
