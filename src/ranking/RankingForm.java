package ranking;

import login.LoginForm;
import login.UsersData;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.util.List;

public class RankingForm extends JFrame {
    private final RecordManager recordManager;
    private final JPanel cardPanel;
    private final CardLayout cardLayout;

    public RankingForm() {
        // 초기화
        recordManager = new RecordManager();
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        // 기본 프레임 설정
        setTitle("랭킹 확인");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 상단에 Home과 Logout 버튼 추가
        JButton homeButton = new JButton("Home");
        JButton logoutButton = new JButton("Logout");
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(homeButton, BorderLayout.WEST);
        topPanel.add(logoutButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // 버튼 동작 설정
        homeButton.addActionListener(event -> dispose());
        logoutButton.addActionListener(event -> {
            UsersData.getInstance().setCurrentUser(null);
            for (Window window : Window.getWindows()) {
                window.dispose();
            }
            SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
        });

        // 난이도별 랭킹 화면 추가
        cardPanel.add(createRankingPanel("HARD"), "상");
        cardPanel.add(createRankingPanel("MEDIUM"), "중");
        cardPanel.add(createRankingPanel("EASY"), "하");
        add(cardPanel, BorderLayout.CENTER);

        // 좌우 화살표 버튼 추가
        JButton leftArrow = new JButton("←");
        JButton rightArrow = new JButton("→");
        leftArrow.addActionListener(event -> cardLayout.previous(cardPanel));
        rightArrow.addActionListener(event -> cardLayout.next(cardPanel));
        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.add(leftArrow);
        add(leftPanel, BorderLayout.WEST);
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.add(rightArrow);
        add(rightPanel, BorderLayout.EAST);

        // 창 크기 설정 및 표시
        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 난이도별 랭킹 화면 생성
    private JPanel createRankingPanel(String difficulty) {
        List<Record> records = recordManager.getRecordsByDifficulty(difficulty);
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(difficulty + " 난이도 랭킹", SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea rankingTextArea = new JTextArea();
        rankingTextArea.setEditable(false);
        for (Record record : records) {
            rankingTextArea.append(record.toString() + "\n");
        }

        JScrollPane scrollPane = new JScrollPane(rankingTextArea);
        scrollPane.setBorder(new EmptyBorder(10, 10, 20, 10));
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
}
