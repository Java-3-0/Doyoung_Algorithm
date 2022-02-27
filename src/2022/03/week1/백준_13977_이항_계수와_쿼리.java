// 76712KB, 600ms

package bj13977;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static final long MOD = 1000000007L;
	public static final int MAX_N = 4000000;

	public static long[] factorial = new long[MAX_N + 1];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 1부터 400만까지의 팩토리얼 미리 계산
		precalcFactorials();

		// 쿼리 수 입력		
		int M = Integer.parseInt(br.readLine());

		// 각 쿼리 처리
		for (int m = 0; m < M; m++) {
			// N, K 입력
			st = new StringTokenizer(br.readLine(), " ");
			int N = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());
			
			// 이항 계수 계산
			long answer = modularDivide(modularDivide(factorial[N], factorial[K]), factorial[N - K]);
			
			// 결과를 출력 스트링빌더에 추가
			sb.append(answer).append("\n");
		}

		// 출력		
		System.out.print(sb.toString());
		
	} // end main

	/** fact(0) 부터 fact(MAX_N)까지를 미리 계산 */
	public static void precalcFactorials() {
		factorial[0] = 1L;
		factorial[1] = 1L;
		
		for (int i = 2; i <= MAX_N; i++) {
			factorial[i] = (factorial[i-1] * i) % MOD;
		}
	}
	
	/** 모듈러 연산에서의 A / B를 A * B^(-1)로 구해서 리턴 */
	public static long modularDivide(long A, long B) {
		return (A * getMultInv(B)) % MOD;
	}

	/** MOD에서 a의 곱셈 역원을 리턴 */
	public static long getMultInv(long A) {
		return pow(A, MOD - 2L);
	}

	/** a^b를 MOD로 나눈 나머지를 구해서 리턴 */
	public static long pow(long A, long B) {
		if (B == 0L)
			return 1L;

		long half = pow(A, B / 2L);

		long halfSqr = (half * half) % MOD;
		if (B % 2L == 0L) {
			return halfSqr;
		}

		else {
			return (halfSqr * A) % MOD;
		}
	}
}