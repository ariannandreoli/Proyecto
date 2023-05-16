package pojo;

import java.util.Objects;



public class EntrenadorPokemon{
	
	private Entrenador entrenador;	
	private Pokemon pokemon;
	private int cantidad;

	public EntrenadorPokemon (Entrenador entrenador, Pokemon pokemon, int cantidad) {
		super();
		this.entrenador = entrenador;
		this.pokemon = pokemon;
		this.cantidad= cantidad; 
	}


	public Entrenador getEntrenador() {
		return entrenador;
	}

	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;
	}
	
	public Pokemon getPokemon() {
		return pokemon;
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}


	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	@Override
	public int hashCode() {
		return Objects.hash(entrenador, pokemon);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntrenadorPokemon other = (EntrenadorPokemon) obj;
		return Objects.equals(entrenador, other.entrenador) && Objects.equals(pokemon, other.pokemon);
	}


	@Override
	public String toString() {
		return "EntrenadorPokemon [entrenador=" + entrenador + ", pokemon=" + pokemon + ", cantidad=" + cantidad + "]";
	}

	
	
}
