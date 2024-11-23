package ranking;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RecordManager {
    private List<Record> records;
    private static final String FILE_PATH = "ranking.txt";

    public RecordManager() {
        records = new ArrayList<>();
        loadRecords();
    }

    public List<Record> getRecords() {
        return records;
    }

    public List<Record> getRecordsByDifficulty(String difficulty) {
        List<Record> filtered = new ArrayList<>();
        for (Record record : records) {
            if (record.getDifficulty().equalsIgnoreCase(difficulty)) {
                filtered.add(record);
            }
        }
        filtered.sort(Comparator.comparingInt(Record::getTime));
        return filtered;
    }

    private void loadRecords() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] recordData = line.split(",");
                if (recordData.length == 3) {
                    String difficulty = recordData[0];
                    String nickname = recordData[1];
                    int time = Integer.parseInt(recordData[2]);
                    Record record = new Record(difficulty, nickname, time);
                    records.add(record);
                } else {
                    System.out.println("잘못된 형식의 데이터 " + line);
                }
            }
        } catch (FileNotFoundException e) {
            // 파일이 없을 경우, 처음 실행하는 경우일 수 있으므로 무시
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveRecord(String difficulty, String userId, int clearTime) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(difficulty + "," + userId + "," + clearTime);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveRecordData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Record record : records) {
                writer.write(record.getDifficulty() + ",");
                writer.write(record.getNickname() + ",");
                writer.write(record.getTime() + "\n");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}