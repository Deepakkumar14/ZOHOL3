package ChessTournament;

public class Match {
    private String result;
    private int opponentId;
    private int matchId;
    private float point;
    private float opponentPoint;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getOpponentId() {
        return opponentId;
    }

    public void setOpponentId(int opponentId) {
        this.opponentId = opponentId;
    }

    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public float getOpponentPoint() {
        return opponentPoint;
    }

    public void setOpponentPoint(float opponentPoint) {
        this.opponentPoint = opponentPoint;
    }
}
