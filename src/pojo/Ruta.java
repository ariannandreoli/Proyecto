package pojo;


public class Ruta {
	private int id;
	private String nombre;
	
	public Ruta(int id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;

	}
	
	
	
	public Ruta() {
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

	@Override
	public String toString() {
		return "Ruta [id=" + id + ", nombre=" + nombre + "]";
	}
	
	

}
