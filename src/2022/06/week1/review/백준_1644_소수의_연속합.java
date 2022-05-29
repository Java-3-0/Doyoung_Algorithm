// 27192KB, 196ms

package bj1644;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
	static final int MAX_N = 4000000;

	static boolean[] isPrime = new boolean[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 에라토스테네스의 체 수행
		precalcPrimes();

		// isPrime이 true인 것들을 리스트에 담는다.
		List<Integer> primeList = new ArrayList<>();
		for (int num = 2; num <= MAX_N; num++) {
			if (isPrime[num]) {
				primeList.add(num);
			}
		}

		// prefix sum 계산
		int size = primeList.size();
		long[] psums = new long[size + 1];
		psums[0] = 0L;

		long sum = 0L;
		for (int i = 0; i < size; i++) {
			sum += (long) primeList.get(i);
			psums[i + 1] = sum;
		}

		// N 입력
		long N = Long.parseLong(br.readLine());

		// 부분합이 N이 되는 경우의 수를 센다
		int left = 0;
		int right = 1;
		int cnt = 0;
		while (left <= size && right <= size) {
			if (left == right) {
				right++;
				continue;
			}

			long rangeSum = psums[right] - psums[left];

			// 합이 부족하면 오른쪽 커서를 옮긴다.
			if (rangeSum < N) {
				right++;
			}
			
			// 합이 같으면 cnt를 증가시키고, 오른쪽 커서를 옮긴다.
			else if (rangeSum == N) {
				cnt++;
				right++;
			}
			
			// 합이 넘치면 왼쪽 커서를 옮긴다.
			else {
				left++;
			}
		}

		// 출력
		System.out.println(cnt);

	} // end main

	/** MAX_N까지의 모든 수에 대해서 소수 여부를 미리 계산해서 isPrime[] 배열에 저장한다 */
	public static void precalcPrimes() {
		Arrays.fill(isPrime, true);
		isPrime[0] = false;
		isPrime[1] = false;

		// 에라토스테네스의 체를 수행
		int sqrt = (int) Math.floor(Math.sqrt(MAX_N));
		for (int num = 2; num <= sqrt; num++) {
			if (isPrime[num]) {
				for (int multiple = num + num; multiple <= MAX_N; multiple += num) {
					isPrime[multiple] = false;
				}
			}
		}
	}
}