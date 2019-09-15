package gatesFunctionality;

import gatesFunctionality.Gates;
import gatesFunctionality.specificGates.*;	// Aquí tenemos todas las cmpuertas individuales


// Factory que se encarga de crear las compuertas 

public class GatesFactory {

	public Gates getGate(GatesName n) {
		
		Gates gate = null;
		
		if(n == GatesName.AND) {
			gate =  new AndGate();
			System.out.println("Creo un And");
		}else if(n == GatesName.OR) {
			gate =  new OrGate();
			System.out.println("Creo un Or");
		}
		
		return gate;
	}
	
}
