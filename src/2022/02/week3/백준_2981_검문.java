// 12780KB, 1448ms

package bj2981;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Main {

	/*
	 * a1 = k1 * M + R, a2 = k2 * M + R일 때, a1 - a2 = (k1 - k2) * M이다. 즉 모든 쌍에 대해 그
	 * 차가 M의 배수이다. 이 차들의 최대공약수를 구하고, 그 최대공약수의 약수들을 구하면 된다.
	 */
	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		int[] arr = new int[N];
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(br.readLine());
		}
		br.close();

		// 오름차순 정렬
		Arrays.sort(arr);

		// 수열의 원소를 2개씩 짝지어서 그 차를 계산
		Queue<Integer> diff = new LinkedList<>();
		for (int i = 1; i < N; i++) {
			diff.offer(arr[i] - arr[i - 1]);
		}

		// 차들의 최대공약수를 계산
		while (diff.size() >= 2) {
			diff.offer(gcd(diff.poll(), diff.poll()));
		}

		// 이 최대공약수의 2 이상의 모든 약수들을 출력에 저장
		StringBuilder sb = new StringBuilder();
		int gcdOfAll = diff.poll();
		for (int num = 2; num <= gcdOfAll; num++) {
			if (gcdOfAll % num == 0) {
				sb.append(num).append(" ");
			}
		}
		sb.append("\n");

		// 출력
		System.out.print(sb.toString());
	}

	/** 두 수의 최대공약수를 리턴 */
	public static int gcd(int a, int b) {
		if (a > b)
			return gcd(b, a);

		if (a == 0)
			return b;

		return gcd(b % a, a);
	}
}
