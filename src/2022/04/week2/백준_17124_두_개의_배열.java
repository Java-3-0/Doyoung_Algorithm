// 266472KB, 1136ms

package bj17124;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static int M, N;
	static int[] A, B;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());
		for (int tc = 1; tc <= TESTS; tc++) {
			// N, M 입력
			st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());

			// 수열 메모리 할당
			A = new int[N];
			B = new int[M];

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

			// 수열 오름차순 정렬
			Arrays.sort(A);
			Arrays.sort(B);

			// 투포인터
			int idxB = 0;
			long answer = 0L;
			for (int num : A) {
				while (idxB < M - 1 && Math.abs(B[idxB] - num) > Math.abs(B[idxB + 1] - num)) {
					idxB++;
				}

				answer += (long) B[idxB];

			}

			// 출력 스트링빌더에 정답 추가
			sb.append(answer).append("\n");
		}
		
		// 출력
		System.out.print(sb.toString());

	} // end main

}