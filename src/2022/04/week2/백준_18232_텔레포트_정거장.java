// 120812KB, 1032ms

package bj18232;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 300000;

	static int N, M;
	static int S, E;
	static List<Integer>[] teleports = new ArrayList[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine(), " ");
		S = Integer.parseInt(st.nextToken());
		E = Integer.parseInt(st.nextToken());

		for (int i = 0; i < teleports.length; i++) {
			teleports[i] = new ArrayList<Integer>();
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int from = Integer.parseInt(st.nextToken());
			int to = Integer.parseInt(st.nextToken());
			teleports[from].add(to);
			teleports[to].add(from);
		}

		int answer = bfs();

		System.out.println(answer);

	} // end main

	public static int bfs() {
		Queue<Integer> q = new ArrayDeque<>();
		boolean[] isVisited = new boolean[MAX_N + 1];

		isVisited[S] = true;
		q.offer(S);

		int depth = 0;
		while (!q.isEmpty()) {
			int size = q.size();
			for (int i = 0; i < size; i++) {
				int now = q.poll();
				if (now == E) {
					return depth;
				}

				// x + 1 시도
				int right = now + 1;
				if (isInRange(right) && !isVisited[right]) {
					isVisited[right] = true;
					q.offer(right);
				}
				
				// x - 1 시도
				int left = now - 1;
				if (isInRange(left) && !isVisited[left]) {
					isVisited[left] = true;
					q.offer(left);
				}

				// 텔레포트 시도
				for (int tele : teleports[now]) {
					if (isInRange(tele) && !isVisited[tele]) {
						isVisited[tele] = true;
						q.offer(tele);
					}
				}
				
			}

			depth++;
		}

		return -1;
	}

	public static boolean isInRange(int x) {
		if (1 <= x && x <= N) {
			return true;
		}

		return false;
	}

}