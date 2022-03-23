// 24156KB, 244ms

package bj1368;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 300;
	static final int MAX_W = 100000;
	static final int MAX_P = 100000;

	static int N;
	static int[] dig = new int[MAX_N];
	static int[][] adjMatrix = new int[MAX_N][MAX_N];

	static class Vertex implements Comparable<Vertex> {
		int vNum;
		int weight;

		public Vertex(int vNum, int weight) {
			super();
			this.vNum = vNum;
			this.weight = weight;
		}

		@Override
		public int compareTo(Vertex v) {
			return this.weight - v.weight;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 정점 수 입력
		N = Integer.parseInt(br.readLine());

		// 각 정점에서 땅을 파는 데 드는 비용 저장
		for (int i = 0; i < N; i++) {
			dig[i] = Integer.parseInt(br.readLine());
		}

		// 간선 정보 입력받아서 adjMatrix에 저장
		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < N; x++) {
				adjMatrix[y][x] = Integer.parseInt(st.nextToken());
			}
		}
		
		// prim 알고리즘으로 mst 길이 계산
		int answer = prim();
		
		// 출력
		System.out.println(answer);

	} // end main

	/** 프림 알고리즘으로 mst의 길이를 계산해서 리턴 */
	public static int prim() {
		int ret = 0;
		boolean[] isVisited = new boolean[N];
		PriorityQueue<Vertex> pq = new PriorityQueue<>();

		// 땅을 파는 비용을 모두 pq에 넣는다.
		for (int i = 0; i < N; i++) {
			pq.offer(new Vertex(i, dig[i]));
		}

		int cnt = 0;
		while (!pq.isEmpty()) {
			// 최소 비용인 간선을 pq에서 뽑아온다.
			Vertex here = pq.poll();

			// 이미 방문한 정점으로 가는 것이라면 무시한다.
			if (isVisited[here.vNum]) {
				continue;
			}

			// 새로 방문하는 정점으로 가는 것이라면 이 정점을 방문한다.
			isVisited[here.vNum] = true;
			ret += here.weight;
			cnt++;
			if (cnt == N) {
				return ret;
			}
			
			// 이 정점으로부터 연결된 간선을 모두 pq에 넣는다.
			for (int to = 0; to < N; to++) {
				if (!isVisited[to]) {
					pq.offer(new Vertex(to, adjMatrix[here.vNum][to]));
				}
			}
		}

		// 간선 연결 전혀 안 하고 땅만 300번 파도 성공이니까 여기에 도달할 일은 없다.
		return -1;
	}

}