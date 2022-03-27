// 11852KB, 84ms

package bj1058;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static final int MAX_V = 50;
	static final int INF = 987654321;

	static int V;
	static int[][] adjMatrix = new int[MAX_V][MAX_V];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		V = Integer.parseInt(br.readLine());

		for (int y = 0; y < V; y++) {
			char[] line = br.readLine().toCharArray();
			for (int x = 0; x < V; x++) {
				adjMatrix[y][x] = line[x] == 'Y' ? 1 : INF;
			}
		}
		
		int[][] dists = floydWarshall();
		
		int answer = getMaxFriends(dists);
		
		System.out.println(answer);

	} // end main
	
	public static int getMaxFriends(int[][] dists) {
		int ret = 0;
		
		for (int y = 0; y < V; y++) {
			int cnt = 0;
			for (int x = 0; x < V; x++) {
				if (y != x && dists[y][x] <= 2) {
					cnt++;
				}
			} // end for x
		
			ret = Math.max(ret, cnt);
	
		} // end for y
		
		return ret;
	}

	public static int[][] floydWarshall() {
		int[][] dists = new int[V][V];
		for (int y = 0; y < V; y++) {
			for (int x = 0; x < V; x++) {
				dists[y][x] = adjMatrix[y][x];
			}
		}

		for (int m = 0; m < V; m++) {
			for (int s = 0; s < V; s++) {
				for (int e = 0; e < V; e++) {
					if (dists[s][m] + dists[m][e] < dists[s][e]) {
						dists[s][e] = dists[s][m] + dists[m][e];
					}
				}
			}
		}

		return dists;
	}
}