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

    public void addAresta(int vIni, int vFim){
        representacao.addAresta(vIni, vFim);
    }

    public Representacao getRepresentacao(){
        return representacao;
    }

    public void imprimeRepresentacao(String mensagem){
        representacao.imprimeRepresentacao(mensagem);
    }
    
    public int getNumVertices(){
    	return numVertices;
    }
}
