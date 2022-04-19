// 52780KB, 436ms

package bj6549;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_N = 100000;
	static long[] seq = new long[MAX_N];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		while (true) {
			st = new StringTokenizer(br.readLine(), " ");

			// 직사각형의 수 입력
			int N = Integer.parseInt(st.nextToken());
			if (N == 0) {
				break;
			}

			// 각 직사각형의 높이 입력
			for (int i = 0; i < N; i++) {
				seq[i] = Long.parseLong(st.nextToken());
			}

			// 정답 계산
			long answer = getMaxArea(0, N - 1);

			// 출력 스트링빌더에 추가
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	private static long getMaxArea(int left, int right) {
		// base case: 너비가 1인 경우
		if (left == right) {
			return seq[left];
		}

		// 분할 정복
		int mid = (left + right) / 2;

		// 양쪽 부분에서 각각 최대 넓이
		long maxAreaLeft = getMaxArea(left, mid);
		long maxAreaRight = getMaxArea(mid + 1, right);

		// 두 부분 모두에 걸치는 사각형의 최대 넓이를 구한다.
		int lo = mid;
		int hi = mid + 1;

		// 일단은 2개 직사각형만 포함하는 사각형을 고려
		long height = Math.min(seq[lo], seq[hi]);
		long maxAreaMid = height * 2L;

		// 이 직사각형의 양 옆 1칸씩 중 더 높이가 높은 쪽으로 확장하는 것을 반복하면서 넓이 갱신
		while (left < lo || hi < right) {
			// 오른쪽으로 확장하는 경우
			if (hi < right && (lo == left || seq[lo - 1] <= seq[hi + 1])) {
				height = Math.min(height, seq[++hi]);
			}
			// 왼쪽으로 확장하는 경우
			else {
				height = Math.min(height, seq[--lo]);
			}

			maxAreaMid = Math.max(maxAreaMid, height * (long) (hi - lo + 1));
		}

		// 분할 정복한 방법들 중 최대 넓이를 리턴
		long ret = Math.max(maxAreaLeft, maxAreaRight);
		ret = Math.max(ret, maxAreaMid);

		return ret;
	}

}