package ChessTournament;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class TournamentField {
    public static void main(String[] args) {
        Logic logic = new Logic();
        Scanner input = new Scanner(System.in);
        int choice=0;
        int i = 0;
        while (i == 0) {
            System.out.println("1.Conduct Tournament\n2.Display matches\n3.Rank List\n4.Exit");
            choice = input.nextInt();
            switch (choice) {
                case 1: {
                    System.out.println("Enter the number of players");
                    int players = input.nextInt();
                    System.out.println("Enter the number of rounds");
                    int rounds = input.nextInt();
                    logic.initializer(players);
                    for (int j = 1; j <= rounds; j++) {
                        System.out.println("Chess Tournament Round " + j);

                        List<Map.Entry<Integer, Player>> list = logic.Scheduler(i);
                        String schedule = logic.schedulerString(list);
                        System.out.println(schedule);

                        list = logic.result(list, j);
                        String result = logic.resultString(list, j);
                        System.out.println(result);

                        String pointsTable = logic.getPointsTable(list, j);
                        System.out.println(pointsTable);

                        logic.setMap(list);
                    }
                    break;
                }
                case 2:{
                    System.out.println("Enter player id ");
                    String output= logic.getPlayer(input.nextInt());
                    System.out.println(output);
                    break;
                }
                case 3:{
                    System.out.println("Rank List of the Tournament");
                    String output= logic.getRankList();
                    System.out.println(output);
                    break;
                }
                case 4:{
                    i++;
                    break;
                }
                default:{
                    System.out.println("Enter valid input");
                    break;
                }
            }
        }
    }
}
