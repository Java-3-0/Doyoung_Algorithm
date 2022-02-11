// 62652KB, 2072ms

package baek15649;

import java.util.Scanner;

public class Main {
	public static int N, M;
	public static boolean[] isSelected;
	public static int[] permu;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		N = sc.nextInt();
		M = sc.nextInt();
		isSelected = new boolean[N + 1];
		permu = new int[N];

		permutation(0);
	}

	public static void permutation(int count) {
		if (count == M) {
			for (int i = 0; i < M; i++) {
				System.out.print(permu[i] + " ");
			}
			
			System.out.println();
			return;
		}

		for (int num = 1; num <= N; num++) {
			if (isSelected[num]) {
				continue;
			}

			permu[count] = num;
			isSelected[num] = true;
			permutation(count + 1);
			isSelected[num] = false;
		}
	}
}
