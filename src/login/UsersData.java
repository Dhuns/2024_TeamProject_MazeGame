// UsersData.java
package login;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

public class UsersData {
    private ArrayList<User> users;
    private static final String FILE_PATH = "user.txt";
    private User currentUser; // 인스턴스 필드로 변경

    private static UsersData instance;

    private UsersData() {
        users = new ArrayList<>();
        loadUserData();
    }

    public static UsersData getInstance() {
        if (instance == null) {
            instance = new UsersData();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public void addUser(User newUser) {
        users.add(newUser);
        saveUserData();
    }

    public boolean isIdOverlap(String userId) {
        return users.stream().anyMatch(user -> user.getId().equals(userId));
    }

    public String getCurrentUserId() {
        if (currentUser != null) {
            return currentUser.getId();
        } else {
            throw new IllegalStateException("No user is currently logged in.");
        }
    }

    public void withdraw(String userId) {
        try {
            User userToRemove = null;

            for (User user : users) {
                if (user.getId().equalsIgnoreCase(userId)) {
                    userToRemove = user;
                    break;
                }
            }

            if (userToRemove != null) {
                users.remove(userToRemove);
                removeUserFromFile(userId);
                saveUserData();
            } else {
                System.out.println("ID에 해당하는 사용자를 찾을 수 없습니다. (입력된 ID: " + userId + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeUserFromFile(String id) {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH));
             BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH + ".tmp"))) {

            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (!userData[0].equals(id)) {
                    writer.write(line + "\n");
                } else {
                    System.out.println("User to remove: " + line);
                }
            }

        } catch (FileNotFoundException e) {
            // 파일이 없을 경우, 처음 실행하는 경우일 수 있으므로 무시
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 임시 파일을 원본 파일로 복사
        try {
            Files.copy(Paths.get(FILE_PATH + ".tmp"), Paths.get(FILE_PATH), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 임시 파일 삭제
        try {
            Files.delete(Paths.get(FILE_PATH + ".tmp"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

    public boolean contains(User user) {
        return users.contains(user);
    }

    private void loadUserData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData.length == 3) {
                    String id = userData[0];
                    String pw = userData[1];
                    String name = userData[2];
                    User user = new User(id, pw, name);
                    users.add(user);
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

    private void saveUserData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                writer.write(user.getId() + ",");
                writer.write(user.getPw() + ",");
                writer.write(user.getName() + ",");
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}