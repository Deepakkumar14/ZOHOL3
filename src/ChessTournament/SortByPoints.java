package ChessTournament;

import java.util.Comparator;

public class SortByPoints implements Comparator<Player> {
    @Override
    public int compare(Player player1, Player player2) {
        Float point1 = player1.getTotalPoints();
        Float point2 = player2.getTotalPoints();
        return point1.compareTo(point2);

    }
}
