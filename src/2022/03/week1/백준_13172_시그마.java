// 17500KB, 184ms

package bj13172;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final long MOD = 1000000007L;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		int M = Integer.parseInt(br.readLine());
		
		long sigma = 0L;
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			long Ni = Long.parseLong(st.nextToken());
			long Si = Long.parseLong(st.nextToken());
			long gcdOfNS = gcd(Ni, Si);
			long N = Ni / gcdOfNS;
			long S = Si / gcdOfNS;
			
			sigma = modAdd(sigma, modDiv(S, N));
		}
		
		long answer = sigma;
		
		System.out.println(answer);

	} // end main

	/** 두 수의 최대공약수를 리턴 */
	public static long gcd(long a, long b) {
		if (a == 0L) {
			return b;
		}
		
		return gcd(b % a, a);
	}
	
	/** 모듈러에서의 a+b를 계산하여 리턴 */
	public static long modAdd(long a, long b) {
		return (a + b) % MOD;
	}
	
	/** 모듈러에서의 a*b를 계산하여 리턴 */
	public static long modMult(long a, long b) {
		return (a * b) % MOD;
	}
	
	/** 모듈러에서의 a/b를 계산하여 리턴 */
	public static long modDiv(long a, long b) {
		return modMult(a, multInverse(b));
	}
	
	/** 모듈러에서의 a의 b승을 계산하여 리턴 */
	public static long modPow(long a, long b) {
		if (b == 0L) {
			return 1L;
		}
		
		long half = modPow(a, b / 2L);
		if (b % 2L == 0L) {
			return modMult(half, half);
		}
		else {
			return modMult(modMult(half, half), a);
		}
	}
	
	/** 모듈러 곱셈에 대한 역원 계산 */
	public static long multInverse(long a) {
		return modPow(a, MOD - 2);
	}
}