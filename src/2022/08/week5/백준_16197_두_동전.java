// 12044KB, 84ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final int MAX_H = 20, MAX_W = 20;
    static final int INF = 987654321;
    static final int DIRECTIONS = 4;
    static final int[] dy = {-1, 1, 0, 0};
    static final int[] dx = {0, 0, -1, 1};
    static final char COIN = 'o', EMPTY = '.', WALL = '#';

    static int H, W;
    static char[][] grid = new char[MAX_H][MAX_W];

    static Queue<Status> q = new ArrayDeque<>();
    static boolean[][][][] visitCheck = new boolean[MAX_H][MAX_W][MAX_H][MAX_W];

    static class Position {
        int y;
        int x;

        public Position(int y, int x) {
            super();
            this.y = y;
            this.x = x;
        }

        @Override
        public String toString() {
            return "Pos [y=" + y + ", x=" + x + "]";
        }

        public Position getNextPos(int dir) {
            return new Position(this.y + dy[dir], this.x + dx[dir]);
        }

        public boolean isInRange() {
            if (0 <= this.y && this.y < H && 0 <= this.x && this.x < W) {
                return true;
            }
            return false;
        }

        public char getGridVal() {
            return grid[this.y][this.x];
        }
    }

    static class Status {
        Position coinPos1;
        Position coinPos2;

        public Status(Position coinPos1, Position coinPos2) {
            this.coinPos1 = coinPos1;
            this.coinPos2 = coinPos2;
        }

        @Override
        public String toString() {
            return "Status{" +
                    "coinPos1=" + coinPos1 +
                    ", coinPos2=" + coinPos2 +
                    '}';
        }

        public boolean isVisited() {
            return visitCheck[this.coinPos1.y][this.coinPos1.x][this.coinPos2.y][this.coinPos2.x];
        }

        public void visit() {
            visitCheck[this.coinPos1.y][this.coinPos1.x][this.coinPos2.y][this.coinPos2.x] = true;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // 그리드 크기 입력
        st = new StringTokenizer(br.readLine(), " ");
        H = Integer.parseInt(st.nextToken());
        W = Integer.parseInt(st.nextToken());

        // 그리드 입력받으면서 동전 위치 저장
        List<Position> coinPositionList = new ArrayList<>();
        for (int y = 0; y < H; y++) {
            String line = br.readLine();
            for (int x = 0; x < W; x++) {
                char c = line.charAt(x);

                switch (c) {
                    case COIN:
                        coinPositionList.add(new Position(y, x));
                        grid[y][x] = EMPTY;
                        break;
                    default:
                        grid[y][x] = c;
                        break;
                }
            }
        }

        Status start = new Status(coinPositionList.get(0), coinPositionList.get(1));
        int answer = bfs(start);

        if (answer <= 10) {
            System.out.println(answer);
        } else {
            System.out.println(-1);
        }

    } // end main

    public static int bfs(Status start) {
        start.visit();
        q.offer(start);

        int depth = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                Status now = q.poll();

                Position pos1 = now.coinPos1;
                Position pos2 = now.coinPos1;

                for (int dir = 0; dir < DIRECTIONS; dir++) {
                    Position movedPos1 = now.coinPos1.getNextPos(dir);
                    Position movedPos2 = now.coinPos2.getNextPos(dir);

                    if (movedPos1.isInRange()) {
                        // 두 동전 모두 범위 내인 경우
                        if (movedPos2.isInRange()) {
                            char val1 = movedPos1.getGridVal();
                            char val2 = movedPos2.getGridVal();

                            Position nextPos1 = val1 == EMPTY ? movedPos1 : pos1;
                            Position nextPos2 = val2 == EMPTY ? movedPos2 : pos2;

                            Status nextStatus = new Status(nextPos1, nextPos2);

                            if (!nextStatus.isVisited()) {
                                nextStatus.visit();
                                q.offer(nextStatus);
                            }
                        }

                        // 2번 동전만 떨어지는 경우
                        else {
                            return depth + 1;
                        }
                    } else {
                        // 1번 동전만 떨어지는 경우
                        if (movedPos2.isInRange()) {
                            return depth + 1;
                        }
                    }
                }
            }

            depth++;
            if (depth >= 10) {
                break;
            }
        }

        return INF;
    }
}
