package model;

public class MooreMachine extends Machine{

    private int numberOfStates;
    private int inputAlphabetSize;
    private int outputAlphabetSize;
    private int initialState;
    private MooreState states[];

    public MooreMachine(int numberOfStates, int inputAlphabetSize, int outputAlphabetSize, int initialState){
        this.numberOfStates = numberOfStates;
        this.inputAlphabetSize = inputAlphabetSize;
        this.outputAlphabetSize = outputAlphabetSize;
        this.initialState = initialState;
        states = new MooreState[numberOfStates];
        for(int i = 0; i < numberOfStates; i++){
            states[i] = new MooreState(inputAlphabetSize,-1);
        }
    }

    public void specifyState(int stateId, int[] transitions, int output){
        states[stateId].setOutput(output);
        for(int i = 0; i < inputAlphabetSize; i++){
            states[stateId].addTransition(i, transitions[i]);
        }
    }
    
    public void fillMooreAutomata(String[][] data) {
    	for(int i=0;i<data.length;i++) {
    		int[] transitions = new int[data[0].length];
        	int state=0;
        	int output=0;
    		for(int j=0;j<data[0].length-1; j++) {
    			String[] value = data[i][j].split(",");
    			state = Integer.parseInt(value[0]);
    			int symbol = Integer.parseInt(value[1]);
    			int nextState = Integer.parseInt(value[2]);
    			output = Integer.parseInt(value[3]);
    			
    			transitions[symbol] = nextState;
    		}
    		specifyState(state,transitions,output);
    	}
    	
    }

    public int getNumberOfStates(){
        return this.numberOfStates;
    }
    public MooreState[] getStates(){
        return this.states;
    }
    public int getTransitionFromState(int state, int inputCharacter){
        return states[state].getTransition(inputCharacter);
    }
    public int getOutputFromState(int state){
        return states[state].getOutput();
    }

    public int getInputAlphabetSize() {
        return this.inputAlphabetSize;
    }

}
