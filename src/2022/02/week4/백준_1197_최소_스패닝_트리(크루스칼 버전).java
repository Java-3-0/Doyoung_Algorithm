// 48028Kb, 524ms

package bj1197;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static class Edge {
		int to;
		int weight;
		
		public Edge(int to, int weight) {
			super();
			this.to = to;
			this.weight = weight;
		}
	}
	
	// 메인 함수 시작
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		
		// 정점 개수, 간선 개수 입력
		st = new StringTokenizer(br.readLine(), " ");
		int V = Integer.parseInt(st.nextToken());
		int E = Integer.parseInt(st.nextToken());
		
		// 그래프 인접 리스트 표현
		ArrayList<Edge>[] adjList = new ArrayList[V];
		// 타 정점에서 자신으로의 간선 비용 중 최소 비용을 저장할 배열
		int[] minEdges = new int[V];
		// 신장트리에 포함되었는지 여부를 나타낼 배열
		boolean[] isVisited = new boolean[V];
		
		// 배열 초기화
		for (int i = 0; i < V; i++) {
			adjList[i] = new ArrayList<Edge>();
		}
		Arrays.fill(minEdges, Integer.MAX_VALUE);
		Arrays.fill(isVisited, false);
		
		// 각 간선 정보를 입력받아서 배열에 저장
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken()) - 1;
			int to = Integer.parseInt(st.nextToken()) - 1;
			int weight = Integer.parseInt(st.nextToken());
			adjList[from].add(new Edge(to, weight));
			adjList[to].add(new Edge(from, weight));
		}
		
		// 0번 정점에서 출발
		int result = 0;
		minEdges[0] = 0;

		// N개의 정점이 모두 연결될 때까지 반복
		for (int i = 0; i < V; i++) {
			int min = Integer.MAX_VALUE;
			int minVertex = 0;
			
			for (int v = 0; v < V; v++) {
				if (!isVisited[v] && minEdges[v] < min) {
					min = minEdges[v];
					minVertex = v;
				}
			} // end for v

			// 방문 여부 갱신하고, 간선 길이만큼 정답에 더함
			isVisited[minVertex] = true;
			result += min;

			// minEdges 배열 갱신
			for (Edge e : adjList[minVertex]) {
				if (!isVisited[e.to]) {
					minEdges[e.to] = Math.min(minEdges[e.to], e.weight);
				}
			}
			
		} // end for i
		
		System.out.println(result);

	} // end main

}