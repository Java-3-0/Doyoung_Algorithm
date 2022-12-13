import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class FloydWarshallTemplate {
	static final int MAX_V = 400;
	static final int MAX_E = MAX_V * (MAX_V - 1);
	static final int INF = 987654321;
	static final int FAIL = -1;

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

		// 간선 정보를 입력받아서 adjMatrix 업데이트
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());

			adjMatrix[from][to] = weight;
		}

		// 모든 정점 간의 최단 경로 길이 계산
		int[][] distances = floydWarshall();

		// 정답 출력
		int answer = 0;
		System.out.print(answer);

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

		for (int mid = 1; mid <= V; mid++) {
			for (int from = 1; from <= V; from++) {
				for (int to = 1; to <= V; to++) {
					if (distances[from][mid] + distances[mid][to] < distances[from][to]) {
						distances[from][to] = distances[from][mid] + distances[mid][to];
					}
				}
			}
		}

		return distances;
	}
}