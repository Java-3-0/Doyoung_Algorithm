// 57184KB, 600ms

package bj11441;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000;
	
	static int N, M;
	static int[] seq = new int[MAX_N + 1];
	static int[] psums = new int[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 수열 크기 입력
		N = Integer.parseInt(br.readLine());
		
		// 수열 입력받고 누적합 계산
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1, sum = 0; i <= N; i++) {
			int input = Integer.parseInt(st.nextToken());
			sum += input;
			seq[i] = input;
			psums[i] = sum;
		}
		
		// 쿼리 수 입력
		M = Integer.parseInt(br.readLine());
		
		// 쿼리마다 합계 출력
		for (int i = 0; i < M ; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			int answer = psums[end] - psums[start - 1];
			sb.append(answer).append("\n");
		}
		
		System.out.print(sb.toString());
		
	} // end main
	
}