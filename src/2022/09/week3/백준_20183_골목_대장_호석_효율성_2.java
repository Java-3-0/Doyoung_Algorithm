import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final int MAX_V = 100000, MAX_E = 500000;
    static final long MAX_BUDGET = (long) 1e14;
    static final int MAX_SHAME = (int) 1e9;
    static final long INF = 98765432109876543L;
    static final int IMPOSSIBLE = -1;
    static int V, E, START, END;
    static long BUDGET;
    static List<Edge>[] adjList = new ArrayList[MAX_V + 1];

    static class Edge {
        int to;
        long cost;

        public Edge(int to, long cost) {
            this.to = to;
            this.cost = cost;
        }
    }

    static class Vertex implements Comparable<Vertex> {
        int vNum;
        long totalCost;

        public Vertex(int vNum, long totalCost) {
            this.vNum = vNum;
            this.totalCost = totalCost;
        }


        @Override
        public int compareTo(Vertex s) {
            return Long.compare(this.totalCost, s.totalCost);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // adjList 메모리 할당
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new ArrayList<>();
        }

        // 정점 수, 간선 수, 시작 정점, 끝 정점, 예산 입력
        st = new StringTokenizer(br.readLine(), " ");
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        START = Integer.parseInt(st.nextToken());
        END = Integer.parseInt(st.nextToken());
        BUDGET = Long.parseLong(st.nextToken());

        // 간선 정보 입력받아서 adjList 에 저장
        for (int i = 0; i < E; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int from = Integer.parseInt(st.nextToken());
            int to = Integer.parseInt(st.nextToken());
            long cost = Long.parseLong(st.nextToken());

            // 양방향 처리
            adjList[from].add(new Edge(to, cost));
            adjList[to].add(new Edge(from, cost));
        }

        long answer = solve();

        System.out.println(answer);

    } // end main

    public static long solve() {
        long left = 0L;
        long right = MAX_BUDGET + 1;

        while (left < right) {
            long mid = (left + right) / 2L;
            if (isPossible(mid)) {
                right = mid;
            } else {
                left = mid + 1L;
            }
        }

        if (right > BUDGET) {
            return IMPOSSIBLE;
        } else {
            return right;
        }
    }

    public static boolean isPossible(long shame) {
        boolean[] isVisited = new boolean[MAX_V + 1];
        PriorityQueue<Vertex> pq = new PriorityQueue<>();

        // 시작 정점 처리
        pq.offer(new Vertex(START, 0));

        // 다익스트라 알고리즘 수행
        while (!pq.isEmpty()) {
            // 현 위치
            Vertex now = pq.poll();

            // 방문 처리
            if (isVisited[now.vNum]) {
                continue;
            }
            isVisited[now.vNum] = true;

            // 종료 조건
            if (now.vNum == END) {
                return true;
            }

            // 연결된 간선 탐색
            for (Edge e : adjList[now.vNum]) {
                int nextVNum = e.to;
                long nextTotalCost = now.totalCost + e.cost;

                // shame 이하의 수치심으로 방문 가능한 곳만 처리
                if (!isVisited[nextVNum] && nextTotalCost <= BUDGET && e.cost <= shame) {
                    pq.offer(new Vertex(nextVNum, nextTotalCost));
                }
            }
        }

        return false;
    }

}