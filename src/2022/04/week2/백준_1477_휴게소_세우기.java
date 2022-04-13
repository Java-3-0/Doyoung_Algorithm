// 11604KB, 76ms

package bj1477;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 50;
	static final int MAX_M = 100;
	static final int MAX_L = 1000;

	static int N, M, L;
	static int[] positions;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, M, L 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());

		// 휴게소 위치 입력
		positions = new int[N + 2];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			positions[i] = Integer.parseInt(st.nextToken());
		}

		// 고속도로 시작 위치와 끝 위치
		positions[0] = 0;
		positions[N + 1] = L;

		// 위치들 오름차순 정렬
		Arrays.sort(positions);

		// 정답 계산
		int answer = getMinRange();

		// 출력
		System.out.println(answer);

	} // end main

	private static int getMinRange() {
		int left = 1;
		int right = L - 1;

		while (left < right) {
			int mid = (left + right) / 2;
			if (isPossible(mid)) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}

		return right;
	}

	public static boolean isPossible(int range) {
		int cnt = 0;
		int len = positions.length;
		for (int i = 1; i < len; i++) {
			int dist = positions[i] - positions[i - 1];
			cnt += (dist / range);
			if (dist % range == 0) {
				cnt--;
			}
		}

		if (cnt <= M) {
			return true;
		}

		else {
			return false;
		}
	}

}