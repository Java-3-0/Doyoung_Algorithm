// 48028Kb, 524ms

package bj1197;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_Kruskal {
	static final int MAX_V = 10000;
	static final int MAX_E = 100000;

	static class DisjointSet {
		int[] parent = new int[MAX_V + 1];

		/** 모든 원소의 대표자를 자기 자신으로 초기화하는 생성자 */
		public DisjointSet() {
			super();
			Arrays.fill(parent, -1);
		}

		/** a의 대표자를 찾는다 */
		public int find(int a) {
			// 자기 자신이 대표자인 경우 그대로 리턴한다.
			if (parent[a] < 0) {
				return a;
			}

			// path compression을 적용하면서 재귀적으로 대표자를 찾는다.
			return parent[a] = find(parent[a]);
		}

		/** a와 b를 같은 집합으로 합친다. 합치는 데 성공하면 true, 실패하면 false를 리턴 */
		public boolean union(int a, int b) {
			int aRoot = find(a);
			int bRoot = find(b);

			// 이미 같은 집합인 경우 false를 리턴하고 끝낸다.
			if (aRoot == bRoot) {
				return false;
			}

			// 이외의 경우, 대표자를 갱신하고 true를 리턴한다.
			parent[bRoot] = aRoot;
			return true;
		}
	}

	static class Edge implements Comparable<Edge> {
		/** 간선이 시작되는 정점 */
		int from;
		/** 간선이 끝나는 정점 */
		int to;
		/** 간선의 가중치 */
		int weight;

		/** 시작 정점, 끝 정점, 가중치를 받아서 간선 객체를 생성하는 생성자 */
		public Edge(int from, int to, int weight) {
			super();
			this.from = from;
			this.to = to;
			this.weight = weight;
		}

		/** 간선 가중치 오름차순으로 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Edge e) {
			return this.weight - e.weight;
		}

	}

	// 메인 함수 시작
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 정점 개수, 간선 개수 입력
		st = new StringTokenizer(br.readLine(), " ");
		int V = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());

		// 각 간선 정보를 입력받아서 배열에 저장
		Edge[] edges = new Edge[E];
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			edges[i] = new Edge(from, to, weight);
		}

		// 간선 가중치 오름차순으로 정렬
		Arrays.sort(edges);

		// 상호 배타적 집합 생성
		DisjointSet ds = new DisjointSet();

		int sumWeight = 0;
		int countEdges = 0;
		for (Edge edge : edges) {
			// 싸이클이 안 생겨서 합칠 수 있는 경우 합친다.
			if (ds.union(edge.from, edge.to)) {
				sumWeight += edge.weight;
				countEdges++;
				if (countEdges == V - 1) {
					break;
				}
			}
		}

		System.out.println(sumWeight);

	} // end main
}