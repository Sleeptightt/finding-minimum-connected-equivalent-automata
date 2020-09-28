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

    /**
     * <b>Description:</b>
     * This function is responsible of finding the parent of the specified state.
     *
     * @param stateToFind, the state to find
     */
    public int find(int stateToFind){
        int parent;
        if(stateToFind == unionFind[stateToFind])
            parent = stateToFind;
        else
            parent = find(unionFind[stateToFind]);
        return parent;
    }

    /**
     * <b>Description:</b>
     * This function is responsible of joining two states into the same component
     *
     * @param stateA, the state to join
     * @param stateB, the state to join
     */
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

    /**
     * <b>Description:</b>
     * This function is responsible of returning the number of states in the union find.
     */
    public int getNumberOfStates() {
        return numberOfStates;
    }

    /**
     * <b>Description:</b>
     * This function is responsible of returning the number of components in the union find.
     */
    public int getNumberOfComponents() {
        return numberOfComponents;
    }

    /**
     * <b>Description:</b>
     * This function is responsible of comparing two union finds
     * @param otherUnionFind, the other union find to be compared.
     */
    public boolean equals(UnionFind otherUnionFind){
        boolean isEqual = true;
        for(int i = 0; i < numberOfStates; i++){
            isEqual = (isEqual && (unionFind[i] ==otherUnionFind.unionFind[i]));
        }
        return isEqual;
    }
}
