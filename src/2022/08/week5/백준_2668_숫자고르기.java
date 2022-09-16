// 11636KB, 80ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.TreeSet;

public class Main {
    static final int MAX_N = 100;

    static int N;
    static int[] seq = new int[MAX_N + 1];
    static boolean[] isVisited = new boolean[MAX_N + 1];

    static Stack<Integer> stack = new Stack();
    static TreeSet<Integer> answerSet = new TreeSet<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        // N 입력
        N = Integer.parseInt(br.readLine());

        // 수열 입력
        for (int i = 1; i <= N; i++) {
            seq[i] = Integer.parseInt(br.readLine());
        }

        // 방문하지 않은 정점마다 dfs 시작
        for (int start = 1; start <= N; start++) {
            if (!isVisited[start]) {
                stack.clear();
                dfs(start);
            }
        }

        // 출력
        sb.append(answerSet.size()).append("\n");
        for (int answer : answerSet) {
            sb.append(answer).append("\n");
        }

        System.out.print(sb.toString());

    } // end main

    /**
     * dfs 를 수행하며 사이클을 판정하여, 사이클에 속하는 노드들을 answerSet 에 담는다
     */
    public static void dfs(int here) {
        // 이미 방문한 노드를 다시 방문했을 때
        if (isVisited[here]) {
            // 스택에 존재한다면 사이클이 생긴 것이므로 그 지점까지를 pop 해서 answerSet 에 담는다
            if (stack.contains(here)) {
                while (!stack.isEmpty()) {
                    int top = stack.pop();
                    answerSet.add(top);
                    if (top == here) {
                        break;
                    }
                }
            }

            return;
        }

        // 새로 방문하는 경우 방문 체크 및 스택에 추가
        isVisited[here] = true;
        stack.push(here);

        // 인접 노드로 재귀 호출
        int next = seq[here];
        dfs(next);
    }
}