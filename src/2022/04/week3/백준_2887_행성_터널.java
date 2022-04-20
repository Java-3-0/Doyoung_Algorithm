// 79160KB, 1840ms

package bj2887;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 100000;

	static int V;
	static Planet[] planets;
	static List<Edge> edgeList = new ArrayList<>();

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
			if (this.weight < e.weight) {
				return -1;
			} else if (this.weight == e.weight) {
				return 0;
			} else {
				return 1;
			}
		}

		@Override
		public String toString() {
			return "Edge [from=" + from + ", to=" + to + ", weight=" + weight + "]";
		}

	}

	static class Planet {
		int idx;
		int x;
		int y;
		int z;

		public Planet(int idx, int x, int y, int z) {
			super();
			this.idx = idx;
			this.x = x;
			this.y = y;
			this.z = z;
		}

		public int getDistanceTo(Planet p) {
			int dx = Math.abs(this.x - p.x);
			int dy = Math.abs(this.y - p.y);
			int dz = Math.abs(this.z - p.z);

			int ret = Math.min(dx, dy);
			ret = Math.min(ret, dz);

			return ret;
		}

		@Override
		public String toString() {
			return "Planet [idx=" + idx + ", x=" + x + ", y=" + y + ", z=" + z + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		V = Integer.parseInt(br.readLine());

		planets = new Planet[V];
		for (int idx = 0; idx < V; idx++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int z = Integer.parseInt(st.nextToken());
			planets[idx] = new Planet(idx, x, y, z);
		}

		// x좌표 순으로 정렬한 후 간선 담기
		Arrays.sort(planets, (p1, p2) -> (p1.x - p2.x));
		for (int i = 1; i < V; i++) {
			int weight = planets[i].x - planets[i - 1].x;
			edgeList.add(new Edge(planets[i - 1].idx, planets[i].idx, weight));
		}

		// y좌표 순으로 정렬한 후 간선 담기
		Arrays.sort(planets, (p1, p2) -> (p1.y - p2.y));
		for (int i = 1; i < V; i++) {
			int weight = planets[i].y - planets[i - 1].y;
			edgeList.add(new Edge(planets[i - 1].idx, planets[i].idx, weight));
		}

		// z좌표 순으로 정렬한 후 간선 담기
		Arrays.sort(planets, (p1, p2) -> (p1.z - p2.z));
		for (int i = 1; i < V; i++) {
			int weight = planets[i].z - planets[i - 1].z;
			edgeList.add(new Edge(planets[i - 1].idx, planets[i].idx, weight));
		}

		// 간선 가중치 오름차순 정렬
		Collections.sort(edgeList);

		// 크루스칼 알고리즘 수행
		DisjointSet ds = new DisjointSet();
		long answer = 0L;
		for (Edge e : edgeList) {
			if (ds.union(e.from, e.to)) {
				answer += (long) e.weight;
			}
		}

		// 정답 출력
		System.out.println(answer);

	} // end main

}