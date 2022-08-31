// 20944KB, 92ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static final int MAX_CAPACITY = 200;
    public static final int BOTTLES = 3;
    public static int[] capacities = new int[BOTTLES];

    public static class State {
        int[] waters = new int[BOTTLES];

        public State(int[] waters) {
            for (int i = 0; i < BOTTLES; i++) {
                this.waters[i] = waters[i];
            }
        }

        public List<State> getNextStates() {
            List<State> ret = new ArrayList<>();

            for (int from = 0; from < BOTTLES; from++) {
                for (int to = 0; to < BOTTLES; to++) {
                    if (from != to) {
                        int move = Math.min(waters[from], capacities[to] - waters[to]);

                        int[] nextWaters = Arrays.copyOf(this.waters, BOTTLES);
                        nextWaters[from] = waters[from] - move;
                        nextWaters[to] = waters[to] + move;

                        State next = new State(nextWaters);

                        ret.add(next);
                    }
                }
            }

            return ret;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
    StringBuilder sb = new StringBuilder();

        // 물통 용량 입력
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < BOTTLES; i++) {
            capacities[i] = Integer.parseInt(st.nextToken());
        }

        // 시작 상태 설정
        int[] waters = {0, 0, capacities[2]};
        State start = new State(waters);

        // bfs 수행
        TreeSet<Integer> possibleWaterAmountInC = bfs(start);

        // 정답 출력
        while (!possibleWaterAmountInC.isEmpty()) {
            sb.append(possibleWaterAmountInC.pollFirst()).append(" ");
        }
        sb.append("\n");

        System.out.print(sb.toString());

    } // end main

    public static TreeSet<Integer> bfs(State start) {
        TreeSet<Integer> ret = new TreeSet<>();
        boolean[][][] isVisited = new boolean[MAX_CAPACITY + 1][MAX_CAPACITY + 1][MAX_CAPACITY + 1];
        Queue<State> q = new ArrayDeque<>();

        isVisited[start.waters[0]][start.waters[1]][start.waters[2]] = true;
        q.offer(start);

        while (!q.isEmpty()) {
            State now = q.poll();
            if (now.waters[0] == 0) {
                ret.add(now.waters[2]);
            }

            List<State> nextStates = now.getNextStates();
            for (State next : nextStates) {
                if (!isVisited[next.waters[0]][next.waters[1]][next.waters[2]]) {
                    isVisited[next.waters[0]][next.waters[1]][next.waters[2]] = true;
                    q.offer(next);
                }
            }
        }

        return ret;
    }
}