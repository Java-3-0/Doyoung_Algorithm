// 114344KB, 2088ms

package bj1753;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int INF = 987654321;
	static final int MAX_V = 20000, MAX_E = 300000;
	
	static int V, E;
	static List<Edge> adjList[];
	
	static class Edge {
		int to;
		int weight;
		public Edge(int to, int weight) {
			super();
			this.to = to;
			this.weight = weight;
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine(), " ");
		V = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());
		adjList = new ArrayList[V + 1];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Edge>();
		}
		
		int start = Integer.parseInt(br.readLine());
		
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			int weight = Integer.parseInt(st.nextToken());
			
			adjList[from].add(new Edge(to, weight));
		}
		
		int[] distances = dijkstra(start);

		for (int i = 1; i <= V; i++) {
			int dist = distances[i];
			if (dist >= INF) {
				sb.append("INF").append("\n");
			}
			else {
				sb.append(dist).append("\n");
			}
			
		}
		System.out.print(sb.toString());
		
	} // end main

	public static int[] dijkstra(int start) {
		boolean[] isSelected = new boolean[V + 1];
		int[] distances = new int[V + 1];
		
		// 모든 정점까지의 최단 거리를 INF로 초기화
		Arrays.fill(distances, INF);
		distances[start] = 0;
		
		// 시작 정점을 선택하고, 모든 정점까지의 최단 거리를 시작 정점에서 바로 오는 거리로 초기화
		for (Edge e : adjList[start]) {
			distances[e.to] = e.weight;
		}
		
		
		for (int i = 1; i <= V; i++) {
			// 가장 가까운 정점을 선택
			int minDist = Integer.MAX_VALUE;
			int closestVertexIdx = 0;
			
			for (int v = 1; v <= V; v++) {
				int dist = distances[v];
				if (!isSelected[v] && dist < minDist) {
					minDist = dist;
					closestVertexIdx = v;
				}
			}
			
			isSelected[closestVertexIdx] = true;
			
			// 그 정점과 인접한 다른 정점까지의 distance 갱신
			for (Edge e : adjList[closestVertexIdx]) {
				distances[e.to] = Math.min(distances[e.to], minDist + e.weight);
			}
		}
		
		return distances;
	}
}