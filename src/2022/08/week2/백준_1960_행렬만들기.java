// 13856KB, 136ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_N = 500;
    static int N;
    static int[] rowOnes = new int[MAX_N];
    static int[] columnOnes = new int[MAX_N];

    static class Column implements Comparable<Column> {
        int idx;
        int remainingCnt;

        public Column(int idx, int remainingCnt) {
            this.idx = idx;
            this.remainingCnt = remainingCnt;
        }

        @Override
        public int compareTo(Column c) {
            return -(this.remainingCnt - c.remainingCnt);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // N 입력
        N = Integer.parseInt(br.readLine());

        // 행들의 1의 개수 입력
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < N; i++) {
            rowOnes[i] = Integer.parseInt(st.nextToken());
        }

        // 열들의 1의 개수 입력
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < N; i++) {
            columnOnes[i] = Integer.parseInt(st.nextToken());
        }

        // 우선순위큐에 추가
        PriorityQueue<Column> pq = new PriorityQueue<>();
        for (int i = 0; i < N; i++) {
            Column column = new Column(i, columnOnes[i]);
            pq.offer(column);
        }

        boolean success = true;
        int[][] answer = new int[N][N];
        for (int rowIdx = 0; rowIdx < N; rowIdx++) {
            List<Column> tmp = new ArrayList<>();

            // 행에 배치해야 하는 1의 개수만큼 처리
            for (int k = 0; k < rowOnes[rowIdx]; k++) {
                // 가장 여유 공간이 많은 열에 배치
                if (!pq.isEmpty() && pq.peek().remainingCnt > 0) {
                    Column pick = pq.poll();
                    answer[rowIdx][pick.idx] = 1;

                    tmp.add(new Column(pick.idx, pick.remainingCnt - 1));
                } else {
                    success = false;
                }
            }

            for (Column column : tmp) {
                pq.offer(column);
            }
        }

        if (success && !pq.isEmpty() && pq.peek().remainingCnt == 0) {
            sb.append(1).append("\n");
            for (int y = 0; y < N; y++) {
                for (int x = 0; x < N; x++) {
                    sb.append(answer[y][x]);
                }
                sb.append("\n");
            }
        } else {
            sb.append(-1).append("\n");
        }


        System.out.print(sb.toString());

    } // end main


}