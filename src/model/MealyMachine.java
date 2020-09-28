package model;

import java.util.Map;

public class MealyMachine extends Machine{

    private int numberOfStates;
    private int inputAlphabetSize;
    private int outputAlphabetSize;
    private int initialState;
    private MealyState states[];

    public MealyMachine(int numberOfStates, int inputAlphabetSize, int outputAlphabetSize, int initialState){
        this.numberOfStates = numberOfStates;
        this.inputAlphabetSize = inputAlphabetSize;
        this.outputAlphabetSize = outputAlphabetSize;
        this.initialState = initialState;
        states = new MealyState[numberOfStates];
        for(int i = 0; i < numberOfStates; i++){
            states[i] = new MealyState(inputAlphabetSize);
        }
    }

    /**
     * <b>Description:</b>
     * This function creates the transitions and outputs of the specified state
     *
     * <b>Pre:</b> the mealy or moore automata must not be null, it should have already been initialized.
     * @param stateId, the state which is to be specified
     * @param transitions, the transitions belonging to said state
     * @param outputs, the outputs belonging to said state
     */
    public void specifyState(int stateId, int[] transitions, int[] outputs){
        for(int i = 0; i < inputAlphabetSize; i++){
            states[stateId].addTransition(i, transitions[i]);
            states[stateId].addOutput(i, outputs[i]);
        }
    }

    /**
     * <b>Description:</b>
     * This function is responsible of creating the states, transitions and outputs of the already created mealy machine.
     *
     * <b>Pre:</b> the mealy or moore automata must not be null, it should have already been initialized.
     * @param data, the states, transitions and outputs that the machine is going to have.
     */
    public void fillMealyAutomata(String[][] data) {
    	for(int i=0;i<data.length;i++) {
    		int[] transitions = new int[data[0].length];
        	int[] outputs = new int[data[0].length];
        	int state=0;
    		for(int j=0;j<data[0].length; j++) {
    			String[] value = data[i][j].split(",");
    			state = Integer.parseInt(value[0]);
    			int symbol = Integer.parseInt(value[1]);
    			int nextState = Integer.parseInt(value[2]);
    			int output = Integer.parseInt(value[3]);
    			
    			transitions[symbol] = nextState;
    			outputs[symbol] = output;
    		}
    		specifyState(state,transitions,outputs);
    	}
    }
    public int getNumberOfStates(){
        return this.numberOfStates;
    }
    public MealyState[] getStates(){
        return this.states;
    }

    public int getTransitionFromState(int state, int inputCharacter){
        return states[state].getTransition(inputCharacter);
    }
    public int getOutputFromState(int state, int inputCharacter){
        return states[state].getOutput(inputCharacter);
    }

    public int getInputAlphabetSize() {
        return this.inputAlphabetSize;
    }
}
