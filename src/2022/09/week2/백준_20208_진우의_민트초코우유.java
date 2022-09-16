// 12936KB, 96ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_GRID_SIZE = 10, MAX_INITIAL_HP = 10, MAX_HEAL_HP = 10, MAX_MILK_CNT = 10;
    static final int MAX_BIT_MASK = (1 << MAX_MILK_CNT) - 1;
    static final int EMPTY = 0, HOUSE = 1, MILK = 2;
    static final int NOT_VISITED = -1;

    static int gridSize, initialHP, healHP;
    static int milkCnt;
    static int answer = 0;
    static Position housePosition;
    static List<Position> milkPositions = new ArrayList<>();

    /**
     * isVisitedWithHealth[curMilkIdx][bitMask]는 현재 curMilkIdx 위치에 있고 방문한 우유들이 bitMask 일 때 최대 체력을 기록
     */
    static int[][] isVisitedWithHealth = new int[MAX_MILK_CNT][MAX_BIT_MASK + 1];

    static class Position {
        int y;
        int x;

        public Position(int y, int x) {
            this.y = y;
            this.x = x;
        }

        public int getDistanceTo(Position position) {
            int dy = Math.abs(this.y - position.y);
            int dx = Math.abs(this.x - position.x);
            return dy + dx;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // 그리드 크기, 시작 체력, 우유 먹을 때 증가하는 체력 입력
        st = new StringTokenizer(br.readLine(), " ");
        gridSize = Integer.parseInt(st.nextToken());
        initialHP = Integer.parseInt(st.nextToken());
        healHP = Integer.parseInt(st.nextToken());

        // 그리드 입력받아서 집 위치, 우유 위치 리스트 저장
        for (int y = 0; y < gridSize; y++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int x = 0; x < gridSize; x++) {
                int gridVal = Integer.parseInt(st.nextToken());
                switch (gridVal) {
                    case HOUSE:
                        housePosition = new Position(y, x);
                        break;
                    case MILK:
                        milkPositions.add(new Position(y, x));
                        break;
                    default:
                        break;
                }
            }
        }

        // 우유 개수 파악
        milkCnt = milkPositions.size();

        // 방문 체크 초기화
        initIsVisitedWithHealth();

        // 첫 우유를 선택해서 진행해 보며 answer 갱신
        for (int firstMilk = 0; firstMilk < milkCnt; firstMilk++) {
            int distance = housePosition.getDistanceTo(milkPositions.get(firstMilk));
            if (distance <= initialHP) {
                solve(firstMilk, (1 << firstMilk), initialHP - distance + healHP);
            }
        }

        // answer 출력
        System.out.println(answer);

    } // end main

    /**
     * 현재 우유 위치, 방문했던 우유들 비트마스킹 정보, 남은 체력이 주어졌을 때 방문 가능한 최대 우유 수를 구해서 정답 갱신
     */
    public static void solve(int curMilkIdx, int bitMask, int remainingHP) {
        // 방문 체크
        if (isVisitedWithHealth[curMilkIdx][bitMask] >= remainingHP) {
            return;
        }
        isVisitedWithHealth[curMilkIdx][bitMask] = remainingHP;

        // 집까지 돌아갈 수 있다면 정답 갱신 시도
        Position curMilkPosition = milkPositions.get(curMilkIdx);
        int distanceToHome = curMilkPosition.getDistanceTo(housePosition);
        if (distanceToHome <= remainingHP) {
            int visitedCnt = Integer.bitCount(bitMask);
            answer = Math.max(answer, visitedCnt);
        }

        // 아직 방문하지 않은 우유를 찾는다
        for (int nextMilkIdx = 0; nextMilkIdx < milkCnt; nextMilkIdx++) {
            if ((bitMask & (1 << nextMilkIdx)) == 0) {
                int nextBitMask = (bitMask | (1 << nextMilkIdx));
                Position nextMilkPosition = milkPositions.get(nextMilkIdx);
                int distanceToNextMilk = curMilkPosition.getDistanceTo(nextMilkPosition);

                // 남은 체력으로 이동할 수 있는 경우 재귀 호출
                if (distanceToNextMilk <= remainingHP) {
                    int nextRemainingHealth = remainingHP - distanceToNextMilk + healHP;
                    solve(nextMilkIdx, nextBitMask, nextRemainingHealth);
                }
            }
        }
    }

    /**
     * isVisitedWithHealth 배열 초기화
     */
    public static void initIsVisitedWithHealth() {
        for (int i = 0; i < isVisitedWithHealth.length; i++) {
            Arrays.fill(isVisitedWithHealth[i], NOT_VISITED);
        }
    }

}