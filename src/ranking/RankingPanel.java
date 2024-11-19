//난이도별 랭킹을 보여주는 패널 (각각의 난이도 데이터를 표시)
package ranking;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RankingPanel extends JPanel {
    public RankingPanel(String difficulty, List<Record> records) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("난이도 " + difficulty));

        JTextArea textArea = new JTextArea(10, 20);
        textArea.setEditable(false);

        StringBuilder content = new StringBuilder();
        int rank = 1;
        for (Record record : records) {
            content.append(rank).append(". ").append(record).append("\n");
            rank++;
        }
        textArea.setText(content.toString());
        add(new JScrollPane(textArea), BorderLayout.CENTER);
    }
}
