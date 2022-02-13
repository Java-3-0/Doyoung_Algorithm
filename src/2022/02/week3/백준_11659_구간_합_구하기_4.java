// 57132KB, 604ms

package bj11659;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_N = 100000;
	
	public static int N;
	public static int M;
	public static long[] numbers = new long[MAX_N + 1];
	public static long[] psum = new long[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		
		// 입력을 받아서 수열과 부분합 저장
		st = new StringTokenizer(br.readLine(), " ");
		long sum = 0L;
		for (int i = 1; i <= N; i++) {
			long input = Long.parseLong(st.nextToken());
			numbers[i] = input;
			sum += input;
			psum[i] = sum;
		}
		
		// 부분합을 이용해서 구간합 계산
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int left = Integer.parseInt(st.nextToken());
			int right = Integer.parseInt(st.nextToken());
			long answer = psum[right] - psum[left - 1];
			sb.append(answer).append("\n");
		}
		
		// 출력
		System.out.println(sb.toString());
		
	}
}
