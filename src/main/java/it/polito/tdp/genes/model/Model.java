package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {

	private List<Integer> vertici;
	private GenesDao dao;
	private Graph<Integer, DefaultWeightedEdge> grafo;
	
	
	public Model() {
		dao = new GenesDao();
	}
	
	
	public void creaGrafo() {
		grafo = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		vertici = new ArrayList<>();
		
		vertici = dao.getVertici();
		
		Graphs.addAllVertices(this.grafo, this.vertici);
		
		List<Adiacenza> archi = dao.getArchi();
		for(Adiacenza arco : archi) {
			Graphs.addEdge(this.grafo, arco.getC1(), arco.getC2(), arco.getPeso());
		}
		
	}
	
	public Integer NVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public Integer NArchi() {
		return this.grafo.edgeSet().size();
	}
	
	public Double minPeso() {
		Double min = 500.00;
		
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			if(min> grafo.getEdgeWeight(e)) {
				min = grafo.getEdgeWeight(e);
			}
		}
		return min;
		
	}
	
	
	public Double maxPeso() {
		Double max = -500.00;
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			if(max< grafo.getEdgeWeight(e)) {
				max = grafo.getEdgeWeight(e);
			}
		}
		return max;
	}
	
	public Integer maggSoglia(Double soglia) {
		Integer magg = 0;
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			if(grafo.getEdgeWeight(e) > soglia) {
				magg ++;
			}
		}
		return magg;
	}
	
	public Integer minSoglia(Double soglia) {
		Integer min = 0;
		for(DefaultWeightedEdge e : this.grafo.edgeSet()) {
			if(grafo.getEdgeWeight(e) < soglia) {
				min ++;
			}
		}
		return min;
	}

}