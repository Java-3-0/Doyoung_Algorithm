// 11652KB, 80ms

package bj1389;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
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

		// 정점 개수, 간선 개수 입력
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		// adjMatrix를 무한대로 초기화
		adjMatrix = new int[V + 1][V + 1];
		for (int i = 0; i < adjMatrix.length; i++) {
			Arrays.fill(adjMatrix[i], INF);
		}

		// 출발 정점과 도착 정점이 같으면 weight는 0
		for (int i = 1; i <= V; i++) {
			adjMatrix[i][i] = 0;
		}

		// adjMatrix 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());

			adjMatrix[from][to] = 1;
			adjMatrix[to][from] = 1;
		}

		// 모든 정점 간의 최단 경로 길이 계산
		int[][] distances = floydWarshall();

		// 각 사람마다의 케빈 베이컨 수 계산
		int[] kevinBacon = new int[V + 1];
		for (int from = 1; from <= V; from++) {
			for (int to = 1; to <= V; to++) {
				// 모든 사람은 친구 관계로 연결되어 있다 하였으므로, INF를 따로 처리하지 않아도 된다.
				kevinBacon[from] += distances[from][to];
			}
		}
		
		// 케빈 베이컨 수가 가장 작은 사람 계산
		int min = INF;
		int minIdx = 0;
		for (int person = V; person >= 1; person--) {
			if (kevinBacon[person] <= min) {
				min = kevinBacon[person];
				minIdx = person;
			}
		}
		
		// 출력
		System.out.print(minIdx);

	} // end main

	/** 플로이드-와샬 알고리즘으로 모든 정점 쌍 간의 최단 경로 길이를 구해서 2차원 배열 형태로 리턴 */
	public static int[][] floydWarshall() {
		// adjMatrix 내용대로 distances를 초기화
		int[][] distances = new int[V + 1][V + 1];
		for (int y = 1; y <= V; y++) {
			for (int x = 1; x <= V; x++) {
				distances[y][x] = adjMatrix[y][x];
			}
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