// 17936KB, 108ms

package bj14226;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;

public class Main {
	static final int MAX_S = 1000;
	static final int MAX_SIZE = MAX_S * 2 + 1;
	static final int INF = 987654321;
	
	static int S;
	static int size;
	
	static class Status {
		int screen;
		int clipboard;

		public Status(int screen, int clipboard) {
			super();
			this.screen = screen;
			this.clipboard = clipboard;
		}

		public Status action1() {
			return new Status(this.screen, this.screen);
		}
		
		public Status action2() {
			return new Status(this.screen + this.clipboard, this.clipboard);
		}
		
		public Status action3() {
			return new Status(this.screen - 1, this.clipboard);
		}
		
		public boolean isInRange() {
			if (0 <= this.screen && this.screen < size && 0 <= this.clipboard && this.clipboard < size) {
				return true;
			}
			return false;
		}
		
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// S 입력
		S = Integer.parseInt(br.readLine());

		// 전역변수 설정
		size = S * 2 + 1;

		int answer = solve();
		
		System.out.println(answer);
		
	} // end main

	public static int solve() {
		boolean[][] isVisited = new boolean[size][size];
		Queue<Status> q = new LinkedList<>();
		
		
		Status start = new Status(1, 0);
		isVisited[start.screen][start.clipboard] = true;
		q.offer(start);
		
		int depth = 0;
		while (!q.isEmpty()) {
			int queueSize = q.size();
			for (int i = 0; i < queueSize; i++) {
				Status now = q.poll();
				if (now.screen == S) {
					return depth;
				}
				
				Status[] nexts = new Status[3];
				nexts[0] = now.action1();
				nexts[1] = now.action2();
				nexts[2] = now.action3();
				
				for (Status next : nexts) {
					if (next.isInRange() && !isVisited[next.screen][next.clipboard]) {
						isVisited[next.screen][next.clipboard] = true;
						q.offer(next);
					}
				}
			}
			depth++;
		}
		
		return INF;
	}

}