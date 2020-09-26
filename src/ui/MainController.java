package ui;

import java.util.ArrayList;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import model.MealyMachine;
import model.MooreMachine;

public class MainController {

    @FXML
    private TextField initialState;

    @FXML
    private TextField statesField;

    @FXML
    private TextField inputAlphabet;

    @FXML
    private TextField outputAlphabet;

    @FXML
    private ComboBox<String> typeAutomata;

    @FXML
    private Button btnConfirm;

    @FXML
    private ScrollPane scrollTable;
    
    private MealyMachine mealy;
    private MooreMachine moore;

    @FXML
    private void initialize() {
    	typeAutomata.getItems().addAll("Mealy", "Moore");
    	

    }
    @FXML
    void generateTable(ActionEvent event) {
    		String[] states = statesField.getText().split(",");
    		String[] input = inputAlphabet.getText().split(",");
    		String[] output = outputAlphabet.getText().split(",");
    		String start= initialState.getText();
    		String type = typeAutomata.getValue();
    		
    		if(type.equals("Mealy")) {
    			generateMealyTable(input,states);
    			mealy = new MealyMachine(states.length, input.length,output.length,0);
    		}else {
    			generateMooreTable(input,states);
    			moore = new MooreMachine(states.length, input.length,output.length,0);
    		}
    		
    }
    
    @FXML
    void find_automata(ActionEvent event) {

    }
    
    
   public void generateMealyTable(String[] input, String[] states) {
	   int length = states.length;
	   int width = input.length;

	   GridPane root = new GridPane();
	   for(int y = 0; y < length+1; y++)
	   {
		   for(int x = 0; x < width+1; x++)
		   {	
	        	if(x==0 && y==0) {
	        		Button a = new Button("states"); 
	        		a.setPrefWidth(100);
	                root.add(a, x,y);
	        	}
	        	else if(x==0 && y>0) {
	        		Button a = new Button(states[y-1]); 
	        		a.setPrefWidth(100);
	                root.add(a, x,y);
	        	}else if(y==0 && x>0) {
	        		Button symbol = new Button(input[x-1]); 
	        		symbol.setPrefWidth(100);
	                root.add(symbol, x,y);
	        	}else {
	        		TextField a = new TextField("");
	        		a.setPrefWidth(100);
	        		root.add(a, x,y);
	        	}
		   }
	   }   
		scrollTable.setContent(root);
   }

   public void generateMooreTable(String[] input, String[] states) {
	   int length = states.length;
	   int width = input.length;

	   GridPane root = new GridPane();
	   for(int y = 0; y < length+1; y++)
	   {
		   for(int x = 0; x < width+2; x++)
		   {	
	        	if(x==0 && y==0) {
	        		Button a = new Button("States"); 
	        		a.setPrefWidth(100);
	                root.add(a, x,y);
	        	}else if(x==width+1 && y==0) {
	        		Button a = new Button("Output"); 
	        		a.setPrefWidth(100);
	                root.add(a, x,y);
	        	}
	        	else if(x==0 && y>0) {
	        		Button a = new Button(states[y-1]); 
	        		a.setPrefWidth(100);
	                root.add(a, x,y);
	        	}else if(y==0 && x>0) {
	        		Button symbol = new Button(input[x-1]); 
	        		symbol.setPrefWidth(100);
	                root.add(symbol, x,y);
	        	}else {
	        		TextField a = new TextField("");
	        		a.setPrefWidth(100);
	        		root.add(a, x,y);
	        	}
		   }
	   }  
	   scrollTable.setContent(root); 
	 
   }

}
