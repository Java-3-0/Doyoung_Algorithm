// 13808KB, 136ms

package bj11502;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	static final int MAX_K = 1000;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// MAX_K 이하의 소수 미리 계산
		int[] primes = getPrimes();
		int len = primes.length;

		final int TESTS = Integer.parseInt(br.readLine());

		tcLoop: for (int testCase = 1; testCase <= TESTS; testCase++) {
			int K = Integer.parseInt(br.readLine());

			// 세 소수 쌍을 완전탐색하면서 답을 찾는다.
			for (int i = 0; i < len; i++) {
				for (int j = i; j < len; j++) {
					for (int k = j; k < len; k++) {
						int p1 = primes[i];
						int p2 = primes[j];
						int p3 = primes[k];

						// 답을 찾은 경우 스트링빌더에 추가
						if (p1 + p2 + p3 == K) {
							sb.append(p1).append(" ").append(p2).append(" ").append(p3).append("\n");
							continue tcLoop;
						}
					}
				}
			}

			// 불가능하다면 0을 출력 스트링빌더에 추가
			sb.append(0).append("\n");

		} // end for testCase

		System.out.print(sb.toString());

	} // end main

	public static int[] getPrimes() {
		boolean[] isPrime = new boolean[MAX_K + 1];
		Arrays.fill(isPrime, true);

		// 0과 1은 소수가 아니다
		isPrime[0] = false;
		isPrime[1] = false;

		// 2 이상의 수에 대해 에라토스테네스의 체 수행
		for (int p = 2; p <= MAX_K; p++) {
			if (isPrime[p]) {
				for (int num = p + p; num <= MAX_K; num += p) {
					isPrime[num] = false;
				}
			}
		}

		// 소수인 것들을 리스트에 담는다.
		List<Integer> primes = new ArrayList<>();
		for (int num = 0; num <= MAX_K; num++) {
			if (isPrime[num]) {
				primes.add(num);
			}
		}

		// 리스트를 배열로 변환
		int size = primes.size();
		int[] ret = new int[size];
		for (int i = 0; i < size; i++) {
			ret[i] = primes.get(i);
		}

		return ret;
	}

}