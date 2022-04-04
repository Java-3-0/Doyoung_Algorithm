// 45484KB, 412ms

package bj11780;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 100;
	static final int MAX_E = 100000;
	static final int INF = 987654321;

	static int V, E;
	static int[][] adjMatrix;
	static int[][] distances;
	static int[][] nextToVisit;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 정점 개수, 간선 개수 입력
		V = Integer.parseInt(br.readLine());
		E = Integer.parseInt(br.readLine());

		// 메모리 할당
		adjMatrix = new int[V + 1][V + 1];
		nextToVisit = new int[V + 1][V + 1];
		distances = new int[V + 1][V + 1];

		// adjMatrix를 무한대로 초기화
		for (int i = 0; i < adjMatrix.length; i++) {
			Arrays.fill(adjMatrix[i], INF);
		}

		// 출발 정점과 도착 정점이 같으면 weight는 0
		for (int i = 1; i <= V; i++) {
			adjMatrix[i][i] = 0;
		}

		// 버스 정보를 입력받아서 adjMatrix 업데이트
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			// 기존 edge보다 weight 더 작은 edge가 들어오면 갱신
			if (weight < adjMatrix[from][to]) {
				adjMatrix[from][to] = weight;
			}
		}

		// 모든 정점 간의 최단 경로 길이 계산
		floydWarshall();

		// 최소 비용을 형식에 맞게 출력 스트링빌더에 추가
		for (int from = 1; from <= V; from++) {
			for (int to = 1; to <= V; to++) {
				int dist = distances[from][to];
				if (dist == INF) {
					sb.append(0).append(" ");
				} else {
					sb.append(dist).append(" ");
				}
			}
			sb.append("\n");
		}

		// n * n줄의 경로를 출력 스트링빌더에 추가
		for (int y = 1; y <= V; y++) {
			for (int x = 1; x <= V; x++) {
				if (distances[y][x] == 0 || distances[y][x] == INF) {
					sb.append("0").append("\n");
				}

				else {
					List<Integer> list = new ArrayList<>();

					int from = y, to = x;
					list.add(from);
					while (from != to) {
						int next = nextToVisit[from][to];
						list.add(next);
						from = next;
					}

					sb.append(list.size()).append(" ");
					for (int num : list) {
						sb.append(num).append(" ");
					}
					sb.append("\n");
				}
			}
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 플로이드-와샬 알고리즘으로 모든 정점 쌍 간의 최단 경로 길이를 구해서 2차원 배열 형태로 리턴 */
	public static void floydWarshall() {
		// adjMatrix 내용대로 distances를 초기화
		for (int y = 1; y <= V; y++) {
			for (int x = 1; x <= V; x++) {
				distances[y][x] = adjMatrix[y][x];
				nextToVisit[y][x] = x;
			}
		}

		// 플로이드 와샬 알고리즘 수행
		for (int m = 1; m <= V; m++) {
			for (int from = 1; from <= V; from++) {
				for (int to = 1; to <= V; to++) {
					if (distances[from][m] + distances[m][to] < distances[from][to]) {
						distances[from][to] = distances[from][m] + distances[m][to];
						nextToVisit[from][to] = nextToVisit[from][m];
					}
				}
			}
		}

		return;
	}
}