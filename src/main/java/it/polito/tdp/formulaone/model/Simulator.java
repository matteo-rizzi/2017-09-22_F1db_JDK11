package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import it.polito.tdp.formulaone.model.Event.EventType;

public class Simulator {

	// CODA DEGLI EVENTI
	private PriorityQueue<Event> queue;

	// PARAMETRI DA INPUT
	private Race race = new Race();
	private Double P = 0.04;
	private Integer T = 20;

	// MODELLO DEL MONDO
	private int lapCorrente;
	Model model;

	// VALORI DA CALCOLARE
	private List<DriverPunteggio> result;

	public List<DriverPunteggio> getResult() {
		return result;
	}

	public void setRace(Race race) {
		this.race = race;
	}

	public void setP(Double p) {
		P = p;
	}

	public void setT(Integer t) {
		T = t;
	}

	public Simulator(Model model) {
		this.model = model;
	}

	public void init() {
		this.queue = new PriorityQueue<Event>();
		this.result = new ArrayList<DriverPunteggio>();
		this.lapCorrente = 1;
		for (Driver driver : this.model.getDriversByRace(race)) {
			this.result.add(new DriverPunteggio(driver, 0));
		}
		for (Driver driver : this.model.getDriversByRace(race)) {
			LapTime lt = this.model.getLapTimesByRace(race, driver, 1);
			if(lt != null) {
				Event e = new Event(EventType.FINE_GIRO, (lt.getMiliseconds() / 1000.0), lt);
				if (e != null)
				this.queue.add(e);
			}
		}
	}

	public void run() {
		while (!this.queue.isEmpty()) {
			Event e = this.queue.poll();
			System.out.println(e);
			processEvent(e);
		}
	}

	private void processEvent(Event e) {
		switch (e.getType()) {
		case FINE_GIRO:
			if (e.getLap().getLap() == this.lapCorrente) {
				// NON E' ANCORA ARRIVATO NESSUNO, SONO IL PRIMO
				this.lapCorrente++;
				for (DriverPunteggio dp : this.result) {
					if (dp.getDriver().getDriverId() == e.getLap().getDriverId().getDriverId()) {
						dp.incrementaPunteggio();
						System.out.println(dp);
					}
				}
			}
			double probabilita = Math.random();
			LapTime lt = this.model.getLapTimesByRace(race, e.getLap().getDriverId(), e.getLap().getLap() + 1);
			if (probabilita < 0.04 && lt != null) {
				// PAUSA!!!!!!!
				this.queue.add(new Event(EventType.PAUSA, e.getTime() + T, lt));
			} else if (lt != null) {
				this.queue.add(new Event(EventType.FINE_GIRO, e.getTime() + (lt.getMiliseconds() / 1000.0), lt));
			}

			break;
		case PAUSA:
			this.queue.add(
					new Event(EventType.FINE_GIRO, e.getTime() + (e.getLap().getMiliseconds() / 1000.0), e.getLap()));
			break;
		}

	}

}
