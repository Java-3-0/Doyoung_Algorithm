// 12044KB, 128ms

package bj17179;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_LEN = 4000000;

	static int N, M, L;
	static int[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, M, L 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());

		// 수열 입력
		seq = new int[M + 2];
		for (int i = 1; i <= M; i++) {
			seq[i] = Integer.parseInt(br.readLine());
		}
		seq[0] = 0;
		seq[M + 1] = L;

		// N개의 질문에 대해 정답 계산
		for (int i = 0; i < N; i++) {
			int cuts = Integer.parseInt(br.readLine());
			int answer = solve(cuts);
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 이분탐색으로 정답 계산 */
	public static int solve(int cuts) {
		int left = 1;
		int right = MAX_LEN;

		while (left < right) {
			int mid = (left + right) / 2;

			if (isPossible(mid, cuts)) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}

		return right;
	}

	/** x가 가능한 답인지 여부를 리턴 */
	public static boolean isPossible(int x, int cuts) {
		long cnt = 0;
		int prevCut = seq[0];
		for (int i = 1; i < seq.length; i++) {
			int pos = seq[i];

			if (pos - prevCut > x) {
				prevCut = pos;
				cnt++;
			}
		}

		if (cnt <= cuts) {
			return true;
		} else {
			return false;
		}
	}

}