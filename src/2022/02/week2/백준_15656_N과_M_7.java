// 126908KB, 748ms

package baek15656;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static int N, M;
	public static int[] input;
	public static int[] permu;
	public static StringBuilder sb;

	public static void main(String[] args) {
		// N, M 입력
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();
		
		// 전역변수 레퍼런스 할당
		permu = new int[M];
		sb = new StringBuilder();
		input = new int[N];
		
		// 수열 입력
		for (int i = 0; i < N; i++) {
			input[i] = sc.nextInt();
		}
		sc.close();
		
		// 입력받은 수열 오름차순 정렬
		Arrays.sort(input);

		// 중복순열 생성
		permutation(0);

		// 출력
		System.out.print(sb.toString());
	}

	/** 중복순열을 계산해서 스트링빌더에 담는다 */
	public static void permutation(int count) {
		if (count == M) {
			for (int i = 0; i < M; i++) {
				sb.append(permu[i]).append(" ");
			}
			sb.append("\n");
			return;
		}

		for (int pickIdx = 0; pickIdx < N; pickIdx++) {
			permu[count] = input[pickIdx];
			permutation(count + 1);
		}
	}
}

