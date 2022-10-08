// 22444KB, 248ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 빌딩 수 입력
        int N = Integer.parseInt(br.readLine());

        // 빌딩 높이들 입력받아서 배열에 저장
        int[] heights = new int[N];
        for (int i = 0; i < N; i++) {
            heights[i] = Integer.parseInt(br.readLine());
        }

        Stack<Integer> stack = new Stack<>();
        int[] counts = new int[N];

        // 왼쪽부터 빌딩을 하나씩 처리
        for (int now = 0; now < N; now++) {
            int height = heights[now];

            // 자신보다 작은 빌딩들을 스택에서 꺼내서 볼 수 있는 개수를 저장한다
            while (!stack.isEmpty()) {
                int topIdx = stack.peek();
                int prevHeight = heights[topIdx];

                if (prevHeight <= height) {
                    counts[topIdx] = (now - topIdx - 1);
                    stack.pop();
                } else {
                    break;
                }
            }

            // 자신을 스택에 넣는다
            stack.push(now);
        }

        // 끝까지 스택에 남은 것들 처리
        while (!stack.isEmpty()) {
            int top = stack.pop();
            counts[top] = (N - 1) - top;
        }

        // 정답 계산
        long answer = 0L;
        for (int cnt : counts) {
            answer += (long) cnt;
        }

        // 출력
        System.out.println(answer);

    } // end main

}