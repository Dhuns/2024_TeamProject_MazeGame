package maze;

import login.InformationForm;

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
    private int elapsedTime = 0;
    private JLabel timerLabel;
    //private String userId = InformationForm.getUserId();

    public MazeGame(int size, GamePanel.Difficulty difficulty) {
        maze = new Maze(size);
        player = new Player(0, 0);
        Random random = new Random();

        do {
            flagX = random.nextInt(size);
            flagY = random.nextInt(size);
        } while (maze.getMaze()[flagX][flagY] == 1 || (flagX == 0 && flagY == 0));

        setTitle("미로 찾기 게임");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        gamePanel = new GamePanel(maze, player, flagX, flagY, difficulty);
        add(gamePanel);

        timerLabel = new JLabel("경과 시간: 0초");
        timerLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        timerLabel.setForeground(Color.DARK_GRAY);

        JPanel timerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        timerPanel.add(timerLabel);
        timerPanel.setBackground(Color.WHITE);

        add(timerPanel, BorderLayout.NORTH);

        loadImages();

        pack();
        setResizable(false);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                movePlayer(e.getKeyCode());
            }
        });

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
        if (reachedDestination) return;

        if (player.move(keyCode, maze)) {
            if (player.getX() == flagX && player.getY() == flagY) {
                reachedDestination = true;
                stopTimer();
                JOptionPane.showMessageDialog(this, "목적지에 도착했습니다! 클리어 시간: " + elapsedTime + "초");
                dispose();
                //new InformationForm(null, userId);
                setVisible(true);
            }
            gamePanel.updatePlayerPosition(player.getX(), player.getY());
        }
    }

    public void startMazeGame() {
        String[] options = {"하", "중", "상"};
        int difficultyChoice = JOptionPane.showOptionDialog(
                null,
                "난이도를 선택하세요:",
                "난이도 선택",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]
        );

        GamePanel.Difficulty difficulty;
        int size;
        switch (difficultyChoice) {
            case 0:
                difficulty = GamePanel.Difficulty.EASY;
                size = 20;
                break;
            case 1:
                difficulty = GamePanel.Difficulty.MEDIUM;
                size = 30;
                break;
            case 2:
                difficulty = GamePanel.Difficulty.HARD;
                size = 40;
                break;
            default:
                difficulty = GamePanel.Difficulty.MEDIUM;
                size = 30;
                break;
        }

        MazeGame game = new MazeGame(size, difficulty);
        game.setSize(600, 600);
        game.setVisible(true);
    }

    public static void main(String[] args) {
        MazeGame mazeGame = new MazeGame(10, GamePanel.Difficulty.MEDIUM);
        mazeGame.startMazeGame();
    }
}