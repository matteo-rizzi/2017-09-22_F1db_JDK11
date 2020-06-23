package it.polito.tdp.formulaone.model;

public class Event implements Comparable<Event> {

	public enum EventType {
		FINE_GIRO, PAUSA
	}

	private EventType type;
	private Double time;
	private LapTime lap;

	public Event(EventType type, Double time, LapTime lap) {
		super();
		this.type = type;
		this.time = time;
		this.lap = lap;
	}

	public EventType getType() {
		return type;
	}

	public void setType(EventType type) {
		this.type = type;
	}

	public Double getTime() {
		return time;
	}

	public void setTime(Double time) {
		this.time = time;
	}

	public LapTime getLap() {
		return lap;
	}

	public void setLap(LapTime lap) {
		this.lap = lap;
	}

	@Override
	public int compareTo(Event other) {
		return this.time.compareTo(other.getTime());
	}

	@Override
	public String toString() {
		return "Event [type=" + type + ", time=" + time + ", lap=" + lap + "]";
	}

}
