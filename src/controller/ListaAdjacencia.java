/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import Grafos.desenho.Edge;
import Grafos.desenho.Graph;

/**
 *
 * @author Danilo Medeiros Eler
 */
public class ListaAdjacencia extends Representacao{
    private No[] listaVertices;

    @Override
    public void init(int numVertices) {
        this.numVert = numVertices;
        listaVertices = new No[numVertices];
    }
    
    @Override
    public boolean verifyAdjacency(int u, int v){
		No aux = listaVertices[u];
		while(aux != null){
			if(aux.getVertID() == v){
				return true;
			}
			aux = aux.getProx();
		}
		return false;
	}

    @Override
    public void addAresta(int vIni, int vFim, int weight) {
        No novoNo = new No(vFim, weight);
        novoNo.setProx( listaVertices[vIni] );
        listaVertices[vIni] = novoNo;

        novoNo = new No(vIni, weight);
        novoNo.setProx( listaVertices[vFim] );
        listaVertices[vFim] = novoNo;
    }
    
    @Override
    public int getWeight(int u, int v){
    	No aux = listaVertices[u];
		while(aux != null){
			if(aux.getVertID() == v){
				return aux.getWeight();
			}
			aux = aux.getProx();
		}
		return 0;
    }
    
    @Override
    public void addArestaD(int vIni, int vFim, int weight) {
        No novoNo = new No(vFim, weight);
        novoNo.setProx( listaVertices[vIni] );
        listaVertices[vIni] = novoNo;
    }

    public No getAdjacentes(int vert){
        return listaVertices[vert];
    }

    @Override
    public void imprimeRepresentacao(String mensagem) {
        System.out.println("=================================");
        System.out.println(mensagem);
        System.out.println("=================================\n");
        for (int i=0; i<listaVertices.length; i++){
            No aux = listaVertices[i];
             System.out.print("(((" + i + ")))-->");
            while (aux != null){
                System.out.print("|" + aux.getVertID() + "|--> ");
                aux = aux.getProx();
            }
            System.out.println("//");
        }
    }
    
    public void prim(Graph g){
    	for (int i=0; i<listaVertices.length; i++){
            No aux = listaVertices[i];
            g.getVertex().get(i).getCircle().setOpacity(1.0f);
            while (aux != null){
                System.out.print("|" + aux.getVertID() + "|--> ");
                
                g.getVertex().get(aux.getVertID()).getCircle().setOpacity(1.0f);
                
                for (Edge edge : g.getEdges()) {
                    if(edge.getSource().getID() == i && edge.getTarget().getID() == aux.getVertID()){
                    	edge.getConnect().setOpacity(1.0f);
                   		edge.getConnect().setOpacity(1.0f);
                   		edge.getConnect().setStrokeWidth(3.0f);
                    }
                }
                
                aux = aux.getProx();
            }
            System.out.println("//");
        }
    }
    
    @Override
    public int[][] getInverse(){
    	int[][] inv = new int[listaVertices.length][listaVertices.length];
    	for(int i=0; i < listaVertices.length; i++){
    		for(int j=0; j < listaVertices.length; j++){
    			inv[i][j] = 0;
    		}
    	}
    	for(int i=0; i<listaVertices.length; i++){
	    	No aux = listaVertices[i];
	    	int target = i;
	    	while(aux != null){
	    		int source = aux.getVertID();
	    		inv[source][target] = 1;
	    		aux = aux.getProx();
	    	}
    	}
    	return inv;
    }
    
    @Override
    public int[][] getInverseWeight(){
    	int[][] inv = new int[listaVertices.length][listaVertices.length];
    	for(int i=0; i < listaVertices.length; i++){
    		for(int j=0; j < listaVertices.length; j++){
    			inv[i][j] = 0;
    		}
    	}
    	for(int i=0; i<listaVertices.length; i++){
	    	No aux = listaVertices[i];
	    	int target = i;
	    	while(aux != null){
	    		int source = aux.getVertID();
	    		inv[source][target] = aux.getWeight();
	    		aux = aux.getProx();
	    	}
    	}
    	return inv;
    }
    

}
