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
	public boolean getResult(); 
	public void setResult(boolean result); 
	public boolean isEntrance(); 
	public void setEntrance(boolean isEntrance); 
	public ImageView getGateImage(); 
	public Rectangle getEntry1(); 
	public void setEntry1(Rectangle entry1); 
	public Rectangle getEntry2(); 
	public void setEntry2(Rectangle entry2); 
	
	
	
}
