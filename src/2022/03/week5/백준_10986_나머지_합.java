// 167856KB, 456ms

package bj10986;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_N = 1000000, MAX_M = 1000;

	static int N, M;
	static int[] seq, psum;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 전역변수 메모리 할당
		seq = new int[N + 1];
		psum = new int[N + 1];

		// 수열 입력받고 누적합도 계산 (누적합은 MOD M 해서 계산)
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1, sum = 0; i <= N; i++) {
			int input = Integer.parseInt(st.nextToken());
			sum += input;
			sum %= M;

			seq[i] = input;
			psum[i] = sum;
		}

		// psum에 존재하는 각 수들의 개수를 카운팅
		int[] counts = new int[M];
		for (int num : psum) {
			counts[num]++;
		}

		// 각 카운팅된 수 cnt마다 2개를 선택할 수 있으므로 cntC2들의 합이 정답이 된다.
		long answer = 0L;
		for (int cnt : counts) {
			answer += comb2(cnt);
		}

		// 정답 출력
		System.out.println(answer);

	} // end main

	/** nC2를 계산해서 리턴 */
	public static long comb2(int n) {
		if (n < 2) {
			return 0L;
		}

		return (long) n * (long) (n - 1) / 2L;
	}

}