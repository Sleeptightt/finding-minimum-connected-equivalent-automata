package ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Machine;
import model.MealyMachine;
import model.MooreMachine;
import model.PartitionAlgorithm;

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
    private Label partitions;

    @FXML
    private ScrollPane endAutomataScroll;
    
    @FXML
    private ComboBox<String> typeAutomata;

    @FXML
    private ScrollPane scrollTable;
    
    private MealyMachine mealy;
    private MooreMachine moore;
    
    Map<String,Integer> statesMap;
    Map<Integer,String> rstatesMap;
    Map<String,Integer> outputMap;
    Map<Integer,String> routputMap;
    
    GridPane root;
    GridPane paneB;
    TextField[][] data;

    @FXML
    private void initialize() {
    	typeAutomata.getItems().addAll("Mealy", "Moore");
    	 root= new GridPane();
    	 paneB = new GridPane();
    	 statesMap = new HashMap<>();
    	 rstatesMap = new HashMap<>();
    	 outputMap = new HashMap<>();
    	 routputMap = new HashMap<>();
    }
    @FXML
    void generateTable(ActionEvent event) {
    		String[] states = statesField.getText().split(",");
    		String[] input = inputAlphabet.getText().split(",");
    		String[] output = outputAlphabet.getText().split(",");
    		String start= initialState.getText();
    		String type = typeAutomata.getValue();
    		
    		for(int i=0; i<states.length;i++) {
    			statesMap.put(states[i],i);
    			rstatesMap.put(i,states[i]);
    		}
    		for(int i=0; i<output.length;i++) {
    			outputMap.put(output[i],i);
    			routputMap.put(i,output[i]);
    		}
    		
    		if(type.equals("Mealy")) {
    			data = new TextField[states.length][input.length];
    			generateMealyTable(input,states);
    			mealy = new MealyMachine(states.length, input.length,output.length,0);
    		}else {

    			data = new TextField[states.length][input.length+1];
    			generateMooreTable(input,states);
    			moore = new MooreMachine(states.length, input.length,output.length,0);

    		}
    		
    }
    
    
    @FXML
    void find_automata(ActionEvent event) {
    	String type = typeAutomata.getValue();
    	if(type.equals("Mealy")){
    		
    		fillAutomata("Mealy");                     // primero llena el automata en el modelo
    		
    		 PartitionAlgorithm algorithm = new PartitionAlgorithm();
    	     ArrayList<Integer>[] end = algorithm.MealyPartition(mealy);    // luego hace el algoritmo  
    	     
    	     showMinimunMealyAutomata(end, inputAlphabet.getText().split(","), mealy);    // finalmente muestra el automata equivalente
    	    
    	     String ans = "";                               // esto es para mostrar los nuevos estados qi  i>=0
    	     for(int i=0; i<end.length;i++) {
    	    	 ans+="q"+i+" = { ";
    	    	 for(int j=0; j<end[i].size(); j++) {
    	    		 System.out.print(end[i].get(j)+" ");
    	    		 if(j==end[i].size()-1)
    	    			 ans+=rstatesMap.get(end[i].get(j))+" }";
    	    		 else 
    	    			 ans+=rstatesMap.get(end[i].get(j))+", ";
    	    	 }
    	    	 ans+="\n";
    	    	 System.out.println();
    	     }
    	     partitions.setText(ans);
    	}else if(type.equals("Moore")) {
    		fillAutomata("Moore");   
    		
    		PartitionAlgorithm algorithm = new PartitionAlgorithm();
    	    ArrayList<Integer>[] end = algorithm.MoorePartition(moore);    // luego hace el algoritmo  
    	    showMinimunMooreAutomata(end, inputAlphabet.getText().split(","), moore);    // finalmente muestra el automata equivalente
    	    
    	    String ans = "";                               // esto es para mostrar los nuevos estados qi  i>=0
   	     for(int i=0; i<end.length;i++) {
   	    	 ans+="q"+i+" = { ";
   	    	 for(int j=0; j<end[i].size(); j++) {
   	    		 System.out.print(end[i].get(j)+" ");
   	    		 if(j==end[i].size()-1)
   	    			 ans+=rstatesMap.get(end[i].get(j))+" }";
   	    		 else 
   	    			 ans+=rstatesMap.get(end[i].get(j))+", ";
   	    	 }
   	    	 ans+="\n";
   	    	 System.out.println();
   	     }
   	     partitions.setText(ans);
    	}
    	
    }
    
    public void fillAutomata(String type) {    /// este método carga los datos de la tabla y los manda al modelo pa que llene el automata
    	String[][] dataModel = new String[data.length][data[0].length];
    	
    	for(int i=0; i<data.length;i++) {
    		for(int j=0; j<data[0].length;j++) {
    			if(!data[i][j].getText().isEmpty()) {
    				if(type.equals("Mealy")) {
    					
    					String[] value = data[i][j].getText().split(",");
	    				dataModel[i][j]=""+i+","+j+","+statesMap.get(value[0])+","+outputMap.get(value[1]);   /// state,symbol,next state, output  -->  a,0,b,1
    				}
    				else if(type.equals("Moore")) {
    					dataModel[i][j]=""+i+","+j+","+statesMap.get(data[i][j].getText()) +","+ data[i][data[0].length-1].getText();   /// state,symbol,next state,   -->  a,0,b
    				}
    			}
    		}
    	}
    	if(type.equals("Mealy")) {
    		mealy.fillMealyAutomata(dataModel);
    	}
    	else if(type.equals("Moore")) {
    		moore.fillMooreAutomata(dataModel);
    	}
    	
    }
    
