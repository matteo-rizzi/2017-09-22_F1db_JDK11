package it.polito.tdp.formulaone.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Year;
import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model m = new Model();
		
		m.creaGrafo(2009);
		
		List<DriverPunteggio> result = m.simula(0.04, 20, new Race(1, Year.of(2009),1 ,1 ,"Australian Grand Prix", LocalDate.of(2009, 03, 29), LocalTime.of(06, 00), "http://en.wikipedia.org/wiki/2009_Australian_Grand_Prix"));
		
		for(DriverPunteggio dp : result) {
			System.out.println(dp);
		}
	}

}
