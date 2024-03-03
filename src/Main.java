import core.ApplicationFramework;
import model.agent.BFSWithTailFollowing;
import model.agent.SingleHamiltonianCycle;
import model.agent.SingleHamiltonianCycleWithShortcuts;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

//        System.out.println("Choose which agent you want to use.");
//        System.out.println("1. SingleHamiltonianCycle");
//        System.out.println("2. SingleHamiltonianCycleWithShortcuts");
//        System.out.println("3. BFSWithTailFollowing");
//
//        Scanner sc = new Scanner(System.in);
//        int n = sc.nextInt();

        ApplicationFramework main = ApplicationFramework.getInstance();
        switch (2) {
            case 1:
                main.setAgent(new SingleHamiltonianCycle());
                break;
            case 2:
                main.setAgent(new SingleHamiltonianCycleWithShortcuts());
                break;
            default:
                main.setAgent(new BFSWithTailFollowing());
                break;
        }
        main.initialize();
    }
}