public void showMinimunMooreAutomata(List<Integer>[] end, String[] input, Machine automata) {
    	
    	Map<Integer,String> map = new HashMap<>();
    	for(int i=0; i<end.length;i++) {
    		map.put(i,"q"+i);
    	}
    	 int length = end.length;
  	   int width = input.length;
  	 paneB.getChildren().clear();
  	   for(int y = 0; y < length+1; y++)
  	   {
  		   for(int x = 0; x < width+2; x++)
  		   {	
  			 if(x==0 && y==0) {
	        		Button a = new Button("States"); 
	        		a.setPrefWidth(100);
	        		paneB.add(a, x,y);
	        	}else if(x==width+1 && y==0) {
	        		Button a = new Button("Output"); 
	        		a.setPrefWidth(100);
	        		paneB.add(a, x,y);
	        	}
	        	else if(x==0 && y>0) {
	        		Button a = new Button("q"+(y-1)); 
	        		a.setPrefWidth(100);
	        		paneB.add(a, x,y);
	        	}else if(y==0 && x>0) {
	        		Button symbol = new Button(input[x-1]); 
	        		symbol.setPrefWidth(100);
	        		paneB.add(symbol, x,y);
	        	}else {
	        		if(x==width+1) {
	        			String output = routputMap.get(automata.getOutputFromState(end[y-1].get(0),0));
	  	        		TextField a = new TextField(output);
	  	        		a.setPrefWidth(100);
	  	        		paneB.add(a, x,y);
	        		}else {
	  	        		int nextState = automata.getTransitionFromState(end[y-1].get(0), x-1);
	  	        		int stateq = findStateq(end,nextState,automata);
	  	        		TextField a = new TextField(map.get(stateq));
	  	        		a.setPrefWidth(100);
	  	        		paneB.add(a, x,y);
	        		}
  	        	}
  		   }
  	   }   
  	   
  		endAutomataScroll.setContent(paneB);
    }
    
   
    public void showMinimunMealyAutomata(List<Integer>[] end, String[] input, Machine automata) {
    	
    	Map<Integer,String> map = new HashMap<>();
    	for(int i=0; i<end.length;i++) {
    		map.put(i,"q"+i);
    	}
    	 int length = end.length;
  	   int width = input.length;
  	  paneB.getChildren().clear();
  	   
  	   for(int y = 0; y < length+1; y++)
  	   {
  		   for(int x = 0; x < width+1; x++)
  		   {	
  	        	if(x==0 && y==0) {
  	        		Button a = new Button("states"); 
  	        		a.setPrefWidth(100);
  	        		paneB.add(a, x,y);
  	        	}
  	        	else if(x==0 && y>0) {
  	        		Button a = new Button("q"+(y-1)); 
  	        		a.setPrefWidth(100);
  	        		paneB.add(a, x,y);
  	        	}else if(y==0 && x>0) {
  	        		Button symbol = new Button(input[x-1]); 
  	        		symbol.setPrefWidth(100);
  	        		paneB.add(symbol, x,y);
  	        	}else {
  	        		int nextState = automata.getTransitionFromState(end[y-1].get(0), x-1);
  	        		int stateq = findStateq(end,nextState,automata);
  	        		String output = routputMap.get(automata.getOutputFromState(end[y-1].get(0), x-1));
  	        		TextField a = new TextField(map.get(stateq)+","+output);
  	        		a.setPrefWidth(100);
  	        		paneB.add(a, x,y);
  	        	}
  		   }
  	   }   
  	   
  		endAutomataScroll.setContent(paneB);
    }
    
 // este metodo encuentra el estado q al que pertenece el estado u de la siguiente transicion:   v --> u 
    
    public int findStateq(List<Integer>[] end, int nextState, Machine automata) {    
    	int stateq = 0;
    	boolean exit=false;
    	 for(int i=0; i<end.length && !exit;i++) {
	    	 for(int j=0; j<end[i].size() && !exit; j++) {
	    		if(end[i].get(j)==nextState) {
	    			stateq=i;
	    			exit= true;
	    		}
	    		
	    	 }
	    	
	     }
	     return stateq;
    }
    
   public void generateMealyTable(String[] input, String[] states) {
	   int length = states.length;
	   int width = input.length;
	   root.getChildren().clear();
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
	        		data[y-1][x-1] = a;
	        		root.add(a, x,y);
	        	}
		   }
	   }   
	   
		scrollTable.setContent(root);
   }

   public void generateMooreTable(String[] input, String[] states) {
	   int length = states.length;
	   int width = input.length;
	   root.getChildren().clear();
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
	        		data[y-1][x-1] = a;
	        		root.add(a, x,y);
	        	}
		   }
	   }  

	   scrollTable.setContent(root); 
	 
   }

}
