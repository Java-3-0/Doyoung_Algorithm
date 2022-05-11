package bj15900;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 500000;

	static int V;
	static List<Integer>[] adjList = new ArrayList[MAX_V + 1];
	static boolean[] isVisited = new boolean[MAX_V + 1];
	static int[] depths = new int[MAX_V + 1];
	static boolean[] isLeaf = new boolean[MAX_V + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		V = Integer.parseInt(br.readLine());

		for (int i = 0; i < V - 1; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			adjList[from].add(to);
			adjList[to].add(from);
		}

		dfs(1, 0);
		
		int sum = 0;
		for (int v = 1; v <= V; v++) {
			if (isLeaf[v]) {
				sum += depths[v];
			}
		}

		if (sum % 2 == 0) {
			System.out.println("No");
		}
		else {
			System.out.println("Yes");
		}
		
	} // end main

	public static void dfs(int here, int depth) {
		isVisited[here] = true;
		depths[here] = depth;

		boolean flag = false;
		for (int next : adjList[here]) {
			if (!isVisited[next]) {
				dfs(next, depth + 1);
				flag = true;
			}
		}
		
		if (!flag) {
			isLeaf[here] = true;
		}
	}

}