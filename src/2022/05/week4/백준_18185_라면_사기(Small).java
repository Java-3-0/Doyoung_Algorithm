// 12956KB, 108ms

package bj18185

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 공장 개수 입력
		int N = Integer.parseInt(br.readLine());

		// 공장마다 구매할 라면 개수 입력
		long[] seq = new long[N + 2]; // 인덱스 범위 끝에서 따로 처리해주지 않기 위해 0을 가지는 두 칸을 더 만듦.
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Long.parseLong(st.nextToken());
		}

		long sumCost = 0L;
		for (int i = 0; i < N; i++) {
			// 두 번째 공장이 세 번째 공장보다 많이 가지고 있다면
			// (그 둘의 차)와 (현재 공장이 가진 값)의 최소값만큼 먼저 산다
			if (seq[i + 1] > seq[i + 2]) {
				long buyPre = Math.min(seq[i], seq[i + 1] - seq[i + 2]);
				long costPre = 5L * buyPre;
				sumCost += costPre;
				seq[i] -= buyPre;
				seq[i + 1] -= buyPre;
			}

			// 연속된 세 공장의 최소값만큼 사 본다
			long buy3 = Math.min(seq[i], Math.min(seq[i + 1], seq[i + 2]));
			long cost3 = 7L * buy3;
			sumCost += cost3;
			seq[i] -= buy3;
			seq[i + 1] -= buy3;
			seq[i + 2] -= buy3;

			// 연속된 두 공장의 최소값만큼 사 본다
			long buy2 = Math.min(seq[i], seq[i + 1]);
			long cost2 = 5L * buy2;
			sumCost += cost2;
			seq[i] -= buy2;
			seq[i + 1] -= buy2;

			// 이 공장 하나에서만 산다
			long buy1 = seq[i];
			long cost1 = 3L * buy1;
			sumCost += cost1;
			seq[i] -= buy1;
		}

		System.out.println(sumCost);

	} // end main

}