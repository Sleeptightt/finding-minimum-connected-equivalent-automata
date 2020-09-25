package ui;

import model.MealyMachine;
import model.PartitionAlgorithm;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args){
        MealyMachine machine = new MealyMachine(9,2,2,0);
        int transitions[] = new int[2];
        int outputs[] = new int[2];
        transitions[0] = 1; transitions[1] = 2; outputs[0] = 0; outputs[1] = 0;
        machine.specifyState(0,transitions,outputs);

        transitions[0] = 2; transitions[1] = 3; outputs[0] = 1; outputs[1] = 1;
        machine.specifyState(1,transitions,outputs);

        transitions[0] = 3; transitions[1] = 4; outputs[0] = 0; outputs[1] = 0;
        machine.specifyState(2,transitions,outputs);

        transitions[0] = 2; transitions[1] = 1; outputs[0] = 1; outputs[1] = 1;
        machine.specifyState(3,transitions,outputs);

        transitions[0] = 5; transitions[1] = 4; outputs[0] = 1; outputs[1] = 1;
        machine.specifyState(4,transitions,outputs);

        transitions[0] = 6; transitions[1] = 2; outputs[0] = 0; outputs[1] = 0;
        machine.specifyState(5,transitions,outputs);

        transitions[0] = 5; transitions[1] = 6; outputs[0] = 1; outputs[1] = 1;
        machine.specifyState(6,transitions,outputs);

        transitions[0] = 8; transitions[1] = 1; outputs[0] = 1; outputs[1] = 0;
        machine.specifyState(7,transitions,outputs);

        transitions[0] = 7; transitions[1] = 3; outputs[0] = 1; outputs[1] = 0;
        machine.specifyState(8,transitions,outputs);
        if(machine == null)System.out.println("HOLA");
        PartitionAlgorithm algo = new PartitionAlgorithm();
        ArrayList<Integer>[] end = algo.MealyPartition(machine);
        for(int i = 0; i < end.length; i++){
            for(int j = 0;  j < end[i].size(); j++){
                System.out.print(end[i].get(j)+" ");
            }
            System.out.println();
        }
    }
}
