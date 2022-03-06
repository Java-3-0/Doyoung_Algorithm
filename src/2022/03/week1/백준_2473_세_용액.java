// 14336KB, 664ms

package bj2473;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 5000;

	static int N;
	static long[] liquids;
	static long minValue = Long.MAX_VALUE;
	static int[] minIdxs = new int[3];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 용액 수 입력
		N = Integer.parseInt(br.readLine());

		// 용액 입력
		st = new StringTokenizer(br.readLine(), " ");
		liquids = new long[N];
		for (int i = 0; i < N; i++) {
			liquids[i] = Long.parseLong(st.nextToken());
		}

		// 용액을 특성값 오름차순으로 정렬
		Arrays.sort(liquids);

		// 두 용액을 선택하는 것을 완전탐색하고, 마지막 하나의 용액을 찾는다.
		for (int idx1 = 0; idx1 < N; idx1++) {
			for (int idx2 = idx1 + 1; idx2 < N; idx2++) {
				makeLiquid(idx1, idx2);
			}
		}

		// 결과를 출력
		sb.append(liquids[minIdxs[0]]).append(" ");
		sb.append(liquids[minIdxs[1]]).append(" ");
		sb.append(liquids[minIdxs[2]]).append("\n");
		System.out.print(sb.toString());
	} // end main

	/** 두 용액이 정해졌을 때, value의 절대값을 최소화하는 하나의 용액을 찾고, minValue를 갱신한다 */
	public static void makeLiquid(int idx1, int idx2) {
		long sumOfTwo = liquids[idx1] + liquids[idx2];

		// 이분탐색
		int left = idx2 + 1;
		int right = N - 1;
		int mid;
		while (left <= right) {
			mid = (left + right) / 2;

			long sumOfThree = sumOfTwo + liquids[mid];
			long sumAbs = Math.abs(sumOfThree);

			if (sumAbs < minValue) {
				minValue = sumAbs;
				minIdxs[0] = idx1;
				minIdxs[1] = idx2;
				minIdxs[2] = mid;
			}

			if (sumOfThree > 0) {
				right = mid - 1;
			} else {
				left = mid + 1;
			}

		}

		return;
	}

}