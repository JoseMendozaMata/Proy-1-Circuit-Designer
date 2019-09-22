package mainInterface;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

/* Esta clase se encarga de mostrar mensajes de aviso al usuario para informar de algún resultado o inconveniente
 * durante la ejecución del progrma
 * Entradas: titulo, mensaje
 * Salida: Ninguna
*/

public class PopUpWindow extends Application{

	Stage stage = new Stage();
	VBox container = new VBox(15);
	Scene scene = new Scene(container, 200, 150);
	
	public void start(Stage primaryStage) throws Exception {
	
		stage = primaryStage;
		
		container = new VBox(15);
		
		scene = new Scene(container, 300, 150);
		stage.setScene(scene);
		
		
	}
	
	// Setea los parámetros de la ventana
	public void setWindow(String title, String msg) {
		
		System.out.println("Debug");
		System.out.println("Titulo: " + title);
		System.out.println("Mensaje: " + msg);
		
		stage.setTitle(title);
		
		System.out.println("Se puso el título");
		
		// Texto de la ventana emergente
	
		Text text = new Text(msg);
		System.out.println("Se puso el mensaje");
		text.setTextAlignment(TextAlignment.CENTER);
		
		// Botón para cerrar el dialogBox
		Button ok = new Button("Ok");
		ok.setOnAction(e -> {
			stage.close();
		});
		
		System.out.println("Se hace el boton");
		
		container.getChildren().addAll(text, ok);
		
		System.out.println("Se pone las putas mierdas de mensajes en el contenedor");
		
		stage.setScene(scene);
		
		System.out.println("Se pone la escena");
		
		stage.show();
		System.out.println("Debería de mostrar la mierda esa");
	
	}
}
