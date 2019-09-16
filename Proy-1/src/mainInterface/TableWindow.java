package mainInterface;

import java.util.ArrayList;

import gatesFunctionality.CircuitList;
import gatesFunctionality.Gates;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


// Esta clase representa la ventana de la tabla de verdad
public class TableWindow{
	
	Scene scene;
	Stage window;
	HBox container = new HBox();
	VBox mainContainer = new VBox();
	ListView<Integer> InputList = new ListView<>();
	ListView<TextField> EntryList = new ListView<>();
	CircuitList circuitlist = null;		// Me permite revisar la lista con la que estoy trabajando
	
	public void setCircuit(CircuitList circuit){
		this.circuitlist = circuit;
	}
	
	public void displayTrueTable() {
		
		window = new Stage();
		window.setMinWidth(500);
		window.setMinHeight(500);
		window.setTitle("TrueTable");
		
		InputList.setPrefWidth(200);
		EntryList.setPrefHeight(200);
		
		scene = new Scene(mainContainer);
		
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
		mainContainer.getChildren().add(container);
		
		//Boton para obtener salidas del circuito
		Button run = new Button("Obtener Output");
		run.setOnAction(e -> {
			this.run();
		});
		mainContainer.getChildren().add(run);
		
		System.out.println("Termina setTable");
	}
	
	public void run() {
		
		// Reviso si la lista está vacía
		System.out.println("El tamaño de la lista, en run, es de :" + circuitlist.lenght);
		System.out.println("El primer elemento es: " + circuitlist.first);
		
		// Obtengo los id de las compuertas de entrada
		ObservableList<Integer> idCompuertas = InputList.getItems();
		// Obtengo los valores de entry de las compuertas
		ObservableList<TextField> entryCompuertas = EntryList.getItems();
		
		System.out.println("idCompuertas es: " + idCompuertas);
		System.out.println("entryCompuertas es: " + entryCompuertas);
		
		// Seteo los valores de input de las compuertas respectiva
		for(Integer compuerta:idCompuertas) {	// Obtengo la compuerta de la que estoy hablando
			for(TextField field:entryCompuertas) {	// Obtengo los valores a setear en esa compuerta
			
				// Busco la compuerta por su id
				
				String id = Integer.toString(compuerta);
				
				Gates gate = this.circuitlist.getById(id);
				
				
				// Obtengo los valores a setear en los entry de la compuerta
				
				String textValues = field.getText();
				
				// Ordeno los valores de input como una lista [Entry1Value, Entry2Value]
				String[] valores = textValues.split(",");
				
				// Convierto los 1 en true y 0 en false
				Boolean[] valoresBoolean = new Boolean[2];
				
				for(int i = 0; i < valores.length; i++) {
					
					String val = valores[i];
					System.out.println(val + "y es: " + val.getClass());
					
					// Convierto de valores string a booleanos para asignarlos a la compuerta
					if(val.equals("1")) {
						valoresBoolean[i] = true;
					}else if(val.equals("0")) {
						valoresBoolean[i] = false;
					}else {
						System.out.println("No metió ni cero ni uno");
					}
				}
				
				// Seteo los valores de entrada de las compuertas de entrada
				System.out.println("Pongo");
				gate.setValueInput1(valoresBoolean[0]);
				gate.setValueInput2(valoresBoolean[1]);
				
			}
		}
		
		//Aquí obtengo el valor de las operaciones de compuertas
		System.out.println("Empiezo a buscar el valor de las operaciones de las compuertas");
		
		for(Integer compuerta:idCompuertas) {
			
			// Obtengo el nodo de entrada
			Gates nodo = circuitlist.getById(Integer.toString(compuerta));
			
			// Calculo su valor de salida
			nodo.setOutputValue();
			// Obtengo el valor de salida
			nodo.getOutputValue();
			
			//Imprimo (por el momento) el valor de salida de cada compuerta entrada
			System.out.println("El valor de la compuerta " + compuerta + " es: " + nodo.getOutputValue());
		}
		
	}
	
}
