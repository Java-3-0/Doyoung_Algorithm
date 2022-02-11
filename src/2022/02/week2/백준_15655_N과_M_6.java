// 17768KB, 224ms

package baek15655;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static int N, M;
	public static int[] input;
	public static int[] combi;
	public static StringBuilder sb;

	public static void main(String[] args) {
		// N, M 입력
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		
		// 전역변수 레퍼런스 할당
		combi = new int[M];
		sb = new StringBuilder();
		input = new int[N];
		
		// 수열 입력
		for (int i = 0; i < N; i++) {
			input[i] = sc.nextInt();
		}
		sc.close();
		
		// 입력받은 수열 오름차순 정렬
		Arrays.sort(input);

		// 조합 생성
		combination(0, 0);

		// 출력
		System.out.print(sb.toString());
	}

	/** 조합을 계산해서 스트링빌더에 담는다 */
	public static void combination(int start, int count) {
		if (count == M) {
			for (int i = 0; i < M; i++) {
				sb.append(combi[i]).append(" ");
			}
			sb.append("\n");
			return;
		}

		for (int pickIdx = start; pickIdx < N; pickIdx++) {
			combi[count] = input[pickIdx];
			combination(pickIdx + 1, count + 1);			
		}
	}
}
