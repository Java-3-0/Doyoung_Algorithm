// 163580KB, 980ms

package bj11400;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = (int) 1e5;
	static final int MAX_E = (int) 1e6;

	static int V, E;
	static List<Integer>[] adjList = new ArrayList[MAX_V + 1];
	static int[] discovered = new int[MAX_V + 1];
	static List<Edge> cutEdges = new ArrayList<>();
	static int counter = 0;

	static class Edge implements Comparable<Edge> {
		int from;
		int to;

		public Edge(int from, int to) {
			super();
			this.from = from;
			this.to = to;
		}

		@Override
		public int compareTo(Edge e) {
			if (this.from == e.from) {
				return this.to - e.to;
			}

			return this.from - e.from;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// discovered 배열을 -1로 초기화
		Arrays.fill(discovered, -1);

		// 정점 수, 간선 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 간선 정보 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			adjList[A].add(B);
			adjList[B].add(A);
		}

		// 단절선 여부 파악
		for (int v = 1; v <= V; v++) {
			if (discovered[v] == -1) {
				dfs(v, -1);
			}
		}

		// 단절선들을 정렬
		Collections.sort(cutEdges);

		// 정답을 형식에 맞게 출력용 스트링빌더에 추가
		sb.append(cutEdges.size()).append("\n");
		for (Edge e : cutEdges) {
			sb.append(e.from).append(" ").append(e.to).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/**
	 * parent에서 now로의 간선이 절단선인지 판정하여 cutEdges 리스트에 넣고, now의 서브트리로부터 역방향 간선으로 갈 수 있는
	 * 정점들 중 가장 빨리 발견된 정점 번호를 리턴
	 */
	public static int dfs(int now, int parent) {
		// 현재 정점의 발견 순서를 기록
		discovered[now] = counter++;
		int ret = discovered[now];

		// now로부터 이어진 정점들을 dfs로 탐색
		for (int next : adjList[now]) {
			// parent에서 now로의 간선을 반대로 타고 오지는 않아야 한다
			if (next == parent) {
				continue;
			}

			// 새로 발견한 정점인 경우
			if (discovered[next] == -1) {
				// next이하의 서브트리에서부터 역방향으로 갈 수 있는 정점 중 가장 빨리 발견된 정점을 찾는다
				int earliest = dfs(next, now);

				// now가 이 정점보다 빨리 발견되었다면, now에서 next로의 간선은 절단선이다
				if (discovered[now] < earliest) {
					if (next < now) {
						cutEdges.add(new Edge(next, now));
					} else {
						cutEdges.add(new Edge(now, next));
					}
				}

				ret = Math.min(ret, earliest);
			}

			// 이미 발견된 정점인 경우
			else {
				ret = Math.min(ret, discovered[next]);
			}
		}

		return ret;
	}

}