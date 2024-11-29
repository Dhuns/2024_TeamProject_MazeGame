package maze;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player {
    private int x, y;
    private BufferedImage playerImage;
    private BufferedImage playerUPImage;
    private BufferedImage playerDOWNImage;
    private BufferedImage playerLEFTImage;
    private BufferedImage playerRIGHTImage;
    private Direction currentDirection;

    // 방향 enum 추가
    public enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    public Player(int startX, int startY) {
        this.x = startX;
        this.y = startY;
        this.currentDirection = Direction.UP; // 기본 방향 설정

        // 초기 이미지 설정 (이미지를 GamePanel에서 전달받거나 여기서 로딩)
        this.playerImage = playerUPImage;  // 기본 이미지는 UP으로 설정
    }

    // 방향에 맞는 이미지 설정
    public void setDirection(Direction direction) {
        this.currentDirection = direction;

        // 방향에 따라 이미지 설정
        switch (direction) {
            case UP:
                playerImage = playerUPImage;
                break;
            case DOWN:
                playerImage = playerDOWNImage;
                break;
            case LEFT:
                playerImage = playerLEFTImage;
                break;
            case RIGHT:
                playerImage = playerRIGHTImage;
                break;
        }
    }

    // 방향에 맞는 이미지를 반환
    public BufferedImage getPlayerImage() {
        return playerImage;
    }

    // 좌표 getter
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // 좌표 setter
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // 이미지 설정 메소드
    public void setPlayerUPImage(BufferedImage playerUPImage) {
        this.playerUPImage = playerUPImage;
    }

    public void setPlayerDOWNImage(BufferedImage playerDOWNImage) {
        this.playerDOWNImage = playerDOWNImage;
    }

    public void setPlayerLEFTImage(BufferedImage playerLEFTImage) {
        this.playerLEFTImage = playerLEFTImage;
    }

    public void setPlayerRIGHTImage(BufferedImage playerRIGHTImage) {
        this.playerRIGHTImage = playerRIGHTImage;
    }

    // 이동 메소드 (이미지와 좌표 갱신)
    public boolean move(int keyCode, Maze maze) {
        int newX = x;
        int newY = y;

        if (keyCode == KeyEvent.VK_UP && x > 0 && maze.getMaze()[x - 1][y] == 0) {
            newX--;
            setDirection(Direction.UP);
        } else if (keyCode == KeyEvent.VK_DOWN && x < maze.getSize() - 1 && maze.getMaze()[x + 1][y] == 0) {
            newX++;
            setDirection(Direction.DOWN);
        } else if (keyCode == KeyEvent.VK_LEFT && y > 0 && maze.getMaze()[x][y - 1] == 0) {
            newY--;
            setDirection(Direction.LEFT);
        } else if (keyCode == KeyEvent.VK_RIGHT && y < maze.getSize() - 1 && maze.getMaze()[x][y + 1] == 0) {
            newY++;
            setDirection(Direction.RIGHT);
        }

        if (newX != x || newY != y) {
            x = newX;
            y = newY;
            return true;
        }
        return false;
    }
}
