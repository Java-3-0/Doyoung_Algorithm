// 11508KB, 76ms

package bj9095;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	public static final int MAX_N = 10;
	public static int[] cache = new int[MAX_N + 1];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		Arrays.fill(cache, -1);
		
		int T = Integer.parseInt(br.readLine());
		for (int testCase = 1; testCase <= T; testCase++) {
			int n = Integer.parseInt(br.readLine());
			int answer = getNumberOfWays(n);
			sb.append(answer).append("\n");
		}
		
		System.out.print(sb.toString());
	}
	
	public static int getNumberOfWays(int n) {
		// base case : n = 1, 2, 3
		if (n == 1) return 1;
		if (n == 2) return 2;
		if (n == 3) return 4;
		
		// 캐시에 이미 계산되어 있는 경우
		if (cache[n] != -1) return cache[n];
		
		// n >= 4이고, 캐시에 계산되지 않아서 새로 계산하는 경우
		int ret = 0;
		// 첫 칸에 1 놓는 경우, 2 놓는 경우, 3 놓는 경우를 각각 더한다.
		ret += getNumberOfWays(n-1);
		ret += getNumberOfWays(n-2);
		ret += getNumberOfWays(n-3);
		
		return cache[n] = ret;
	}
}
