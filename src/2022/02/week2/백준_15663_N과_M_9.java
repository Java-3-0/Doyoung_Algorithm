// 23712KB, 284ms

package baek15654;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
	public static final int MAX_NUM = 10000;

	public static int N, M;
	public static int[] input;
	public static int[] permu;
	public static int[] canUse;
	public static StringBuilder sb;

	public static void main(String[] args) {
		// N, M 입력
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		M = sc.nextInt();

		// 전역변수 레퍼런스 할당
		permu = new int[M];
		canUse = new int[MAX_NUM + 1];
		sb = new StringBuilder();
		input = new int[N];

		// 수열 입력
		for (int i = 0; i < N; i++) {
			int num = sc.nextInt();
			canUse[num]++;
			input[i] = num;
		}
		sc.close();

		// 입력받은 수열 오름차순 정렬
		Arrays.sort(input);

		// 순열 생성
		permutation(0);

		// 출력
		System.out.print(sb.toString());
	}

	/** 순열을 계산해서 스트링빌더에 담는다 */
	public static void permutation(int count) {
		if (count == M) {
			for (int i = 0; i < M; i++) {
				sb.append(permu[i]).append(" ");
			}
			sb.append("\n");
			return;
		}

		for (int pickIdx = 0; pickIdx < N; pickIdx++) {
			int num = input[pickIdx];
            // 쓸 수 있는 개수가 더 이상 없는 경우
			if (canUse[num] <= 0)
				continue;

            // 이전 idx와 이번 idx에 들은 값이 같아서 한 번만 세고 스킵해야 하는 경우
			if (pickIdx >= 1 && input[pickIdx] == input[pickIdx - 1])
				continue;

            // 재귀 호출 부분
			canUse[num]--;
			permu[count] = num;
			permutation(count + 1);
			canUse[num]++;
		}
	}
}
