// 11648KB, 76ms

package bj2660;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 50;
	static final int INF = 987654321;

	static int V, E;
	static int[][] adjMatrix;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 정점 개수 입력
		V = Integer.parseInt(br.readLine());

		// adjMatrix를 무한대로 초기화
		adjMatrix = new int[V + 1][V + 1];
		for (int i = 0; i < adjMatrix.length; i++) {
			Arrays.fill(adjMatrix[i], INF);
			adjMatrix[i][i] = 0;
		}

		// adjMatrix 입력
		while (true) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());

			if (from == -1 && to == -1) {
				break;
			}

			adjMatrix[from][to] = 1;
			adjMatrix[to][from] = 1;
		}

		// 모든 정점 간의 최단 경로 길이 계산
		int[][] distances = floydWarshall();

		// 점수가 가장 작은 사람들을 구해서 candidates에 넣는다
		int minScore = INF;
		List<Integer> candidates = new ArrayList<>();
		for (int from = 1; from <= V; from++) {
			int score = 0;

			for (int to = 1; to <= V; to++) {
				score = Math.max(score, distances[from][to]);
			}

			if (score < minScore) {
				minScore = score;
				candidates.clear();
				candidates.add(from);
			} else if (score == minScore) {
				candidates.add(from);
			}
		}

		// 스트링빌더에 첫째 줄 출력 내용 담기
		sb.append(minScore).append(" ").append(candidates.size()).append("\n");

		// 스트링빌더에 둘째 줄 출력 내용 담기
		for (int cand : candidates) {
			sb.append(cand).append(" ");
		}
		sb.append("\n");

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