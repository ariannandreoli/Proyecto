package pojo;

public class Pokedex {
	private int id;
	private int idEntrenador;
	private String descripcion;
	
	public Pokedex (int id, int idEntrenador, String descripcion) {
		super();
		this.id = id;
		this.idEntrenador = idEntrenador;
		this.descripcion= descripcion;

	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public int getIdEntrenador() {
		return idEntrenador;
	}
	public void setIdEntrenador(int idEntrenador) {
		this.idEntrenador = idEntrenador;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
	public String toString() {
		return "Pokedex [id=" + id + ", idEntrenador=" + idEntrenador + ", descripcion=" + descripcion + "]";
	}
	

}
