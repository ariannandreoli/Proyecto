package pojo;

public class EntrenadorCentro {
	private Entrenador entrenador;	
	private CentroPokemon centro;

	public EntrenadorCentro (Entrenador entrenador, CentroPokemon centro) {
		super();
		this.entrenador = entrenador;
		this.centro = centro;
	}

	public Entrenador getEntrenador() {
		return entrenador;
	}

	public void setEntrenador(Entrenador entrenador) {
		this.entrenador = entrenador;
	}

	public CentroPokemon getCentro() {
		return centro;
	}

	public void setCentro(CentroPokemon centro) {
		this.centro = centro;
	}

	@Override
	public String toString() {
		return "EntrenadorCentro [entrenador=" + entrenador + ", centro=" + centro + "]";
	}
	
	

}
