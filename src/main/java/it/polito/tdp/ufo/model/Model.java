package it.polito.tdp.ufo.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import it.polito.tdp.ufo.db.SightingsDAO;

public class Model {
	
	private SightingsDAO dao;
	private Graph<String, DefaultWeightedEdge> grafo;
	private List<String> vertex;
	private List<String> soluzioneMigliore;
	private Integer best;
	
	public List<Anno> getAnno(){
		this.dao= new SightingsDAO();
		return this.dao.getAnno();
	}
	
	public void creaGrafo(Anno anno) {
		this.dao = new SightingsDAO();
		this.grafo = new SimpleDirectedGraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		this.vertex = new ArrayList<String>(this.dao.getVertex(anno.getAnno()));
		
		Graphs.addAllVertices(this.grafo, this.vertex);
		
		if(!this.vertex.isEmpty()) {
			for(String stato : this.vertex) {
				LocalDateTime first = this.dao.getFirst(stato, anno.getAnno());
				List<String> successivi = new ArrayList<String>(this.dao.getSuccessivi(stato, anno.getAnno(), first));
				if(!successivi.isEmpty()) {
					for(String s : successivi) {
						Graphs.addEdgeWithVertices(this.grafo, stato, s);
					}
				}
			}
		}
		
	}
	
	public List<String> getSuccessivi(String stato){
		
		List<String> successivi = new ArrayList<String>();
		
		for(DefaultWeightedEdge e : this.grafo.outgoingEdgesOf(stato)) {
			successivi.add(Graphs.getOppositeVertex(this.grafo, e, stato));
		}
		
		return successivi;
		
	}
	
	public List<String> getPrecedenti(String stato){
		
		List<String> precedenti = new ArrayList<String>();
		
		for(DefaultWeightedEdge e : this.grafo.incomingEdgesOf(stato)) {
			precedenti.add(Graphs.getOppositeVertex(this.grafo, e, stato));
		}
		
		return precedenti;
		
	}
	
	public List<String> trovaPercorso(String partenza){
		
		this.soluzioneMigliore = null;
		this.best = 0;
		
		List<String> parziale = new ArrayList<String>();
		
		parziale.add(partenza);
		cerca(parziale, 1);
		
		return this.soluzioneMigliore;
		
	}
	
	private void cerca(List<String> parziale, int livello) {
		
		String ultimo = parziale.get(parziale.size()-1);
		
		if(parziale.size() > this.best) {
			if(this.soluzioneMigliore == null) {
				this.soluzioneMigliore = new ArrayList<String>(parziale);
				this.best = parziale.size();
			} else {
				this.soluzioneMigliore = new ArrayList<String>(parziale);
				this.best = parziale.size();
			}
		}
		
		for(String s : Graphs.successorListOf(this.grafo, ultimo)) {
			if(!parziale.contains(s)) {
				parziale.add(s);
				cerca(parziale, livello+1);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}

	public String getInfo() {
		return "Grafo creato ( Vertici " + this.grafo.vertexSet().size() + ", Archi " + this.grafo.edgeSet().size() + ")";
	}

	public List<String> getVertex() {
		return vertex;
	}
	
	

}
