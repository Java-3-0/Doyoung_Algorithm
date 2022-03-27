// 42232KB, 364ms

package bj2015;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 200000;
	static final int MAX_K = 2000000000;

	static int N, K;
	static long[] seq;
	static long[] psum;
	static Map<Long, Long> counts = new HashMap<>();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		// 메모리 할당
		seq = new long[N + 1];
		psum = new long[N + 1];

		// 수열 입력받고 부분합 계산
		st = new StringTokenizer(br.readLine(), " ");
		long sum = 0L;
		for (int i = 1; i <= N; i++) {
			long input = Long.parseLong(st.nextToken());
			sum += input;

			seq[i] = input;
			psum[i] = sum;
		}

		// 합이 K인 부분합 개수 파악
		long answer = 0L;
		put(psum[0]);
		for (int i = 1; i <= N; i++) {
			// i보다 왼쪽 칸 중 psum이 (psum[i] - K)인 것들의 수를 찾는다.
			long here = psum[i];
			long needed = here - K;
			answer += get(needed);
			put(here);
		}

		// 출력
		System.out.println(answer);

	} // end main

	// 맵에 n을 하나 카운트한다.
	public static void put(long n) {
		if (counts.containsKey(n)) {
			counts.put(n, counts.get(n) + 1L);
		} else {
			counts.put(n, 1L);
		}
	}

	// 맵에서 n이 몇 개 카운트되었는지 가져온다.
	public static long get(long n) {
		if (!counts.containsKey(n)) {
			return 0L;
		} else {
			return counts.get(n);
		}
	}

}