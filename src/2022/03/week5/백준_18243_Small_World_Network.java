package bj18243;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 100;
	static final int MAX_E = MAX_V * (MAX_V - 1) / 2;
	static final int INF = 987654321;

	static int V, E;
	static int[][] adjMatrix = new int[MAX_V + 1][MAX_V + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		initAdjMatrix();
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			adjMatrix[a][b] = 1;
			adjMatrix[b][a] = 1;
		}

		int[][] dists = floydWarshall();

		String answer = isSmallWorld(dists) ? "Small World!" : "Big World!";
		
		System.out.println(answer);
		
	} // end main
	
	public static boolean isSmallWorld(int[][] dists) {
		for (int y = 1; y <= V; y++) {
			for (int x = 1; x <= V; x++) {
				if (dists[y][x] > 6) {
					return false;
				}
			}
		}
		
		return true;
	}

	public static int[][] floydWarshall() {
		int[][] dists = new int[V + 1][V + 1];
		for (int y = 1; y <= V; y++) {
			for (int x = 1; x <= V; x++) {
				dists[y][x] = adjMatrix[y][x];
			}
		}

		for (int m = 1; m <= V; m++) {
			for (int s = 1; s <= V; s++) {
				for (int e = 1; e <= V; e++) {
					if (dists[s][m] + dists[m][e] < dists[s][e]) {
						dists[s][e] = dists[s][m] + dists[m][e];
					}
				}
			}
		}

		return dists;
	}

	public static void initAdjMatrix() {
		for (int y = 1; y <= V; y++) {
			for (int x = 1; x <= V; x++) {
				if (y == x) {
					adjMatrix[y][x] = 0;
				} else {
					adjMatrix[y][x] = INF;
				}
			}
		}
	}
}