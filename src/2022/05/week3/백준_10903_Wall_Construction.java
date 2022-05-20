// 13656KB, 108ms

package bj10903;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
	static final double INF = 987654321098765432.0;

	/** 위치 객체 */
	static class Position implements Comparable<Position> {
		double x;
		double y;

		public Position(double x, double y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Position p) {
			if (this.y == p.y) {
				return Double.compare(this.x, p.x);
			}
			return -Double.compare(this.y, p.y);
		}
		
		public double getDistanceTo(Position p) {
			double dy = this.y - p.y;
			double dx = this.x - p.x;
			double distance = Math.sqrt(dy * dy + dx * dx);
			
			return distance;
		}

		@Override
		public String toString() {
			return "Position [x=" + x + ", y=" + y + "]";
		}

	}

	/** 메인 함수 */
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N과 반지름 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		double radius = Double.parseDouble(st.nextToken());

		// 위치 입력
		Position[] positions = new Position[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			double x = Double.parseDouble(st.nextToken());
			double y = Double.parseDouble(st.nextToken());
			positions[i] = new Position(x, y);
		}

		// 컨벡스 헐 계산
		List<Position> convexHull = getConvexHull(positions);

		// 컨벡스 헐을 이루는 점들의 순서대로 거리를 구해서 그 합을 계산
		double answer = 0.0;
		int size = convexHull.size();
		for (int i = 0; i < size; i++) {
			Position now = convexHull.get(i);
			Position next = convexHull.get((i + 1) % size);
			answer += now.getDistanceTo(next);
		}

		// 반지름만큼 떨어져있으니 원의 둘레만큼 길이 추가
		answer += 2.0 * Math.PI * radius;
		
		// 출력
		System.out.println(answer);

	} // end main
	
	/** 세 점의 외적 계산. 반시계 방향이면 양수, 일직선이면 0, 시계 방향이면 음수 리턴 */
	public static double ccw(Position a, Position b, Position c) {
		double x1 = a.x;
		double x2 = b.x;
		double x3 = c.x;
		double y1 = a.y;
		double y2 = b.y;
		double y3 = c.y;

		double sum1 = x1 * y2 + x2 * y3 + x3 * y1;
		double sum2 = y1 * x2 + y2 * x3 + y3 * x1;
		double ret = sum1 - sum2;

		return ret;
	}

	/** 컨벡스 헐에 포함되는 정점들을 리스트에 담아서 리턴 */
	public static List<Position> getConvexHull(Position[] positions) {
		int len = positions.length;

		// 위치들을 x좌표 오름차순, y좌표 오름차순 정렬
		Arrays.sort(positions);

		// 스택 생성
		Stack<Position> lower = new Stack<>();
		Stack<Position> upper = new Stack<>();

		// 아래 껍질 계산
		for (int i = 0; i < len; i++) {
			while (lower.size() > 1
					&& (ccw(lower.get(lower.size() - 2), lower.get(lower.size() - 1), positions[i]) >= 0)) {
				lower.pop();
			}
			lower.add(positions[i]);
		}

		// 위 껍질 계산
		for (int i = len - 1; i >= 0; i--) {
			while (upper.size() > 1
					&& (ccw(upper.get(upper.size() - 2), upper.get(upper.size() - 1), positions[i]) >= 0)) {
				upper.pop();
			}
			upper.add(positions[i]);
		}

		// 시작점, 끝점에서의 중복 제거
		lower.pop();
		upper.pop();

		// 아래 껍질과 위 껍질을 합쳐서 정답 계산
		List<Position> ret = new ArrayList<>();
		ret.addAll(lower);
		ret.addAll(upper);

		return ret;
	}

}