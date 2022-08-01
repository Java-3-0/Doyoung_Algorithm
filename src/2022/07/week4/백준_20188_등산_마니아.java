// 158000KB, 2176ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    static final int MAX_N = 300000;

    static int N;
    static List<Integer>[] adjList = new ArrayList[MAX_N + 1];
    static boolean[] isVisited = new boolean[MAX_N + 1];
    static int[] subtreeSizes = new int[MAX_N + 1];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // adjList 메모리 할당
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new ArrayList<Integer>();
        }

        // 정점 수 입력
        N = Integer.parseInt(br.readLine());

        // 간선 정보 입력
        for (int i = 0; i < N - 1; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            // 양방향 처리
            adjList[a].add(b);
            adjList[b].add(a);
        }

        // dfs 수행
        int root = 1;
        isVisited[root] = true;
        dfs(root);

        long answer = 0L;
        for (int v = 2; v <= N; v++) {
            long cnt = 0L;
            long k = (long) N - (long) subtreeSizes[v];
            cnt += ((long) N * (long) (N - 1) / 2L) - (k * (k - 1L) / 2L);

            answer += cnt;
        }

        System.out.println(answer);
    }

    public static void dfs(int now) {
        subtreeSizes[now] = 1;

        for (int next : adjList[now]) {
            if (!isVisited[next]) {
                isVisited[next] = true;
                dfs(next);
                subtreeSizes[now] += subtreeSizes[next];
            }
        }
    }
}
