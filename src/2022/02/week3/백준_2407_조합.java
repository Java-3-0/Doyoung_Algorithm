// 11888KB, 80ms

package bj2407;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_N = 100;
	public static BigInteger[][] cache = new BigInteger[MAX_N + 1][MAX_N + 1];
	public static final BigInteger CACHE_EMPTY = new BigInteger("-1");

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
		
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		System.out.println(binaryCoefficient(N, K).toString());
	}

	public static BigInteger binaryCoefficient(int N, int K) {
		// base case
		if (K == 0 || K == N) {
			return new BigInteger("1");
		}
		
		// 캐시에 저장된 값이 있어서 그대로 사용하는 경우
		if (cache[N][K] != CACHE_EMPTY) {
			return cache[N][K];
		}

		// nCr = n-1Ck-1 + n-1Ck을 통해 직접 새로 계산하는 경우
		return cache[N][K] = (binaryCoefficient(N - 1, K - 1).add(binaryCoefficient(N - 1, K)));
	}

}