package model;

import java.util.Arrays;

public class UnionFind {

    private int numberOfStates;
    private int numberOfComponents;
    private int[] unionFind;
    private int[] sizeOfComponent;

    public UnionFind(int numberOfStates){
        this.numberOfStates = numberOfStates;
        unionFind = new int[numberOfStates];
        sizeOfComponent = new int[numberOfStates];
        Arrays.fill(sizeOfComponent,1);
        for(int i = 0; i<numberOfStates; i++){
            unionFind[i] = i;
        }
        numberOfComponents = numberOfStates;
    }

    public int find(int stateToFind){
        int parent;
        if(stateToFind == unionFind[stateToFind])
            parent = stateToFind;
        else
            parent = find(unionFind[stateToFind]);
        return parent;
    }

    public boolean join(int stateA, int stateB){
        stateA = unionFind[stateA];
        stateB = unionFind[stateB];
        boolean canJoin = true;
        if(stateA == stateB)
            canJoin = false;
        else{
            if(sizeOfComponent[stateA] < sizeOfComponent[stateB]){
                int temp = stateA;
                stateA = stateB;
                stateB = temp;
            }
            sizeOfComponent[stateA]+=sizeOfComponent[stateB];
            unionFind[stateB] = stateA;
            numberOfComponents--;
        }
        return canJoin;
    }

    public int getNumberOfStates() {
        return numberOfStates;
    }

    public int getNumberOfComponents() {
        return numberOfComponents;
    }
}
