package model;

public class MooreState {
    private int transitionFunction[];
    private int output;

    public MooreState(int inputAlphabetSize, int output){
        transitionFunction = new int[inputAlphabetSize];
        this.output = output;
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
     * This function is responsible of obtaining the output from this state.
     *
     * <b>Pre:</b> the mealy or moore automata must not be null, it should have already been initialized.
     */
    public int getOutput(){
        return output;
    }

    /**
     * <b>Description:</b>
     * This function is responsible of comparing two states and determining if they are equal based on their outputs
     *
     * <b>Pre:</b> both of the mealy or moore automatas must not be null, they should have already been initialized.
     * @param otherState, the other state to be compared
     */
    public boolean equals(MooreState otherState) {
        boolean equal = (this.output == otherState.output);
        return equal;
    }

    /**
     * <b>Description:</b>
     * This function is responsible of adding the output to this state
     *
     * <b>Pre:</b> the mealy or moore automata must not be null, it should have already been initialized.
     * @param output, the output function value
     */
    public void setOutput(int output) {
        this.output = output;
    }
}
