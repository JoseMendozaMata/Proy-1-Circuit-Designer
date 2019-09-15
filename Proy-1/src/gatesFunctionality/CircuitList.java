
package gatesFunctionality;
import gatesFunctionality.specificGates.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import mainInterface.Interface;

// Esta es la lista enlazada en la cual voy a colocar las compuertas que haya creado, representa el circuito que
// estoy creando

public class CircuitList {

	public Gates first;	// Primer elemento del circuito
	public Gates last; 	// Ultimo elemento del circuito
	static public Rectangle sourceRectangle;	
	static public Rectangle destinyRectangle;
	
	public static int lenght;
	
	public CircuitList() {
		this.first = null;
		this.last = null;
		sourceRectangle = null;
		destinyRectangle = null;
	}
	
	// Método para agregar compuertas a la lista, que es el circuito
	
	public void addGate(Gates gate) {
		
		if(this.first == null) {
			this.first = gate;
			this.last = gate;
			this.lenght++;
			
		}else {
			Gates node = this.first;
			
			while(node.getNext() != null) {
				node = node.getNext();
			}
			
			System.out.println("Se añadió un elemento");
			++lenght;
			node.setNext(gate);
			gate.setPrev(node);
			this.last = gate;
		}
		
	}
	
	// Me dice si puedo conectar dos compuertas
	public boolean canConnect() {

		boolean res = false;
		System.out.println("En can connect");

		//Megavalidación de colores Legendaria:
		/*	Rectangulo color AQUA o DARKRED representan entradas de las compuertas
		 * Rectangulo color GREEN representan las salidas
		 * Si el rectangulo es AQUA O DARKRED, solo puede conectarse a un rectangulo GREEN
		 * Si el rectangulo es GREEN, solo puede conectarse a algo que no sea GREEN
		 */

		// Si es una entrada, solo puede conectarse con salidas
		if((this.sourceRectangle.getFill() == Color.AQUA ||
				this.sourceRectangle.getFill() == Color.DARKRED
				) && this.destinyRectangle.getFill() == Color.GREEN) {

			res = true;
		}

		if((this.sourceRectangle.getFill() == Color.GREEN &&
				!(this.destinyRectangle.getFill() == Color.GREEN))) {

			res = true;
		}
		
		Gates node1 = null;
		Gates node2 = null;
		
		node1 = this.getByRectangle(sourceRectangle);
		node2 = this.getByRectangle(destinyRectangle);
		
		if(node1.equals(node2)) {
			System.out.println("Los rectangulos pertenecen a la misma compuerta");
			res = false;
		}
		
		return res;

	}

	 // Conecta dos compuertas que se encuentran en esta lista
	public void connectGates() {
		
		//TODO Conectar lógicamente las compuertas relacionadas
		 Rectangle source = CircuitList.sourceRectangle;
		 Rectangle destiny = CircuitList.destinyRectangle;
		 
		 System.out.println("En conecting gates");
		 System.out.println(source);
		 System.out.println(destiny);
		 
		 // Las compuertas a las que pertenecen los rectangulos de las conexiones que quiero hacer
		 Gates sourceGate = null;
		 Gates destinyGate = null;
		 
		 sourceGate = this.getByRectangle(source);
		 destinyGate = this.getByRectangle(destiny);
	
		 System.out.println("sourceGate : " +  sourceGate );
		 System.out.println("destinyGate : " +  destinyGate );
		 
		 // Aquí debo conectar logicamente la compuerta, que está bien loco, porque este es cuando lo conecto
		 // pero respecto a as compuertas,no a la lista
		 
		 System.out.println("Los prev Gate del destiny son:");
		 System.out.println("prev 1 : " + destinyGate.getPrevGate1());
		 System.out.println("prev 2 : " + destinyGate.getPrevGate2());
		 
		 // Esto es cuando puedo conectar un output con un input de unas compuertas
		 if(destinyRectangle.equals(destinyGate.getEntry1()) && destinyGate.getPrevGate1() == null) {
			 System.out.println("Puedo conectar el input 1");
			 destinyGate.setPrevGate1(sourceGate);
		 }
		 if(destinyRectangle.equals(destinyGate.getEntry2()) && destinyGate.getPrevGate2() == null){
			 System.out.println("Puedo conectar el input 2");
			 destinyGate.setPrevGate2(sourceGate);
		 }
		 
		 System.out.println(sourceGate.getGateImage().getId());
		 System.out.println(destinyGate.getGateImage().getId());
		 
	}
	
	 // Busca un nodo por rectángulo, ya sea Entry1, Entry2 u Output
	public Gates getByRectangle(Rectangle Rect) {
		
		Gates node = this.first;
		System.out.println("En getByRectangle: " + Rect);
		
		if(node.getEntry1().equals(Rect) || node.getEntry2().equals(Rect) || node.getOutput().equals(Rect)) {
			System.out.println("Encontrado, es el primero");;
		}else {
			while(!node.getEntry1().equals(Rect) && !node.getEntry2().equals(Rect) && !node.getOutput().equals(Rect)) {
				node = node.getNext();
				if(node == null) {
					System.out.println("No encontré el rectangulo");
				}
			}
			// Aca lo encuentro
			System.out.println("Encontrado");
		}
		
		return node;
	}
	
	// Obtiene la compuerta por medio del id de la imagen
	public Gates getById(String sourceId) {
		
		Gates node = this.first;
		
		if(node.getGateImage().getId() == sourceId) {
			System.out.println(node.getGateImage().getId());
			System.out.println("Encontrado por id, es el primero");
		}else {
			while(!(node.getGateImage().getId() == sourceId)) {
				System.out.println(node.getGateImage().getId());
				node = node.getNext();
			}
			// Aca lo encuentro
			System.out.println("Encontrado por id");
		}
		
		return node;
	}
	
	// Esto indica cuáles nodos son nodos de entrada, osea que hay que ponerles input
	public void getInputGates() {
		
		System.out.println("Reviso entradas");
		
		Gates nodo = this.first;
		
		while(nodo != null) {
			
			if(nodo.getPrevGate1() == null && nodo.getPrevGate2() == null) {
				System.out.println(nodo.getGateImage().getId());
			}
			nodo = nodo.getNext();
		}
		
	}
	
}
