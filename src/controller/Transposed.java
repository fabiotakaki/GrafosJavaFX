package controller;


public class Transposed {
	private Grafo g;
	private Grafo t;
	
	public Transposed(Grafo g){
		this.g = g;
		this.t = new Grafo(g.getNumVertices(), new ListaAdjacencia());
	}
	
	public Grafo execute(){
		int matrix[][] = g.getRepresentacao().getInverse();
		int weight[][] = g.getRepresentacao().getInverseWeight();
		
		for(int i=0; i<matrix.length; i++){
			for(int j=0; j < matrix[i].length; j++){
				if(matrix[i][j] == 1){
					t.addArestaD(i, j, weight[i][j]);
				}
			}
		}
		return t;
	}
}
