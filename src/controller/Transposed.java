package controller;

import Grafos.desenho.Edge;
import Grafos.desenho.Graph;
import Grafos.desenho.Vertex;

public class Transposed {
	private Grafo g;
	private Grafo t;
	private Graph d;
	
	public Transposed(Grafo g){
		this.g = g;
		this.t = new Grafo(g.getNumVertices(), new ListaAdjacencia());
		this.d = new Graph(g.getNumVertices());
	}
	
	public Grafo execute(){
		int matrix[][] = g.getRepresentacao().getInverse();
		int weight[][] = g.getRepresentacao().getInverseWeight();
		
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j < matrix[i].length; j++){
				if(matrix[i][j] == 1){
					Vertex vS = this.d.getVertex().get(i);
                    Vertex vT = this.d.getVertex().get(j); 
					Edge e = new Edge(vS, vT, weight[i][j], 1);
					d.addEdge(e);
					
					t.addArestaD(i, j, weight[i][j]);
				}
			}
		}
		return t;
	}
	
	public Graph getGraph(){
		return d;
	}
}
