package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PartitionAlgorithm {

    private UnionFind partitions;

    public PartitionAlgorithm(){}

    public ArrayList<Integer>[] MealyPartition(MealyMachine machine){
        int numberOfStates = machine.getNumberOfStates();
        partitions = new UnionFind(numberOfStates);
        MealyState[] states = machine.getStates();
        for(int i = 0; i < numberOfStates; i++){
            for(int j = i+1; j<numberOfStates; j++){
                if(states[i].equals(states[j]))
                    partitions.join(i,j);
            }
        }
        return partition(machine);
    }

    public ArrayList<Integer>[] MoorePartition(MooreMachine machine){
        int numberOfStates = machine.getNumberOfStates();
        partitions = new UnionFind(numberOfStates);
        MooreState[] states = machine.getStates();
        for(int i = 0; i < numberOfStates; i++){
            for(int j = i+1; j<numberOfStates; j++){
                if(states[i].equals(states[j]))
                    partitions.join(i,j);
            }
        }
        return partition(machine);
    }

    public ArrayList<Integer>[] partition(Machine machine){
        int numberOfStates = machine.getNumberOfStates();
        ArrayList<Integer>[] finalPartitions;
        while(true){
            UnionFind lastIteration = partitions;
            partitions = new UnionFind(numberOfStates);
            for(int i = 0; i < numberOfStates; i++){
                for(int j = i+1; j<numberOfStates; j++){
                    if(lastIteration.find(i) == lastIteration.find(j)){
                        int inputAlphabetSize = machine.getInputAlphabetSize();
                        boolean canJoin = true;
                        for(int k = 0; k < inputAlphabetSize; k++){
                            int firstTransition = machine.getTransitionFromState(i,k);
                            int secondTransition = machine.getTransitionFromState(j,k);
                            if(firstTransition != secondTransition){
                                canJoin = false;
                            }
                        }
                        if(canJoin){
                            partitions.join(i,j);
                        }
                    }
                }
            }

            if(lastIteration.equals(partitions)) {
                break;
            }
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
