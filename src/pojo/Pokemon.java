package pojo;

import java.util.Objects;

public class Pokemon {
	private int id;
	private String nombre;
	private int nivel;
	private String habilidad;
	private String genero;
	private Ruta rutaP;
	
	public Pokemon (int id, String nombre, int nivel, String habilidad, String genero) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nivel = nivel;
		this.habilidad= habilidad;
		this.genero = genero;
	}
	
	public Pokemon (int id, String nombre, int nivel, String habilidad, String genero, Ruta rutaP) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.nivel = nivel;
		this.habilidad= habilidad;
		this.genero = genero;
		this.rutaP = rutaP; 
	}

	public Pokemon() {
		
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


	public int getNivel() {
		return nivel;
	}


	public void setNivel(int nivel) {
		this.nivel = nivel;
	}


	public String getHabilidad() {
		return habilidad;
	}


	public void setHabilidad(String habilidad) {
		this.habilidad = habilidad;
	}


	public String getGenero() {
		return genero;
	}


	public void setGenero(String genero) {
		this.genero = genero;
	}


	public Ruta getRutaP() {
		return rutaP;
	}


	public void setRutaP(Ruta rutaP) {
		this.rutaP = rutaP;
	}

	

	@Override
	public int hashCode() {
		return Objects.hash(genero, habilidad, id, nivel, nombre, rutaP);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pokemon other = (Pokemon) obj;
		return Objects.equals(genero, other.genero) && Objects.equals(habilidad, other.habilidad) && id == other.id
				&& nivel == other.nivel && Objects.equals(nombre, other.nombre) && rutaP == other.rutaP;
	}


	@Override
	public String toString() {
		return "Pokemon [id=" + id + ", nombre=" + nombre + ", nivel=" + nivel + ", habilidad=" + habilidad
				+ ", genero=" + genero + ", rutaP=" + rutaP + "]";
	}



}
