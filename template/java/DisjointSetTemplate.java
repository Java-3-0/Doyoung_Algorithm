import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class DisjointSetTemplate {
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
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int TESTS = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= TESTS; tc++) {

		}

		st = new StringTokenizer(br.readLine(), " ");
		Integer.parseInt(st.nextToken());
		
		int answer = 0;
		System.out.println(answer);
		System.out.print(sb.toString());

	} // end main

}