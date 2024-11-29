package maze;

import java.util.Random;

public class Maze {
    private int[][] maze;
    private int size;

    public Maze(int size) {
        this.size = size;
        this.maze = new int[size][size];
        generateMaze();
    }

    private void generateMaze() {
        // 모든 칸을 벽으로 설정
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                maze[i][j] = 1; // 벽으로 초기화
            }
        }

        // 시작점과 끝점 초기화
        maze[0][0] = 0; // 시작점
        if (size > 1) {
            maze[size - 2][size - 2] = 0; // 목적지를 (size-2, size-2)로 설정
        }

        // 경로 생성
        createPath(0, 0);
    }

    private void createPath(int x, int y) {
        int[] dirX = {-2, 2, 0, 0}; // 상하
        int[] dirY = {0, 0, -2, 2}; // 좌우
        Random rand = new Random();

        // 방향을 무작위로 섞기
        for (int i = 0; i < 4; i++) {
            int randomIndex = rand.nextInt(4);
            int tempX = dirX[i];
            int tempY = dirY[i];
            dirX[i] = dirX[randomIndex];
            dirY[i] = dirY[randomIndex];
            dirX[randomIndex] = tempX;
            dirY[randomIndex] = tempY;
        }

        for (int i = 0; i < 4; i++) {
            int newX = x + dirX[i];
            int newY = y + dirY[i];

            // 범위 체크 및 길 만들기
            if (newX >= 0 && newX < size && newY >= 0 && newY < size && maze[newX][newY] == 1) {
                maze[newX][newY] = 0; // 길 만들기
                maze[x + dirX[i] / 2][y + dirY[i] / 2] = 0; // 벽 제거
                createPath(newX, newY); // 재귀 호출
            }
        }
    }

    public int[][] getMaze() {
        return maze;
    }

    public int getSize() {
        return size;
    }

    public boolean isValidMove(int x, int y) {
        // 범위 체크
        if (x < 0 || x >= size || y < 0 || y >= size) {
            return false;
        }
        // 벽인지 체크
        return maze[x][y] == 0; // 0이면 길, 1이면 벽
    }
}
