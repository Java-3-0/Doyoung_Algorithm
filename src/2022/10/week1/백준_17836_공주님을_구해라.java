// 13560KB, 124ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_R = 100, MAX_C = 100, MAX_T = 10000;
    static final int INF = 987654321;
    static final int DIRECTIONS = 4;
    static final int SWORD = 2, WALL = 1, EMPTY = 0;
    static final int[] dy = {-1, 1, 0, 0};
    static final int[] dx = {0, 0, -1, 1};

    static int R, C, T;
    static int[][] grid = new int[MAX_R][MAX_C];
    static Queue<Position> q = new ArrayDeque<>();
    static boolean[][] visitCheck = new boolean[MAX_R][MAX_C];

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
            if (0 <= this.y && this.y < R && 0 <= this.x && this.x < C) {
                return true;
            }
            return false;
        }

        public boolean isVisited() {
            return visitCheck[this.y][this.x];
        }

        public void visit() {
            visitCheck[this.y][this.x] = true;
        }

        public int getGridVal() {
            return grid[this.y][this.x];
        }

        public int getDistanceTo(Position p) {
            int dy = Math.abs(this.y - p.y);
            int dx = Math.abs(this.x - p.x);
            return dy + dx;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine(), " ");
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        T = Integer.parseInt(st.nextToken());

        for (int y = 0; y < R; y++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int x = 0; x < C; x++) {
                grid[y][x] = Integer.parseInt(st.nextToken());
            }
        }

        int answer = bfs(new Position(0, 0));

        if (answer > T) {
            System.out.println("Fail");
        } else {
            System.out.println(answer);
        }

    } // end main

    public static int bfs(Position start) {
        Position princess = new Position(R - 1, C - 1);
        int ret = INF;

        // 시작 정점 처리
        start.visit();
        q.offer(start);

        // bfs 수행
        int depth = 0;
        while (!q.isEmpty()) {
            if (depth > T) {
                break;
            }

            int size = q.size();
            for (int i = 0; i < size; i++) {
                Position here = q.poll();

                // 도착한 경우 리턴값 갱신
                if (here.y == princess.y && here.x == princess.x) {
                    ret = Math.min(ret, depth);
                }

                // 그람을 찾은 경우 리턴값 갱신
                if (here.getGridVal() == SWORD) {
                    int distanceToPrincess = here.getDistanceTo(princess);
                    ret = Math.min(ret, depth + distanceToPrincess);
                }

                // 다음 정점으로 계속 탐색
                for (int dir = 0; dir < DIRECTIONS; dir++) {
                    Position next = here.getNextPos(dir);

                    // 갈 수 있는 곳이면 큐에 넣는다
                    if (next.isInRange() && next.getGridVal() != WALL && !next.isVisited()) {
                        next.visit();
                        q.offer(next);
                    }
                }
            }

            depth++;
        }

        return ret;
    }

}