package it.polito.tdp.formulaone;

import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.formulaone.model.Adiacenza;
import it.polito.tdp.formulaone.model.DriverPunteggio;
import it.polito.tdp.formulaone.model.Model;
import it.polito.tdp.formulaone.model.Race;
import it.polito.tdp.formulaone.model.Season;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Season> boxAnno;

    @FXML
    private Button btnSelezionaStagione;

    @FXML
    private ComboBox<Race> boxGara;

    @FXML
    private Button btnSimulaGara;

    @FXML
    private TextField textInputK;

    @FXML
    private TextField textInputK1;

    @FXML
    private TextArea txtResult;

    @FXML
    void doSelezionaStagione(ActionEvent event) {
    	this.txtResult.clear();
    	this.boxGara.getItems().clear();
    	
    	try {
    		if(this.boxAnno.getValue() == null) {
    			this.txtResult.appendText("Errore! Per poter creare il grafo devi selezionare una stagione dall'apposito menu!\n");
    			return;
    		}
    		Integer anno = this.boxAnno.getValue().getYear();
    		
    		this.model.creaGrafo(anno);
    		this.txtResult.appendText("Grafo creato!\n");
    		this.txtResult.appendText("# VERTICI: " + this.model.nVertici() + "\n");
    		this.txtResult.appendText("# ARCHI: " + this.model.nArchi() + "\n\n");
    		
    		this.boxGara.getItems().addAll(this.model.getVertici());
    		
    		List<Adiacenza> pesoMax = this.model.getAdiacenzePesoMassimo();
    		this.txtResult.appendText("I vertici che formano gli archi con peso massimo sono: (peso massimo = " + pesoMax.get(0).getPeso() + ")\n");
    		for(Adiacenza a : pesoMax) {
    			this.txtResult.appendText(a + "\n");
    		}
    		
    	} catch(RuntimeException e) {
    		this.txtResult.appendText("Errore nella creazione del grafo!\n");
    		return;
    	}

    }

    @FXML
    void doSimulaGara(ActionEvent event) {
    	this.txtResult.clear();
    	
    //	try {
    		if(this.boxAnno.getValue() == null) {
    			this.txtResult.appendText("Errore! Per poter creare il grafo devi selezionare una stagione dall'apposito menu!\n");
    			return;
    		}
    		Double P;
    		try {
    			P = Double.parseDouble(this.textInputK.getText());
    		} catch(NumberFormatException e) {
    			this.txtResult.appendText("Errore! Devi selezionare un valore decimale per P.\n");
        		return;
    		}
    		
    		Integer T;
    		try {
    			T = Integer.parseInt(this.textInputK1.getText());
    		} catch(NumberFormatException e) {
    			this.txtResult.appendText("Errore! Devi selezionare un valore intero per T.\n");
        		return;
    		}
    		
    		if(this.boxGara.getValue() == null) {
    			this.txtResult.appendText("Errore! Devi selezionare una gara dall'apposito menu.\n");
        		return;
    		}
    		
    		Race race = this.boxGara.getValue();
    		List<DriverPunteggio> result = this.model.simula(P, T, race);
    		Collections.sort(result);
    		for(DriverPunteggio dp : result) {
    			this.txtResult.appendText(dp + "\n");
    		}
    		
    		/*} catch(RuntimeException e) {
    		this.txtResult.appendText("Errore! Per poter avviare la simulazione devi prima creare il grafo!\n");
    		return;
    	}*/

    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnSelezionaStagione != null : "fx:id=\"btnSelezionaStagione\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert boxGara != null : "fx:id=\"boxGara\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert btnSimulaGara != null : "fx:id=\"btnSimulaGara\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK != null : "fx:id=\"textInputK\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert textInputK1 != null : "fx:id=\"textInputK1\" was not injected: check your FXML file 'FormulaOne.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'FormulaOne.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxAnno.getItems().addAll(this.model.getAllSeasons());
	}
}
