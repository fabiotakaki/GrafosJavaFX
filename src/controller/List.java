package controller;

/**
 * @author Fábio Takaki
 *
 */
public class List {
	private No head;

	public List(){
		this.head = null;
	}
	
	public void add(int value, int weight){
		No aux = new No(value, weight);
		aux.setProx(head);
		head = aux;
	}
	
	public void addFinal(int value, int weight){
		No aux = new No(value, weight);
		if(head == null) head = aux;
		else{
			No aux2 = head;
			while(aux2.getProx() != null)
				aux2 = aux2.getProx();
			aux2.setProx(aux);
		}
	}
	
	public boolean exist(int value){
		No aux = head;
		while(aux != null){
			if(aux.getVertID() == value){
				return true;
			}
			aux = aux.getProx();
		}
		return false;
	}
	
	public String show(){
            String imp="";
		No aux = head;
		while(aux != null){
                    imp = imp + aux.getVertID()+"(P:"+aux.getWeight()+")-->";
                    aux = aux.getProx();
		}
		imp = imp +"null";
                return imp;
	}
	
	public int getWeight(int value){
		No aux = head;
		while(aux != null){
			if(aux.getVertID() == value) return aux.getWeight();
			aux = aux.getProx();
		}
		return 0;
	}
	
	public No getHead(){
		return head;
	}
	
	public void setHead(No head){
		this.head = head;
	}
	
	
}
