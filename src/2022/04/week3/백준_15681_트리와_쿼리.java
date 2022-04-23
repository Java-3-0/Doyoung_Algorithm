// 76384KB, 760ms

package bj15681;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = (int) 1e5;

	static int N;
	static List<Integer>[] adjList = new ArrayList[MAX_N + 1];
	static int[] subTreeSizes = new int[MAX_N + 1];
	static boolean[] isVisited = new boolean[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// adjList 메모리 할당
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		int R = Integer.parseInt(st.nextToken());
		int Q = Integer.parseInt(st.nextToken());

		for (int e = 0; e < N - 1; e++) {
			st = new StringTokenizer(br.readLine(), " ");
			int node1 = Integer.parseInt(st.nextToken());
			int node2 = Integer.parseInt(st.nextToken());
			adjList[node1].add(node2);
			adjList[node2].add(node1);
		}

		makeTree(R);

		for (int q = 0; q < Q; q++) {
			int nodeNum = Integer.parseInt(br.readLine());
			int answer = subTreeSizes[nodeNum];
			sb.append(answer).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	public static int makeTree(int start) {
		isVisited[start] = true;
		int ret = 1;

		for (int next : adjList[start]) {
			if (!isVisited[next]) {
				ret += makeTree(next);
			}
		}

		subTreeSizes[start] = ret;
		return ret;
	}

}