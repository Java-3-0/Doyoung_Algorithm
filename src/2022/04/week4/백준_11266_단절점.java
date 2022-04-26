// 45512KB, 420ms

package bj11266;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = (int) 1e4;
	static final int MAX_E = (int) 1e5;
    
	static int V, E;
	static List<Integer>[] adjList = new ArrayList[MAX_V + 1];
	static int[] discovered = new int[MAX_V + 1];
	static boolean[] isCutVertex = new boolean[MAX_V + 1];
	static int counter = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

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

		// 단절점 여부 파악
		for (int v = 1; v <= V; v++) {
			if (discovered[v] == -1) {
				findEarliest(v, true);
			}
		}

		// 단절점들을 리스트에 담는다
		List<Integer> cutPoints = new ArrayList<>();
		for (int v = 1; v <= V; v++) {
			if (isCutVertex[v]) {
				cutPoints.add(v);
			}
		}

		// 정답을 형식에 맞게 출력용 스트링빌더에 추가
		sb.append(cutPoints.size()).append("\n");
		for (int point : cutPoints) {
			sb.append(point).append(" ");
		}
		sb.append("\n");

		// 출력
		System.out.print(sb.toString());

	} // end main

	/**
	 * now가 절단점인지 판정하여 isCutVertex 배열을 갱신하고, now의 서브트리로부터 역방향 간선으로 갈 수 있는 정점들 중 가장
	 * 빨리 발견된 정점 번호를 리턴
	 */
	public static int findEarliest(int now, boolean isRoot) {
		// 현재 정점의 발견 순서를 기록
		discovered[now] = counter++;
		int ret = discovered[now];

		int children = 0;

		// now로부터 이어진 정점들을 dfs로 탐색
		for (int next : adjList[now]) {
			// 새로 발견한 정점인 경우
			if (discovered[next] == -1) {
				children++;

				// next이하의 서브트리에서부터 역방향으로 갈 수 있는 정점 중 가장 빨리 발견된 정점을 찾는다
				int earliest = findEarliest(next, false);

				// now가 이 정점보다 빨리 발견되었다면, now는 절단점이다
				if (!isRoot && discovered[now] <= earliest) {
					isCutVertex[now] = true;
				}

				ret = Math.min(ret, earliest);
			}

			// 이미 발견된 정점인 경우
			else {
				ret = Math.min(ret, discovered[next]);
			}
		}

		// 루트인 경우, 자녀가 둘 이상이어야지만 절단점
		if (isRoot && 2 <= children) {
			isCutVertex[now] = true;
		}

		return ret;
	}

}