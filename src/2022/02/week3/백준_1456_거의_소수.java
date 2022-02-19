// 86132KB, 676ms

package bj1456;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		long A = Long.parseLong(st.nextToken());
		long B = Long.parseLong(st.nextToken());

		// 루트 B 이하의 모든 소수를 계산
		int sqrtB = (int) Math.sqrt(B);
		boolean[] isPrime = eratosthenes(sqrtB);

		List<Integer> primeList = new ArrayList<>();
		for (int num = 2; num <= sqrtB; num++) {
			if (isPrime[num]) {
				primeList.add(num);
			}
		}

		Set<Long> almostPrimes = getAlmostPrimes(primeList, A, B);

		int answer = almostPrimes.size();

		System.out.println(answer);
	}

	public static Set<Long> getAlmostPrimes(List<Integer> primeList, long A, long B) {
		Set<Long> ret = new TreeSet<>();

		for (int prime : primeList) {
			for (int exponent = 2; exponent <= 100; exponent++) {
				double d = Math.pow(prime, exponent);
				if ((double) Long.MAX_VALUE < d) {
					break;
				}

				if (A <= (double) d && (double) d <= B) {
					ret.add((long) d);
				}
			}
		}

		return ret;
	}

	/** N까지의 모든 수에 대해 에레토스테네스의 체를 수행한 결과를 리턴 */
	public static boolean[] eratosthenes(int N) {
		// isPrime 배열 초기화
		boolean[] isPrime = new boolean[N + 1];
		Arrays.fill(isPrime, true);
		// 0과 1은 소수가 아니다
		isPrime[0] = false;
		isPrime[1] = false;

		// 2이상의 모든 p에 대해서, p가 소수라면 p의 배수들을 모두 지운다.
		for (int p = 2; p <= N; p++) {
			if (!isPrime[p]) {
				continue;
			}

			for (int num = p + p; num <= N; num += p) {
				isPrime[num] = false;
			}
		}

		return isPrime;
	}
}
