package ChessTournament;

import java.util.*;

public class Logic {

    public void initializer(int players){
        for(int i=1;i<=players;i++){
            Player player=new Player();
            player.setPlayerId(i);
            player.setPlayerName("Player "+i);
            Cache.INSTANCE.setTournamentMap(player.getPlayerId(),player);
        }
    }

    public String Scheduler(int match){
        String output="";
        LinkedHashMap<Integer,Player> tournamentMap=Cache.INSTANCE.getTournamentMap();
        List<Map.Entry<Integer, Player> > list = new ArrayList(tournamentMap.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<Integer, Player>>() {
            @Override
            public int compare(Map.Entry<Integer, Player> player1, Map.Entry<Integer, Player> player2) {
                return (int) (player2.getValue().getTotalPoints()-player1.getValue().getTotalPoints());
            }
        });
        tournamentMap.clear();
        for(Map.Entry<Integer,Player> a:list)
            tournamentMap.put(a.getKey(), a.getValue());

        for(int i=0;i<list.size();i++) {
            String player1 = list.get(i++).getValue().getPlayerName();
            String player2 = null;
            if (i < list.size()) {
                player2 = list.get(i).getValue().getPlayerName();
            }else
                player2="gets a bye";

            output += player1 + " vs " + player2+"\n";
        }
        result(list,match);
        return output;
    }

    public void result(List<Map.Entry<Integer, Player>> list,int matchId) {
        String output="";
        for(int i=0;i<list.size();i++) {
            Player player1 = list.get(i++).getValue();
            setResult(player1,1,0,"Won",list.get(i).getValue());

                    ArrayList<Match> opponent1 = player2.getMatches();
                    Match match1 = new Match();
                    if (opponent1 == null)
                        opponent1 = new ArrayList<>();
                    match1.setMatchId(matchId);
                    match1.setResult("Lose");
                    match1.setOpponentId(player1.getPlayerId());
                    match1.setPoint(0);
                    match1.setOpponentPoint(1);
                    opponent1.add(match1);

        }

    }

    public Player setResult(){

        player1.setTotalPoints(1);
        Player player2 = list.get(i).getValue();
        player2.setTotalPoints(0);
        ArrayList<Match> opponent = player1.getMatches();
        Match match = new Match();
        if (opponent == null)
            opponent = new ArrayList<>();
        match.setMatchId(matchId);
        match.setResult("Won");
        match.setOpponentId(player2.getPlayerId());
        match.setPoint(1);
        match.setOpponentPoint(0);
        opponent.add(match);
        return

    }


}
