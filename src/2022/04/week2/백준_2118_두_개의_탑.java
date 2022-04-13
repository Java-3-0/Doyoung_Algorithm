// 17716KB, 172ms

package bj2118;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 지점 개수 입력
		int N = Integer.parseInt(br.readLine());

		// 지점 사이 거리 입력
		int [] seq = new int[2 * N - 1];
		for (int i = 0; i < N - 1; i++) {
			int input = Integer.parseInt(br.readLine());
			seq[i] = input;
			seq[i + N] = input;
		}
		seq[N - 1] = Integer.parseInt(br.readLine());

		// 전체 합계의 절반 계산
		int sumAll = 0;
		for (int i = 0; i < N; i++) {
			sumAll += seq[i];
		}
		int half = sumAll / 2;

		int left = 0;
		int right = 0;
		int dist = seq[0];
		int maxDist = 0;

		while (left < N) {
			if (dist <= half) {
				maxDist = maxDist < dist ? dist : maxDist;

				if (right == N - 1) {
					break;
				}
				dist += seq[++right];
			} else {
				dist -= seq[left++];
			}

		}
		
		System.out.println(maxDist);

	} // end main

}