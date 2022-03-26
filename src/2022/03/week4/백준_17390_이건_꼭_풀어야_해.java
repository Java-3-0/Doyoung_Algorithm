// 137148KB, 1536ms

package bj17390;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 300000;
	
	static int N, Q;
	static int[] seq;
	static int[] psums;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, Q 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		Q = Integer.parseInt(st.nextToken());
		
		// 수열 입력
		seq = new int[N + 1];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			int input = Integer.parseInt(st.nextToken());
			seq[i] = input;
		}
		
		// 수열 정렬
		Arrays.sort(seq);
		
		// 누적합 계산
		psums = new int[N + 1];
		psums[0] = 0;
		for (int i = 1, sum = 0; i <= N; i++) {
			sum += seq[i];
			psums[i] = sum;
		}
		
		// 쿼리마다 합계 출력
		for (int i = 0; i < Q ; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int answer = psums[end] - psums[start - 1];
			sb.append(answer).append("\n");
		}
		
		System.out.print(sb.toString());
		
	} // end main
	
}