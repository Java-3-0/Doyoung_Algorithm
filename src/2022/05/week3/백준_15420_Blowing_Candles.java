// 22024KB, 304ms

package bj15420;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
	static final double INF = 9876543210987654.3;

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

		// 점의 개수 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		double R = Double.parseDouble(st.nextToken());

		// 위치 입력
		Position[] positions = new Position[N];
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			double x = Double.parseDouble(st.nextToken());
			double y = Double.parseDouble(st.nextToken());
			positions[i] = new Position(x, y);
		}

		// 볼록껍질 계산
		List<Position> convexHull = getConvexHull(positions);

		// 변과 그 변에서 가장 멀리 떨어진 점의 거리 중 최소값을 구한다
		double answer = RotatingCalipers(convexHull);

		// 출력
		System.out.print(answer);

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

	/** 회전하는 캘리퍼스를 이용하여 각 변으로부터 가장 멀리 떨어진 점으로의 거리들 중 최소값을 구한다 */
	public static double RotatingCalipers(List<Position> polygon) {
		int size = polygon.size();

		int idx2 = 1;
		double ret = INF;

		for (int idx1 = 0; idx1 < size; idx1++) {
			double maxDist = 0;

			while (true) {
				int nextIdx1 = (idx1 + 1) % size;
				int nextIdx2 = (idx2 + 1) % size;

				double y1 = polygon.get(nextIdx1).y - polygon.get(idx1).y;
				double x1 = polygon.get(nextIdx1).x - polygon.get(idx1).x;
				double y2 = polygon.get(nextIdx2).y - polygon.get(idx2).y;
				double x2 = polygon.get(nextIdx2).x - polygon.get(idx2).x;

				// 기준점을 A로 잡고
				// idx1에서 nextIdx1로의 벡터는 B로 나타낸다
				// idx2에서 nextIdx2로의 벡터는 C로 나타낸다.
				Position A = new Position(0, 0);
				Position B = new Position(y1, x1);
				Position C = new Position(y2, x2);

				// 반시계 방향인 동안 idx2를 옮긴다.
				if (ccw(A, B, C) > 0) {
					idx2 = (idx2 + 1) % size;
				}
				// 반시계 방향이 끝날 때 거리를 계산하고, 루프를 빠져나옴으로써 idx1을 옮긴다.
				else {
					Position p1 = polygon.get(idx1);
					Position p2 = polygon.get(nextIdx1);
					Position p3 = polygon.get(idx2);

					// x좌표가 같은 경우, divide by zero를 피하기 위해 따로 처리
					if (p1.x == p2.x) {
						double dist = Math.abs(p3.x - p1.x);
						maxDist = Math.max(maxDist, dist);
					}
					// 일반적인 경우, 점과 직선 사이의 거리 공식 이용
					else {
						double m = (p2.y - p1.y) / (p2.x - p1.x);
						double b = (-m * p1.x) + p1.y;

						// mx - y + b = 0과 (p3.y, p3.x)의 거리를 구한다
						double numer = Math.abs(m * p3.x - p3.y + b);
						double denom = Math.sqrt(m * m + 1 * 1);
						double dist = numer / denom;
						maxDist = Math.max(maxDist, dist);
					}

					break;
				}
			} // end while true

			// 최소값 갱신
			ret = Math.min(ret, maxDist);

		} // end for idx1

		return ret;
	}

}