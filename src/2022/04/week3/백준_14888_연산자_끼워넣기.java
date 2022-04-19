// 12848KB, 84ms

package bj14888;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 11;
	static final int INF = (int) 1e9 + (int) 1e6;

	static int N;
	static int[] seq = new int[MAX_N];

	static int maxResult = -INF;
	static int minResult = INF;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 수열 크기 입력
		N = Integer.parseInt(br.readLine());

		// 수열 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		// 각 연산자의 개수
		st = new StringTokenizer(br.readLine(), " ");
		int add = Integer.parseInt(st.nextToken());
		int sub = Integer.parseInt(st.nextToken());
		int mul = Integer.parseInt(st.nextToken());
		int div = Integer.parseInt(st.nextToken());
		
		// 모든 순열 완전탐색
		permutation(seq[0], 1, add, sub, mul, div);
		
		// 출력
		sb.append(maxResult).append("\n").append(minResult).append("\n");
		System.out.print(sb.toString());
		
	} // end main

	public static void permutation(int accum, int idx, int add, int sub, int mul, int div) {
		if (idx == N) {
			maxResult = Math.max(maxResult, accum);
			minResult = Math.min(minResult, accum);
			return;
		}
		
		if (add > 0) {
			permutation(accum + seq[idx], idx + 1, add - 1, sub, mul, div);
		}
		if (sub > 0) {
			permutation(accum - seq[idx], idx + 1, add, sub - 1, mul, div);
		}
		if (mul > 0) {
			permutation(accum * seq[idx], idx + 1, add, sub, mul - 1, div);
		}
		if (div > 0) {
			permutation(accum / seq[idx], idx + 1, add, sub, mul, div - 1);
		}
		
		return;
	}

}