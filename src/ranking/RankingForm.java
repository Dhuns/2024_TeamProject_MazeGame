package ranking;

import login.LoginForm;
import login.UsersData;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.util.List;

public class RankingForm extends JFrame {
    private final RecordManager recordManager; // final 추가
    private final JPanel cardPanel;           // final 추가
    private final CardLayout cardLayout;      // final 추가

    public RankingForm() {
        this(null);
    }

    public RankingForm(JFrame informationForm) {
        recordManager = new RecordManager(); // 초기화
        cardLayout = new CardLayout();       // 초기화
        cardPanel = new JPanel(cardLayout);  // 초기화

        setTitle("랭킹 확인");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 상단 Home 및 Logout 버튼 생성
        JButton homeButton = new JButton("Home");
        JButton logoutButton = new JButton("Logout");

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(homeButton, BorderLayout.WEST);
        topPanel.add(logoutButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Home 버튼 동작
        homeButton.addActionListener(e -> dispose());

        // Logout 버튼 동작
        logoutButton.addActionListener(e -> {
            UsersData.getInstance().setCurrentUser(null); // 사용자 초기화
            // 현재 열려 있는 모든 창 닫기
            for (Window window : Window.getWindows()) {
                window.dispose();
            }
            // 새로운 로그인 화면 띄우기
            SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
        });

        // 난이도별 CardLayout 구성
        cardPanel.add(createRankingPanel("HARD"), "상");
        cardPanel.add(createRankingPanel("MEDIUM"), "중");
        cardPanel.add(createRankingPanel("EASY"), "하");
        add(cardPanel, BorderLayout.CENTER);

        // 좌우 화살표 버튼 생성
        JButton leftArrow = new JButton("←");
        JButton rightArrow = new JButton("→");

        leftArrow.addActionListener(e -> cardLayout.previous(cardPanel));
        rightArrow.addActionListener(e -> cardLayout.next(cardPanel));

        JPanel leftPanel = new JPanel(new GridBagLayout());
        leftPanel.add(leftArrow);
        add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.add(rightArrow);
        add(rightPanel, BorderLayout.EAST);

        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 난이도별 랭킹 패널 생성 메서드
    private JPanel createRankingPanel(String difficulty) {
        List<Record> records = recordManager.getRecordsByDifficulty(difficulty);
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(difficulty + " 난이도 랭킹", SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        // 랭킹 데이터 표시
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
