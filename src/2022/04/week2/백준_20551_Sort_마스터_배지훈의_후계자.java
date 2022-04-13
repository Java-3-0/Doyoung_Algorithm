// 59572KB, 704ms

package bj20551;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = (int) (2 * 1e5);
	static final int MAX_M = (int) (2 * 1e5);
	static final int FAIL = -1;

	static int N, M;
	static int[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 수열 입력
		seq = new int[N];
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(br.readLine());
		}

		// 수열 오름차순 정렬
		Arrays.sort(seq);

		// M개의 질문 처리
		for (int i = 0; i < M; i++) {
			int D = Integer.parseInt(br.readLine());
			int answer = getFirstAppearance(D);
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 가장 먼저 나타나는 위치를 리턴 */
	private static int getFirstAppearance(int D) {
		int left = 0;
		int right = N - 1;

		while (left < right) {
			int mid = (left + right) / 2;

			if (seq[mid] >= D) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}

		if (seq[right] == D) {
			return right;
		} else {
			return FAIL;
		}
	}

}