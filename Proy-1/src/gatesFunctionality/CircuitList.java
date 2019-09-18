
package gatesFunctionality;
import java.util.ArrayList;

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
		
		Gates sourceNode = null;
		Gates destinyNode = null;
		
		sourceNode = this.getByRectangle(sourceRectangle);
		destinyNode = this.getByRectangle(destinyRectangle);
		
		// Cuando se trata de conectar una compuerta consigo misma
		if(sourceNode.equals(destinyNode)) {
			System.out.println("Los rectangulos pertenecen a la misma compuerta");
			res = false;
		}
		
		// Esto es cuando puedo conectar un output con un input de unas compuertas
		// Estos if es cuando quiero conectar un output con un input
		if(destinyRectangle.equals(destinyNode.getEntry1())) {	// Se que hablo del entry1
			if(!(destinyNode.getPrevGate1() == null)) {	// si no está vacío, no lo puedo conectar
				System.out.println("Input 1 ocupado");
				res = false;
			}
		}
		else if(destinyRectangle.equals(destinyNode.getEntry2())){	// Se que hablo del entry2
			if(!(destinyNode.getPrevGate2() == null)) {
				System.out.println("Input 2 oupado");
				res = false;
			}
			
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
		 
		 // Aquí debo conectar logicamente la compuerta, que está bien loco, porque este es cuando lo conecto
		 // pero respecto a as compuertas,no a la lista
		 
		 // Esto es cuando puedo conectar un output con un input de unas compuertas
		 // Estos if es cuando quiero conectar un output con un input
		 if(destinyRectangle.equals(destinyGate.getEntry1()) && destinyGate.getPrevGate1() == null) {
			 System.out.println("Puedo conectar el input 1");
			 destinyGate.setPrevGate1(sourceGate);
			 sourceGate.setNextGate(destinyGate);
		 }
		 if(destinyRectangle.equals(destinyGate.getEntry2()) && destinyGate.getPrevGate2() == null){
			 System.out.println("Puedo conectar el input 2");
			 destinyGate.setPrevGate2(sourceGate);
			 sourceGate.setNextGate(destinyGate);
		 }
		 
		 // Este es cuando quiero conectar un input1 con un output
		 if(sourceRectangle.equals(sourceGate.getEntry1())) {
			 System.out.println("Conectando entry1 con output");
			 sourceGate.setPrevGate1(destinyGate);
			 destinyGate.setNextGate(sourceGate);
		 }
		 
		 // Este es cuando quiero conectar un input1 con un output
		 if(sourceRectangle.equals(sourceGate.getEntry2())) {
			 System.out.println("Conectando entry2 con output");
			 sourceGate.setPrevGate2(destinyGate);
			 destinyGate.setNextGate(sourceGate);
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
		
		if(node.getGateImage().getId().equals(sourceId)) {
			System.out.println(node.getGateImage().getId());
			
		}else {
			while(!(node.getGateImage().getId().equals(sourceId))) {
			
				node = node.getNext();
			}
			// Aca lo encuentro
			System.out.println("Encontrado por id");
		}
		return node;
	}
	
	// Esto indica cuáles nodos son nodos de entrada, osea que hay que ponerles input
	public ArrayList<Gates> getInputGates() {
		
		System.out.println("Reviso entradas");
		
		// Aquí guardo las compuertas de entrada del circuito
		ArrayList<Gates> Entrys = new ArrayList<>();
		
		Gates nodo = this.first;

		// Busco las compuertas que son entradas de circuito, esto es que ambas entrys sean nulas
		while(nodo != null) {
			if(nodo.getPrevGate1() == null && nodo.getPrevGate2() == null) {
				Entrys.add(nodo);	// Agrego el nodo a Gates
			}
			nodo = nodo.getNext();
		}
		System.out.println(Entrys);
		return Entrys;
		
	}
	
	public void peekPrevNext(int index) {
		
		int cont = 0;
		Gates node = this.first;
		
		// Accedo al nodo que quiero ver
		while(cont != index) {
			node = node.getNext();
			cont++;
		}
		
		System.out.println("Prev1 de la compuerta " + cont + " : " + node.getPrevGate1());
		System.out.println("Prev2 de la compuerta " + cont + " : " + node.getPrevGate2());
		System.out.println("Next de la compuerta " + cont + " : " + node.getNextGate());
		
	}
	
	// Obtengo el output del circuito
	public boolean getOutputCircuit(ArrayList<Gates> generation) {

		System.out.println("Esta generacion tiene: " + generation.size() + " elementos");

		boolean result = false;

		ArrayList<Gates> newGen = new ArrayList<>();

		for(Gates gate:generation) {

			System.out.println("Compuerta" + gate.getId().getText());
			System.out.println("El Entry1Value de esta compuerta es: " + gate.getPrevGate1().getOutputValue());
			System.out.println("El Entry2Value de esta compuerta es: " + gate.getPrevGate2().getOutputValue());

			gate.setValueInput1(gate.getPrevGate1().getOutputValue()); 	// Seteo los inputs con valores de compuertas anteriores
			gate.setValueInput2(gate.getPrevGate2().getOutputValue()); 	// Seteo los inputs con valores de compuertas anteriores
			gate.setOutputValue(); 	// Seteo el valor output de las compuertas de la generación

			System.out.println("El OutputValue de esta compuerta es: " + gate.getOutputValue());

			if(!(newGen.contains(gate.getNextGate()))) {	// Si la nueva generacion no tenía la compuerta,la agrego
				newGen.add(gate.getNextGate());
			} 

			if(gate.getNextGate() == null) {	// Quiere decir que se llegó a un punto de salida del circuito
				System.out.println("En el nodo final");
				gate.setOutputValue();
				result = gate.getOutputValue();
				return result;
			}

		}

		System.out.println("Mando a llamar al metodo generacion");

		return getOutputCircuit(newGen);
	}
	
}
