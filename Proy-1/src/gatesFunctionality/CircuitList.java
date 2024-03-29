
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
	
	// M�todo para agregar compuertas a la lista, que es el circuito
	
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
			
			System.out.println("Se a�adi� un elemento");
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

		//Megavalidaci�n de colores Legendaria:
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
		
		System.out.println("Busco el rectangulo del sourceNode");
		sourceNode = this.getByRectangle(sourceRectangle);
		System.out.println("Busco el rectangulo del destinyNode");
		destinyNode = this.getByRectangle(destinyRectangle);
		
		// Cuando se trata de conectar una compuerta consigo misma
		if(sourceNode.equals(destinyNode)) {
			System.out.println("Los rectangulos pertenecen a la misma compuerta");
			res = false;
		}
		
		// Esto es cuando puedo conectar un output con un input de unas compuertas
		// Estos if es cuando quiero conectar un output con un input
		if(destinyRectangle.equals(destinyNode.getEntry1())) {	// Se que hablo del entry1
			if(!(destinyNode.getPrevGate1() == null)) {	// si no est� vac�o, no lo puedo conectar
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
		System.out.println("Termino canConnect");
		return res;

	}

	 // Conecta dos compuertas que se encuentran en esta lista
	public void connectGates() {
		
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
		 
		 // Aqu� debo conectar logicamente la compuerta, que est� bien loco, porque este es cuando lo conecto
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
	
	 // Busca un nodo por rect�ngulo, ya sea Entry1, Entry2 u Output
	public Gates getByRectangle(Rectangle Rect) {
		
		Gates node = this.first;
		System.out.println("En getByRectangle: " + Rect);
		
		//El �nico y detergente not 
		Gates notGate = isNotRect(Rect);
		
		if(notGate != null) {
			System.out.println("Retorno el not que encontre");
			return notGate;
		}
		
		System.out.println("No es un not el que busco");
		if(node.getEntry1().equals(Rect) || node.getEntry2().equals(Rect) || node.getOutput().equals(Rect)) {
			System.out.println("Encontrado, es el primero");;
		}else {
			System.out.println("Empiezo a recorrer la lista para buscar el rect�ngulo");
			while(!node.getEntry1().equals(Rect) && !node.getEntry2().equals(Rect) && !node.getOutput().equals(Rect)) {
				node = node.getNext();
				if(node == null) {
					System.out.println("No encontr� el rectangulo");
				}
			}
			// Aca lo encuentro
			System.out.println("Encontrado");
		}
		System.out.println("Termino canConnect");
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
	
	// Esto indica cu�les nodos son nodos de entrada, osea que hay que ponerles input
	public ArrayList<Gates> getInputGates() {
		
		System.out.println("Reviso entradas");
		
		// Aqu� guardo las compuertas de entrada del circuito
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
	public ReferenceList getOutputCircuit(ReferenceList generation, ReferenceList outputs) {

		System.out.println("Esta generacion tiene: " + generation.lenght + " elementos");

		boolean result = false;
		
		if(generation.lenght == 0) {
			
			System.out.println("Ya no quedan elementos");
			return outputs;
		}
		
		ReferenceList newGen = new ReferenceList();
		System.out.println("Empiezo el for de getOutput");
		for(int i = 0; i < generation.lenght; i++) {
			
			// obtengo la compuerta que guarda el id de la generaci�n, que contiene los id de las compuertas
			// de entrada del circuito
			System.out.println("Busco el indice: " + i + " de la reference list");
			Gates gate = getById(generation.getIndex(i).getReference());
			System.out.println(gate);
			System.out.println("Encuentro la compuerta");
			
			System.out.println("Compuerta" + gate.getId().getText());
			System.out.println("El Entry1Value de esta compuerta es: " + gate.getPrevGate1().getOutputValue());
			
			//El unico y detergente not
			if(!(gate.getType() == "NOT")) {
				System.out.println("Encuentro una compuerta que no es not");
				System.out.println("El Entry2Value de esta compuerta es: " + gate.getPrevGate2().getOutputValue());
				gate.setValueInput2(gate.getPrevGate2().getOutputValue()); 	// Seteo los inputs con valores de compuertas anteriores
			}
			
			gate.setValueInput1(gate.getPrevGate1().getOutputValue()); 	// Seteo los inputs con valores de compuertas anteriores
			gate.setOutputValue(); 	// Seteo el valor output de las compuertas de la generaci�n

			System.out.println("El OutputValue de esta compuerta es: " + gate.getOutputValue());
			
			/*
			if(!(newGen.contains(gate.getNextGate()))) {	// Si la nueva generacion no ten�a la compuerta,la agrego
				newGen.add(gate.getNextGate());
			} 
			*/
			
			if(gate.getNextGate() == null) {	// Quiere decir que se lleg� a un punto de salida del circuito
				System.out.println("En el nodo final");
				gate.setOutputValue();
				result = gate.getOutputValue();
				System.out.println("Un resultado del circuito es: " + result + " dado por la compuerta: " + gate.getId().getText());
				
				ReferenceNode out = new ReferenceNode();
				out.setReference(gate.getId().getText());	// A�ado la id de la compuerta a los resultados
				
				if(!(outputs.IsRepeated(gate.getId().getText()))) {	// Valido si la salida es la misma
					System.out.println("La compuerta de salida " + gate.getId().getText() + " no se repite");
					outputs.add(out);
				}else {
					System.out.println("La compuerta de salida " + gate.getId().getText() + " se repite");
				}
				
				
			}else {	//Quiere decir que hay una siguiente compuerta por comparar
				
				if(!(newGen.IsRepeated(gate.getNextGate().getId().getText()))) {	// Revisa si el elem est� repetido
					System.out.println("No est� repedido el " + gate.getNextGate().getId().getText());
					ReferenceNode newRef = new ReferenceNode();
					newRef.setReference(gate.getNextGate().getId().getText());
					newGen.add(newRef);
				}
				
			}
			
		}
		
		//System.out.println("Mando a llamar al metodo generacion");
		
		return getOutputCircuit(newGen, outputs);
		
	}
	
	public Gates isNotRect(Rectangle rect) {	// Me indica que el rect�ngulo pertenece a una compuerta not
	
		Gates node = this.first;
		
		if(node.getType() == "NOT" && (node.getEntry1().equals(rect) || node.getOutput().equals(rect))) {	//Si el primer elemento es un not y el rectangulo  le pertenece
			System.out.println("El primer elemento de la lista es un not y es el que busco");
		}else {
			
			while(node != null) {	//Busco si se cumple que el rectangulo pertenece a un not
				
				if(node.getType() == "NOT" && (node.getEntry1().equals(rect) || node.getOutput().equals(rect))) {
					System.out.println("Encontr� el rectangulo, pertenece a un not");
					return node;
				}
				
				node = node.getNext();
			}
			
		}
		
		return node;
		
	}
	
}
