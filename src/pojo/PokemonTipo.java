package pojo;

import java.util.Objects;

public class PokemonTipo {
	private Pokemon pokemon;
	private Tipo tipo;
	
	public PokemonTipo(Pokemon pokemon, Tipo tipo) {
		super();
		this.pokemon = pokemon;
		this.tipo = tipo;
	}

		public Pokemon getPokemon() {
		return pokemon;
	}

	public void setPokemon(Pokemon pokemon) {
		this.pokemon = pokemon;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

		@Override
		public int hashCode() {
			return Objects.hash(pokemon, tipo);
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			PokemonTipo other = (PokemonTipo) obj;
			return Objects.equals(pokemon, other.pokemon) && Objects.equals(tipo, other.tipo);
		}

		@Override
		public String toString() {
			return "PokemonTipo [pokemon=" + pokemon + ", tipo=" + tipo + "]";
		}



}
