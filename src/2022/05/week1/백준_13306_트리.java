// 161664KB, 1144ms

package bj13306;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 200000, MAX_Q = 200000;

	static int V, Q;
	static int[] parent = new int[MAX_V + 1];

	static class DisjointSet {
		int[] representative = new int[MAX_V + 1];

		/** 모든 원소의 대표자를 자기 자신으로 초기화하는 생성자 */
		public DisjointSet() {
			super();
			Arrays.fill(representative, -1);
		}

		/** a의 대표자를 찾는다 */
		public int find(int a) {
			// 자기 자신이 대표자인 경우 그대로 리턴한다.
			if (representative[a] == -1) {
				return a;
			}

			// path compression을 적용하면서 재귀적으로 대표자를 찾는다.
			return representative[a] = find(representative[a]);
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
			representative[bRoot] = aRoot;
			return true;
		}
	}

	static class Query {
		int queryType;
		int a;
		int b;

		public Query(int queryType, int a, int b) {
			super();
			this.queryType = queryType;
			this.a = a;
			this.b = b;
		}

		public Query(int queryType, int a) {
			super();
			this.queryType = queryType;
			this.a = a;
		}

		@Override
		public String toString() {
			return "Query [queryType=" + queryType + ", a=" + a + ", b=" + b + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 정점 수, 쿼리 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());

		// 트리의 각 노드마다 부모 정보 입력
		Arrays.fill(parent, 1);
		for (int node = 2; node <= V; node++) {
			parent[node] = Integer.parseInt(br.readLine());
		}

		// 질의들을 입력받아서 스택에 담는다.
		Stack<Query> queries = new Stack<>();
		for (int i = 0; i < V - 1 + Q; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int queryType = Integer.parseInt(st.nextToken());
			if (queryType == 0) {
				int a = Integer.parseInt(st.nextToken());
				queries.add(new Query(queryType, a));
			} else {
				int a = Integer.parseInt(st.nextToken());
				int b = Integer.parseInt(st.nextToken());
				queries.add(new Query(queryType, a, b));
			}
		}

		// DisjointSet 생성
		DisjointSet ds = new DisjointSet();

		// 스택에 있는 질의를 하나씩 처리하면서 간선을 이어가본다
		Stack<String> results = new Stack<>();
		while (!queries.isEmpty()) {
			Query q = queries.pop();
			int queryType = q.queryType;
			int a = q.a;
			int b = q.b;

			// 0번 타입 쿼리의 경우 간선 잇기
			if (queryType == 0) {
				int p = parent[a];
				ds.union(a, p);
			}

			// 1번 타입 쿼리의 경우 간선 연결 여부 확인
			else {
				if (ds.find(a) == ds.find(b)) {
					results.push("YES\n");
				} else {
					results.push("NO\n");
				}
			}
		}

		// 결과도 역순이므로 다시 역순으로 출력
		while (!results.isEmpty()) {
			sb.append(results.pop());
		}

		System.out.print(sb.toString());
	}
}