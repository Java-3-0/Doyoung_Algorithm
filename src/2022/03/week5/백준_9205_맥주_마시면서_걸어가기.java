// 13448KB, 124ms

package bj9205;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100;
	static int N;

	static class Position {
		int y;
		int x;

		public Position(int y, int x) {
			super();
			this.y = y;
			this.x = x;
		}

		int getDistanceTo(Position p) {
			int distY = Math.abs(this.y - p.y);
			int distX = Math.abs(this.x - p.x);

			return distY + distX;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int testCase = 1; testCase <= TESTS; testCase++) {
			N = Integer.parseInt(br.readLine());
			Position[] positions = new Position[N + 2];
			for (int i = 0; i < N + 2; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int x = Integer.parseInt(st.nextToken());
				int y = Integer.parseInt(st.nextToken());

				positions[i] = new Position(y, x);
			}
			
			if (canArrive(positions)) {
				sb.append("happy").append("\n");
			}
			else {
				sb.append("sad").append("\n");
			}
		}
		
		System.out.print(sb.toString());

	} // end main

	public static boolean canArrive(Position[] positions) {
		// 인접 리스트 생성
		List<Integer>[] adjList = new ArrayList[N + 2];
		for (int i = 0; i < adjList.length; i++) {
			adjList[i] = new ArrayList<Integer>();
		}
		
		for (int from = 0; from < N + 2; from++) {
			for (int to = from + 1; to < N + 2; to++) {
				if (positions[from].getDistanceTo(positions[to]) <= 1000) {
					adjList[from].add(to);
					adjList[to].add(from);
				}
			}
		}
		
		// bfs를 통해 방문 여부 파악
		boolean[] isVisited = new boolean[N + 2];
		Queue<Integer> q = new LinkedList<>();
		
		isVisited[0] = true;
		q.offer(0);
		
		while(!q.isEmpty()) {
			int here = q.poll();
			
			for (int there : adjList[here]) {
				if (!isVisited[there]) {
					isVisited[there] = true;
					q.offer(there);
				}
			}
		}
		
		return isVisited[N + 1];
	}
	
}