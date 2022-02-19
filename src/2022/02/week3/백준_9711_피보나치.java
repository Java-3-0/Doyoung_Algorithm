package bj11444;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static final BigInteger ZERO = new BigInteger("0");
	public static final BigInteger ONE = new BigInteger("1");
	public static final BigInteger CACHE_EMPTY = new BigInteger("-1");
	
	public static final int MAX_P = 10000;
	public static BigInteger[] cache = new BigInteger[MAX_P + 1]; 
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		Arrays.fill(cache, CACHE_EMPTY);
		
		int T = Integer.parseInt(br.readLine());
		
		for (int testCase = 1; testCase <= T; testCase++) {
			st = new StringTokenizer(br.readLine(), " ");
			int P = Integer.parseInt(st.nextToken());
			BigInteger MOD = new BigInteger(st.nextToken());
			
			BigInteger answer = fibo(P).remainder(MOD);
			
			sb.append("Case #").append(testCase).append(": ").append(answer.toString()).append("\n");
		}
		
		System.out.print(sb.toString());
	}
	
	/** n번째 피보나치 수를 리턴하는 함수 */
	public static BigInteger fibo(int N) {
		// base case : fibo(0) = 0, fibo(1) = 1
		if (N == 0) return ZERO;
		if (N == 1) return ONE;
		
		if (cache[N] != CACHE_EMPTY) {
			return cache[N];
		}
		
		return cache[N] = fibo(N-1).add(fibo(N-2));
	}
}