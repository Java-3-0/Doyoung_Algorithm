// 165128KB, 988ms

package bj20670;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
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
			if (this.x == p.x) {
				return Double.compare(this.y, p.y);
			}
			return Double.compare(this.x, p.x);
		}

		public double getDistanceTo(Position p) {
			double dy = this.y - p.y;
			double dx = this.x - p.x;
			double distance = Math.sqrt(dy * dy + dx * dx);

			return distance;
		}

		public Position subtract(Position p) {
			return new Position(this.x - p.x, this.y - p.y);
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

		// N, M, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		// 도형 A 위치 입력
		List<Position> positionsOuter = new ArrayList<>(N);
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			double x = Double.parseDouble(st.nextToken());
			double y = Double.parseDouble(st.nextToken());
			positionsOuter.add(new Position(x, y));
		}

		// 도형 B 위치 입력
		List<Position> positionsInner = new ArrayList<>(M);
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < M; i++) {
			double x = Double.parseDouble(st.nextToken());
			double y = Double.parseDouble(st.nextToken());
			positionsInner.add(new Position(x, y));
		}

		//
		List<Position> positionsSign = new ArrayList<>(K);
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < K; i++) {
			double x = Double.parseDouble(st.nextToken());
			double y = Double.parseDouble(st.nextToken());
			positionsSign.add(new Position(x, y));
		}

		// 볼록껍질 계산
		List<Position> convexHullOuter = getConvexHull(positionsOuter);
		List<Position> convexHullInner = getConvexHull(positionsInner);

		// 규칙에 맞지 않는 점 개수를 센다
		int cntBreakingRule = 0;
		for (Position targetPoint : positionsSign) {
			// 바깥 도형의 안쪽, 안쪽 도형의 바깥쪽이면 규칙에 맞는다
			if (isInside(targetPoint, convexHullOuter) && !isInside(targetPoint, convexHullInner)) {
				continue;
			}
			// 이외의 경우 규칙에 맞지 않는다
			else {
				cntBreakingRule++;
			}
		}

		// 출력
		if (cntBreakingRule > 0) {
			System.out.println(cntBreakingRule);
		} else {
			System.out.println("YES");
		}

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

	/** 점이 볼록 다각형 내부에 있는지 여부를 리턴한다 */
	public static boolean isInside(Position targetPoint, List<Position> convexHull) {
		int size = convexHull.size();

		int left = 1;
		int right = size - 1;

		Position originPoint = convexHull.get(0);

		// 전체 다각형 범위 각도에 포함되지 않는 경우
		if (ccw(originPoint, convexHull.get(left), targetPoint) < 0) {
			return false;
		}
		if (ccw(originPoint, targetPoint, convexHull.get(right)) < 0) {
			return false;
		}

		// 이외의 경우, 어느 점과 어느 점 사이 각도에 포함되는지 찾는다
		while (left + 1 < right) {
			int mid = (left + right) / 2;
			Position midPoint = convexHull.get(mid);
			if (ccw(originPoint, midPoint, targetPoint) > 0) {
				left = mid;
			} else {
				right = mid;
			}
		}

		// 그 각도에서 다각형 안쪽인지 바깥쪽인지 판단한다
		if (ccw(convexHull.get(left), convexHull.get(right), targetPoint) > 0) {
			return true;
		}
		return false;
	}

	/** 컨벡스 헐에 포함되는 정점들을 리스트에 담아서 리턴 */
	public static List<Position> getConvexHull(List<Position> positions) {
		int size = positions.size();

		// 위치들을 x좌표 오름차순, y좌표 오름차순 정렬
		Collections.sort(positions);

		// 스택 생성
		Stack<Position> lower = new Stack<>();
		Stack<Position> upper = new Stack<>();

		// 아래 껍질 계산
		for (int i = 0; i < size; i++) {
			while (lower.size() > 1
					&& (ccw(lower.get(lower.size() - 2), lower.get(lower.size() - 1), positions.get(i)) >= 0)) {
				lower.pop();
			}
			lower.add(positions.get(i));
		}

		// 위 껍질 계산
		for (int i = size - 1; i >= 0; i--) {
			while (upper.size() > 1
					&& (ccw(upper.get(upper.size() - 2), upper.get(upper.size() - 1), positions.get(i)) >= 0)) {
				upper.pop();
			}
			upper.add(positions.get(i));
		}

		// 시작점, 끝점에서의 중복 제거
		lower.pop();
		upper.pop();

		// 아래 껍질과 위 껍질을 합쳐서 정답 계산
		List<Position> ret = new ArrayList<>();
		while (!lower.isEmpty()) {
			ret.add(lower.pop());
		}
		while (!upper.isEmpty()) {
			ret.add(upper.pop());
		}

		return ret;
	}

}