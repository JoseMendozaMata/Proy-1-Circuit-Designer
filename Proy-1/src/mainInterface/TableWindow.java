package mainInterface;

import java.util.ArrayList;

import gatesFunctionality.Gates;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;


// Esta clase representa la ventana de la tabla de verdad
public class TableWindow{
	
	Scene scene;
	Stage window;
	HBox container = new HBox();
	ListView<Integer> InputList = new ListView<>();
	ListView<TextField> EntryList = new ListView<>();
	
	public void displayTrueTable() {
		
		window = new Stage();
		window.setMinWidth(500);
		window.setMinHeight(500);
		window.setTitle("TrueTable");
		
		InputList.setPrefWidth(200);
		EntryList.setPrefHeight(200);
		
		scene = new Scene(container);
		
		window.setScene(scene);
		window.show();	
	}
	
	// Aquí me encargo de configurar las listas de la interfaz
	public void setTable(ArrayList<Gates> EntryGates) {
		
		if(EntryGates.isEmpty()) {	// Por si la lista está vacía
			System.out.println("Lista vacía");
			return;
		}
		
		// Seteo la listView de los nombres de la compuerta que es entrada al circuito
		for(int i = 0; i < EntryGates.size(); i++) {
			
			System.out.println("entra for: " + EntryGates.get(i));
			int id = Integer.parseInt(EntryGates.get(i).getGateImage().getId());
			this.InputList.getItems().add(id);
		}
		
		for(int i = 0; i < EntryGates.size(); i++) {
			
			TextField field = new TextField();
			field.setPromptText("Entrada 1, Entrada 2");
			
			field.setPrefHeight(14);
			
			this.EntryList.getItems().add(field);
			
		}
		
		container.getChildren().addAll(InputList, EntryList);
		
		System.out.println("Termina setTable");
	}
	
}
