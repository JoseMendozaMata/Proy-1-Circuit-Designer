package gatesFunctionality;

public class ReferenceList {

	ReferenceNode first = null;
	
	public int lenght = 0;
	//Tiene que ser de strings
	
	public void add(ReferenceNode ref) {
		if(!(this.IsRepeated(ref.getReference()))) {

			if(this.first == null) {
				this.first = ref;
				this.lenght++;
				System.out.println("Se añade el primer elemento de ReferenceList, este es: " + ref.getReference());
				
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
				
	}
	
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
	public boolean IsRepeated(String content) {
		
		ReferenceNode node = this.first;
		
		if(node == null) {		// Si la lista está vacía, no hay nada repetido
			System.out.println("referencia vacia");
			return false;
		}
		if(node.getReference() == content) {	// Se repite con el primer elemento de la lista
			System.out.println("Se repite con el primer elemento");
			return true;
		}
		
		while(node != null) {
			
			System.out.println("Comparo si: " + node.getReference() + " es igual a " + content);
			
			if(node.getReference() == content) {
				System.out.println("Repetido");
				return true;
			}
			node = node.getNext();
		}
		
		return false;
	}
}
