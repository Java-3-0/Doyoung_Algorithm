// 20532KB, 248ms

package baek15652;

import java.util.Scanner;

public class Main {
	public static int N, M;
	public static int[] combi;
	public static StringBuilder sb;

	public static void main(String[] args) {
		// 입력
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		sc.close();

		// 전역변수 레퍼런스 할당
		combi = new int[N];
		sb = new StringBuilder();

		// 중복조합 생성
		combination(1, 0);

		// 출력
		System.out.print(sb.toString());
	}

	/** 중복조합을 계산해서 스트링빌더에 담는다 */
	public static void combination(int start, int count) {
		if (count == M) {
			for (int i = 0; i < M; i++) {
				sb.append(combi[i]).append(" ");
			}
			sb.append("\n");
			return;
		}

		for (int pick = start; pick <= N; pick++) {
			combi[count] = pick;
			combination(pick, count + 1);

		}
	}
}
