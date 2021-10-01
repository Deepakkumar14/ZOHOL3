package ChessTournament;

import java.security.KeyStore;
import java.util.*;

public class Logic {

    public void initializer(int players) {
        for (int i = 1; i <= players; i++) {
            Player player = new Player();
            player.setPlayerId(i);
            player.setPlayerName("Player " + i);
            Cache.INSTANCE.setTournamentMap(player.getPlayerId(), player);
        }
    }

    public List<Map.Entry<Integer, Player>> Scheduler(int match) {
        LinkedHashMap<Integer, Player> tournamentMap = Cache.INSTANCE.getTournamentMap();
        List<Map.Entry<Integer, Player>> list = new ArrayList(tournamentMap.entrySet());
        list=sortList(list);
        if(match>1){
        ArrayList<Integer> temp=new ArrayList<>();
            System.out.println(temp);
        LinkedHashMap<Integer, Player> finalList=new LinkedHashMap<>();
        for(int i=0;i< list.size();i++){
            int playerId=list.get(i).getKey();
            if(!(temp.contains(playerId) )) {
                ArrayList<Match> opponent = list.get(i).getValue().getMatches();
                ArrayList<Integer> opponentId = new ArrayList<>();
                System.out.println(opponentId);
                for (Match matches : opponent)
                    opponentId.add(matches.getOpponentId());
                for (int j = i + 1; j < list.size(); j++) {
                    int playerId2 = list.get(j).getKey();
                    if (!(opponentId.contains(playerId) && temp.contains(playerId2))) {
                        finalList.put(playerId, list.get(i).getValue());
                        finalList.put(playerId2, list.get(j).getValue());
                        temp.add(playerId);
                        temp.add(playerId2);
                        break;
                    }
                }
            }
            }
            list=new ArrayList(finalList.entrySet());
            System.out.println(list);
        }
        return list;
    }

    public List<Map.Entry<Integer, Player>> result(List<Map.Entry<Integer, Player>> list, int matchId) {
        if (list.size() % 2 != 0) {
            Player bye = list.get(list.size() - 1).getValue();
            setResultBye(bye, matchId);
        }
        for (int i = 0; i < list.size() - 1; i++) {
            Player player = list.get(i++).getValue();
            Player player1 = list.get(i).getValue();
            int decider = (int) (Math.random() * (3 - 1 + 1) + 2);
            if (decider == 3) {
                setResultWon(player, matchId, player1);
                setResultLose(player1, matchId, player);
            } else if (decider == 2) {
                setResultWon(player1, matchId, player);
                 setResultLose(player, matchId, player1);
            } else {
             setResultDraw(player1, matchId, player);
              setResultDraw(player, matchId, player1);

            }
        }
        return list;
    }

    public void setResultWon(Player player, int matchId, Player player1) {
        Match match = new Match();
        ArrayList<Match> matches = player.getMatches();
        if (matches == null) {
            matches = new ArrayList<>();
        }
        player.setTotalPoints(player.getTotalPoints() + 1);
        match.setMatchId(matchId);
        match.setResult("Win");
        match.setOpponentId(player1.getPlayerId());
        match.setPoint(1);
        match.setOpponentPoint(0);
        matches.add(match);
        player.setMatches(matches);
    }

    public void setResultLose(Player player, int matchId, Player player1) {
        Match match = new Match();
        ArrayList<Match> matches = player.getMatches();
        if (matches == null) {
            matches = new ArrayList<>();
        }
        player.setTotalPoints(player.getTotalPoints());
        match.setMatchId(matchId);
        match.setResult("Lose");
        match.setOpponentId(player1.getPlayerId());
        match.setPoint(0);
        match.setOpponentPoint(1);
        matches.add(match);
        player.setMatches(matches);
    }

    public void setResultDraw(Player player, int matchId, Player player1) {
        Match match = new Match();
        ArrayList<Match> matches = player.getMatches();
        if (matches == null) {
            matches = new ArrayList<>();
        }
        player.setTotalPoints(player.getTotalPoints() + 0.5f);
        match.setMatchId(matchId);
        match.setResult("Draw");
        match.setOpponentId(player1.getPlayerId());
        match.setPoint(0.5f);
        match.setOpponentPoint(0.5f);
        matches.add(match);
        player.setMatches(matches);
    }

