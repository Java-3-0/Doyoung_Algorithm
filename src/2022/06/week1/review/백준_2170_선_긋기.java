// 321732KB, 1376ms

package bj2170;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

		/** 선분을 시작 위치 오름차순, 끝 위치 오름차순 순으로 정렬하기 위한 비교함수 */
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
		List<Line> lines = new ArrayList<Line>(N);
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			lines.add(new Line(x, y));
		}

		// 선 오름차순 정렬
		Collections.sort(lines);

		// 선분의 길이 총합을 계산
		int answer = 0;
		int lineEnd = -MAX_POS;
		for (Line line : lines) {
			int start = line.x;
			int end = line.y;

			// 선분이 생기는 경우
			if (lineEnd <= end) {
				// 일부만 겹치는 경우, 새로 생기는 부분만큼만
				if (start <= lineEnd) {
					answer += (end - lineEnd);
				}

				// 아예 오른쪽에 새로 생기는 선분인 경우
				else {
					answer += (end - start);
				}

				lineEnd = end;
			}
		}

		// 결과 출력
		System.out.println(answer);

	} // end main

}