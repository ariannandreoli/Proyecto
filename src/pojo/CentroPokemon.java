package pojo;

public class CentroPokemon {
	private int id;
	private String ciudad;
	private String trabajadores;
	
	public CentroPokemon (int id, String trabajadores, String ciudad) {
		super();
		this.id = id;
		this.ciudad = ciudad;
		this.trabajadores= trabajadores;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getTrabajadores() {
		return trabajadores;
	}

	public void setTrabajadores(String trabajadores) {
		this.trabajadores = trabajadores;
	}

	@Override
	public String toString() {
		return "CentroPokemon [id=" + id + ", ciudad=" + ciudad + ", trabajadores=" + trabajadores + "]";
	}

	
}
