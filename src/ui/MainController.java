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


/**
* This class defines the necessary attributes and methods to show and control the GUI Automata.
*/

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
    
    // we treat states and symbols as integers. So these maps allow to pass a state or a symbol from integer to string and vice versa
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
    

    /**
     * <b>Description:</b>
     * This method allows to generate and displays an automata table for the user to enter the automata data.
     * In this method the model's state machine is created depending on whether it is a Mealy or Moore automata
     * If it is a Moore automata then the table has an additional column at the end to specify the output of each state.
     * 
     * @param event, the click on the button
     * <b>post:</b>  the automata from the model has been created
     */
    
    
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
    		paneB.getChildren().clear();
    		partitions.setText("");
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
    /**
     * <b>Description:</b>
     * This method solve the model specifically the partition algorithm. 
     * From this method the method that shows the minimum equivalent automata and the one that shows the final partitions is called.
     * 
     * <b>Pre:</b> the mealy or moore automata must not be null, it should have already been initialized in the generateTable method
     * @param event, the click on the button
     * <b>post:</b>  an object of type PartitionAlgorithm has been created to make the partitioning algorithm
     */
    
    @FXML
    void find_automata(ActionEvent event) {
    	String type = typeAutomata.getValue();
    	if(type.equals("Mealy")){
    		
    		fillAutomata("Mealy");                     // First fill the automaton in the model
    		
    		 PartitionAlgorithm algorithm = new PartitionAlgorithm();
    	     ArrayList<Integer>[] end = algorithm.MealyPartition(mealy);    // then do the partitioning algorithm  
    	     
    	     showMinimunMealyAutomata(end, inputAlphabet.getText().split(","), mealy);    // finally shows the equivalent automata
    	    
    	     showPartitions(end);
    	     
    	}else if(type.equals("Moore")) {
    		fillAutomata("Moore");   
    		
    		PartitionAlgorithm algorithm = new PartitionAlgorithm();
    	    ArrayList<Integer>[] end = algorithm.MoorePartition(moore);    
    	    showMinimunMooreAutomata(end, inputAlphabet.getText().split(","), moore);   
    	    
    	    showPartitions(end);
    	}
    	
    }
    /**
     * <b>Description:</b>
     * This method shows the final partitions generated in the partitioning algorithm
     * 
     * <b>Pre:</b> the model's partitioning algorithm must be solved
     * @param end, are the final partitions
     */
    
    public void showPartitions(ArrayList<Integer>[] end) {
    	 String ans = "";                               
	     for(int i=0; i<end.length;i++) {
	    	 ans+="q"+i+" = { ";
	    	 for(int j=0; j<end[i].size(); j++) {
	    		 if(j==end[i].size()-1)
	    			 ans+=rstatesMap.get(end[i].get(j))+" }";
	    		 else 
	    			 ans+=rstatesMap.get(end[i].get(j))+", ";
	    	 }
	    	 ans+="\n";
	    	 
	     }
	     partitions.setText(ans);
    }
    /**
     * <b>Description:</b>
     * this method loads the data entered by the user in the automata table and sends it to the model so that each -
     * class (MooreMachine or MealyMachine) fills the automata (assign transitions and outputs).
     * 
     * @param type, type of automaton to be filled
     * <b>post:</b> the model's automata (mealy or moore) was assigned the transitions and outputs
     */
    
    public void fillAutomata(String type) {  
    	String[][] dataModel = new String[data.length][data[0].length];
    	
    	for(int i=0; i<data.length;i++) {
    		for(int j=0; j<data[0].length;j++) {
    			if(!data[i][j].getText().isEmpty()) {
    				if(type.equals("Mealy")) {
    					
    					String[] value = data[i][j].getText().split(",");
	    				dataModel[i][j]=""+i+","+j+","+statesMap.get(value[0])+","+outputMap.get(value[1]);   /// dataModel[i][j] = state,symbol,next state, output  -->  a,0,b,1
    				}
    				else if(type.equals("Moore") && j<data[0].length-1) {
    					
    					dataModel[i][j]=""+i+","+j+","+statesMap.get(data[i][j].getText()) +","+ data[i][data[0].length-1].getText();   /// state,symbol,next state,output   -->  a,0,b,1
    					
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
    /**
     * <b>Description:</b>
     * this method displays the equivalent minimum moore automaton table that was found with the partitioning algorithm
     * 
     * <b>Pre: </b> The partitioning algorithm should have already been performed
     * @param end, are the final partitions
     * @param input, the automata input symbols
     * @param automata, moore's automaton model
     */
    
    public void showMinimunMooreAutomata(List<Integer>[] end, String[] input, MooreMachine automata) {
    	
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
		        		a.setPrefWidth(50);
		        		paneB.add(a, x,y);
		        	}else if(x==width+1 && y==0) {
		        		Button a = new Button("Output"); 
		        		a.setPrefWidth(70);
		        		paneB.add(a, x,y);
		        	}
		        	else if(x==0 && y>0) {
		        		Button a = new Button("q"+(y-1)); 
		        		a.setPrefWidth(50);
		        		paneB.add(a, x,y);
		        	}else if(y==0 && x>0) {
		        		Button symbol = new Button(input[x-1]); 
		        		symbol.setPrefWidth(50);
		        		paneB.add(symbol, x,y);
		        	}else {
		        		if(x==width+1) {
		        			String output = routputMap.get( automata.getOutputFromState(end[y-1].get(0)));
		  	        		TextField a = new TextField(output);
		  	        		a.setPrefWidth(50);
		  	        		paneB.add(a, x,y);
		        		}else {
		  	        		int nextState = automata.getTransitionFromState(end[y-1].get(0), x-1);
		  	        		int stateq = findStateq(end,nextState,automata);
		  	        		TextField a = new TextField(map.get(stateq));
		  	        		a.setPrefWidth(50);
		  	        		paneB.add(a, x,y);
		        		}
	  	        	}
	  		   }
	  	   }   
	  	   
	  		endAutomataScroll.setContent(paneB);
	    }
    
    /**
     * <b>Description:</b>
     * this method displays the equivalent minimum mealy automaton table that was found with the partitioning algorithm
     * 
     * <b>Pre: </b> The partitioning algorithm should have already been performed
     * @param end, are the final partitions
     * @param input, the automata input symbols
     * @param automata, mealy's automaton model
     */
    
    public void showMinimunMealyAutomata(List<Integer>[] end, String[] input, MealyMachine automata) {
    	
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
  	        		a.setPrefWidth(50);
  	        		paneB.add(a, x,y);
  	        	}
  	        	else if(x==0 && y>0) {
  	        		Button a = new Button("q"+(y-1)); 
  	        		a.setPrefWidth(50);
  	        		paneB.add(a, x,y);
  	        	}else if(y==0 && x>0) {
  	        		Button symbol = new Button(input[x-1]); 
  	        		symbol.setPrefWidth(50);
  	        		paneB.add(symbol, x,y);
  	        	}else {
  	        		int nextState = automata.getTransitionFromState(end[y-1].get(0), x-1);
  	        		int stateq = findStateq(end,nextState,automata);
  	        		String output = routputMap.get(automata.getOutputFromState(end[y-1].get(0), x-1));
  	        		TextField a = new TextField(map.get(stateq)+","+output);
  	        		a.setPrefWidth(50);
  	        		paneB.add(a, x,y);
  	        	}
  		   }
  	   }   
  	   
  		endAutomataScroll.setContent(paneB);
    }
    
    /**
     * <b>Description:</b>
     * this method finds to which new state qi belongs a state of the original automata
     * 
     * <b>Pre: </b> The partitioning algorithm should have already been performed
     * @param end, are the final partitions
     * @param state, state from the original automata
     * @param automata, the original automata
     * 
     * @return the position in the list end of the new state qi to which the state of the original automata belongs
     */
    
    public int findStateq(List<Integer>[] end, int state, Machine automata) {    
    	int stateq = 0;
    	boolean exit=false;
    	 for(int i=0; i<end.length && !exit;i++) {
	    	 for(int j=0; j<end[i].size() && !exit; j++) {
	    		if(end[i].get(j)==state) {
	    			stateq=i;
	    			exit= true;
	    		}
	    		
	    	 }
	    	
	     }
	     return stateq;
    }
    
    /**
     * <b>Description:</b>
     * this method allows displaying the empty table of a mealy automaton for the user to enter the transitions and outputs
     * 
     * @param input, the automata input symbols
     * @param states, states from the original automata
    */
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
	        		a.setPrefWidth(50);
	                root.add(a, x,y);
	        	}
	        	else if(x==0 && y>0) {
	        		Button a = new Button(states[y-1]); 
	        		a.setPrefWidth(50);
	                root.add(a, x,y);
	        	}else if(y==0 && x>0) {
	        		Button symbol = new Button(input[x-1]); 
	        		symbol.setPrefWidth(50);
	                root.add(symbol, x,y);
	        	}else {
	        		TextField a = new TextField("");
	        		a.setPrefWidth(50);
	        		data[y-1][x-1] = a;
	        		root.add(a, x,y);
	        	}
		   }
	   }   
	   
		scrollTable.setContent(root);
   }
   
   /**
    * <b>Description:</b>
    * this method allows displaying the empty table of a moore automaton for the user to enter the transitions and outputs
    * 
    * @param input, the automata input symbols
    * @param states, states from the original automata
   */

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
	        		a.setPrefWidth(50);
	                root.add(a, x,y);
	        	}else if(x==width+1 && y==0) {
	        		Button a = new Button("Output"); 
	        		a.setPrefWidth(70);
	                root.add(a, x,y);
	        	}
	        	else if(x==0 && y>0) {
	        		Button a = new Button(states[y-1]); 
	        		a.setPrefWidth(50);
	                root.add(a, x,y);
	        	}else if(y==0 && x>0) {
	        		Button symbol = new Button(input[x-1]); 
	        		symbol.setPrefWidth(50);
	                root.add(symbol, x,y);
	        	}else {
	        		TextField a = new TextField("");
	        		a.setPrefWidth(50);
	        		data[y-1][x-1] = a;
	        		root.add(a, x,y);
	        	}
		   }
	   }  

	   scrollTable.setContent(root); 
	 
   }

}
