// 50000KB, 512ms

package bj10451;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_V = 1000;
	
	static int V;
	static int[] connectedTo = new int[MAX_V + 1];
	static boolean[] isVisited = new boolean[MAX_V + 1];
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// 메모리 초기화
			initMemory();
			
			// 정점 수 입력
			V = Integer.parseInt(br.readLine());

			// 간선 정보 입력
			st = new StringTokenizer(br.readLine(), " ");
			for (int from = 1; from <= V; from++) {
				int to = Integer.parseInt(st.nextToken());
				connectedTo[from] = to;
			}
			
			// dfs로 그룹 개수를 찾는다
			int answer = 0;
			for (int v = 1; v <= V; v++) {
				if (!isVisited[v]) {
					dfs(v);
					answer++;
				}
			}
			
			sb.append(answer).append("\n");
		}
		
		// 출력
		System.out.print(sb.toString());

	} // end main
	
	/** now로부터 방문 가능한 정점들을 dfs로 탐색한다 */
	private static void dfs(int now) {
		isVisited[now] = true;
		
		int next = connectedTo[now];
		
		if (!isVisited[next]) {
			dfs(next);
		}
	}

	/** 전역변수 메모리 초기화 */
	public static void initMemory() {
		Arrays.fill(connectedTo, 0);
		Arrays.fill(isVisited, false);
	}
}