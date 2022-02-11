// 26012KB, 380ms

package baek15649;

import java.util.Scanner;

public class Main {
	public static int N, M;
	public static boolean[] isSelected;
	public static int[] permu;
	public static StringBuilder sb;

	public static void main(String[] args) {
		// 입력
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		sc.close();
		
		// 전역변수 레퍼런스 할당
		isSelected = new boolean[N + 1];
		permu = new int[N];
		sb = new StringBuilder();
		
		permutation(0);
		
		System.out.print(sb.toString());
	}

	public static void permutation(int count) {
		if (count == M) {
			for (int i = 0; i < M; i++) {
				sb.append(permu[i]).append(" ");
			}
			sb.append("\n");
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
