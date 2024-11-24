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
        recordManager = new RecordManager();
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        setTitle("랭킹 확인");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JButton homeButton = new JButton("Home");
        JButton logoutButton = new JButton("Logout");

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(homeButton, BorderLayout.WEST);
        topPanel.add(logoutButton, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        homeButton.addActionListener(event -> dispose());

        logoutButton.addActionListener(event -> {
            UsersData.getInstance().setCurrentUser(null);
            for (Window window : Window.getWindows()) {
                window.dispose();
            }
            SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
        });

        cardPanel.add(createRankingPanel("HARD"), "상");
        cardPanel.add(createRankingPanel("MEDIUM"), "중");
        cardPanel.add(createRankingPanel("EASY"), "하");
        add(cardPanel, BorderLayout.CENTER);

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

        setSize(600, 400);
        setLocationRelativeTo(null);
        setVisible(true);
    }

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
