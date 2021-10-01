package ChessTournament;

import java.util.LinkedHashMap;

public enum Cache {
    INSTANCE;


    private LinkedHashMap<Integer,Player> tournamentMap=new LinkedHashMap<>();

    public LinkedHashMap<Integer, Player> getTournamentMap() {
        return tournamentMap;
    }

    public void setTournamentMap(int id ,Player player) {
        tournamentMap.put(id,player);
    }

    public Player getPlayer(int id ) {
        return tournamentMap.get(id);
    }


}
