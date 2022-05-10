// 12928KB, 100ms

package bj1004;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static class Circle {
		int centerX;
		int centerY;
		int radius;

		public Circle(int centerX, int centerY, int radius) {
			super();
			this.centerX = centerX;
			this.centerY = centerY;
			this.radius = radius;
		}

		boolean containsPoint(int x, int y) {
			int dx = x - this.centerX;
			int dy = y - this.centerY;
			int distSquare = dx * dx + dy * dy;
			int radiusSquare = radius * radius;

			if (distSquare < radiusSquare) {
				return true;
			} else {
				return false;
			}
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());
		
		for (int tc = 1; tc <= TESTS; tc++) {
			// 시작, 끝 위치 입력
			st = new StringTokenizer(br.readLine(), " ");
			int startX = Integer.parseInt(st.nextToken());
			int startY = Integer.parseInt(st.nextToken());
			int endX = Integer.parseInt(st.nextToken());
			int endY = Integer.parseInt(st.nextToken());
			
			// 원 개수 입력
			int N = Integer.parseInt(br.readLine());
			
			// 원 입력받아서 처리
			int answer = 0;
			for (int i = 0; i < N;i ++) {
				// 원 입력
				st = new StringTokenizer(br.readLine(), " ");
				int centerX = Integer.parseInt(st.nextToken());
				int centerY = Integer.parseInt(st.nextToken());
				int radius = Integer.parseInt(st.nextToken());
				Circle c = new Circle(centerX, centerY, radius);
				
				// 시작 정점과 끝 정점이 원 내부에 있는지 여부를 파악
				boolean containsStart = c.containsPoint(startX, startY);
				boolean containsEnd = c.containsPoint(endX, endY);
				
				// 시작 정점과 끝 정점 중 하나만 원 내부에 있으면 이 원을 통과해야 함
				if (containsStart ^ containsEnd) {
					answer++;
				}
			}
			
			// 출력용 스트링빌더에 정답을 추가
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());
		
	} // end main
}