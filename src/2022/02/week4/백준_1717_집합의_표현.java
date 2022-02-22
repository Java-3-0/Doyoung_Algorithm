// 48580KB, 376ms

package bj1717;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000000;
	static final int MAX_M = 100000;

	static class DisjointSet {
		int[] parent = new int[MAX_N + 1];

		/** 모든 원소의 대표자를 자기 자신으로 초기화하는 생성자 */
		public DisjointSet() {
			super();
			Arrays.fill(parent, -1);
		}

		/** a의 대표자를 찾는다 */
		public int find(int a) {
			// 자기 자신이 대표자인 경우 그대로 리턴한다.
			if (parent[a] == -1) {
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

	public static DisjointSet djs = new DisjointSet();
	public static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		// 각 커맨드 명령 입력받아서 처리
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int commandType = Integer.parseInt(st.nextToken());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			handleCommand(commandType, a, b);
		}

		// 결과 출력
		System.out.print(sb.toString());
	}

	/** 하나의 커맨드 명령을 처리하는 함수 */
	public static void handleCommand(int commandType, int a, int b) {
		switch (commandType) {
		case 0:
			djs.union(a, b);
			break;
		case 1:
			if (djs.find(a) == djs.find(b)) {
				sb.append("YES\n");
			} else {
				sb.append("NO\n");
			}
			break;
		default:
			break;
		}
	}
}