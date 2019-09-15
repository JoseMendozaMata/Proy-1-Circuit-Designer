package gatesFunctionality;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public interface Gates {

	public double getPosX();
	public void setPosX(double posX); 
	
	public double getPosY(); 
	public void setPosY(double posY); 
	
	public boolean getValueInput1(); 
	public void setValueInput1(boolean valueInput1); 
	
	public boolean getValueInput2(); 
	public void setValueInput2(boolean valueInput2); 
	
	public boolean isConected(); 
	public void setConected(boolean isConected); 
	
	public boolean getOutputValue(); 
	public void setOutputValue(); 
	
	public boolean isEntrance(); 
	public void setEntrance(boolean isEntrance); 
	
	public ImageView getGateImage(); 
	public void setGateImage();
	
	public Rectangle getEntry1(); 
	public void setEntry1(); 
	
	public Rectangle getEntry2(); 
	public void setEntry2(); 
	
	public Rectangle getOutput(); 
	public void setOutput();
	
	public Gates getPrev();
	public void setPrev(Gates gate);
	
	public Gates getNextGate();
	public void setNextGate(Gates gate);
	
	public Gates getPrevGate1();
	public void setPrevGate1(Gates gate);
	
	public Gates getPrevGate2();
	public void setPrevGate2(Gates gate);
	
	public Gates getNext();
	public void setNext(Gates gate);
	
	public void moveGate(double newX, double newY);
	
}
