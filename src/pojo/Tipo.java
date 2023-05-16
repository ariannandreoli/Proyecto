package pojo;

import java.io.Serializable;
import java.util.Random;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Tipo")
@XmlAccessorType(XmlAccessType.FIELD)
public class Tipo implements Serializable {
	
	private static final long serialVersionUID = new Random().nextLong();

	
	@XmlElement
	private int id;
	
	@XmlAttribute
	private String nombre;
	
	
	
	public Tipo() {
		super();
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

