package it.polito.tdp.formulaone.model;

public class LapTime {

	private Race raceId ; // refers to {@link Race}
	private Driver driverId ; // referst to {@link Driver}
	private int lap ;
	// NOT: only the combination of the 3 fields (raceId, driverId, lap) is guaranteed to be unique
	private int position ;
	private String time ; // printable version of lap time
	private int miliseconds ; // numerical version, sutable for computations
	public LapTime(Race raceId, Driver driverId, int lap, int position, String time, int miliseconds) {
		super();
		this.raceId = raceId;
		this.driverId = driverId;
		this.lap = lap;
		this.position = position;
		this.time = time;
		this.miliseconds = miliseconds;
	}
	public Race getRaceId() {
		return raceId;
	}
	@Override
	public String toString() {
		return "LapTime [driverId=" + driverId + ", lap=" + lap + ", miliseconds=" + miliseconds + "]";
	}
	public void setRaceId(Race raceId) {
		this.raceId = raceId;
	}
	public Driver getDriverId() {
		return driverId;
	}
	public void setDriverId(Driver driverId) {
		this.driverId = driverId;
	}
	public int getLap() {
		return lap;
	}
	public void setLap(int lap) {
		this.lap = lap;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getMiliseconds() {
		return miliseconds;
	}
	public void setMiliseconds(int miliseconds) {
		this.miliseconds = miliseconds;
	}
	
	
}

