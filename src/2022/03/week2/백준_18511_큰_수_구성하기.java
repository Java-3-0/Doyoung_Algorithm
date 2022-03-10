// 11820KB, 356ms

package bj18511;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		// 사용 가능한 원소들 입력
		st = new StringTokenizer(br.readLine(), " ");
		Set<Integer> usable = new HashSet<>();
		for (int i = 0; i < K; i++) {
			usable.add(Integer.parseInt(st.nextToken()));
		}

		// 정답 계산
		int answer = getMaxNumPossible(N, usable);

		// 출력
		System.out.println(answer);

	} // end main

	public static int getMaxNumPossible(int N, Set<Integer> usable) {
		for (int i = N; i >= 1; i--) {
			if (isPossible(i, usable)) {
				return i;
			}
		}

		return -1;
	}

	public static boolean isPossible(int num, Set<Integer> usable) {
		while (num > 0) {
			int digit = num % 10;
			num /= 10;

			if (!usable.contains(digit)) {
				return false;
			}
		}

		return true;
	}
}