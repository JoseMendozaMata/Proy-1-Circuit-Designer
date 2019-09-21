package gatesFunctionality;

public class ReferenceList {

	ReferenceNode first = null;
	
	int lenght = 0;
	//Tiene que ser de strings
	
	public void add(ReferenceNode ref) {
		
		if(this.first == null) {
			this.first = ref;
			this.lenght++;
			System.out.println("Se añade el primer elemento de ReferenceList");
			
		}else {
			ReferenceNode node = this.first;
			
			while(node.getNext() != null) {
				node = node.getNext();
				
			}
			
			System.out.println("Se añadió un elemento");
			++lenght;
			node.setNext(ref);
		}
		
	}
	//TODO: Crear lista de referencias y setearla para el algoritmo
	
	public ReferenceNode getIndex(int index) {
		
		ReferenceNode node = this.first;
		int cont = 0;
		
		while(cont < index) {
			
			node = node.getNext();
			cont++;
		}
		System.out.println("Retorno el nodo que tiene la id: " + node.getReference());
		return node;
	}
	public boolean IsRepeated() {
		
		
		
		return false;
	}
}
