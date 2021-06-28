package it.polito.tdp.ufo;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.Anno;
import it.polito.tdp.ufo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Anno> boxAnno;

    @FXML
    private ComboBox<String> boxStato;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleAnalizza(ActionEvent event) {

    	
    	String stato = this.boxStato.getValue();
    	if(stato == null) {
    		System.out.println("Errore, devi selezionare uno stato!");
    		return;
    	}
    	
    	List<String> successivi = new ArrayList<String>(this.model.getSuccessivi(stato));
    	List<String> precedenti = new ArrayList<String>(this.model.getPrecedenti(stato));
    	
    	this.txtResult.appendText("\n\nSuccessivi...\n\n");
    	
    	if(successivi.isEmpty()) {
    		this.txtResult.appendText("Nessuno stato trovato\n\n");
    	} else {
    		for(String s : successivi) {
    			this.txtResult.appendText(s + "\n");
    		}
    	}
    	
    	this.txtResult.appendText("\n\nPrecedenti...\n\n");
    	
    	if(precedenti.isEmpty()) {
    		this.txtResult.appendText("Nessuno stato trovato\n\n");
    	} else {
    		for(String s : precedenti) {
    			this.txtResult.appendText(s + "\n");
    		}
    	}
    	
   
    	

    }

    @FXML
    void handleAvvistamenti(ActionEvent event) {
    	
    	this.txtResult.clear();
    	this.txtResult.appendText("Creazione grafo...\n\n");
    	
    	Anno anno = this.boxAnno.getValue();
    	
    	if(anno == null) {
    		System.out.println("Errore, devi selezionare un anno!");
    		return;
    	}
    	
    	this.model.creaGrafo(anno);
    	this.txtResult.appendText(this.model.getInfo());
    	
    	this.boxStato.getItems().clear();
    	this.boxStato.getItems().addAll(this.model.getVertex());

    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	
    	String stato = this.boxStato.getValue();
    	
    	if(stato == null) {
    		System.out.println("Errore, devi selezionare uno stato!");
    		return;
    	}
    	
    	this.txtResult.appendText("\n\nSequenza...\n\n");
    	
    	List<String> seq = new ArrayList<String>(this.model.trovaPercorso(stato));
    	
    	if(seq.size() == 1) {
    		this.txtResult.appendText("Nessun percorso trovato");
    	} else {
    		for(String s : seq) {
    			this.txtResult.appendText(s + "\n");
    		}
    	}

    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxAnno.getItems().addAll(this.model.getAnno());
	}
}
