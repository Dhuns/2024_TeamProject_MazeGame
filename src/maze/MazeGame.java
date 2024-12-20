package maze;

import login.LoginForm;
import login.UsersData;
import ranking.RecordManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class MazeGame extends JFrame {
    private Maze maze;
    private Player player;
    private GamePanel gamePanel;
    private boolean reachedDestination = false;
    private int flagX;
    private int flagY;
    private Timer timer;
    private int elapsedTime = 0; // 초 단위로 경과 시간 저장
    private JLabel timerLabel;
    private RecordManager recordManager;
    private LoginForm loginForm;


    public MazeGame(int size, GamePanel.Difficulty difficulty) {
        this.loginForm = loginForm;
        maze = new Maze(size);
        player = new Player(0, 0);
        Random random = new Random();
        recordManager = new RecordManager();

        do {
            flagX = random.nextInt(size);
            flagY = random.nextInt(size);
        } while (maze.getMaze()[flagX][flagY] == 1 || (flagX == 0 && flagY == 0));

        setTitle("미로 찾기 게임");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gamePanel = new GamePanel(maze, player, flagX, flagY, difficulty);
        add(gamePanel);

        // 타이머 레이블 설정
        timerLabel = new JLabel("경과 시간: 0초");
        timerLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        timerLabel.setForeground(Color.DARK_GRAY);

        // 타이머 패널 설정 (우측 상단)
        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        timerPanel.add(timerLabel);
        timerPanel.setBackground(Color.WHITE);  // 배경을 흰색으로 설정하여 깔끔하게 표시

        add(timerPanel, BorderLayout.NORTH);
        loadImages(); // 이미지 로드

        pack();
        setResizable(false);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                movePlayer(e.getKeyCode());
            }
        });

        // 타이머 설정
        startTimer();
    }

    private void startTimer() {
        timer = new Timer(1000, e -> {
            elapsedTime++;
            timerLabel.setText("경과 시간: " + elapsedTime + "초");
        });
        timer.start();
    }

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }

    private void loadImages() {
        gamePanel.setBlockImage(ImageLoader.loadImage("images/block.jpg"));
        gamePanel.setPathImage(ImageLoader.loadImage("images/path.jpg"));
        gamePanel.setPlayerImage(ImageLoader.loadImage("images/playerDOWN.png"));
        gamePanel.setFlagImage(ImageLoader.loadImage("images/flag.png"));
        gamePanel.setPlayerUPImage(ImageLoader.loadImage("images/playerUP.png"));
        gamePanel.setPlayerDOWNImage(ImageLoader.loadImage("images/playerDOWN.png"));
        gamePanel.setPlayerLEFTImage(ImageLoader.loadImage("images/playerLEFT.png"));
        gamePanel.setPlayerRIGHTImage(ImageLoader.loadImage("images/playerRIGHT.png"));
    }

    private void movePlayer(int keyCode) {
        if (reachedDestination) return;

        String direction = "";
        if (player.move(keyCode, maze)) {
            if (keyCode == KeyEvent.VK_UP) {
                direction = "UP";
            } else if (keyCode == KeyEvent.VK_DOWN) {
                direction = "DOWN";
            } else if (keyCode == KeyEvent.VK_LEFT) {
                direction = "LEFT";
            } else if (keyCode == KeyEvent.VK_RIGHT) {
                direction = "RIGHT";
            }

            if (player.getX() == flagX && player.getY() == flagY) {
                reachedDestination = true;
                stopTimer(); // 타이머 정지
                JOptionPane.showMessageDialog(this, "목적지에 도착했습니다! 클리어 시간: " + elapsedTime + "초");
                saveRanking(); // 랭킹 저장
                dispose();
            }

            gamePanel.updatePlayerPosition(player.getX(), player.getY(), direction);
        }
    }

    private void saveRanking() {
        String userId = UsersData.getInstance().getCurrentUserId();
        String difficulty = gamePanel.getDifficulty().toString();
        recordManager.saveRecord(difficulty, userId, elapsedTime);
    }


}
