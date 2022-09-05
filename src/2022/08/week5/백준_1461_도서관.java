// 11864KB, 84ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        st = new StringTokenizer(br.readLine(), " ");
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        PriorityQueue<Integer> leftPQ = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> rightPQ = new PriorityQueue<>(Collections.reverseOrder());

        // 입력을 받아서 방향마다 절대값을 리스트에 담는다.
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < N; i++) {
            int num = Integer.parseInt(st.nextToken());

            if (num >= 0) {
                rightPQ.offer(num);
            } else {
                leftPQ.offer(-num);
            }
        }

        int answer = 0;

        // 마지막에 방문할 곳 처리
        int maxLeft = leftPQ.isEmpty() ? 0 : leftPQ.peek();
        int maxRight = rightPQ.isEmpty() ? 0 : rightPQ.peek();
        answer -= Math.max(maxLeft, maxRight);

        // 나머지 방문할 곳 처리
        while (!leftPQ.isEmpty()) {
            for (int i = 0; i < M; i++) {
                if (!leftPQ.isEmpty()) {
                    int distance = leftPQ.poll();
                    if (i == 0) {
                        answer += (2 * distance);
                    }
                }
            }
        }

        while (!rightPQ.isEmpty()) {
            for (int i = 0; i < M; i++) {
                if (!rightPQ.isEmpty()) {
                    int distance = rightPQ.poll();
                    if (i == 0) {
                        answer += (2 * distance);
                    }
                }
            }
        }

        System.out.println(answer);
    }
}
