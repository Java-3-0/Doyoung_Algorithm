// 327912KB, 2216ms

package bj2170;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_POS = (int) 1e9;
	
	
	static class Line implements Comparable<Line> {
		int x;
		int y;

		public Line(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Line l) {
			if (this.x == l.x) {
				return this.y - l.y;
			}
			return this.x - l.x;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 선 개수 입력
		int N = Integer.parseInt(br.readLine());

		// 선 입력
		Line[] lines = new Line[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			lines[i] = new Line(x, y);
		}

		// 선 오름차순 정렬
		Arrays.sort(lines);

		int answer = 0;
		int start = -MAX_POS;
		int end = -MAX_POS;
		for (Line line : lines) {
			int nextStart = line.x;
			int nextEnd = line.y;
			
			// 이미 있던 선분에 포함되는 경우
			if (start <= nextStart && nextEnd <= end) {
				continue;
			}
			
			// 선분이 겹치지 않는 경우
			if (end < nextStart) {
				answer += (nextEnd - nextStart);
			}
			// 선분의 일부가 겹치는 경우
			else {
				answer += (nextEnd - end);
			}
			
			start = nextStart;
			end = nextEnd;
			
		}

		System.out.println(answer);

	} // end main

}