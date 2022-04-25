// 28968KB, 272ms

package bj18783;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N, M, K;
	static int[] seq;
	static int LOG_K;
	static int[][] table;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, M, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		// LOG_K 계산
		LOG_K = (int) Math.ceil(Math.log(K) / Math.log(2));

		// seq 배열 메모리 할당
		seq = new int[N + 1];

		// sparse table 메모리 할당
		table = new int[LOG_K + 1][N + 1];

		// 초기화
		for (int i = 1; i <= N; i++) {
			seq[i] = i;
		}

		// M번의 연산 수행
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int left = Integer.parseInt(st.nextToken());
			int right = Integer.parseInt(st.nextToken());
			swapRange(left, right);
		}

		// 희소 배열 생성
		makeSparseTable();

		// 정답 계산 후 출력
		for (int num = 1; num <= N; num++) {
			int answer = getResult(num, K);
			sb.append(answer).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	/** num에 작업을 k번 수행한 후의 결과를 리턴 */
	public static int getResult(int num, int k) {
		for (int row = LOG_K; row >= 0; row--) {
			if ((k & (1 << row)) != 0) {
				num = table[row][num];
			}
		}

		return num;
	}

	/** 희소 배열 테이블을 계산해서 저장한다 */
	public static void makeSparseTable() {
		for (int num = 1; num <= N; num++) {
			table[0][num] = seq[num];
		}

		for (int row = 1; row <= LOG_K; row++) {
			for (int num = 1; num <= N; num++) {
				table[row][num] = table[row - 1][table[row - 1][num]];
			}
		}
	}

	/** seq에서 left부터 right까지를 swap한다 */
	public static void swapRange(int left, int right) {
		int half = (right - left + 1) / 2;
		for (int i = 0; i < half; i++) {
			int tmp = seq[left + i];
			seq[left + i] = seq[right - i];
			seq[right - i] = tmp;
		}
	}

}