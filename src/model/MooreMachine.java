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

    /**
     * <b>Description:</b>
     * This function creates the transitions and outputs of the specified state
     *
     * <b>Pre:</b> the mealy or moore automata must not be null, it should have already been initialized.
     * @param stateId, the state which is to be specified
     * @param transitions, the transitions belonging to said state
     * @param output, the output belonging to said state
     */
    public void specifyState(int stateId, int[] transitions, int output){
        states[stateId].setOutput(output);
        for(int i = 0; i < inputAlphabetSize; i++){
            states[stateId].addTransition(i, transitions[i]);
        }
    }

    /**
     * <b>Description:</b>
     * This function is responsible of creating the states, transitions and outputs of the already created moore machine.
     *
     * <b>Pre:</b> the mealy or moore automata must not be null, it should have already been initialized.
     * @param data, the states, transitions and outputs that the machine is going to have.
     */
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
