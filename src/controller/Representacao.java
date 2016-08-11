/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

/**
 *
 * @author Danilo Medeiros Eler
 */
public abstract class Representacao {
    protected int numVert = 0;
    public int getNumVertices(){
        return numVert;
    }
    public abstract void init(int numVertices);
    public abstract void addAresta(int vIni, int vFim, int weight);
    public abstract void addArestaD(int vIni, int vFim, int weight);
    public abstract void imprimeRepresentacao(String mensagem);
}
