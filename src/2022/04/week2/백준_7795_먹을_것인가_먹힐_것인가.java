// 37528KB, 348ms

package bj7795;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// N, M 입력
			st = new StringTokenizer(br.readLine(), " ");
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());

			int[] A = new int[N];
			int[] B = new int[M];

			// 수열 A 입력
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < N; i++) {
				A[i] = Integer.parseInt(st.nextToken());
			}

			// 수열 B 입력
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < M; i++) {
				B[i] = Integer.parseInt(st.nextToken());
			}

			// 각 수열 오름차순 정렬
			Arrays.sort(A);
			Arrays.sort(B);

			// 정답 계산
			int answer = getCounts(A, B);

			// 출력 스트링빌더에 추가
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 문제의 조건에 맞는 쌍의 개수 리턴 */
	public static int getCounts(int[] A, int[] B) {
		int lenA = A.length;
		int lenB = B.length;

		int ret = 0;
		int idx2 = 0;
		for (int idx1 = 0; idx1 < lenA; idx1++) {
			while (idx2 < lenB && A[idx1] > B[idx2]) {
				idx2++;
			}

			ret += idx2;
		}

		return ret;
	}

}