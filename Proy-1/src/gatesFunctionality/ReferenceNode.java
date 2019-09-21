package gatesFunctionality;

public class ReferenceNode {

	private String reference = null;
	private ReferenceNode next = null;
	
	
	public void setReference(String reference) {
		
		this.reference = reference;
		
	}
	
	public String getReference() {
		return this.reference;
	}
	
	public void setNext(ReferenceNode node) {
		this.next = node;
	}
	
	public ReferenceNode getNext() {
		return this.next;
	}
	
}
