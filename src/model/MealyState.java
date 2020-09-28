package model;

public class MealyState {

    private int transitionFunction[];
    private int outputFunction[];

    public MealyState(int inputAlphabetSize){
        transitionFunction = new int[inputAlphabetSize];
        outputFunction = new int[inputAlphabetSize];
    }

    /**
     * <b>Description:</b>
     * This function is responsible of adding transitions given an input character and the transition state.
     *
     * <b>Pre:</b> the mealy or moore automata must not be null, it should have already been initialized.
     * @param inputCharacter, the input character received by this state
     * @param nextState, the transition state
     */
    public void addTransition(int inputCharacter, int nextState){
        transitionFunction[inputCharacter] = nextState;
    }

    /**
     * <b>Description:</b>
     * This function is responsible of obtaining the transition from this state given an input character
     *
     * <b>Pre:</b> the mealy or moore automata must not be null, it should have already been initialized.
     * @param inputCharacter, the input character received by this state
     */
    public int getTransition(int inputCharacter){
        return transitionFunction[inputCharacter];
    }

    /**
     * <b>Description:</b>
     * This function is responsible of adding the output to this state, given a specific input symbol
     *
     * <b>Pre:</b> the mealy or moore automata must not be null, it should have already been initialized.
     * @param inputCharacter, the input character received by this state
     * @param output, the output function value
     */
    public void addOutput(int inputCharacter, int output){
        outputFunction[inputCharacter] = output;
    }

    /**
     * <b>Description:</b>
     * This function is responsible of obtaining the output from this state given an input symbol
     *
     * <b>Pre:</b> the mealy or moore automata must not be null, it should have already been initialized.
     * @param inputCharacter, the input character received by this state
     */
    public int getOutput(int inputCharacter){
        return outputFunction[inputCharacter];
    }

    /**
     * <b>Description:</b>
     * This function is responsible of comparing two states and determining if they are equal based on their outputs
     *
     * <b>Pre:</b> both of the mealy or moore automatas must not be null, they should have already been initialized.
     * @param otherState, the other state to be compared
     */
    public boolean equals(MealyState otherState) {
        if(outputFunction.length != otherState.outputFunction.length) return false;
        boolean equal = true;
        for(int i = 0; i < outputFunction.length; i++){
            equal = (equal && (outputFunction[i] == otherState.outputFunction[i]));
        }
        return equal;
    }
}
