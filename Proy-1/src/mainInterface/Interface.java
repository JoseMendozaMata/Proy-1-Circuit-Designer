package mainInterface;

import gatesFunctionality.*;
import gatesFunctionality.specificGates.AndGate;

import java.io.File;
import java.util.Random;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

// Programa principal

public class Interface extends Application{

	Stage window;
	Scene scene;
	Group mainContainer;
	ListView<ImageView> gatesList;
	Pane canvas;
	CircuitList circuit;
	
	public static void main(String[]args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		window = primaryStage;
		mainContainer = new Group();
		
		// Boton para pruebas, mensajes en consola
		
		mainContainer.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
				//Esto evalua los elementos de la lista y las compuertas en si, con los rectángulos
				// Para saber cuando conectar o no nodos, alguno está vacío
				if(CircuitList.sourceRectangle == null  || CircuitList.destinyRectangle == null) {
					;
				}else {		// Cuando voy a conectar las compuertas
					
					// Actualizo el cursor, para que sepa que se deseleccionó el rectangulo 
					// Aca reviso que la compuerta cumpla las reglas, revisar CircuitList para los metodos
					if(circuit.canConnect()) {
						System.out.println("Pueden conectarse estas compuertas");
						// Hago la conección entre compuertas
						circuit.connectGates();
						// Hago la linea de coneccion
						Line conection = new Line(CircuitList.sourceRectangle.getX(),
								CircuitList.sourceRectangle.getY(),
								CircuitList.destinyRectangle.getX(),
								CircuitList.destinyRectangle.getY()
								);
						
						Random rnd = new Random();
						conection.setStroke(Color.color(rnd.nextDouble(),rnd.nextDouble(), rnd.nextDouble()));
						CircuitList.sourceRectangle = null;
						CircuitList.destinyRectangle = null;
						
						canvas.getChildren().add(conection);
						
					}else {
						System.out.println("No pueden conectarse");
					}
					
					CircuitList.sourceRectangle = null;
					CircuitList.destinyRectangle = null;	
				}
			}
		});
		
		canvas = new Pane();
		canvas.setMinWidth(300);
		canvas.setMinHeight(400);
			
		gatesList = new ListView<>();	// Lista con las compuertas, seteadas para un drag and drop
		gatesList.setLayoutX(300);
		
		setGatesList();
		
		// ESte botón me revisa qué compuertas son entradas del circuito
		Button button = new Button("Revisar Entradas");
		canvas.getChildren().add(button);
		button.setOnAction(e -> {
			circuit.getInputGates();
		});
		
		Button button1 = new Button("Realizar tabla de verdad");
		canvas.getChildren().add(button);
		button.setOnAction(e -> {
			circuit.getInputGates();
		});
		
	
		// Creo la lista que será mi circuito
		circuit = new CircuitList();
		
		canvas.setOnDragOver(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent event) {
				//System.out.println("Drag on");
				event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
				
			}
		});
		
		// Lo que sucede cuando dropeo la compuerta lógica de la listview en el canvas(Pane)
		// Aquí se crea la compueta lógica que estoy tirando en el pane
		canvas.setOnDragDropped(new EventHandler<DragEvent>() {

			@Override
			public void handle(DragEvent mouse) {
				
				// Aquí pongo lo que quier que pase cuando meto una nueva compuerta al canvas
				if(mouse.getTransferMode() == TransferMode.COPY) {
					
					System.out.println("Dropped in: " + mouse.getSceneX() + ", " + mouse.getSceneY());

					Dragboard recieve = mouse.getDragboard();	// Obtengo la dragboard

					//System.out.println(recieve.getString());	// Veo el Id, para saber qué compuerta estoy arrastrando

					String id = recieve.getString();

					//Crear la compuerta que necesito poner en el Pane


					GatesName gateName = getGateValue(id);	// Convierto el ID como un GatesName, para lamar a la factoría
					//System.out.println(gateName);

					// Aqui llamo a la factory para que me haga una compuerta de un tipo

					GatesFactory factory = new GatesFactory();	// Creo una factory
					Gates gate = factory.getGate(gateName);		//Le digo que me haga una compuerta específica

					gate.setPosX(mouse.getSceneX());	// Seteo las posiciones de la compuerta
					gate.setPosY(mouse.getSceneY());
					gate.setGateImage();
					gate.setEntry1();
					gate.setEntry2();
					gate.setOutput();

					// OJO: Añado las compuertas a la lista, por lo cual puedo saber en esta instancia de la lista
					// de qué rectangulos estoy hablando
					circuit.addGate(gate);

					// Coloco la imagen en el canvas, dados una paosicion x y y del mouse
					//canvasField.drawImage(newImage.getImage(), mouse.getSceneX() - imgW * 0.5, mouse.getSceneY() - imgH * 0.5);
					canvas.getChildren().addAll(gate.getGateImage(), gate.getEntry1(), gate.getEntry2(), gate.getOutput());
				
				// Lo que pasa cuando quiero mover una compuerta dentro del canvas
				}else if(mouse.getTransferMode() == TransferMode.MOVE) {
					
					System.out.println("Moviendo compuerta dentro del canvas");
					
					System.out.println(mouse.getDragboard().getString());
					
					// Busco qué compuerta estoy moviendo por medio del id que le asigné
					Gates movingGate = circuit.getById(mouse.getDragboard().getString());
					
					System.out.println("Moved to: " + mouse.getSceneX() + ", " + mouse.getSceneY());
					
					// Muevo la compuerta , para hacer algo con ella
					movingGate.moveGate(mouse.getSceneX(), mouse.getSceneY());
					
					// TODO Buscar manera de mover la conexion cuando muevo la compuerta
					
				}
			}
		});
		
		mainContainer.getChildren().addAll(canvas, gatesList);
		
		scene = new Scene(mainContainer, 400, 400);
		window.setScene(scene);
		window.show();
	}

	// Este método pone las imágenes dentro de la listview, pone funcionalidad de drag and drop a todas
	// las imágenes
	
	private void setGatesList() {
		
		File file = new File("src/Images");	// Cargo la carpeta imagenes
		
		String[] files = file.list();	// hago una lista con las imagenes
		
		for(int i = 0; i < files.length; i++) {	// Recorro con un for para cargar todas las imagenes
			
			Image img = new Image("Images/" + files[i]);	// Creo la imagen 
			ImageView imgv = new ImageView();	// La pongo en una ImageView
			imgv.setId(files[i]);		// Coloco un Id para poder saber qué elemento estoy arrastrando en la ListView
			imgv.setImage(img);		// Coloco la imagen en la Lista
			
			this.gatesList.getItems().add(imgv);
			
		}
		
		// Esto se ejecuta cuando se detecta un arrastre en la lista de compuertas
		this.gatesList.setOnDragDetected(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouse) {
				
				// En la lista, obtengo el item seleccionado y empiezo el drag and drop
				
				ImageView image = gatesList.getSelectionModel().getSelectedItem();	// Obtengo el elemento seleccionado
				// COPY hace referencia a que estoy depositando una nueva compuerta del gatesList al canvas
				Dragboard db = image.startDragAndDrop(TransferMode.COPY);
				
				ClipboardContent content = new ClipboardContent();
				content.putString(image.getId());
				db.setContent(content);
				
				mouse.consume();
				
			}
			
			
		});
		
	}
	
	// Este método me ayuda a identificar las compuertas que estoy tirando en el canvas
	private GatesName getGateValue(String Id) {
		
		GatesName name = null;
		
		switch(Id) {
			
		case "OR.png":
			name = GatesName.OR;
			break;
		case "AND.png":
			name = GatesName.AND;
			break;
		case "NAND.png":
			name = GatesName.NAND;
			break;
		default:
			System.out.println("No existe el tipo de copuerta que quiere usar");
		}
		
		return name;
		
	}
	
	
	// Esta será a función que se ejecuta cuando se quiere correr el programa
	public void run() {
		
			
	}

	
}
