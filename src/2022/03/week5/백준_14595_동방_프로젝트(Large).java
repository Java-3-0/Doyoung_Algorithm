// 29024KB, 228ms

package bj14595;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000000;
	static final int MAX_M = 5000;

	static int N, M;

	static class DisjointSet {
		int[] parent = new int[MAX_N + 1];

		/** 크기를 입력받아서 상호 배타적 집합을 생성하고, 모든 원소의 루트를 -1로 초기화하는 생성자 */
		public DisjointSet(int size) {
			parent = new int[size + 1];
			Arrays.fill(parent, -1);
		}

		/** 파인드 연산 수행 */
		int find(int a) {
			int pa = parent[a];
			if (pa < 0) {
				return a;
			}

			return parent[a] = find(pa);
		}

		/** 유니온 연산 수행 */
		boolean union(int a, int b) {
			int rootA = find(a);
			int rootB = find(b);

			if (rootA == rootB) {
				return false;
			}

			// 루트의 번호가 작은 쪽이 새로운 루트가 되도록 붙인다.
			else if (rootA < rootB) {
				parent[rootA] += parent[rootB];
				parent[rootB] = rootA;
				return true;
			}

			else {
				parent[rootB] += parent[rootA];
				parent[rootA] = rootB;
				return true;
			}
		}

		/** a번과 연결되어서 하나가 된 방의 크기를 리턴 */
		int getSize(int a) {
			int rootA = find(a);

			return -parent[rootA];
		}

		/** 존재하는 방의 개수를 리턴 */
		int getRoomCount() {
			int ret = 0;

			for (int i = 1; i < parent.length; i++) {
				if (parent[i] < 0) {
					ret++;
				}
			}

			return ret;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, M 입력
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());

		// N의 크기를 가진 DisjointSet 생성
		DisjointSet ds = new DisjointSet(N);

		// M개의 쿼리 수행
		for (int i = 0; i < M; i++) {
			// from, to 입력
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());

			// from + 1에서 to 사이의 모든 방을 from과 유니온해야 하는데, 이미 합쳐진 방을 또 union할 필요가 없으니 커서를 한 번에 옮긴다.
			for (int cursor = to; cursor > from;) {
				int nextCursor = ds.find(cursor) - 1;
				ds.union(from, cursor);
				cursor = nextCursor;
			}
		}
		
		// 남은 방 개수 계산
		int answer = ds.getRoomCount();
		
		// 출력
		System.out.println(answer);

	} // end main

}