package gatesFunctionality.specificGates;

import gatesFunctionality.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;

public class AndGate implements Gates{
	
	private double posX;
	private double posY;
	private boolean ValueInput1;
	private boolean ValueInput2;
	private boolean isConected;
	private boolean Result;
	private boolean isEntrance;
	private ImageView gateImage;
	private Rectangle Entry1;
	private Rectangle Entry2;
	
	public AndGate(double posX, double posY, GatesName gateType) {
		
		this.posX = posX;		// Seteo la posición en X
		this.posY = posY;		// Seteo la posición en X
		
		this.gateImage = new ImageView(new Image("Images/AND.png"));	// Pongo la imagen AND
		
		this.Entry1 = new Rectangle(5 , 5, this.gateImage.getX(), 	// Seteo los rectangulos de las entradas
				this.gateImage.getY() + (this.gateImage.getY()/4));
		
		this.Entry2 = new Rectangle(5 , 5, this.gateImage.getX(), 	// Seteo los rectangulos
				this.gateImage.getY() + (this.gateImage.getY()*(3/4)));
		
	}

	@Override
	public double getPosX() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPosX(double posX) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getPosY() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setPosY(double posY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getValueInput1() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setValueInput1(boolean valueInput1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getValueInput2() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setValueInput2(boolean valueInput2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isConected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setConected(boolean isConected) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getResult() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setResult(boolean result) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEntrance() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setEntrance(boolean isEntrance) {
		// TODO Auto-generated method stub
		
	}

	// Este método se encarga de devolver la imagen de la compuerta And
	@Override
	public ImageView getGateImage() {
		
		return this.gateImage;
	}
	
	// Devuelve el rectángulo que representa la entrada 1
	@Override
	public Rectangle getEntry1() {
	
		return this.Entry1;
	}

	// Método que s eencarga de mover el rectángulo cuando éste se mueve en el Pane
	@Override
	public void setEntry1(Rectangle entry1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle getEntry2() {
		
		return this.Entry2;
	}

	// Método que se encarga de mover el rectángulo cuando éste se mueve en el Pane
	@Override
	public void setEntry2(Rectangle entry2) {
		// TODO Auto-generated method stub
		
	}
	
}
