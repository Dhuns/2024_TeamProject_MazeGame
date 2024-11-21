// 랭킹 데이터를 관리함(파일 입출력, 데이터 세이브&로드, 정렬)
package ranking;

import java.io.*;
import java.util.*;

public class RecordManager {
    private static final String FILE_PATH = "game_records.txt";
    private List<Record> records;

    public RecordManager() {
        records = new ArrayList<>();
        loadRecords(); // 파일에서 기록을 불러옴
    }

    // 새로운 기록 추가
    public void addRecord(Record record) {
        records.add(record);
        saveRecordToFile(record); // 새 기록만 파일에 추가
    }

    // 특정 난이도의 기록 반환
    public List<Record> getRecordsByDifficulty(String difficulty) {
        List<Record> filtered = new ArrayList<>();
        for (Record record : records) {
            if (record.getDifficulty().equalsIgnoreCase(difficulty)) {
                filtered.add(record);
            }
        }
        filtered.sort(Comparator.comparingInt(Record::getTime)); // 시간 기준 정렬
        return filtered;
    }

    // 파일에서 기록 불러오기
    private void loadRecords() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    String difficulty = parts[0];
                    String playerId = parts[1];
                    int time = Integer.parseInt(parts[2]);
                    records.add(new Record(difficulty, playerId, time));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("플레이 기록 파일이 없습니다. 새로 생성합니다.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 새 기록을 파일에 저장
    private void saveRecordToFile(Record record) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(record.getDifficulty() + "," + record.getPlayerId() + "," + record.getTime() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
