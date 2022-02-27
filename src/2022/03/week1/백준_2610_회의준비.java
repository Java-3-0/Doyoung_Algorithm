// 12820KB, 124ms

package bj2610;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 100;
	static final int MAX_E = 100000;
	static final int INF = 987654321;

	static int V, E;
	static int[][] adjMatrix;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 정점 개수, 간선 개수 입력
		V = Integer.parseInt(br.readLine());
		E = Integer.parseInt(br.readLine());

		// adjMatrix를 무한대로 초기화
		adjMatrix = new int[V + 1][V + 1];
		for (int i = 0; i < adjMatrix.length; i++) {
			Arrays.fill(adjMatrix[i], INF);
		}

		// 간선 정보를 입력받아서 adjMatrix 업데이트
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());

			adjMatrix[from][to] = 1;
			adjMatrix[to][from] = 1;
		}

		// 모든 정점 간의 최단 경로 길이 계산
		int[][] distances = floydWarshall();

		// 위원회를 나눈다.
		boolean[] isVisited = new boolean[V + 1];
		ArrayList<ArrayList<Integer>> groups = new ArrayList<ArrayList<Integer>>();
		for (int person = 1; person <= V; person++) {
			if (!isVisited[person]) {
				ArrayList<Integer> group = new ArrayList<>();
				isVisited[person] = true;
				group.add(person);
				for (int other = 1; other <= V; other++) {
					int path = distances[person][other];
					if (0 < path && path < INF) {
						isVisited[other] = true;
						group.add(other);
					}
				}

				groups.add(group);
			}
		}

		// 각 위원회마다 대표자를 정한다.
		List<Integer> representatives = new ArrayList<>();
		for (ArrayList<Integer> group : groups) {
			int minTime = Integer.MAX_VALUE;
			int minIdx = 0;
			for (int to : group) {
				int time = 0;
				for (int from : group) {
					if (time < distances[from][to]) {
						time = distances[from][to];
					}
				}

				if (time < minTime) {
					minTime = time;
					minIdx = to;
				}
			}
			
			representatives.add(minIdx);
		}

		// 대표자 번호를 오름차순으로 정렬한다.
		Collections.sort(representatives);
		
		// 결과를 출력 스트링빌더에 추가한다.
		sb.append(representatives.size()).append("\n");
		for (int r : representatives) {
			sb.append(r).append("\n");
		}
		
		
		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 플로이드-와샬 알고리즘으로 모든 정점 쌍 간의 최단 경로 길이를 구해서 2차원 배열 형태로 리턴 */
	public static int[][] floydWarshall() {
		// adjMatrix 내용대로 distances를 초기화
		int[][] distances = new int[V + 1][V + 1];
		for (int y = 1; y <= V; y++) {
			for (int x = 1; x <= V; x++) {
				distances[y][x] = adjMatrix[y][x];
			}

			distances[y][y] = 0;
		}

		for (int m = 1; m <= V; m++) {
			for (int from = 1; from <= V; from++) {
				for (int to = 1; to <= V; to++) {
					if (distances[from][m] + distances[m][to] < distances[from][to]) {
						distances[from][to] = distances[from][m] + distances[m][to];
					}
				}
			}
		}

		return distances;
	}
}