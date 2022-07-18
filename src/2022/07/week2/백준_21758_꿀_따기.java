// 25996KB, 292ms

package boj21758;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000;

	static int N;
	static int[] seq = new int[MAX_N + 1];
	static int[] psums = new int[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 수열 크기 입력
		N = Integer.parseInt(br.readLine());

		// 수열 입력받고 부분합 계산
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1, sum = 0; i <= N; i++) {
			int input = Integer.parseInt(st.nextToken());
			sum += input;

			seq[i] = input;
			psums[i] = sum;
		}

		// 정답 계산 및 출력
		int answer = solve();

		System.out.println(answer);

	} // end main

	public static int solve() {
		int maxHoney = 0;

		// 꿀통을 맨 오른쪽에 두는 경우 -> 벌 하나는 맨 왼쪽에서 출발, 다른 하나는 모든 위치를 시도
		for (int i = 2; i <= N - 1; i++) {
			int bee1 = getRangeSum(2, N) - seq[i];
			int bee2 = getRangeSum(i + 1, N);
			int honey = bee1 + bee2;
			maxHoney = Math.max(maxHoney, honey);
		}

		// 꿀통을 맨 왼쪽에 두는 경우 -> 벌 하나는 맨 오른쪽에서 출발, 다른 하나는 모든 위치를 시도
		for (int i = 2; i <= N - 1; i++) {
			int bee1 = getRangeSum(1, N - 1) - seq[i];
			int bee2 = getRangeSum(1, i - 1);
			int honey = bee1 + bee2;
			maxHoney = Math.max(maxHoney, honey);
		}

		// 꿀통을 그 외의 위치에 두는 경우 -> 벌 하나는 맨 왼쪽에서 출발, 다른 하나는 맨 오른쪽에서 출발
		for (int i = 2; i <= N - 1; i++) {
			int bee1 = getRangeSum(2, i);
			int bee2 = getRangeSum(i, N - 1);
			int honey = bee1 + bee2;
			maxHoney = Math.max(maxHoney, honey);
		}

		return maxHoney;
	}
	
	/** seq에서 [left, right] 구간의 구간합을 구해서 리턴 */
	public static int getRangeSum(int left, int right) {
		return psums[right] - psums[left - 1];
	}

}