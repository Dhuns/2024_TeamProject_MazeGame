package ranking;

import login.LoginForm;
import login.UsersData;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.util.List;

public class RankingForm extends JFrame {
    private RecordManager recordManager;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JFrame parentInformationForm; // Home 화면(InformationForm) 참조

    public RankingForm() {
        this(null); // 기본 생성자는 InformationForm 없이 실행
    }

    // 생성자: InformationForm을 전달받아야 함
    public RankingForm(JFrame informationForm) {
        this.parentInformationForm = informationForm; // Home 화면 참조 저장
        recordManager = new RecordManager();
        setTitle("랭킹 확인");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // 홈버튼, 로그아웃 버튼
        JButton homeButton = new JButton("Home");
        JButton logoutButton = new JButton("Logout");
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(homeButton, BorderLayout.WEST);
        topPanel.add(logoutButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Home 버튼 클릭 동작: 현재 창만 닫기
        homeButton.addActionListener(e -> dispose());

        // Logout 버튼 클릭 동작
        logoutButton.addActionListener(e -> {
            UsersData.getInstance().setCurrentUser(null); // 현재 사용자 초기화
            // 현재 프로그램에서 열려 있는 모든 창 닫기
            Window[] windows = Window.getWindows();
            for (Window window : windows) {
                window.dispose();
            }
            // 새로운 로그인 창 띄우기
            SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
        });

        // CardLayout으로 난이도별 랭킹 화면 구성
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createRankingPanel("HARD"), "상");
        cardPanel.add(createRankingPanel("MEDIUM"), "중");
        cardPanel.add(createRankingPanel("EASY"), "하");

        add(cardPanel, BorderLayout.CENTER);

        // 중앙 좌우 화살표 버튼 추가
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

        setSize(600, 400); // 창 크기 설정
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // 난이도별 랭킹 패널 생성
    private JPanel createRankingPanel(String difficulty) {
        List<Record> records = recordManager.getRecordsByDifficulty(difficulty);
        JPanel panel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel(difficulty + " 난이도 랭킹", SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        // 텍스트 영역 추가 및 하단 공백 설정
        JTextArea rankingTextArea = new JTextArea();
        rankingTextArea.setEditable(false);
        for (Record record : records) {
            rankingTextArea.append(record.toString() + "\n");
        }

        JScrollPane scrollPane = new JScrollPane(rankingTextArea);
        scrollPane.setBorder(new EmptyBorder(10, 10, 20, 10)); // 하단에 20px 공백 추가
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }
}
