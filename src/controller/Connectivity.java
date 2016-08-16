package controller;

import Grafos.desenho.Graph;
import Grafos.desenho.color.RainbowScale;
import javafx.scene.paint.Color;

public class Connectivity {
	private Grafo g;
	private Grafo t;
	private Graph d;
	private String[] colors;
	private int[] starts;
	private int[] ends;
	private int time;
	
	// para os caminhos
	private int goodPath[];
	private boolean verify;
	
	//cor
	private Color color;
	
	/*
	 * colors:
	 *		w: white
	 * 		g: gray
	 *		b: black
	 */
	
	public Connectivity(Grafo g, Graph d){
		this.g 		= g;
		this.d		= d;
		this.time	= 0;
		this.colors = new String[g.getNumVertices()];
		this.starts = new int[g.getNumVertices()];
		this.ends 	= new int[g.getNumVertices()];
		
		this.goodPath = new int[g.getNumVertices()];
		this.verify = true;
	}
	
	public boolean process(int num){
		//transposição
		Transposed tp = new Transposed(g);
		this.t = tp.execute();
		
		//cores
		RainbowScale cS = new RainbowScale();
		int colorStep = 255 / t.getNumVertices();
		this.color = cS.getColor(0*colorStep);
		
		if(num >= t.getNumVertices())
                    return false;
		for(int i=0; i< t.getNumVertices(); i++){
			this.colors[i] = "w";
			this.goodPath[i] = -1;
		}
		
		this.time = 0;
		
		if(colors[num].equals("w")){
			visit(num); // visito o vértice inicial
		}
        verify = false;
		for(int i=0; i< t.getNumVertices(); i++){
			this.color = cS.getColor((i+1)*colorStep);
			if(colors[i].equals("w")){
				visit(i); // visito o vértice
			}
		}
                
        return true;
	}
	
	protected void visit(int i){
		d.getVertex().get(i).changeColor(this.color);
		this.colors[i] = "g";
		this.time++;
		this.starts[i] = time;
		
		for(int j=0; j < t.getNumVertices(); j++){
			if(t.verifyAdjacency(i, j)){
				if(colors[j] == "w"){
					visit(j);
					if(verify) goodPath[j] = i;
				}
			}
		}
		colors[i] = "b";
		this.time++;
		ends[i] = this.time;
		
	}
	
	public int[] getGoodPath(){
		return goodPath;
	}
	
	public String show(){
        String imp = "";
		for(int i=0; i< t.getNumVertices(); i++){
                    imp = imp+i+"\nChegada:" + starts[i]+"\nFinal:" + ends[i]+"\n\n";
		}
        return imp;
	}
}
