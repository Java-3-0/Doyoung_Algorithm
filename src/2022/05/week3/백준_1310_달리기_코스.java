// 57048KB, 764ms

package bj1310;

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

		public double getDistanceSquareTo(Position p) {
			double dy = this.y - p.y;
			double dx = this.x - p.x;

			return dy * dy + dx * dx;
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

		// 점의 개수 입력
		int N = Integer.parseInt(br.readLine());

		// 위치 입력
		Position[] positions = new Position[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			double x = Double.parseDouble(st.nextToken());
			double y = Double.parseDouble(st.nextToken());
			positions[i] = new Position(x, y);
		}

		// 로테이팅 캘리퍼스로 정답 계산
		double answer = RotatingCalipers(positions);

		System.out.println((long) Math.round(answer));

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

	/** 회전하는 캘리퍼스를 이용하여 두 점 사이의 최대 거리를 구한다 */
	public static double RotatingCalipers(Position[] positions) {
		// 볼록 껍질을 이루는 점들을 찾는다
		List<Position> convexHull = getConvexHull(positions);
		int size = convexHull.size();

		int idx2 = 1;
		double maxDistSquare = -INF;

		for (int idx1 = 0; idx1 < size; idx1++) {
			while (true) {
				int nextIdx1 = (idx1 + 1) % size;
				int nextIdx2 = (idx2 + 1) % size;

				double y1 = convexHull.get(nextIdx1).y - convexHull.get(idx1).y;
				double x1 = convexHull.get(nextIdx1).x - convexHull.get(idx1).x;
				double y2 = convexHull.get(nextIdx2).y - convexHull.get(idx2).y;
				double x2 = convexHull.get(nextIdx2).x - convexHull.get(idx2).x;

				Position A = new Position(0, 0);
				Position B = new Position(y1, x1);
				Position C = new Position(y2, x2);

				// 반시계 방향인 동안 idx2를 옮긴다.
				if (ccw(A, B, C) > 0) {
					idx2 = (idx2 + 1) % size;
				}
				// 반시계 방향이 끝날 때 거리를 계산하고, 루프를 빠져나옴으로써 idx1을 옮긴다.
				else {
					double distSquare = convexHull.get(idx1).getDistanceSquareTo(convexHull.get(idx2));
					if (maxDistSquare < distSquare) {
						maxDistSquare = distSquare;
					}
					break;
				}
			}

		}

		return maxDistSquare;
	}

}