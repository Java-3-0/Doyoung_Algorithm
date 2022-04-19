// 18200KB, 240ms

package bj1450;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_N = 30;
	static final long MAX_C = (long) 1e9;

	static int N;
	static long C;
	static long[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 물건 개수, 가방 최대 무게 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		C = Long.parseLong(st.nextToken());

		// 각 물건의 무게 입력
		st = new StringTokenizer(br.readLine(), " ");
		seq = new long[N];
		for (int i = 0; i < N; i++) {
			seq[i] = Long.parseLong(st.nextToken());
		}

		// 반으로 나눠서 각각 가능한 합계들을 계산
		int mid = (N - 1) / 2;
		List<Long> leftSums = getSums(0, mid);
		List<Long> rightSums = getSums(mid + 1, N - 1);

		// 각 리스트 오름차순 정렬
		Collections.sort(leftSums);
		Collections.sort(rightSums);

		// 각 리스트 크기 계산
		int leftSize = leftSums.size();
		int rightSize = rightSums.size();

		// 시작 위치
		int leftIdx = 0;
		int rightIdx = rightSize - 1;

		// 투 포인터
		long answer = 0L;
		while (leftIdx < leftSize && rightIdx >= 0) {
			long leftVal = leftSums.get(leftIdx);
			long rightVal = rightSums.get(rightIdx);

			if (leftVal + rightVal > C) {
				rightIdx--;
			} else {
				answer += (rightIdx + 1);
				leftIdx++;
			}
		}

		// 정답 출력
		System.out.println(answer);

	} // end main

	/** [left, right] 범위의 물건들에서 가능한 모든 합계를 리턴 */
	public static List<Long> getSums(int left, int right) {
		List<Long> ret = new ArrayList<>();
		ret.add(0L);

		int len = right - left + 1;
		for (int bitMask = 1; bitMask < (1 << len); bitMask++) {
			long sum = 0L;
			for (int i = 0; i < len; i++) {
				if ((bitMask & (1 << i)) != 0) {
					sum += seq[left + i];
				}

			} // end for i

			if (sum <= C) {
				ret.add(sum);
			}

		} // end for bitMask

		return ret;
	}

}