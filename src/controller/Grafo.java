/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

/**
 *
 * @author Danilo Medeiros Eler
 */
public class Grafo {
    private int numVertices;
    private Representacao representacao;

    public Grafo(int numVert, Representacao representacao){
        this.numVertices = numVert;
        this.representacao = representacao;
        this.representacao.init(numVert);
    }

    public void addAresta(int vIni, int vFim, int weight){
    	representacao.addAresta(vIni, vFim, weight);
    }
    
    public void addArestaD(int vIni, int vFim, int weight){
    	representacao.addArestaD(vIni, vFim, weight);
    }

    public Representacao getRepresentacao(){
        return representacao;
    }
    
    public boolean verifyAdjacency(int u, int v){
		return representacao.verifyAdjacency(u, v);
	}

    public void imprimeRepresentacao(String mensagem){
        representacao.imprimeRepresentacao(mensagem);
    }
    
    public int getNumVertices(){
    	return numVertices;
    }

	public int getWeight(int u, int v) {
		// TODO Auto-generated method stub
		return representacao.getWeight(u, v);
	}
}
