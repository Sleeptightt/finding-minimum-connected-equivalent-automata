package model;

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

    public void specifyState(int stateId, int[] transitions, int[] outputs){
        for(int i = 0; i < inputAlphabetSize; i++){
            states[stateId].addTransition(i, transitions[i]);
            states[stateId].addOutput(i, outputs[i]);
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

    public int getInputAlphabetSize() {
        return this.inputAlphabetSize;
    }
}
