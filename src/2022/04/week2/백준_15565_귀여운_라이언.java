// 77748KB, 348ms

package bj15565;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int INF = 987654321;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		// 수열 입력
		st = new StringTokenizer(br.readLine(), " ");
		int[] seq = new int[N];
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		// 초기 상태
		int left = 0;
		int right = 0;
		int cnt = 0;
		if (seq[0] == 1) {
			cnt++;
		}

		// 투포인터로 정답 구하기
		int minLen = 987654321;

		while (right < N) {
			if (right < left) {
				right++;
				continue;
			}

			if (cnt >= K) {
				int len = right - left + 1;
				minLen = len < minLen ? len : minLen;
				if (seq[left] == 1) {
					cnt--;
				}
				left++;

			} else {
				right++;
				if (right >= N) {
					break;
				}

				if (seq[right] == 1) {
					cnt++;
				}
			}
		}

		// 정답 출력
		if (minLen == INF) {
			System.out.println(-1);
		} else {
			System.out.println(minLen);
		}

	} // end main

}