// 18368KB, 252ms

package baek15650;

import java.util.Scanner;

public class Main {

	public static int N, M;
	public static int[] comb;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		N = sc.nextInt();
		M = sc.nextInt();
		comb = new int[M];

		combination(1, 0);
	}

	public static void combination(int startNum, int cnt) {
		if (cnt == M) {
			for (int i = 0; i < M; i++) {
				System.out.print(comb[i] + " ");
			}
			System.out.println();
			return;
		}

		for (int pick = startNum; pick <= N; pick++) {
			comb[cnt] = pick;
			combination(pick + 1, cnt + 1);
		}
	}
}
