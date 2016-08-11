/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;



/**
 *
 * @author Danilo Medeiros Eler
 */
public class ListaAdjacencia extends Representacao{
    private No[] listaVertices;
    
    @Override
    public void init(int numVertices) {
        numVert = numVertices;
        listaVertices = new No[numVertices];
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

}
