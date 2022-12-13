import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class KruskalTemplate {
	static final int MAX_V = 10000;
	static final int MAX_E = 100000;
	static final int FAIL = -1;

	static int V, E;

	/** 분리 집합 객체 */
	static class DisjointSet {
		int[] parent;

		/** 원소의 개수를 입력받아서, parent[] 배열을 할당하고, 모든 원소의 parent를 -1로 초기화하는 생성자 */
		public DisjointSet(int size) {
			super();
			parent = new int[size + 1];
			Arrays.fill(parent, -1);
		}

		/** 파인드 연산 수행 */
		public int find(int a) {
			int pa = parent[a];
			if (pa < 0) {
				return a;
			}

			return parent[a] = find(pa);
		}

		/** 유니온 연산 수행 */
		public boolean union(int a, int b) {
			int aRoot = find(a);
			int bRoot = find(b);

			if (aRoot == bRoot) {
				return false;
			}

			parent[aRoot] += parent[bRoot];
			parent[bRoot] = aRoot;
			return true;
		}

		/** a가 속한 집합의 크기를 리턴 */
		public int getSize(int a) {
			return -parent[find(a)];
		}

		/** DisjointSet 내에 존재하는 집합의 개수를 리턴 */
		public int getNumberOfSets() {
			int ret = 0;

			for (int i = 1; i < parent.length; i++) {
				if (parent[i] < 0) {
					ret++;
				}
			}

			return ret;
		}
	}

	/** 간선 객체 */
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
		StringBuilder sb = new StringBuilder();

		// 정점 개수, 간선 개수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 각 간선 정보를 입력받아서 배열에 저장
		Edge[] edges = new Edge[E];
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			edges[i] = new Edge(from, to, weight);
		}

		// mst길이를 크루스칼 알고리즘으로 계산
		int mst = Kruskal(edges);

		// 출력
		int answer = mst;
		System.out.println(answer);

	} // end main

	/** 크루스칼 알고리즘으로 MST 길이를 구해서 리턴 */
	public static int Kruskal(Edge[] edges) {
		// 간선 가중치 오름차순으로 정렬
		Arrays.sort(edges);

		// 상호 배타적 집합 생성
		DisjointSet ds = new DisjointSet(V);

		// 가중치 낮은 간선부터, 아직 유니온되지 않았다면 유니온
		int ret = 0;
		int countEdges = 0;
		for (Edge edge : edges) {
			if (ds.union(edge.from, edge.to)) {
				ret += edge.weight;
				countEdges++;
				if (countEdges == V - 1) {
					return ret;
				}
			}
		}

		return FAIL;
	}
}