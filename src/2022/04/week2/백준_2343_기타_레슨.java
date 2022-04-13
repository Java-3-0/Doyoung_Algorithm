// 22648KB, 280ms

package bj2343;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int INF = (int) (1e9 + 1);

	static int N;
	static int M;
	static int[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 수열 입력
		seq = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		// 정답 계산
		int answer = solve();

		// 출력
		System.out.println(answer);

	} // end main

	/** 이분탐색으로 정답 계산 */
	public static int solve() {
		int left = 1;
		int right = INF;

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

	/** x가 가능한 답인지 여부를 리턴 */
	public static boolean isPossible(int x) {
		long cnt = 1;
		int sum = 0;
		for (int num : seq) {
			if (num > x) {
				return false;
			}

			sum += num;
			if (sum > x) {
				cnt++;
				sum = num;
			}
		}

		if (cnt <= M) {
			return true;
		} else {
			return false;
		}
	}

}