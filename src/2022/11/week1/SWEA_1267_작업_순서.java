import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Solution {
    static final int MAX_V = 1000, MAX_E = 3000;

    static int V, E;
    static List<Integer>[] adjList = new ArrayList[MAX_V + 1];
    static int[] inDegrees = new int[MAX_V + 1];

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // 인접 리스트 메모리 할당
        for (int i = 0; i <= MAX_V; i++) {
            adjList[i] = new ArrayList<Integer>();
        }

        final int TESTS = 10;
        for (int tc = 1; tc <= TESTS; tc++) {
            // 메모리 초기화
            initMemory();

            // 정점 수, 간선 수 입력
            st = new StringTokenizer(br.readLine(), " ");
            V = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());

            // 간선 정보 입력
            st = new StringTokenizer(br.readLine(), " ");
            for (int e = 0; e < E; e++) {
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());

                adjList[from].add(to);
                inDegrees[to]++;
            }

            // 테스트케이스 번호를 출력에 추가
            sb.append("#").append(tc).append(" ");

            // 위상 정렬
            Queue<Integer> q = new LinkedList<>();
            for (int i = 1; i <= V; i++) {
                if (inDegrees[i] == 0) {
                    q.offer(i);
                }
            }
            while (!q.isEmpty()) {
                int now = q.poll();
                sb.append(now).append(" ");

                for (int to : adjList[now]) {
                    inDegrees[to]--;

                    if (inDegrees[to] == 0) {
                        q.offer(to);
                    }
                }
            }
            sb.append("\n");
        }

        System.out.print(sb.toString());

    } // end main

    public static void initMemory() {
        for (int i = 0; i < adjList.length; i++) {
            adjList[i].clear();
        }
        Arrays.fill(inDegrees, 0);
    }

}