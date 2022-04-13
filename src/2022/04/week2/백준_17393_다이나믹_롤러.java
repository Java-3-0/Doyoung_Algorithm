// 280852KB, 884ms

package bj17393;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static final int FAIL = -1;

	static int N;
	static long[] A, B;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N 입력
		N = Integer.parseInt(br.readLine());

		// 수열 메모리 할당
		A = new long[N];
		B = new long[N];

		// 수열 A 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			A[i] = Long.parseLong(st.nextToken());
		}

		// 수열 B 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			B[i] = Long.parseLong(st.nextToken());
		}

		// 이분탐색
		for (int i = 0; i < N; i++) {
			long num = A[i];
			int answer = findStartIdx(i, num);

			answer -= i;

			sb.append(answer).append(" ");
		}

		sb.append("\n");

		// 정답 출력
		System.out.print(sb.toString());

	} // end main

	public static int findStartIdx(int leftIdx, long num) {
		int left = leftIdx;
		int right = N - 1;

		while (left < right) {
			int mid = (left + right + 1) / 2;

			if (num >= B[mid]) {

				left = mid;
			} else {
				right = mid - 1;
			}
		}

		return left;
	}

}