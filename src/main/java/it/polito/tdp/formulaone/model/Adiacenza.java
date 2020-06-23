package it.polito.tdp.formulaone.model;

public class Adiacenza implements Comparable<Adiacenza>{

	private Race primo;
	private Race secondo;
	private Integer peso;

	public Adiacenza(Race primo, Race secondo, Integer peso) {
		super();
		this.primo = primo;
		this.secondo = secondo;
		this.peso = peso;
	}

	public Race getPrimo() {
		return primo;
	}

	public void setPrimo(Race primo) {
		this.primo = primo;
	}

	public Race getSecondo() {
		return secondo;
	}

	public void setSecondo(Race secondo) {
		this.secondo = secondo;
	}

	public Integer getPeso() {
		return peso;
	}

	public void setPeso(Integer peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		return "Gara 1: " + this.primo + ", gara 2: " + this.secondo;
	}

	@Override
	public int compareTo(Adiacenza other) {
		return other.getPeso().compareTo(this.peso);
	}

}
