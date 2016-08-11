/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;



/**
 *
 * @author Danilo Medeiros Eler
 */
public class No {
    private int vertID;
    private int weight;
    private No prox;

    public No(int vertID, int weight) {
        this.vertID = vertID;
        this.setWeight(weight);
        this.prox = null;
    }

    public int getVertID() {
        return vertID;
    }

    public void setVertID(int vertID) {
        this.vertID = vertID;
    }

    public No getProx() {
        return prox;
    }

    public void setProx(No prox) {
        this.prox = prox;
    }

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
}