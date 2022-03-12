// 12680KB, 428ms

package bj1503;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000;
	static final int MAX_M = 50;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		// 제외할 수들 입력
		st = new StringTokenizer(br.readLine(), " ");
		Set<Integer> excludedNums = new HashSet<>();
		for (int i = 0; i < M; i++) {
			excludedNums.add(Integer.parseInt(st.nextToken()));
		}

		// 제외하지 않은 수들
		int[] availableNums = new int[MAX_N + 1 - M];
		for (int num = 1, idx = 0; num <= MAX_N + 1; num++) {
			if (!excludedNums.contains(num)) {
				availableNums[idx] = num;
				idx++;
			}
		}

		// N - xyz의 절댓값의 최소치를 구한다.
		int minDiff = Integer.MAX_VALUE;
		int len = availableNums.length;
		for (int i = 0; i < len; i++) {
			for (int j = i; j < len; j++) {
				for (int k = j; k < len; k++) {
					int diff = Math.abs(N - availableNums[i] * availableNums[j] * availableNums[k]);
					minDiff = diff < minDiff ? diff : minDiff;
				}
			}
		}

		// 출력
		System.out.println(minDiff);
	}
}