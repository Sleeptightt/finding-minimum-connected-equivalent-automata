import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MealyMachine {

    private int numberOfStates;
    private int inputAlphabetSize;
    private int outputAlphabetSize;
    private int initialState;
    private MealyState states[];
    private UnionFind partitions;

    public MealyMachine(int numberOfStates, int inputAlphabetSize, int outputAlphabetSize, int initialState){
        this.numberOfStates = numberOfStates;
        this.inputAlphabetSize = inputAlphabetSize;
        this.outputAlphabetSize = outputAlphabetSize;
        this.initialState = initialState;
        states = new MealyState[numberOfStates];
        partitions = new UnionFind(numberOfStates);
    }

    public void specifyState(int stateId, int[] transitions, int[] outputs){
        for(int i = 0; i < inputAlphabetSize; i++){
            states[stateId].addTransition(i, transitions[i]);
            states[stateId].addOutput(i, outputs[i]);
        }
    }

    public void initializePartitionAlgorithm(){
        for(int i = 0; i < numberOfStates; i++){
            for(int j = i+1; j<numberOfStates; j++){
                if(states[i].equals(states[j]))
                    partitions.join(i,j);
            }
        }
        partition();
    }

    public ArrayList<Integer>[] partition(){
        ArrayList<Integer>[] finalPartitions;

        UnionFind lastIteration = partitions;
        while(true){
            for(int i = 0; i < numberOfStates; i++){
                for(int j = i+1; j<numberOfStates; j++){
                    if(lastIteration.find(i) == lastIteration.find(j)){

                    }
                }
            }

            if(lastIteration == partitions)
                break;
        }

        finalPartitions = new ArrayList[partitions.getNumberOfComponents()];
        Map<Integer, Integer> indexMapping = new HashMap<>();
        int currIndex = 0;
        for(int i = 0; i < numberOfStates; i++){
            int currState = partitions.find(i);
            if(!indexMapping.containsKey(currState)){
                indexMapping.put(currState, currIndex);
                currIndex++;
            }
            currState = indexMapping.get(currState);
            finalPartitions[currState].add(i);
        }
        return finalPartitions;
    }

}
