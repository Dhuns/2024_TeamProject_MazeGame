//전체 랭킹 화면 (난이도별 패널을 포함함 - 메인)
package ranking;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RankingForm extends JFrame {
    private RecordManager recordManager;

    public RankingForm() {
        recordManager = new RecordManager();
        setTitle("랭킹 확인");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(1, 3)); // 난이도별 패널을 가로로 배치

        // 난이도별 패널 추가
        add(createRankingPanel("상"));
        add(createRankingPanel("중"));
        add(createRankingPanel("하"));

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createRankingPanel(String difficulty) {
        List<Record> records = recordManager.getRecordsByDifficulty(difficulty);
        return new RankingPanel(difficulty, records);
    }
}
