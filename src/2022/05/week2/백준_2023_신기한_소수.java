// 11532KB, 76ms

package bj2023;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static final int MAX_N = 8;

	static int N;
	static int[] permu;
	static int[] startDigits = { 2, 3, 5, 7 };
	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 자리수 입력
		N = Integer.parseInt(br.readLine());
		permu = new int[N];

		// 가능한 첫 자리 숫자들에 대해서, 나머지 자리들 시도
		for (int start : startDigits) {
			permu[0] = start;
			permutation(1, start);
		}

		// 정답 출력
		System.out.print(sb.toString());

	} // end main

	/** 가능한 모든 순열을 완전탐색하면서 sb에 정답을 담는다 */
	private static void permutation(int startIdx, int accumNum) {
		// 실패한 경우
		if (!isPrime(accumNum)) {
			return;
		}

		// 끝까지 성공한 경우
		if (startIdx == N) {
			for (int num : permu) {
				sb.append(num);
			}
			sb.append("\n");
			return;
		}

		// 더 만들어보는 경우
		for (int num = 1; num <= 9; num += 2) {
			permu[startIdx] = num;
			permutation(startIdx + 1, accumNum * 10 + num);
		}
	}

	/** n의 소수 여부를 리턴 */
	public static boolean isPrime(int n) {
		if (n < 2) {
			return false;
		}

		int sqrt = (int) Math.floor(Math.sqrt(n));

		for (int i = 2; i <= sqrt; i++) {
			if (n % i == 0) {
				return false;
			}
		}

		return true;
	}
}