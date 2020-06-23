package it.polito.tdp.formulaone.model;


public class DriverPunteggio implements Comparable<DriverPunteggio>{

	private Driver driver;
	private Integer punteggio;

	public DriverPunteggio(Driver driver, Integer punteggio) {
		super();
		this.driver = driver;
		this.punteggio = punteggio;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public Integer getPunteggio() {
		return punteggio;
	}

	public void setPunteggio(Integer punteggio) {
		this.punteggio = punteggio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((driver == null) ? 0 : driver.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DriverPunteggio other = (DriverPunteggio) obj;
		if (driver == null) {
			if (other.driver != null)
				return false;
		} else if (!driver.equals(other.driver))
			return false;
		return true;
	}

	public void incrementaPunteggio() {
		this.punteggio ++;
	}

	@Override
	public String toString() {
		return "Pilota: " + this.driver + ", punteggio: " + this.punteggio;
	}

	@Override
	public int compareTo(DriverPunteggio other) {
		return other.getPunteggio().compareTo(this.punteggio);
	}
}
