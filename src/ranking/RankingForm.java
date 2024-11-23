package ranking;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class RankingForm extends JFrame {
    private RecordManager recordManager;
    private JPanel cardPanel;
    private CardLayout cardLayout;

    public RankingForm() {
        recordManager = new RecordManager();
        setTitle("랭킹 확인");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        cardPanel.add(createRankingPanel("HARD"), "상");
        cardPanel.add(createRankingPanel("MEDIUM"), "중");
        cardPanel.add(createRankingPanel("EASY"), "하");

        add(cardPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton highButton = new JButton("상");
        JButton mediumButton = new JButton("중");
        JButton lowButton = new JButton("하");

        highButton.addActionListener(e -> cardLayout.show(cardPanel, "상"));
        mediumButton.addActionListener(e -> cardLayout.show(cardPanel, "중"));
        lowButton.addActionListener(e -> cardLayout.show(cardPanel, "하"));

        buttonPanel.add(highButton);
        buttonPanel.add(mediumButton);
        buttonPanel.add(lowButton);

        add(buttonPanel, BorderLayout.NORTH);

        JButton backButton = new JButton("뒤로가기");
        backButton.addActionListener(e -> dispose());
        add(backButton, BorderLayout.SOUTH);

        pack();
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
        panel.add(new JScrollPane(rankingTextArea), BorderLayout.CENTER);

        return panel;
    }
}