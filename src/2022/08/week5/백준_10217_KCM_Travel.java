// 76292KB, 1952ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
    static final int MAX_V = 100;
    static final int MAX_M = 10000;
    static final int MAX_E = 10000;

    static final int INF = (int) (1e9 + 1e6);

    static int V, M, E;
    static List<Edge>[] adjList = new ArrayList[MAX_V + 1];

    static int[][] minTimes = new int[MAX_V + 1][MAX_M + 1];
    static PriorityQueue<Vertex> pq = new PriorityQueue<>();

    /**
     * 간선 객체
     */
    static class Edge {
        int to;
        int moneyTicket;
        int timeTicket;

        public Edge(int to, int moneyTicket, int timeTicket) {
            this.to = to;
            this.moneyTicket = moneyTicket;
            this.timeTicket = timeTicket;
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "to=" + to +
                    ", moneyTicket=" + moneyTicket +
                    ", timeTicket=" + timeTicket +
                    '}';
        }
    }

    /**
     * 다익스트라 알고리즘 우선순위큐에 넣기 위한 정점 객체
     */
    static class Vertex implements Comparable<Vertex> {
        int vNum;
        int money;
        int time;

        public Vertex(int vNum, int money, int time) {
            this.vNum = vNum;
            this.money = money;
            this.time = time;
        }

        /**
         * 소요시간이 적은 순으로 정렬하기 위한 비교함수
         */
        @Override
        public int compareTo(Vertex v) {
            if (this.time == v.time) {
                return Integer.compare(this.money, v.money);
            }

            return Integer.compare(this.time, v.time);
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "vNum=" + vNum +
                    ", money=" + money +
                    ", time=" + time +
                    '}';
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        StringBuilder sb = new StringBuilder();

        // adjList 메모리 할당
        for (int i = 0; i < adjList.length; i++) {
            adjList[i] = new ArrayList<Edge>();
        }

        // 테스트케이스 개수 입력
        final int TESTS = Integer.parseInt(br.readLine());

        // 각 테스트케이스 수행
        for (int tc = 1; tc <= TESTS; tc++) {
            // 메모리 초기화
            initMemory();

            // 정점 수, 총 지원 비용, 간선 수 입력
            st = new StringTokenizer(br.readLine(), " ");
            V = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());

            // 간선 정보 입력
            for (int i = 0; i < E; i++) {
                st = new StringTokenizer(br.readLine(), " ");
                int from = Integer.parseInt(st.nextToken());
                int to = Integer.parseInt(st.nextToken());
                int moneyTicket = Integer.parseInt(st.nextToken());
                int timeTicket = Integer.parseInt(st.nextToken());

                adjList[from].add(new Edge(to, moneyTicket, timeTicket));
            }

            // 시작 정점, 끝 정점
            int start = 1;
            int end = V;

            // 거리 계산
            int answer = dijkstra(start, end);

            if (answer == INF) {
                sb.append("Poor KCM");
            } else {
                sb.append(answer);
            }
            sb.append("\n");
        }

        System.out.print(sb);


    } // end main

    /**
     * 테스트테이스마다 메모리 초기화
     */
    public static void initMemory() {
        for (int i = 0; i < adjList.length; i++) {
            adjList[i].clear();
        }

        for (int i = 0; i < minTimes.length; i++) {
            Arrays.fill(minTimes[i], INF);
        }
        pq.clear();
    }

    /**
     * 다익스트라 알고리즘으로 start에서부터 end까지의 최소 비용을 리턴
     */
    public static int dijkstra(int start, int end) {
        // 시작 정점 처리
        minTimes[start][0] = 0;
        pq.offer(new Vertex(start, 0, 0));

        // 다익스트라 알고리즘 수행
        while (!pq.isEmpty()) {
            Vertex now = pq.poll();

            for (Edge e : adjList[now.vNum]) {
                int nextV = e.to;
                int nextMoney = now.money + e.moneyTicket;
                int nextTime = now.time + e.timeTicket;

                // 비용이 감소하는 경우 방문
                if (nextMoney <= M && nextTime < minTimes[nextV][nextMoney]) {
                    // 해당 비용 이상으로 이 정점을 방문하는 최소 시간을 갱신
                    for (int money = nextMoney; money <= M; money++) {
                        if (nextTime < minTimes[nextV][money]) {
                            minTimes[nextV][money] = nextTime;
                        } else {
                            break;
                        }
                    }

                    // 큐에 추가
                    pq.offer(new Vertex(e.to, now.money + e.moneyTicket, now.time + e.timeTicket));
                }
            }
        }

        int ret = minTimes[V][M];

        return ret;
    }
}
