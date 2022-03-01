package bj1647;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
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
		int from;
		int to;
		int weight;

		public Edge(int from, int to, int weight) {
			super();
			this.from = from;
			this.to = to;
			this.weight = weight;
		}

		@Override
		public int compareTo(Edge e) {
			return this.weight - e.weight;
		}
	}

	static final int MAX_V = 100000;
	static final int MAX_E = 1000000;

	static int V, E;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 정점 개수, 간선 개수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 간선 입력
		Edge[] edges = new Edge[E];
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			edges[i] = new Edge(from, to, weight);
		}

		int answer = kruskal(edges);

		System.out.println(answer);

	} // end main

	public static int kruskal(Edge[] edges) {
		// 간선 길이 오름차순으로 정렬
		Arrays.sort(edges);

		// 크루스칼 알고리즘 수행
		DisjointSet ds = new DisjointSet();
		List<Integer> weights = new ArrayList<>();
		int cnt = 0;
		for (Edge e : edges) {
			if (ds.union(e.from, e.to)) {
				weights.add(e.weight);

				if (cnt == V - 1) {
					break;
				}
			}
		}

		// 선택된 간선들을 오름차순으로 정렬
		Collections.sort(weights);

		// 가장 긴 간선을 끊고, 나머지 간선들 길이는 더함
		int sum = 0;
		for (int i = 0; i < weights.size() - 1; i++) {
			sum += weights.get(i);
		}

		return sum;
	}
}