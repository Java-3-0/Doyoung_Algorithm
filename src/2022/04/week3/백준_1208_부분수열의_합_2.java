// 84512KB, 1128ms

package bj1208;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static int N, S;
	static int[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, S 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		S = Integer.parseInt(st.nextToken());

		// 수열 입력
		seq = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		long answer = 0L;

		// 크기가 1인 수열인 경우 심플하게 정답 계산
		if (N == 1) {
			if (seq[0] == S) {
				answer = 1L;
			}
		}

		// 크기가 2 이상인 수열의 경우 분할 정복
		else {
			// 절반으로 분할해서 각각의 합을 모두 구한다
			int mid = (N - 1) / 2;
			List<Integer> leftSums = getSums(0, mid);
			List<Integer> rightSums = getSums(mid + 1, N - 1);

			Collections.sort(leftSums);
			Collections.sort(rightSums);

			int leftSize = leftSums.size();
			int rightSize = rightSums.size();

			int leftIdx = 0;
			int rightIdx = rightSize - 1;

			// leftSums의 각각 경우에 대해서 필요한 합이 rightSums에 몇 개 있는지 센다
			while (leftIdx < leftSize && rightIdx >= 0) {
				int sum1 = leftSums.get(leftIdx);
				int sum2 = rightSums.get(rightIdx);
				int sum = sum1 + sum2;

				if (sum == S) {
					long cnt1 = 1L;
					leftIdx++;

					long cnt2 = 1L;
					rightIdx--;

					while (leftIdx < leftSize && leftSums.get(leftIdx) == sum1) {
						cnt1++;
						leftIdx++;
					}

					while (rightIdx >= 0 && rightSums.get(rightIdx) == sum2) {
						cnt2++;
						rightIdx--;
					}

					answer += cnt1 * cnt2;

				} else if (sum < S) {
					leftIdx++;
				} else {
					rightIdx--;
				}

			}

			// 합이 0인 경우, 공집합인 부분수열의 경우의 수를 뺀다
			if (S == 0) {
				answer--;
			}

		} // end while

		System.out.println(answer);

	} // end main

	/** [left, right] 범위 내의 모든 부분수열에 대해 그 합을 리스트에 담아서 리턴 */
	public static List<Integer> getSums(int left, int right) {
		List<Integer> ret = new ArrayList<>();

		int len = right - left + 1;
		if (len <= 0) {
			return ret;
		}

		for (int bitMask = 0; bitMask < (1 << len); bitMask++) {
			int sum = 0;

			for (int i = 0; i < len; i++) {
				if ((bitMask & (1 << i)) != 0) {
					sum += seq[left + i];
				}
			}

			ret.add(sum);
		}

		return ret;
	}

}