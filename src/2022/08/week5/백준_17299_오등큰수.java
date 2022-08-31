// 195844KB, 988ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_N = 1000000, MAX_A = 1000000;
    static int N;
    static int[] counts = new int[MAX_A + 1];
    static int[] seq;
    static int[] answers;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // N 입력
        N = Integer.parseInt(br.readLine());

        // 메모리 할당
        seq = new int[N];
        answers = new int[N];

        // answers 배열 초기화
        Arrays.fill(answers, -1);

        // 수열 입력받으면서 카운팅
        st = new StringTokenizer(br.readLine(), " ");
        for (int i = 0; i < N; i++) {
            int num = Integer.parseInt(st.nextToken());
            seq[i] = num;
            counts[num]++;
        }

        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < N; i++) {
            while (!stack.isEmpty() && counts[seq[stack.peek()]] < counts[seq[i]]) {
                int idx = stack.pop();
                answers[idx] = seq[i];
            }

            stack.push(i);
        }

        for (int answer : answers) {
            sb.append(answer).append(" ");
        }
        sb.append("\n");

        System.out.print(sb.toString());
    }
}
