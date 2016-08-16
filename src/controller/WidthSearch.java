package controller;

import controller.Grafo;
import controller.No;
import controller.Queue;

public class WidthSearch {
	private Grafo g;
	private String[] colors;
	private int pi[];
	private int distance[];
	private Queue q;
	
	public WidthSearch(Grafo g){
		this.g = g;
		this.colors = new String[g.getNumVertices()];
		this.pi = new int[g.getNumVertices()];
		this.distance = new int[g.getNumVertices()];
		this.q = new Queue();
	}
	
	public boolean process(int num){
                if(num >= g.getNumVertices())
                    return false;
		int s = num;
		int w = 0;
		for(int i=0; i<g.getNumVertices(); i++){
			colors[i] = "w";
			distance[i] = Integer.MAX_VALUE;
			pi[i] = -1;
		}

		colors[s] = "g";
		distance[s] = 0;
		pi[s] = -1;
		q.add(s, w);
		
		while(!q.verifyEmpty()){
			No u = q.getHead();
			q.remove();
			for(int v=0; v<g.getNumVertices(); v++){
				if(g.verifyAdjacency(u.getVertID(), v)){
					if(colors[v] == "w"){
						colors[v] = "g";
						distance[v] = distance[u.getVertID()]+1;
						pi[v] = u.getVertID();
						q.add(v, w);
					}
				}
			}
			colors[u.getVertID()] = "b";
		}
                return true;
	}
	
	public int[] getPi(){
		return this.pi;
	}
	
	public String[] getColors(){
		return this.colors;
	}
	
	public int[] getDistances(){
		return this.distance;
	}
	
	public String show(){
        String imp = "";
		for(int i=0; i< g.getNumVertices(); i++){
            if(distance[i] == Integer.MAX_VALUE)
                imp+="D ["+i+"]: XX\n";
            else
                imp+="D ["+i+"]: "+distance[i]+"\n";
		}
        return imp;
	}
}