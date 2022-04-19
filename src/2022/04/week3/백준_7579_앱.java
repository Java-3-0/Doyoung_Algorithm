// 11972KB, 112ms

package bj7579;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100, MAX_M = (int) 1e7, MAX_C = 100;
	static final int MAX_COST = MAX_N * MAX_C;
	static final int EMPTY = -1;
	
	static int N, M;
	static int[] memories = new int[MAX_N];
	static int[] costs = new int[MAX_N];
	
	/** dp[i]: 비용 i로 확보 가능한 최대 메모리를 저장 */
	static int[] dp = new int[MAX_COST + 1];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 메모리 사용량 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			memories[i] = Integer.parseInt(st.nextToken());
		}
		
		// 비용 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			costs[i] = Integer.parseInt(st.nextToken());
		}
		
		// dp 배열 초기화
		Arrays.fill(dp, EMPTY);
		dp[0] = 0;
		
		// N개의 앱에 대해 dp 수행
		for (int app = 0; app < N; app++) {
			for (int k = MAX_COST; k >= 0; k--) {
				int prev = k - costs[app];
				if (prev >= 0 && dp[prev] != EMPTY) {
					dp[k] = Math.max(dp[k], dp[prev] + memories[app]);
				}
			}
		}
		
		// 최소 비용 찾아서 출력
		for (int i = 0; i <= MAX_COST; i++) {
			if (dp[i] >= M) {
				System.out.println(i);
				break;
			}
		}
		
	} // end main

}