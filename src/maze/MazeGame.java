package maze;

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

    public MazeGame(int size) {
        maze = new Maze(size);
        player = new Player(0, 0);
        Random random = new Random();

        // 랜덤한 flag 위치 생성
        do {
            flagX = random.nextInt(size);
            flagY = random.nextInt(size);
        } while (maze.getMaze()[flagX][flagY] == 1 || (flagX == 0 && flagY == 0)); // 벽이나 시작 위치에 flag가 생성되지 않도록

        setTitle("미로 찾기 게임");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // 패널 설정
        gamePanel = new GamePanel(maze, player, flagX, flagY);
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
        gamePanel.setBlockImage(ImageLoader.loadImage("images/block.png"));
        gamePanel.setPathImage(ImageLoader.loadImage("images/path.png"));
        gamePanel.setPlayerImage(ImageLoader.loadImage("images/player.png"));
        gamePanel.setFlagImage(ImageLoader.loadImage("images/flag.png"));
    }

    private void movePlayer(int keyCode) {
        if (reachedDestination) return; // 도착하면 더 이상 이동 불가

        if (player.move(keyCode, maze)) {
            if (player.getX() == flagX && player.getY() == flagY) {
                reachedDestination = true;
                stopTimer(); // 타이머 정지
                JOptionPane.showMessageDialog(this, "목적지에 도착했습니다! 클리어 시간: " + elapsedTime + "초");
                System.exit(0); // 프로그램 종료
            }
            gamePanel.updatePlayerPosition(player.getX(), player.getY());
        }
    }

    public void startMazeGame() {
        String input = JOptionPane.showInputDialog("미로의 사이즈를 입력하세요:");
        int size = Integer.parseInt(input);
        MazeGame game = new MazeGame(size);
        game.setSize(600, 600); // 프레임 크기 설정
        game.setVisible(true);
    }
}