// 517128KB, 2820ms

package bj22863;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static long K;
	static int[] lastStatus;
	static int[] shuffle;
	static int[][] table;
	static int LOG_K;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Long.parseLong(st.nextToken());

		// LOG_K 계산
		LOG_K = (int) Math.ceil(Math.log(K) / Math.log(2));

		// 메모리 할당
		lastStatus = new int[N + 1];
		shuffle = new int[N + 1];

		// sparse table 메모리 할당
		table = new int[LOG_K + 1][N + 1];

		// lastStatus[] 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			lastStatus[i] = Integer.parseInt(st.nextToken());
		}

		// shuffle[] 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			shuffle[i] = Integer.parseInt(st.nextToken());
		}

		// 희소 배열 생성
		makeSparseTable();

		// 정답 계산
		int[] answers = new int[N + 1];
		for (int endIdx = 1; endIdx <= N; endIdx++) {
			int startIdx = getStartIdx(endIdx, K);
			answers[startIdx] = lastStatus[endIdx];
		}

		// 정답 출력
		for (int i = 1; i <= N; i++) {
			sb.append(answers[i]).append(' ');
		}
		sb.append("\n");

		System.out.print(sb.toString());

	} // end main

	/** idx에 작업을 k번 수행한 후의 결과 idx를 리턴 */
	public static int getStartIdx(int idx, long k) {
		int cur = idx;

		for (int row = LOG_K; row >= 0; row--) {
			if ((k & (1L << (long) row)) != 0L) {
				cur = table[row][cur];
			}
		}

		return cur;
	}

	/** 희소 배열 테이블을 계산해서 저장한다 */
	public static void makeSparseTable() {
		for (int idx = 1; idx <= N; idx++) {
			table[0][idx] = shuffle[idx];
		}

		for (int row = 1; row <= LOG_K; row++) {
			for (int idx = 1; idx <= N; idx++) {
				table[row][idx] = table[row - 1][table[row - 1][idx]];
			}
		}
	}
}