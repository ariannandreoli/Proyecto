package pojo;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlTransient;


@XmlAccessorType(XmlAccessType.FIELD)
public class Tipo implements Serializable {
	
	private static final long serialVersionUID = new Random().nextLong();

	@XmlTransient
	private int id;
	
	@XmlAttribute
	private String nombre;
	
	@XmlElement(name = "pokemonTipo")
	@XmlElementWrapper(name = "PokemonTipo")
    private List<PokemonTipo> pokemonTipos;
	
	public Tipo() {
		super();
	}
	
	public Tipo (int id) {
		super();
		this.id = id;

	}
	
	public Tipo (int id, String nombre) {
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
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return "Tipo [id=" + id + ", nombre=" + nombre + "]";
	}
	

}

