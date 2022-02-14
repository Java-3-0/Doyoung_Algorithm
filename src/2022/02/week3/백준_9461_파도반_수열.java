// 11636KB, 84ms

package bj9461;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	public static final int MAX_N = 100;
	public static long[] cache = new long[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		
		for (int t = 0; t < T; t++) {
			// 캐시 -1로 초기화
			Arrays.fill(cache, -1L);
			
			// 입력
			int N = Integer.parseInt(br.readLine());
			
			// P(N) 계산
			long answer = padovan(N);
			
			// 출력
			sb.append(answer).append("\n");
		}
		System.out.print(sb.toString());
	}

	public static long padovan (int n) {
		// base case : n = 1 ~ 5
		if (n <= 3) return 1L;
		if (n == 4) return 2L;
		if (n == 5) return 2L;
		
		// 캐시에 계산되어 있는 경우
		if (cache[n] != -1L) return cache[n];
		
		// 새로 계산해서 캐시에 넣으면서 리턴하는 경우
		return cache[n] = padovan(n-1) + padovan(n-5);
	}
}
