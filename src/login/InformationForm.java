// InformationForm.java
package login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import maze.GamePanel;
import maze.MazeGame;
import ranking.RankingForm;

public class InformationForm extends JPanel {
    private LoginForm parentFrame;
    private JLabel userInfoLabel;

    public InformationForm(LoginForm parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        userInfoLabel = new JLabel();
        add(userInfoLabel, BorderLayout.NORTH);

        try {
            String userId = UsersData.getInstance().getCurrentUserId();
            userInfoLabel.setText("Logged in as: " + userId);
        } catch (IllegalStateException e) {
            userInfoLabel.setText("No user is currently logged in.");
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton startGameBtn = new JButton("Start Game");
        startGameBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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

                GamePanel.Difficulty difficulty = GamePanel.Difficulty.MEDIUM; // Default value
                int size = 0;
                switch (difficultyChoice) {
                    case 0:
                        difficulty = GamePanel.Difficulty.EASY;
                        size = 5;
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
                        break;
                }

                MazeGame mazeGame = new MazeGame(size, difficulty);
                mazeGame.setSize(600, 600);
                mazeGame.setVisible(true);
            }
        });
        buttonPanel.add(startGameBtn);

        JButton checkStoryBtn = new JButton("Check Game Story");
        checkStoryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(parentFrame, "Game Story: ...", "Game Story", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        buttonPanel.add(checkStoryBtn);

        JButton checkRankingBtn = new JButton("Check Ranking");
        checkRankingBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RankingForm rankingForm = new RankingForm();
                rankingForm.setVisible(true);
            }
        });
        buttonPanel.add(checkRankingBtn);

        add(buttonPanel, BorderLayout.CENTER);
    }

    public void setcheck(String userInfo) {
        userInfoLabel.setText("Logged in as: " + userInfo);
    }
}