package ChessTournament;

import java.util.Scanner;

public class TournamentField {
    public static void main(String[] args) {
        Logic logic=new Logic();
        Scanner input=new Scanner(System.in);
        System.out.println("Enter the number of players");
        int players=input.nextInt();
        System.out.println("Enter the number of rounds");
        int rounds=input.nextInt();
        logic.initializer(players);
       //String output= logic.Scheduler();
        for(int i=1;i<=rounds;i++){
            String output= logic.Scheduler(rounds);
            System.out.println(output);
        }
    }
}
