// 11784KB, 88ms

package bj1059;

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

		// L 입력
		int L = Integer.parseInt(br.readLine());

		// S 입력
		Set<Integer> S = new HashSet<>();
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < L; i++) {
			S.add(Integer.parseInt(st.nextToken()));
		}

		// N 입력
		int N = Integer.parseInt(br.readLine());

		// 좋은 구간의 개수 계산
		int answer = getAnswer(S, N);

		// 출력
		System.out.println(answer);

	} // end main

	public static int getAnswer(Set<Integer> S, int N) {
		if (S.contains(N)) {
			return 0;
		}

		int maxSmaller = 0;
		int minBigger = 1001;
		for (int num : S) {
			if (num < N && maxSmaller < num) {
				maxSmaller = num;
			}
			if (num > N && num < minBigger) {
				minBigger = num;
			}
		}

		int left = maxSmaller + 1;
		int right = minBigger - 1;
		int ret = 0;
		for (int i = left; i <= right; i++) {
			for (int j = i + 1; j <= right; j++) {
				if (i <= N && N <= j) {
					ret++;
				}
			}
		}

		return ret;
	}

}