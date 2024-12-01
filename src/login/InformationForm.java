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
            userInfoLabel.setText("현재 로그인한 아이디는  " + userId + "  입니다.  ");
        } catch (IllegalStateException e) {
            userInfoLabel.setText("No user is currently logged in.");
        }

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));

        JButton checkStoryBtn = new JButton(" 게임 스토리 ");
        checkStoryBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(parentFrame, "    과거 중세시기 수많은 영주들 및 호족들의 세력다툼으로 인하여 끊임없는 전쟁을 하던 시절이 있었다. 승리한 군주들은 명예롭고 호화스러운 생활을 이어갔지만 패배한 군주들은 자신의 금은보화들을 갖고 도망가기 급급했다…\n" +
                        "    \n" +
                        "    이마저도 전부 챙길수 없어 곳곳의 동굴에 보물들을 숨겨두고 후일을 도모하였다. 이후 세월이 많이 흘러 되찾지 못한 보물이들이 동굴 곳곳에 퍼져있다.\n" +
                        "    \n" +
                        "    자, 숨겨진 보물들을 찾을 시간이다!", "게임 스토리", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        buttonPanel.add(checkStoryBtn);

        JButton startGameBtn = new JButton(" 게임시작 ");
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
                        size = 13;
                        break;
                    case 1:
                        difficulty = GamePanel.Difficulty.MEDIUM;
                        size = 13;
                        break;
                    case 2:
                        difficulty = GamePanel.Difficulty.HARD;
                        size = 13;
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

        JButton checkRankingBtn = new JButton(" 랭킹 확인 ");
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
        userInfoLabel.setText("현재 로그인한 아이디는 " + userInfo + "입니다.");
    }
}