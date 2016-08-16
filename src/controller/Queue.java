package controller;


public class Queue {
	private List l;
	
	public Queue(){
		this.l = new List();
	}
	
	public void add(int value, int weight){
		l.addFinal(value, weight);
	}
	
	public void remove(){
		No aux = l.getHead();
		aux = aux.getProx();
		l.setHead(aux);
	}
	
	public No getHead(){
		return l.getHead();
	}
	
	public void show(){
		l.show();
	}
	
	public boolean verifyEmpty(){
		if(l.getHead() == null) return true;
		else return false;
	}
}
