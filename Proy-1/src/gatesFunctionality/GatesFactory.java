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
		}else if(n == GatesName.NAND) {
			gate = new NandGate();
			System.out.println("Creo un Nand");
		}else if(n == GatesName.NOR) {
			gate = new NorGate();
			System.out.println("Creo un Nor");
		}else if(n == GatesName.XOR) {
			gate = new XorGate();
			System.out.println("Creo un Xor");
		}else if(n == GatesName.XNOR) {
			gate = new XnorGate();
			System.out.println("Creo un Xnor");
		}else if(n == GatesName.NOT) {
			gate = new NotGate();
			System.out.println("Creo un Not");
		}
		
		return gate;
	}
	
}
