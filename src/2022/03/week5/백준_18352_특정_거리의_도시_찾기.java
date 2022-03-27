// 349356KB, 1584ms

package bj18352;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_V = 300000;
	static final int MAX_E = 1000000;
	static final int MAX_K = 300000;
	static final int INF = 987654321;
	
	static int V, E, K, X;
	static List<Integer>[] adjList = new ArrayList[MAX_V + 1];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}
		
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());

		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			adjList[from].add(to);
		}
		
		int[] minDists = bfs(X);
		
		for (int i = 1; i <= V; i++) {
			if (minDists[i] == K) {
				sb.append(i).append("\n");
			}
		}
		
		if (sb.length() == 0) {
			sb.append(-1).append("\n");
		}
		
		System.out.print(sb.toString());
		
	} // end main
	
	public static int[] bfs(int start) {
		int[] ret = new int[V + 1];
		Arrays.fill(ret, INF);
		
		boolean[] isVisited = new boolean[V + 1];
		Queue<Integer> q = new LinkedList<>();
		
		isVisited[start] = true;
		q.offer(start);
		
		int depth = 0;
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				int here = q.poll();
				ret[here] = depth;
				
				for (int there : adjList[here]) {
					if (!isVisited[there]) {
						isVisited[there] = true;
						q.offer(there);
					}
				}
			}
			depth++;
		}
		
		return ret;
	}
	
}