// 16624KB, 132ms

package bj2224;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int ALPHABETS = 26;
	static final int V = ALPHABETS * 2;
	static final int MAX_E = 10000;
	static final int INF = 987654321;

	static int E;
	static int[][] adjMatrix = new int[V][V];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		initAdjMatrix();

		E = Integer.parseInt(br.readLine());
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " => ");
			int p = charToInt(st.nextToken().charAt(0));
			int q = charToInt(st.nextToken().charAt(0));
			adjMatrix[p][q] = 1;
		}

		int[][] dists = floydWarshall();
		
		int cnt = 0;
		for (int y = 0; y < V; y++) {
			for (int x = 0; x < V; x++) {
				if (y != x && dists[y][x] != INF) {
					cnt++;
					sb.append(intToChar(y));
					sb.append(" => ");
					sb.append(intToChar(x));
					sb.append("\n");
				}
			}
		}
		
		System.out.println(cnt);
		System.out.print(sb.toString());
		
	} // end main

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

	public static char intToChar(int n) {
		if (n < ALPHABETS) {
			return (char) (n + 'A');
		}
		else {
			return (char) (n - ALPHABETS + 'a');
		}
	}
	
	public static int charToInt(char c) {
		if ('a' <= c && c <= 'z') {
			return c - 'a' + ALPHABETS;
		}

		else if ('A' <= c && c <= 'Z') {
			return c - 'A';
		}

		else {
			return -1;
		}
	}

	public static void initAdjMatrix() {
		for (int y = 0; y < V; y++) {
			for (int x = 0; x < V; x++) {
				if (y == x) {
					adjMatrix[y][x] = 0;
				} else {
					adjMatrix[y][x] = INF;
				}
			}
		}
	}

}