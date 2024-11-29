package maze;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GamePanel extends JPanel {
    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

    private Maze maze;
    private Player player;
    private boolean[][] visited;
    private BufferedImage blockImage;
    private BufferedImage pathImage;
    private BufferedImage playerImage;
    private BufferedImage flagImage;
    private BufferedImage playerUPImage;
    private BufferedImage playerDOWNImage;
    private BufferedImage playerLEFTImage;
    private BufferedImage playerRIGHTImage;
    private int flagX;
    private int flagY;
    private Difficulty difficulty;

    public GamePanel(Maze maze, Player player, int flagX, int flagY, Difficulty difficulty) {
        this.maze = maze;
        this.player = player;
        this.flagX = flagX;
        this.flagY = flagY;
        this.difficulty = difficulty;
        this.visited = new boolean[maze.getSize()][maze.getSize()];
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setBlockImage(BufferedImage blockImage) {
        this.blockImage = blockImage;
    }

    public void setPathImage(BufferedImage pathImage) {
        this.pathImage = pathImage;
    }

    public void setPlayerImage(BufferedImage playerImage) {
        this.playerImage = playerImage;
    }

    public void setFlagImage(BufferedImage flagImage) {
        this.flagImage = flagImage;
    }

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

    public void updatePlayerPosition(int x, int y, String direction) {
        player.setPosition(x, y);
        visited[player.getX()][player.getY()] = true;

        // 방향에 따라 플레이어 이미지를 설정
        switch (direction) {
            case "UP":
                playerImage = playerUPImage;
                break;
            case "DOWN":
                playerImage = playerDOWNImage;
                break;
            case "LEFT":
                playerImage = playerLEFTImage;
                break;
            case "RIGHT":
                playerImage = playerRIGHTImage;
                break;
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[][] mazeArray = maze.getMaze();
        int cellSize = Math.min(getWidth(), getHeight()) / maze.getSize();

        int offsetX = (getWidth() - (cellSize * maze.getSize())) / 2;
        int offsetY = (getHeight() - (cellSize * maze.getSize())) / 2;

        if (difficulty == Difficulty.EASY) {
            for (int i = 0; i < maze.getSize(); i++) {
                for (int j = 0; j < maze.getSize(); j++) {
                    if (mazeArray[i][j] == 1) {
                        g.drawImage(blockImage, offsetX + j * cellSize, offsetY + i * cellSize, cellSize, cellSize, null);
                    } else {
                        g.drawImage(pathImage, offsetX + j * cellSize, offsetY + i * cellSize, cellSize, cellSize, null);
                    }
                }
            }
            g.drawImage(flagImage, offsetX + flagY * cellSize, offsetY + flagX * cellSize, cellSize, cellSize, null);
        } else if (difficulty == Difficulty.MEDIUM) {
            for (int i = 0; i < maze.getSize(); i++) {
                for (int j = 0; j < maze.getSize(); j++) {
                    if (visited[i][j]) {
                        g.setColor(Color.LIGHT_GRAY);
                        g.fillRect(offsetX + j * cellSize, offsetY + i * cellSize, cellSize, cellSize);
                    }
                }
            }
            for (int i = player.getX() - 1; i <= player.getX() + 1; i++) {
                for (int j = player.getY() - 1; j <= player.getY() + 1; j++) {
                    if (i >= 0 && i < maze.getSize() && j >= 0 && j < maze.getSize()) {
                        if (mazeArray[i][j] == 1) {
                            g.drawImage(blockImage, offsetX + j * cellSize, offsetY + i * cellSize, cellSize, cellSize, null);
                        } else {
                            g.drawImage(pathImage, offsetX + j * cellSize, offsetY + i * cellSize, cellSize, cellSize, null);
                        }
                    }
                }
            }
        } else if (difficulty == Difficulty.HARD) {
            for (int i = player.getX() - 1; i <= player.getX() + 1; i++) {
                for (int j = player.getY() - 1; j <= player.getY() + 1; j++) {
                    if (i >= 0 && i < maze.getSize() && j >= 0 && j < maze.getSize()) {
                        if (mazeArray[i][j] == 1) {
                            g.drawImage(blockImage, offsetX + j * cellSize, offsetY + i * cellSize, cellSize, cellSize, null);
                        } else {
                            g.drawImage(pathImage, offsetX + j * cellSize, offsetY + i * cellSize, cellSize, cellSize, null);
                        }
                    }
                }
            }
        }

        g.drawImage(playerImage, offsetX + player.getY() * cellSize, offsetY + player.getX() * cellSize, cellSize, cellSize, null);

        if (difficulty != Difficulty.EASY && Math.abs(player.getX() - flagX) <= 1 && Math.abs(player.getY() - flagY) <= 1) {
            g.drawImage(flagImage, offsetX + flagY * cellSize, offsetY + flagX * cellSize, cellSize, cellSize, null);
        }
    }
}
