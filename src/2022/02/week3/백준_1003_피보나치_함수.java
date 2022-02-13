// 11416KB, 80ms

package bj1003;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	public static final int MAX_N = 40;

	public static int[] cacheZeros = new int[MAX_N + 1];
	public static int[] cacheOnes = new int[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// 캐시의 모든 공간을 -1로 초기화
		Arrays.fill(cacheZeros, -1);
		Arrays.fill(cacheOnes, -1);

		int T = Integer.parseInt(br.readLine());

		for (int t = 0; t < T; t++) {
			int N = Integer.parseInt(br.readLine());
			sb.append(numZeros(N)).append(" ").append(numOnes(N)).append("\n");
		}

		System.out.print(sb.toString());

	}

	/** fibo(i)를 수행할 때 호출하는 fibo(0)의 횟수를 리턴 */
	public static int numZeros(int i) {
		// base case : fibo(0), fibo(1)
		if (i == 0)
			return 1;
		if (i == 1)
			return 0;

		// 캐시에 이미 계산해 둔 경우
		if (cacheZeros[i] != -1)
			return cacheZeros[i];

		// 새로 계산해야 하는 경우
		return cacheZeros[i] = numZeros(i - 1) + numZeros(i - 2);
	}

	/** fibo(i)를 수행할 때 호출하는 fibo(1)의 횟수를 리턴 */
	public static int numOnes(int i) {
		// base case : fibo(0), fibo(1)
		if (i == 0)
			return 0;
		if (i == 1)
			return 1;

		// 캐시에 이미 계산해 둔 경우
		if (cacheOnes[i] != -1)
			return cacheOnes[i];

		// 새로 계산해야 하는 경우
		return cacheOnes[i] = numOnes(i - 1) + numOnes(i - 2);
	}
}
