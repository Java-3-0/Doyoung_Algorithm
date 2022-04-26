// 64164KB, 668ms

package bj10891;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = (int) 1e5;
	static final int MAX_E = (int) 1e5;

	static int V, E;
	static List<Integer>[] adjList = new ArrayList[MAX_V + 1];
	static int[] discovered = new int[MAX_V + 1];
	static int[] comeBackCnt = new int[MAX_V + 1];
	static int counter = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		// discovered 배열을 -1로 초기화
		Arrays.fill(discovered, -1);

		// 정점 수, 간선 수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// 간선 정보 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			adjList[A].add(B);
			adjList[B].add(A);
		}

		// 선인장 여부 파악해서 결과 출력
		if (isCactus()) {
			System.out.println("Cactus");
		} else {
			System.out.println("Not cactus");
		}

	} // end main

	/** adjList가 나타내는 그래프가 선인장인지 여부를 리턴 */
	public static boolean isCactus() {
		// dfs 수행하면서 사이클 개수를 센다
		for (int v = 1; v <= V; v++) {
			if (discovered[v] == -1) {
				dfs(v, -1);
			}
		}

		// 사이클 개수가 2개 이상인 정점이 있으면 선인장이 아니다
		for (int v = 1; v <= V; v++) {
			if (comeBackCnt[v] > 1) {
				return false;
			}
		}

		// 이외의 경우 선인장이다
		return true;
	}

	/** 각 정점으로의 사이클 개수를 세고 now의 서브트리로부터 역방향 간선으로 갈 수 있는 정점들 중 가장 빨리 발견된 정점 번호를 리턴 */
	public static int dfs(int now, int parent) {
		// 현재 정점의 발견 순서를 기록
		discovered[now] = counter++;
		int ret = discovered[now];

		// now로부터 이어진 정점들을 dfs로 탐색
		for (int next : adjList[now]) {
			// parent에서 now로의 간선을 반대로 타고 오지는 않아야 한다
			if (next == parent) {
				continue;
			}

			// 새로 발견한 정점인 경우
			if (discovered[next] == -1) {
				// next이하의 서브트리에서부터 역방향으로 갈 수 있는 정점 중 가장 빨리 발견된 정점을 찾는다
				int earliest = dfs(next, now);

				// 서브트리로부터 (now 또는 now보다 빨리 발견된 정점)으로 갈 수 있다면, now로의 사이클이 존재
				if (discovered[now] >= earliest) {
					comeBackCnt[now]++;
				}

				ret = Math.min(ret, earliest);
			}

			// 이미 발견된 정점인 경우
			else {
				if (discovered[next] < discovered[now]) {
					comeBackCnt[now]++;
				}
				ret = Math.min(ret, discovered[next]);
			}
		}

		return ret;
	}

}