    public void setResultBye(Player player, int matchId) {
        Match match = new Match();
        ArrayList<Match> matches = player.getMatches();
        if (matches == null) {
            matches = new ArrayList<>();
        }
        player.setTotalPoints(player.getTotalPoints() + 1);
        match.setMatchId(matchId);
        match.setResult("Bye");
        match.setOpponentId(0);
        match.setPoint(1);
        match.setOpponentPoint(0);
        matches.add(match);
        player.setMatches(matches);
    }


    public void setMap(List<Map.Entry<Integer, Player>> list) {
        LinkedHashMap<Integer, Player> tournamentMap = Cache.INSTANCE.getTournamentMap();
        tournamentMap.clear();
        for (Map.Entry<Integer, Player> a : list)
            tournamentMap.put(a.getKey(), a.getValue());
    }

    public String schedulerString(List<Map.Entry<Integer, Player>> list) {
        String output = "";
        for (int j = 0; j < list.size(); j++) {
            String player1 = list.get(j++).getValue().getPlayerName();
            String player2 = null;
            if (j < list.size()) {
                player2 = list.get(j).getValue().getPlayerName();
            } else
                player2 = "gets a bye";

            output += player1 + " vs " + player2 + "\n";
        }
        return output;
    }

    public String resultString(List<Map.Entry<Integer, Player>> list,int matchId) {
        String output = "";
        output+="Result of round "+matchId+"\n";
            for (int j = 0; j < list.size() - 1; j += 2) {
                Player player1 = list.get(j).getValue();
                ArrayList<Match>list1 = player1.getMatches();
                Match match=list1.get(list1.size()-1);
                output += player1.getPlayerName() + "    (" + match.getPoint() + "," + match.getOpponentPoint() + ")    " + "Player " + match.getOpponentId() + "\n";
            } if (list.size() % 2 != 0) {
            Player player = list.get(list.size() - 1).getValue();
            output += player.getPlayerName() + "    (1:0)   " + "Bye" + "\n";
        }
        return output;
    }

    public String getPointsTable(List<Map.Entry<Integer, Player>> list,int matchId) {
        list=sortList(list);
        String output = "";
        output+="Points after round " +matchId+"\n";
        for (int j = 0; j < list.size() ; j++) {
            Player player1 = list.get(j).getValue();
            output += player1.getPlayerName() +"   ("+player1.getTotalPoints() +")"+ "\n";
        }
        return output;
    }

    public List<Map.Entry<Integer, Player>> sortList(List<Map.Entry<Integer, Player>> list) {
        list.sort(new Comparator<Map.Entry<Integer, Player>>() {
            @Override
            public int compare(Map.Entry<Integer, Player> player1, Map.Entry<Integer, Player> player2) {
                Float value1=player1.getValue().getTotalPoints();
                Float value2=player2.getValue().getTotalPoints();
                return Float.compare(value2,value1);
            }
        });
        return list;
    }

    public String getPlayer(int id ) {
        String output="";
       Player player= Cache.INSTANCE.getPlayer(id);
       output+="Player name : "+player.getPlayerName()+"\n";
        output+="Total points : "+player.getTotalPoints()+"\n";
       ArrayList<Match> matches=player.getMatches();
        for (Match match:matches) {
            output+="Match "+match.getMatchId()+  "     VS     " +"Player "+match.getOpponentId()+"    "+match.getResult()+"\n";
        }
        return output;
    }

    public String getRankList() {
        LinkedHashMap<Integer, Player> tournamentMap = Cache.INSTANCE.getTournamentMap();
        List<Map.Entry<Integer, Player>> list = new ArrayList(tournamentMap.entrySet());
        list=sortList(list);
        String output = "";
        for (int j = 0; j < list.size() ; j++) {
            Player player1 = list.get(j).getValue();
            int rankNumber=j+1;
            output +="Rank   "+rankNumber +"  "+player1.getPlayerName() +"   ("+player1.getTotalPoints() +")"+ "\n";
        }
        return output;
    }
}
