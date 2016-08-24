package controller;

import java.util.ArrayList;
import java.util.Stack;

import Grafos.desenho.Graph;
import Grafos.desenho.Vertex;

public class TopologicalOrder {
	private Grafo g;
	private Graph d;
	private String[] colors;
	private int[] starts;
	private int[] ends;
	private int time;
	
	// pilha ordem topológica
	private Stack<Integer> p;
	
	// para os caminhos
	private int goodPath[];
	private boolean verify;
	
	/*
	 * colors:
	 *		w: white
	 * 		g: gray
	 *		b: black
	 */
	
	public TopologicalOrder(Grafo g, Graph d){
		this.g 		= g;
		this.d		= d;
		this.time	= 0;
		this.colors = new String[g.getNumVertices()];
		this.starts = new int[g.getNumVertices()];
		this.ends 	= new int[g.getNumVertices()];
		
		this.goodPath = new int[g.getNumVertices()];
		this.verify = true;
		this.p = new Stack<Integer>();
	}
	
	public void process(int num){
		for(int i=0; i< g.getNumVertices(); i++){
			this.colors[i] = "w";
			this.goodPath[i] = -1;
		}
		
		this.time = 0;
		
		if(colors[num].equals("w")){
			visit(num); // visito o vértice inicial
		}
        verify = false;
		for(int i=0; i< g.getNumVertices(); i++){
			if(colors[i].equals("w")){
				visit(i); // visito o vértice
			}
		}
                
       	positions();
	}
	
	private void positions(){
		ArrayList<Vertex> v = d.getVertex();

		int i=0;
        while(!p.isEmpty()){
        	int vertex = p.pop();
            float X = (float) 0;
            float Y = (float) 0;
            X = X + (100*i);  
            if(i%2 == 0) Y = 100;
            else Y = 0;
            v.get(vertex).getCircle().setCenterX(X);
            v.get(vertex).getCircle().setCenterY(Y);
            v.get(vertex).getIdVertex().setLayoutX(X - 4);
            v.get(vertex).getIdVertex().setLayoutY(Y - 6);
            i++;
        }
	}
	
	protected void visit(int i){
		this.colors[i] = "g";
		this.time++;
		this.starts[i] = time;
		
		for(int j=0; j < g.getNumVertices(); j++){
			if(g.verifyAdjacency(i, j)){
				if(colors[j] == "w"){
					visit(j);
					if(verify) goodPath[j] = i;
				}
			}
		}
		colors[i] = "b";
		this.time++;
		ends[i] = this.time;
		p.push(i);
		
	}
	
	public int[] getGoodPath(){
		return goodPath;
	}
	
	public String show(){
            String imp = "";
		for(int i=0; i< g.getNumVertices(); i++){
                    imp = imp+i+"\nChegada:" + starts[i]+"\nFinal:" + ends[i]+"\n\n";
		}
                return imp;
	}
}
