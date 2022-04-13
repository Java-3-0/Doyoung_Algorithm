// 26300KB, 268ms

package bj17266;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000;
	static final int MAX_M = MAX_N;

	static int N, M;
	static int[] positions;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, M 입력
		N = Integer.parseInt(br.readLine());
		M = Integer.parseInt(br.readLine());

		// 가로등 위치 입력
		positions = new int[M];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < M; i++) {
			positions[i] = Integer.parseInt(st.nextToken());
		}

		// 위치들 오름차순 정렬
		Arrays.sort(positions);

		// 정답 계산
		int answer = getMinHeight();

		// 출력
		System.out.println(answer);

	} // end main

	/** 가능한 가로등 높이중 최소를 리턴 */
	private static int getMinHeight() {
		int left = 1;
		int right = N;

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

	/** height 높이로 가로등을 설치하는 것이 가능한지 여부를 리턴 */
	public static boolean isPossible(int height) {
		int len = positions.length;
		for (int i = 1; i < len; i++) {
			int dist = positions[i] - positions[i - 1];
			if (dist > 2 * height) {
				return false;
			}
		}

		if (positions[0] - 0 > height) {
			return false;
		}

		if (N - positions[len - 1] > height) {
			return false;
		}

		return true;
	}

}