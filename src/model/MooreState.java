package model;

public class MooreState {
    private int transitionFunction[];
    private int output;

    public MooreState(int inputAlphabetSize, int output){
        transitionFunction = new int[inputAlphabetSize];
        this.output = output;
    }

    public void addTransition(int inputCharacter, int nextState){
        transitionFunction[inputCharacter] = nextState;
    }
    public int getTransition(int inputCharacter){
        return transitionFunction[inputCharacter];
    }

    public int getOutput(){
        return output;
    }

    public boolean equals(MooreState otherState) {
        boolean equal = (this.output == otherState.output);
        return equal;
    }

    public void setOutput(int output) {
        this.output = output;
    }
}
