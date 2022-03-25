// 12804KB, 108ms

package bj10211;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000;
	
	static int N;
	static int[] seq = new int[MAX_N + 1];
	static int[] psums = new int[MAX_N + 1];

	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());
		
		for (int testCase = 1; testCase <= TESTS; testCase++) {
			Arrays.fill(seq, 0);
			Arrays.fill(psums, 0);
			
			N = Integer.parseInt(br.readLine());
			
			st = new StringTokenizer(br.readLine(), " ");
			int sum = 0;
			for (int i = 1; i <= N; i++) {
				int input = Integer.parseInt(st.nextToken());
				seq[i] = input;
				sum += input;
				psums[i] = sum;
			}
			
			int answer = getMaxSum();
			
			sb.append(answer).append("\n");
		}
		
		System.out.print(sb.toString());

	} // end main

	/** 현재 psums에서 구간합의 최대치를 리턴 */
	public static int getMaxSum() {
		int ret = Integer.MIN_VALUE;
		int minLeft = 0;
		
		for (int i = 1; i <= N; i++) {
			ret = Math.max(ret, psums[i] - minLeft);
			minLeft = Math.min(minLeft, psums[i]);
		}
		
		return ret;
	}
}