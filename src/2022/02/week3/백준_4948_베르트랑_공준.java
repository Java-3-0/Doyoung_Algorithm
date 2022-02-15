package bj4948;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_NUM = 123456 * 2;
	public static boolean[] isPrime = new boolean[MAX_NUM + 1];
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		// isPrime 배열 계산
		calcIsPrime();
		
		
		while (true) {
			int N = Integer.parseInt(br.readLine());
			// 0이 나오면 입력의 끝이므로 루프 종료
			if (N == 0) {
				break;
			}

			// n과 2n 사이 소수 개수 출력에 저장
			sb.append(countPrimes(N, 2 * N)).append("\n");
		}
		
		System.out.print(sb.toString());
	}

	/** isPrime 배열을 계산 */
	public static void calcIsPrime() {
		Arrays.fill(isPrime, true);
		isPrime[0] = false;
		isPrime[1] = false;

		for (int num = 2; num <= MAX_NUM; num++) {
			if (!isPrime[num]) {
				continue;
			}

			for (int x = num + num; x <= MAX_NUM; x += num) {
				isPrime[x] = false;
			}
		}
	}
	
	public static int countPrimes (int from, int to) {
		int ret = 0;
		for (int num = from + 1; num <= to; num++) {
			if (isPrime[num]) {
				ret++;
			}
		}
		
		return ret;
	}
}
