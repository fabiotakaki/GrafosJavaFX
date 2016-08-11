/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author Danilo Medeiros Eler
 */
public class MatrizAdjacencia extends Representacao {

    private int[][] matriz;
    private int[][] weight;
    @Override
    public void init(int numVertices) {
        numVert = numVertices;
        matriz = new int[numVertices][numVertices];
        weight = new int[numVertices][numVertices];
        fillMatrizAdjacencia(0);
    }

    public void fillMatrizAdjacencia(int value) {
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                matriz[i][j] = value;
                weight[i][j] = value;
            }
        }
    }

    @Override
    public void addAresta(int vIni, int vFim, int weight) {
        int vi = vIni;
        int vj = vFim;

        matriz[vi][vj] = 1;
        matriz[vj][vi] = 1;
        this.weight[vi][vj] = weight;
        this.weight[vj][vi] = weight;
    }
    
    @Override
    public void addArestaD(int vIni, int vFim, int weight) {
        int vi = vIni;
        int vj = vFim;

        matriz[vi][vj] = 1;
        this.weight[vi][vj] = weight;
    }

    @Override
    public void imprimeRepresentacao(String mensagem) {
        System.out.println("=================================");
        System.out.println(mensagem);
        System.out.println("=================================\n");
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.print(matriz[i][j]+"["+weight[i][j]+"] ");
            }
            System.out.println("");
        }
    }
}
