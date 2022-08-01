// 74440KB, 300ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_N = 500000;
    static final long IMPOSSIBLE = -1L;

    static int N;
    static int A, B;
    static int DA, DB;

    static class MyState {
        int diff;
        int shouldUseDA;

        public MyState(int diff, int shouldUseDA) {
            this.diff = diff;
            this.shouldUseDA = shouldUseDA;
        }

        @Override
        public String toString() {
            return "MyState{" +
                    "diff=" + diff +
                    ", shouldUseDA=" + shouldUseDA +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        // 입력
        st = new StringTokenizer(br.readLine(), " ");
        N = Integer.parseInt(st.nextToken());
        A = Integer.parseInt(st.nextToken());
        B = Integer.parseInt(st.nextToken());
        DA = Integer.parseInt(st.nextToken());
        DB = Integer.parseInt(st.nextToken());

        MyState start = new MyState((B - A + N) % N, 1);
        long answer = bfs(start);

        if (answer == IMPOSSIBLE) {
            System.out.print("Evil Galazy");
        } else {
            System.out.print(answer);
        }


    }

    public static long bfs(MyState start) {
        Queue<MyState> q = new ArrayDeque<>();
        boolean[][] isVisited = new boolean[MAX_N + 1][2];

        // 초기 상태 처리
        isVisited[start.diff][start.shouldUseDA] = true;
        q.offer(start);

        // bfs 수행
        long depth = 0L;
        while (!q.isEmpty()) {
            int size = q.size();
            for (int i = 0; i < size; i++) {
                MyState now = q.poll();
                int diff = now.diff;
                int shouldUseDA = now.shouldUseDA;

                if (diff == 0L) {
                    return depth;
                }

                if (shouldUseDA == 1) {
                    MyState next1 = new MyState((diff + DA) % N, 0);
                    if (!isVisited[next1.diff][next1.shouldUseDA]) {
                        isVisited[next1.diff][next1.shouldUseDA] = true;
                        q.offer(next1);
                    }
                    MyState next2 = new MyState((now.diff - DA + N) % N, 0);
                    if (!isVisited[next2.diff][next2.shouldUseDA]) {
                        isVisited[next2.diff][next2.shouldUseDA] = true;
                        q.offer(next2);
                    }
                } else {
                    MyState next1 = new MyState((diff + DB) % N, 1);
                    if (!isVisited[next1.diff][next1.shouldUseDA]) {
                        isVisited[next1.diff][next1.shouldUseDA] = true;
                        q.offer(next1);
                    }
                    MyState next2 = new MyState((now.diff - DB + N) % N, 1);
                    if (!isVisited[next2.diff][next2.shouldUseDA]) {
                        isVisited[next2.diff][next2.shouldUseDA] = true;
                        q.offer(next2);
                    }
                }
            }

            depth++;
        }

        return IMPOSSIBLE;
    }
}
