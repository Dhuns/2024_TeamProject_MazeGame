// 플레이어의 랭킹 정보를 저장함
package ranking;

public class Record {
    private String difficulty;
    private String playerId;
    private int time;

    public Record(String difficulty, String playerId, int time) {
        this.difficulty = difficulty;
        this.playerId = playerId;
        this.time = time;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getPlayerId() {
        return playerId;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "id: " + playerId + ", " + time + "초";
    }
}
