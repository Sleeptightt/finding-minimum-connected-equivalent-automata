public class MealyState {

    private int transitionFunction[];
    private int outputFunction[];

    public MealyState(int inputAlphabetSize){
        transitionFunction = new int[inputAlphabetSize];
        outputFunction = new int[inputAlphabetSize];
    }

    public void addTransition(int inputCharacter, int nextState){
        transitionFunction[inputCharacter] = nextState;
    }
    public int getTransition(int inputCharacter){
        return transitionFunction[inputCharacter];
    }
    public void addOutput(int inputCharacter, int output){
        outputFunction[inputCharacter] = output;
    }
    public int getOutput(int inputCharacter){
        return outputFunction[inputCharacter];
    }

    public boolean equals(MealyState otherState) {
        if(outputFunction.length != otherState.outputFunction.length) return false;
        boolean equal = true;
        for(int i = 0; i < outputFunction.length; i++){
            equal = (equal && (outputFunction[i] == otherState.outputFunction[i]));
        }
        return equal;
    }
}
