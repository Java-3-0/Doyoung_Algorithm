// 12868KB, 108ms

package bj11047;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// 입력
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		long K = sc.nextLong();

		int[] coins = new int[N];
		for (int i = 0; i < N; i++) {
			coins[i] = sc.nextInt();
		}

		// 필요한 코인 개수 계산
		int count = 0;
		for (int i = N - 1; i >= 0; i--) {
			int coinVal = coins[i];
			count += K / coinVal;
			K = K % coinVal;

			if (K == 0) {
				break;
			}
		}

		// 출력
		System.out.println(count);
	}

}
