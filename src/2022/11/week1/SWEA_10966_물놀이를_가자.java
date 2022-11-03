import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_H = 1000, MAX_W = 1000;
    static final char WATER = 'W', LAND = 'L';
    static final int NOT_VISITED = -1;
    static final int DIRECTIONS = 4;
    static final int[] dy = {-1, 1, 0, 0};
    static final int[] dx = {0, 0, -1, 1};

    static int H, W;
    static int[][] visited = new int[MAX_H][MAX_W];
    static Queue<Position> queue = new ArrayDeque<>();

    static class Position {
        int y;
        int x;

        public Position(int y, int x) {
            super();
            this.y = y;
            this.x = x;
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

        public boolean isVisited() {
            return visited[this.y][this.x] != NOT_VISITED;
        }

        public void visit(int time) {
            visited[this.y][this.x]= time;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        final int TESTS = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= TESTS; tc++) {
            initMemory();

            st = new StringTokenizer(br.readLine(), " ");
            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());

            // 물인 곳을 모두 방문하고 큐에 넣은 후 시작
            for (int y = 0; y < H; y++) {
                String line = br.readLine();
                for (int x = 0; x < W; x++) {
                    char c = line.charAt(x);
                    if (c == WATER) {
                        Position pos = new Position(y, x);
                        pos.visit(0);
                        queue.offer(pos);
                    }
                }
            }

            // bfs 수행
            int depth = 0;
            while (!queue.isEmpty()) {
                depth++;

                int size = queue.size();
                for (int i = 0; i < size; i++) {
                    Position here = queue.poll();

                    for (int dir = 0; dir < DIRECTIONS; dir++) {
                        Position next = here.getNextPos(dir);
                        if (next.isInRange() && !next.isVisited()) {
                            next.visit(depth);
                            queue.offer(next);
                        }
                    }
                }
            }

            // 합산
            int answer = 0;
            for (int y = 0; y< H; y++) {
                for (int x = 0; x < W; x++) {
                    answer += visited[y][x];
                }
            }

            // 출력에 추가
            sb.append("#").append(tc).append(" ").append(answer).append("\n");
        }

        System.out.print(sb.toString());

    } // end main

    public static void initMemory() {
        for (int i = 0; i < visited.length; i++) {
            Arrays.fill(visited[i], NOT_VISITED);
        }

        queue.clear();
    }

}