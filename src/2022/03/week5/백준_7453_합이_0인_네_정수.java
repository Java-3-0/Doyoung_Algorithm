// 152064KB, 3712ms

package bj7453;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 4000;
	static int N;
	static int[] A, B, C, D;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 배열 크기 입력
		N = Integer.parseInt(br.readLine());

		// 배열 공간 할당
		A = new int[N];
		B = new int[N];
		C = new int[N];
		D = new int[N];

		// 배열 입력
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			A[i] = Integer.parseInt(st.nextToken());
			B[i] = Integer.parseInt(st.nextToken());
			C[i] = Integer.parseInt(st.nextToken());
			D[i] = Integer.parseInt(st.nextToken());
		}

		// 정답 계산
		long answer = getNumberOfWays();

		// 출력
		System.out.println(answer);

	} // end main

	public static long getNumberOfWays() {
		int size = N * N;
		int[] AB = new int[size];
		int[] CD = new int[size];

		// 2개씩 더한 합 계산
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				AB[N * i + j] = A[i] + B[j];
			}
		}

		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				CD[N * i + j] = C[i] + D[j];
			}
		}

		// 오름차순 정렬
		Arrays.sort(AB);
		Arrays.sort(CD);
		
		// 투포인터를 사용해서 정답 수 계산
		int front = 0;
		int back = size - 1;

		long answer = 0L;

		while (front < size && back >= 0) {
			int sum1 = AB[front];
			int sum2 = CD[back];
			
			int totalSum = sum1 + sum2;
			if (totalSum == 0) {
				long cnt1 = 1L;
				long cnt2 = 1L;

				front++;
				back--;

				while (front < size && AB[front] == sum1) {
					front++;
					cnt1++;
				}
				while (back >= 0 && CD[back] == sum2) {
					back--;
					cnt2++;
				}

				answer += cnt1 * cnt2;

			} else if (totalSum < 0) {
				front++;
			} else {
				back--;
			}

		}

		return answer;
	}

}