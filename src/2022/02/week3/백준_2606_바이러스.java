// 11620KB, 76ms

package bj2606;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static int V;
	public static int E;

	public static boolean[][] connected;
	public static boolean[] isVisited;
	public static int countInfested;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		// 그래프 vertex 수, edge 수 입력
		V = Integer.parseInt(br.readLine());
		E = Integer.parseInt(br.readLine());

		// 그래프 관련 메모리 할당, 초기화
		connected = new boolean[V + 1][V + 1];
		isVisited = new boolean[V + 1];
		for (int i = 0; i < connected.length; i++) {
			Arrays.fill(connected[i], false);
		}
		Arrays.fill(isVisited, false);

		// 그래프 연결 관계 입력
		for (int i = 0; i < E; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			int com1 = Integer.parseInt(st.nextToken());
			int com2 = Integer.parseInt(st.nextToken());

			connected[com1][com2] = true;
			connected[com2][com1] = true;
		}
		br.close();

		// dfs로 감염 수 계산
		countInfested = 0;
		dfs(1);

		// 감염 수 출력 (1번 컴퓨터는 제외)
		System.out.println(countInfested - 1);
	}

	/** here에서부터 dfs를 통해 탐색하면서 감염자 수를 갱신 */
	public static void dfs(int here) {
		// 이미 방문했던 곳이면 종료
		if (isVisited[here]) {
			return;
		}

		// 방문
		isVisited[here] = true;

		// 감염 수 갱신
		countInfested++;

		// 연결된 곳으로 재귀 호출
		for (int there = 1; there <= V; there++) {
			if (connected[here][there]) {
				dfs(there);
			}
		}
	}
}