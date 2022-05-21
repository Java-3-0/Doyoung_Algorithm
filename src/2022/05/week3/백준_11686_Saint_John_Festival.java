// 33640KB, 440ms

package bj11686;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
	/** 위치 객체 */
	static class Position implements Comparable<Position> {
		long x;
		long y;

		public Position(long x, long y) {
			super();
			this.x = x;
			this.y = y;
		}

		@Override
		public int compareTo(Position p) {
			if (this.x == p.x) {
				return Long.compare(this.y, p.y);
			}
			return Long.compare(this.x, p.x);
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

		// 대형 전등 개수 입력
		int L = Integer.parseInt(br.readLine());

		// 대형 전등 위치들 입력
		List<Position> positionsLarge = new ArrayList<>(L);
		for (int i = 0; i < L; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			long x = Long.parseLong(st.nextToken());
			long y = Long.parseLong(st.nextToken());
			positionsLarge.add(new Position(x, y));
		}
		
		// 소형 전등 개수 입력
		int S = Integer.parseInt(br.readLine());

		// 소형 전등 위치들 입력
		List<Position> positionsSmall = new ArrayList<>(L);
		for (int i = 0; i < S; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			long x = Long.parseLong(st.nextToken());
			long y = Long.parseLong(st.nextToken());
			positionsSmall.add(new Position(x, y));
		}

		// 볼록껍질 계산
		List<Position> convexLarge = getConvexHull(positionsLarge);

		// 삼각형에 포함되는 위치 개수 세기
		int answer = 0;
		for (Position pos : positionsSmall) {
			if (isInside(pos, convexLarge)) {
				answer++;
			}
		}

		// 출력
		System.out.println(answer);

	} // end main

	/** 세 점의 외적 계산. 반시계 방향이면 양수, 일직선이면 0, 시계 방향이면 음수 리턴 */
	public static long ccw(Position a, Position b, Position c) {
		long x1 = a.x;
		long x2 = b.x;
		long x3 = c.x;
		long y1 = a.y;
		long y2 = b.y;
		long y3 = c.y;

		long sum1 = x1 * y2 + x2 * y3 + x3 * y1;
		long sum2 = y1 * x2 + y2 * x3 + y3 * x1;
		long ret = sum1 - sum2;

		return ret;
	}

	/** 점이 볼록 다각형 내부에 있는지 여부를 리턴한다 */
	public static boolean isInside(Position targetPoint, List<Position> convexHull) {
		int size = convexHull.size();
		if (size < 3) {
			return false;
		}

		int left = 1;
		int right = size - 1;

		Position originPoint = convexHull.get(0);

		// 전체 다각형 범위 각도에 포함되지 않는 경우
		if (ccw(originPoint, convexHull.get(left), targetPoint) < 0L) {
			return false;
		}
		if (ccw(originPoint, targetPoint, convexHull.get(right)) < 0L) {
			return false;
		}

		// 이외의 경우, 어느 점과 어느 점 사이 각도에 포함되는지 찾는다
		while (left + 1 < right) {
			int mid = (left + right) / 2;
			Position midPoint = convexHull.get(mid);
			if (ccw(originPoint, midPoint, targetPoint) > 0L) {
				left = mid;
			} else {
				right = mid;
			}
		}

		// 그 각도에서 다각형 안쪽인지 바깥쪽인지 판단한다
		if (ccw(convexHull.get(left), convexHull.get(right), targetPoint) >= 0) {
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