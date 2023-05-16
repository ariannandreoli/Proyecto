package pojo;


public class Ruta {
	private int id;
	private int nombre;
	
	public Ruta(int id, int nombre) {
		super();
		this.id = id;
		this.nombre = nombre;

	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getNombre() {
		return nombre;
	}
	public void setNombre(int nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Ruta [id=" + id + ", nombre=" + nombre + "]";
	}
	
	

}
