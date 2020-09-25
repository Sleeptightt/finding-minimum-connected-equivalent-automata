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

    public int getNumberOfStates(){
        return this.numberOfStates;
    }
    public MooreState[] getStates(){
        return this.states;
    }


    public int getInputAlphabetSize() {
        return this.inputAlphabetSize;
    }

}
