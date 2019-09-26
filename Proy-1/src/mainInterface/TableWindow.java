package mainInterface;

import java.util.ArrayList;

import gatesFunctionality.CircuitList;
import gatesFunctionality.Gates;
import gatesFunctionality.ReferenceList;
import gatesFunctionality.ReferenceNode;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
	
	public void displayTable() {
		
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
		
		//Boton para la tabla de verdad
		Button tTable = new Button("Tabla de verdad");
		tTable.setOnAction(e -> {
			this.displayTrueTable();
		});
		mainContainer.getChildren().add(tTable);
		
		System.out.println("Termina setTable");
	}
	
	public void run() {
		
		// Obtengo los id de las compuertas de entrada
		ObservableList<Integer> idCompuertas = InputList.getItems();
		// Obtengo los valores de entry de las compuertas
		ObservableList<TextField> entryCompuertas = EntryList.getItems();
		
		// Seteo los valores de input de las compuertas de entrada del circuito respectivas
		for(int i = 0; i < idCompuertas.size(); i++) {	// Obtengo la compuerta de la que estoy hablando
			
			// Busco la compuerta por su id

			String id = Integer.toString(idCompuertas.get(i));

			Gates gate = this.circuitlist.getById(id);


			// Obtengo los valores a setear en los entry de la compuerta

			String textValues = entryCompuertas.get(i).getText();

			// Ordeno los valores de input como una lista [Entry1Value, Entry2Value]
			String[] valores = textValues.split(",");

			// Convierto los 1 en true y 0 en false
			Boolean[] valoresBoolean = new Boolean[2];

			for(int j = 0; j < valores.length; j++) {

				String val = valores[j];
				
				// Convierto de valores string a booleanos para asignarlos a la compuerta
				if(val.equals("1")) {
					valoresBoolean[j] = true;
				}else if(val.equals("0")) {
					valoresBoolean[j] = false;
				}else {
					System.out.println("No metió ni cero ni uno");
					return;
				}
			}

			// Seteo los valores de entrada de las compuertas de entrada

			gate.setValueInput1(valoresBoolean[0]);
			gate.setValueInput2(valoresBoolean[1]);
				
		}
		
		//Aquí obtengo el valor de las operaciones de compuertas de entrada
		System.out.println("Empiezo a buscar el valor de las operaciones de las compuertas");
		
		//Obtengo la referencia de todas las compuertas siguientes a las de entrada del circuito
		
		ReferenceList generation = new ReferenceList();
		
		//ReferenceList que contiene los valores de las compuertas que son entradas y salidas al mismo tiempo
		ReferenceList entryOutputValues = new ReferenceList();
		
		// Esta referenceList tiene unos nodos que contienen las id de las compuertas, por lo que puedo
		// Recuperar los nodos conectados a diferentes compuertas
		
		for(Integer compuerta:idCompuertas) {
			
			Gates nodo = circuitlist.getById(Integer.toString(compuerta)).getNextGate();	// Encuentro las entradas por el id
			Gates nodoAct = circuitlist.getById(Integer.toString(compuerta));
			if(nodoAct.getNextGate() == null) {	//La compuerta de entrada, también es salida
				
				System.out.println("La compuerta " + nodoAct.getId().getText() + " es entrada y salida");
				ReferenceNode refEntryOut = new ReferenceNode();
				refEntryOut.setReference(nodoAct.getId().getText());	//Creo nodo de referencia con la compuerta
				entryOutputValues.add(refEntryOut);	//Añado el nodo
				
			}else {
			
				ReferenceNode ref = new ReferenceNode();
				ref.setReference(nodo.getId().getText());	// Pongo el id de la compuerta
				System.out.println("La referencia del nodo es: " + ref.getReference());
				boolean repeat = generation.IsRepeated(ref.getReference());
				System.out.println(repeat);
				if(!(repeat)) {
					System.out.println("El " + ref.getReference() + " no está repetido");
					generation.add(ref);	// Añado el id de la compuerta a la lista de referencias, si no lo tenía
				}else {
					System.out.println("Está repedido el " + ref.getReference());
				}
			
			}
		}
		
		System.out.println("Termino de añadir elementos a la lista de referencia");
		
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
		System.out.println("Llamo getOutputCircuit");
		//Acá Obtengo el output del circuito
		ReferenceList outputCircuit = new ReferenceList();
		//outputCircuit es una lista que tiene las compuertas que son salidas del circuito
		outputCircuit = this.circuitlist.getOutputCircuit(generation, new ReferenceList());	
		
		System.out.println("Output circuit tiene " + outputCircuit.lenght + " elementos");
		System.out.println("entryOutputValues tiene " + entryOutputValues.lenght + " elementos");
		String msg = "";
		
		//Salidas que son entry y output al mismo tiempo
		for(int i = 0; i < entryOutputValues.lenght; i++) {
			
			System.out.println("Entra for de entryOutputValues");
			
			Gates res = this.circuitlist.getById(entryOutputValues.getIndex(i).getReference());
			System.out.println("La salida de " + entryOutputValues.getIndex(i).getReference() + " es: " + res.getOutputValue());
			msg += "La salida de " + entryOutputValues.getIndex(i).getReference() + " es: " + res.getOutputValue() + "\n";
		}
		
		//Salidas "normales"
		for(int i = 0; i < outputCircuit.lenght; i++) {
			
			Gates res = this.circuitlist.getById(outputCircuit.getIndex(i).getReference());
			System.out.println("La salida de " + outputCircuit.getIndex(i).getReference() + " es: " + res.getOutputValue());
			msg += "La salida de " + outputCircuit.getIndex(i).getReference() + " es: " + res.getOutputValue() + "\n";
		}
		
		PopUpWindow outputInfo = new PopUpWindow();
		outputInfo.setWindow("Output del circuito", msg);
		
		//System.out.println("El resultado del circuito es: " + outputCircuit);
		
	}
	
	public void displayTrueTable() {	// Pondré los algoritmos y listas acá de la tabla de verdad
		
		VBox contenedor = new VBox(15);
		contenedor.setMaxHeight(300);
		
		ScrollPane scrollPane = new ScrollPane();
		HBox gatesBox = new HBox(5);
		
		//scrollPane.setPrefSize(450, 300);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		scrollPane.setPrefSize(500, 200);
		
		contenedor.getChildren().add(scrollPane);		
		
		ArrayList<Gates> initGates = this.circuitlist.getInputGates();	// Obtengo las compuertas iniciales del circuito
		
		ReferenceList references = new ReferenceList();	// Lista para pasar a algoritmo para obtener salida del circuito
		
		System.out.println("Empiezo el for de las compuertas de inicio");
		
		for(Gates gate:initGates) {
			
			//Parte visual
			ListView<String> entryGatesList = new ListView<>();
			entryGatesList.getItems().add("Compuerta " + gate.getId().getText());	
			entryGatesList.getItems().add("1,1");
			entryGatesList.getItems().add("1,0");
			entryGatesList.getItems().add("0,1");
			entryGatesList.getItems().add("0,0");
			
			gatesBox.getChildren().add(entryGatesList);
			
			//Setear valores 1,1 y calcular el valor de salida
			gate.setValueInput1(true);
			gate.setValueInput2(true);
			gate.setOutputValue();
			
			//Parte logica, añadir id de las compuertas
			ReferenceNode node = new ReferenceNode();
			
			if(gate.getNextGate() != null) {	//Si tiene una compuerta siguiente de salida
				node.setReference(gate.getNextGate().getId().getText());	// Añado los nodos siguientes para saber 
				references.add(node);
			}
		}
		
		// Calculo los valores de salida de la tabla con los valores 1,1, esto para saber cuantas salidas tiene el circuito 
		ReferenceList outputs = this.circuitlist.getOutputCircuit(references, new ReferenceList());
		System.out.println("La lista de referencias siguientes tiene " + references.lenght + " elementos");
		if(!(references.lenght == 0)) {	//Si hay compuertas de salida que agregar
			
			for(int j = 0; j < outputs.lenght; j++) {

				// Obtengo la compuerta la cual es salida del circuito
				Gates gate = this.circuitlist.getById(outputs.getIndex(j).getReference());	

				//Pongo la interfaz de la salida
				ListView<String> outputList = new ListView<>();	//Nueva Lista de output de la compuerta salida del circuito
				outputList.getItems().add("Compuerta de salida " + gate.getId().getText());	// Titulo

				for(Gates initgate:initGates) {
					//Setear valores 1,1 y calcular el valor de salida
					initgate.setValueInput1(true);
					initgate.setValueInput2(true);
					initgate.setOutputValue();
				}

				// Calculo los valores de salida de la tabla con los valores 1,1
				ReferenceList nOutputs = this.circuitlist.getOutputCircuit(references, new ReferenceList());

				// Obtengo la compuerta la cual es salida del circuito
				gate = this.circuitlist.getById(nOutputs.getIndex(j).getReference());

				outputList.getItems().add(String.valueOf(gate.getOutputValue()));	// Resultado 1,1

				for(Gates initgate:initGates) {
					//Setear valores 1,0 y calcular el valor de salida
					initgate.setValueInput1(true);
					initgate.setValueInput2(false);
					initgate.setOutputValue();
				}

				// Calculo los valores de salida de la tabla con los valores 1,0 
				nOutputs = this.circuitlist.getOutputCircuit(references, new ReferenceList());

				// Obtengo la compuerta la cual es salida del circuito
				gate = this.circuitlist.getById(nOutputs.getIndex(j).getReference());

				//Añado el resultad a la lista
				outputList.getItems().add(String.valueOf(gate.getOutputValue()));	// Resultado 1,0

				for(Gates initgate:initGates) {
					//Setear valores 0,1 y calcular el valor de salida
					initgate.setValueInput1(false);
					initgate.setValueInput2(true);
					initgate.setOutputValue();
				}

				// Calculo los valores de salida de la tabla con los valores 0,1 
				nOutputs = this.circuitlist.getOutputCircuit(references, new ReferenceList());

				// Obtengo la compuerta la cual es salida del circuito
				gate = this.circuitlist.getById(nOutputs.getIndex(j).getReference());

				outputList.getItems().add(String.valueOf(gate.getOutputValue()));	// Resultado 0,1

				for(Gates initgate:initGates) {
					//Setear valores 0,0 y calcular el valor de salida
					initgate.setValueInput1(false);
					initgate.setValueInput2(false);
					initgate.setOutputValue();
				}

				// Calculo los valores de salida de la tabla con los valores 0,0 
				nOutputs = this.circuitlist.getOutputCircuit(references, new ReferenceList());

				// Obtengo la compuerta la cual es salida del circuito
				gate = this.circuitlist.getById(nOutputs.getIndex(j).getReference());

				outputList.getItems().add(String.valueOf(gate.getOutputValue()));	// Resultado 0,0

				gatesBox.getChildren().add(outputList);

			}
		}else {	//Aquí es donde hago la lógica de las compuertas de entrada
			System.out.println("No se necesitan agregar compuertas que tienen otras referencias");
			for(Gates gate:initGates) {
				
				ListView<String> compSalida = new ListView<>();	//La parte de salida de la compuerta
				
				String gateId = gate.getId().getText();
				compSalida.getItems().add("Salida de " + gateId);	//Titulo
				
				//Resultado con 1,1
				gate.setValueInput1(true);
				gate.setValueInput2(true);
				gate.setOutputValue();
				
				compSalida.getItems().add(String.valueOf(gate.getOutputValue()));	//Pongo el resultado
				
				//Resultado con 1,0
				gate.setValueInput1(true);
				gate.setValueInput2(false);
				gate.setOutputValue();
				
				compSalida.getItems().add(String.valueOf(gate.getOutputValue()));	//Pongo el resultado
				
				//Resultado con 0,1
				gate.setValueInput1(false);
				gate.setValueInput2(true);
				gate.setOutputValue();
				
				compSalida.getItems().add(String.valueOf(gate.getOutputValue()));	//Pongo el resultado
				
				//Resultado con 0,0
				gate.setValueInput1(false);
				gate.setValueInput2(false);
				gate.setOutputValue();
				
				compSalida.getItems().add(String.valueOf(gate.getOutputValue()));	//Pongo el resultado
				
				gatesBox.getChildren().add(compSalida);
			}
		}
		
		scrollPane.setContent(gatesBox);
		
		
		Button exit = new Button("Salir");
		exit.setOnAction(e -> {
			window.close();
		});
		
		contenedor.getChildren().add(exit);
		
		Scene scene = new Scene(contenedor);
		window.setScene(scene);
		window.show();
	}
	
}
