// 11592KB, 80ms

package bj21316;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static final int V = 12;
	static final int E = 12;

	static List<Integer>[] adjList = new ArrayList[V + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			adjList[x].add(y);
			adjList[y].add(x);
		}

		int answer = 0;
		for (int v = 1; v <= V; v++) {
			if (adjList[v].size() == 3) {
				Set<Integer> set = new HashSet<>();
				for (int k : adjList[v]) {
					set.add(adjList[k].size());
				}
				if (set.contains(1) && set.contains(2) && set.contains(3)) {
					answer = v;
				}
			}
		}

		System.out.println(answer);

	} // end main

}