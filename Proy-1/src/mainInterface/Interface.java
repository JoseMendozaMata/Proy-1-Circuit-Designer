package mainInterface;

import gatesFunctionality.*;

import java.io.File;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

// Programa principal

public class Interface extends Application{

	Stage window;
	Group mainContainer;
	ListView<ImageView> gatesList;
	Pane canvas;
	
	public static void main(String[]args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		window = primaryStage;
		mainContainer = new Group();
		
		canvas = new Pane();
		canvas.setMinWidth(300);
		canvas.setMinHeight(300);
			
		gatesList = new ListView<>();	// Lista con las compuertas, seteadas para un drag and drop
		gatesList.setLayoutX(300);
		
		setGatesList();
		
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
				
				System.out.println("Dropped in: " + mouse.getSceneX() + ", " + mouse.getSceneY());
				
				Dragboard recieve = mouse.getDragboard();	// Obtengo la dragboard
				ImageView newImage = new ImageView();	// Creo el espacio para poner la nueva imagen
				
				System.out.println(recieve.getString());	// Veo el Id, para saber qué compuerta estoy arrastrando
				
				String id = recieve.getString();
				
				//Crear la compuerta que necesito poner en el Pane
				
				
				GatesName gateName = getGateValue(id);	// Convierto el ID como un GatesName, para lamar a la factoría
				System.out.println(gateName);
				
				
				double imgHC = newImage.getImage().getHeight() * 0.5;	// Obtengo el alto, para arreglar el posicionammiento
				double imgWC = newImage.getImage().getHeight() * 0.5;
				
				newImage.setX(mouse.getSceneX() - imgWC);
				newImage.setY(mouse.getSceneY() - imgHC);
				
				// Coloco la imagen en el canvas, dados una paosicion x y y del mouse
				//canvasField.drawImage(newImage.getImage(), mouse.getSceneX() - imgW * 0.5, mouse.getSceneY() - imgH * 0.5);
				canvas.getChildren().add(newImage);
			}
			
		});
		
		mainContainer.getChildren().addAll(canvas, gatesList);
		
		Scene scene = new Scene(mainContainer, 400, 400);
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
				Dragboard db = image.startDragAndDrop(TransferMode.MOVE);
				
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
	
}
