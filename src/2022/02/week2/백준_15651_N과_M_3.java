// 71765KB, 564ms

package baek15651;

import java.util.Scanner;

public class Main {
	public static int N, M;
	public static int[] permu;
	public static StringBuilder sb;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		sb = new StringBuilder();

		N = sc.nextInt();
		M = sc.nextInt();
		permu = new int[M];

		permutation(0);
		
		System.out.print(sb);
	}

	public static void permutation(int cnt) {
		if (cnt == M) {
			
			for (int num : permu) {
				sb.append(num).append(" ");
			}
			sb.append("\n");
			return;
		}

		for (int pick = 1; pick <= N; pick++) {
			permu[cnt] = pick;
			permutation(cnt + 1);
		}
	}
}
