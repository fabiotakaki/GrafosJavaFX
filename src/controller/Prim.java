package controller;

public class Prim {
	private int Q[]; // vértices que ainda não fazem parte de X
	private int key[]; // peso da aresta mais leve do vértice key[u]
	private Grafo X; // resultado da arvore minima
	private int pi[]; // vértice pai do vértice pi[u]
	private Grafo g;
	
	public Prim(Grafo g){
		this.g  = g;
		Q 		= new int[g.getNumVertices()];
		key 	= new int[g.getNumVertices()];
		X 		= new Grafo(g.getNumVertices(), new ListaAdjacencia());
		pi 		= new int[g.getNumVertices()];
		for(int i = 0; i<g.getNumVertices(); i++){
			Q[i] = 0;
			pi[i] = -1;
		}
	}
	
	protected boolean emptyQ(){
		for(int i=0; i<g.getNumVertices(); i++){
			if(Q[i] == 1){
				return true;
			}
		}
		return false;
	}
	
	protected int extractMin(){
		int aux = Integer.MAX_VALUE;
		int x = 0;
		for(int i=0; i<g.getNumVertices(); i++){
			if(key[i] < aux && Q[i] != 0){
				aux = key[i];
				x = i;
			}
		}
		Q[x] = 0;
		return x;
	}
	
	public Grafo process(int num){
		int r = num;
		for(int i=0; i<g.getNumVertices(); i++){
			key[i] = Integer.MAX_VALUE;
			Q[i] = 1;
		}
		key[r] = 0;
		
		while(emptyQ()){
			int u = extractMin();
			if(pi[u] != -1)
				X.addAresta(u, pi[u], g.getWeight(u, pi[u]));
			for(int v=0; v<g.getNumVertices(); v++){
				if(g.verifyAdjacency(v, u)){
					if(Q[v] == 1 && g.getWeight(u, v) < key[v]){
						key[v] = g.getWeight(u, v);
						pi[v] = u;
					}// end if
				} // end if
			} //end for
		} // end while
		return X;
	}
	
}