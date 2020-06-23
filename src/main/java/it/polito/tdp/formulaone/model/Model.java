package it.polito.tdp.formulaone.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.formulaone.db.FormulaOneDAO;

public class Model {
	
	private FormulaOneDAO dao;
	private Map<Integer, Race> idMap;
	private Map<Integer, Driver> idDriver;
	private Graph<Race, DefaultWeightedEdge> grafo;
	private List<Adiacenza> adiacenze;
	private Simulator sim;
	
	public Model() {
		this.dao = new FormulaOneDAO();
		this.idMap = new HashMap<>();
		this.dao.getRaces(idMap);
		this.idDriver = new HashMap<>();
		this.dao.getDrivers(idDriver);
	}
	
	public List<Season> getAllSeasons() {
		return this.dao.getAllSeasons();
	}
	
	public void creaGrafo(int anno) {
		this.grafo = new SimpleWeightedGraph<Race, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		this.adiacenze = new ArrayList<>();
		
		// aggiungo i vertici
		Graphs.addAllVertices(this.grafo, this.dao.getRacesByYear(idMap, anno));
		
		// aggiungo gli archi
		this.adiacenze = this.dao.getAdiacenze(idMap, anno);
		for(Adiacenza a : adiacenze) {
			Graphs.addEdge(this.grafo, a.getPrimo(), a.getSecondo(), a.getPeso());
		}
	}
	
	public List<Race> getVertici() {
		List<Race> vertici = new ArrayList<>(this.grafo.vertexSet());
		Collections.sort(vertici);
		return vertici;
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}

	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public List<Adiacenza> getAdiacenzePesoMassimo() {
		List<Adiacenza> copia = new ArrayList<>(this.adiacenze);
		List<Adiacenza> pesoMax = new ArrayList<>();
		Collections.sort(copia);
		Integer max = copia.get(0).getPeso();
		for(Adiacenza a : copia) {
			if(a.getPeso() == max)
				pesoMax.add(a);
		}
		return pesoMax;
	}
	
	public LapTime getLapTimesByRace(Race race, Driver driver, int giro) {
		return this.dao.getLapTimes(race, driver, giro);
	}
	
	public List<Driver> getDriversByRace(Race race) {
		return this.dao.getDriversByRace(race, idDriver);
	}
	
	public List<DriverPunteggio> simula(double P, int T, Race race) {
		this.sim = new Simulator(this);
		this.sim.setP(P);
		this.sim.setRace(race);
		this.sim.setT(T);
		this.sim.init();
		this.sim.run();
		return this.sim.getResult();
	}
}
