package gatesFunctionality.specificGates;

import gatesFunctionality.*;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class OrGate implements Gates{
	
	private double posX;
	private double posY;
	private boolean ValueInput1;
	private boolean ValueInput2;
	private boolean OutputValue;
	private boolean isConected;
	private boolean isEntrance;
	private ImageView gateImage;
	private Rectangle Entry1;
	private Rectangle Entry2;
	private Rectangle Output;
	private Text id;

	private Gates next = null;	// Indica el next a nivel de lista
	private Gates prev = null;
	
	private Gates nextGate = null;	// Indica el next a partir del circuito
	private Gates prevGate1 = null;
	private Gates prevGate2 = null;
	
	@Override
	public double getPosX() {
		return this.posX;
	}

	// Multiplico por 0.5 para arreglar la posicion de la imagen al centro, no a la esquina superior izquierda
	@Override
	public void setPosX(double posX) {
		this.posX = posX;
	}

	@Override
	public double getPosY() {
		return this.posY;
	}

	@Override
	public void setPosY(double posY) {
		this.posY = posY;
	}

	@Override
	public boolean getValueInput1() {
		return this.ValueInput1;
	}

	@Override
	public void setValueInput1(boolean valueInput1) {
		this.ValueInput1 = valueInput1;
	}

	@Override
	public boolean getValueInput2() {
		return this.ValueInput2;
	}

	@Override
	public void setValueInput2(boolean valueInput2) {
		this.ValueInput2 = valueInput2;
	}

	@Override
	public boolean isConected() {
		return this.isConected;
	}

	@Override
	public void setConected(boolean isConected) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean getOutputValue() {
		return this.OutputValue;
	}

	@Override
	public void setOutputValue() {
		this.OutputValue = this.getValueInput1() || this.getValueInput2();
	}

	// Me sirve para saber si la compuerta es una entrada del circuito
	@Override
	public boolean isEntrance() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setEntrance(boolean isEntrance) {
		this.isEntrance = isEntrance;
		
	}

	@Override
	public void setGateImage() {
		
		ImageView imgView = new ImageView();		// Coloco la imageview para que se vea la compuerta
		Image gateimg = new Image("Images/OR.png");	// Pongo la imagen and
		
		// Configuro las posiciones de la imageView
		imgView.setX(this.getPosX() - gateimg.getWidth()* 0.5);
		imgView.setY(this.getPosY()- gateimg.getHeight()* 0.5);

		// Acá les pongo un identificador, para saber más rápido de qué compuerta estoy hablando
		imgView.setId(Integer.toString(CircuitList.lenght));
		
		// Configuro para un drag dentro del mismo Pane
		imgView.setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.out.println("Me dragean en or");
				
				Dragboard db = imgView.startDragAndDrop(TransferMode.MOVE);
				
				ClipboardContent content = new ClipboardContent();
				content.putString(imgView.getId());
				db.setContent(content);
				
			}
			
		});
		
		// Coloco la imagen
		imgView.setImage(gateimg);
		
		// Lo añado como atributo
		this.gateImage = imgView;
		
		System.out.println("Termino de setear mi imagen and");
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
	public void setEntry1() {

		// Seteo los rectangulos de las entradas
		this.Entry1 = new Rectangle(this.gateImage.getX() ,	// X 
				this.gateImage.getY() + (this.gateImage.getImage().getHeight() / 3)	//Y
				, 10 // width
				, 10	// height
				);
		this.getEntry1().setFill(Color.DARKRED);
		
		// Aquí pongo la  funcionalidad para detectar la conexión de dos compuertas
		this.getEntry1().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				// Si no hay rectangulo source, lo pongo 
				if (CircuitList.sourceRectangle == null) {
					CircuitList.sourceRectangle = (Rectangle) event.getSource();
				// Si ya había un rectangulo, quiere decir que el que estaba, lo estoy conectando con el que clickeo
				}else if(CircuitList.sourceRectangle != null && event.getSource() != CircuitList.sourceRectangle) {
					CircuitList.destinyRectangle = (Rectangle) event.getSource();
				}
			}
			
		});
		
	}

	@Override
	public Rectangle getEntry2() {
		
		return this.Entry2;
	}

	// Método que se encarga de mover el rectángulo cuando éste se mueve en el Pane
	@Override
	public void setEntry2() {

		// Seteo los rectangulos
		this.Entry2 = new Rectangle(this.gateImage.getX()
				, this.gateImage.getY() + this.gateImage.getImage().getHeight() / 1.5
				, 10
				, 10
				);
		
		this.getEntry2().setFill(Color.AQUA);
		
		// Aquí pongo la  funcionalidad para detectar la conexión de dos compuertas
		this.getEntry2().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				// Esto solo pone el source y el destiny a la lista
				// Si no hay rectangulo source, lo pongo 
				if (CircuitList.sourceRectangle == null) {
					CircuitList.sourceRectangle = (Rectangle) event.getSource();
			
					// Si ya había un rectangulo, quiere decir que el que estaba, lo estoy conectando con el que clickeo
				}else{
					CircuitList.destinyRectangle = (Rectangle) event.getSource();
					System.out.println("Pongo destiny en lista");
				}
			}

		});
		
	}

	@Override
	public Rectangle getOutput() {
		
		return this.Output;
	}

	@Override
	public void setOutput() {
		
		this.Output = new Rectangle(this.gateImage.getX() + this.gateImage.getImage().getWidth()
				, this.gateImage.getY() + this.gateImage.getImage().getHeight() / 2
				, 10
				, 10
				);
		
		this.getOutput().setFill(Color.GREEN);
		
		this.getOutput().setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				// Esto solo setea los source y destiny de la lista
				
				if (CircuitList.sourceRectangle == null) {
					CircuitList.sourceRectangle = (Rectangle) event.getSource();
				//Si ya había un rectangulo, quiere decir que el que estaba, lo estoy conectando con el que clickeo
				}else {
					CircuitList.destinyRectangle = (Rectangle) event.getSource();
				}
			}

		});
		
	}

	@Override
	public Gates getPrev() {
		return this.prev;
	}

	@Override
	public void setPrev(Gates gate) {
		this.prev = gate;
	}

	@Override
	public Gates getNext() {
		return this.next;
	}

	@Override
	public void setNext(Gates gate) {
		this.next = gate;	
	}
	
	//Método que se encarga de mover la compuerta
	public void moveGate(double newX, double newY) {

		//Cambio los atributos de la compuerta
		this.setPosX(newX);
		this.setPosY(newY);
		
		// Cambio la posición de la imagen
		this.getGateImage().setX(getPosX() - this.getGateImage().getImage().getWidth()* 0.5);
		this.getGateImage().setY(getPosY() - this.getGateImage().getImage().getHeight()* 0.5);
		
		// Cambio la posición de los rectángulos
		this.getEntry1().setX(getGateImage().getX());
		this.getEntry1().setY(getGateImage().getY() + this.getGateImage().getImage().getHeight() / 3);
		
		this.getEntry2().setX(getGateImage().getX());
		this.getEntry2().setY(getGateImage().getY() + this.getGateImage().getImage().getHeight() / 1.5);
		
		this.getOutput().setX(getGateImage().getX() + getGateImage().getImage().getWidth());
		this.getOutput().setY(getGateImage().getY() + this.getGateImage().getImage().getHeight() / 2);
		
		this.getId().setX(getGateImage().getX());
		this.getId().setY(getGateImage().getY());
		
	}

	@Override
	public Gates getNextGate() {
		return this.nextGate;
	}

	@Override
	public void setNextGate(Gates gate) {
		this.nextGate = gate;
	}

	@Override
	public Gates getPrevGate1() {
		return this.prevGate1;
	}

	@Override
	public void setPrevGate1(Gates gate) {
		this.prevGate1 = gate;
	}
	
	public Gates getPrevGate2() {
		return this.prevGate2;
	}

	@Override
	public void setPrevGate2(Gates gate) {
		this.prevGate2 = gate;
	}

	@Override
	public Text getId() {
		return this.id;
	}

	@Override
	public void setId() {
		
		Text text = new Text(this.getGateImage().getId());
		text.setX(this.getPosX() - getGateImage().getImage().getWidth()* 0.5);
		text.setY(this.getPosY()- getGateImage().getImage().getHeight()* 0.5);
		
		this.id = text;
		
	}
	
}
