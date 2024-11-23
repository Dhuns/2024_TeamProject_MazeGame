// 플레이어의 랭킹 정보를 저장함
package ranking;

public class Record {
    private String difficulty;
    private String nickname;
    private int time;

    public Record(String difficulty, String nickname, int time) {
        this.difficulty = difficulty;
        this.nickname = nickname;
        this.time = time;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getNickname() {
        return nickname;
    }

    public int getTime() {
        return time;
    }

    @Override
    public String toString() {
        return nickname + ", " + time + "초";
    }
}
