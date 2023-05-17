package pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Pokemon")
@XmlAccessorType(XmlAccessType.FIELD)
public class Pokemon implements Serializable {
	
	private static final long serialVersionUID = new Random().nextLong();
	
	@XmlElement
	private int id;
	
	@XmlAttribute
	private String nombre;
	
	@XmlElement
	private int nivel;
	
	@XmlElement
	private String habilidad;
	
	@XmlElement
	private String genero;
	
	@XmlElement
	private Ruta rutaP;
	
	@XmlElement(name = "PokemonTipo")
	@XmlElementWrapper(name = "PokemonTipos")
    private List<PokemonTipo> pokemonTipos;
	
	
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

	
	
	public List<PokemonTipo> getPokemonTipos() {
		return pokemonTipos;
	}

	public void setPokemonTipos(List<PokemonTipo> pokemonTipos) {
		this.pokemonTipos = pokemonTipos;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Pokemon [id=" + id + ", nombre=" + nombre + ", nivel=" + nivel + ", habilidad=" + habilidad
				+ ", genero=" + genero + ", rutaP=" + rutaP + "]";
	}



}
