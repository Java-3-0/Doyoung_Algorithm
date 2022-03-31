// 102096KB, 636ms

package bj16139;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_LEN = 200000;
	static final int ALPHABETS = 26;

	static char[] S;
	static int Q;
	static int[][] psums = new int[ALPHABETS][MAX_LEN + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		S = br.readLine().toCharArray();
		Q = Integer.parseInt(br.readLine());

		// 스트링 S에서 각 알파벳 개수의 누적합 계산
		int[] counts = new int[ALPHABETS];
		for (int i = 1; i <= S.length; i++) {
			int alphabet = S[i - 1] - 'a';

			counts[alphabet]++;

			for (int k = 0; k < ALPHABETS; k++) {
				psums[k][i] = counts[k];
			}

		}

		// 쿼리 수행
		for (int i = 0; i < Q; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int alphabet = st.nextToken().charAt(0) - 'a';
			int left = Integer.parseInt(st.nextToken()) + 1;
			int right = Integer.parseInt(st.nextToken()) + 1;

			int answer = psums[alphabet][right] - psums[alphabet][left - 1];

			sb.append(answer).append("\n");
		}

		// 결과 출력
		System.out.print(sb.toString());

	} // end main

}