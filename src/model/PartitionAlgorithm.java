package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PartitionAlgorithm {

    public void MealyPartition(MealyMachine machine){
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
