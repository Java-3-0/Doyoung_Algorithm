// 16852KB, 924ms

package bj15661;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	/** 최대 선수 수 */
	public static final int MAX_N = 20;
	/** 팀에 선수 두명이 함께 속했을 때 팀에 더해지는 능력치를 나타낸 2차원 배열 */
	public static int[][] stats = new int[MAX_N][MAX_N];
	/** 선수 선택 여부를 나타낼 boolean 배열 */
	public static boolean[] isPicked = new boolean[MAX_N];

	/** 선수 수 */
	public static int N;
	/** 구하려는 답의 최소값 */
	public static int minDiff;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 전역변수 초기화
		minDiff = Integer.MAX_VALUE;
		Arrays.fill(isPicked, false);

		// 입력
		N = Integer.parseInt(br.readLine());

		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < N; x++) {
				int input = Integer.parseInt(st.nextToken());
				stats[y][x] = input;
			}
		}

		// N명 중 N/2명을 고르는 모든 조합을 만들어보며 minDiff 값 갱신
		combination(0, 0);

		// 답 출력
		System.out.println(minDiff);
	}

	/** N명의 선수들 중 1 ~ N - 1명의 선수를을 고르는 조합을 완전탐색하면서 minDiff 값 갱신 */
	public static void combination(int hereIdx, int cnt) {
		// 팀이 완성되었을 경우
		if (hereIdx == N) {
			// 0명이거나 N명인 경우 제외
			if (cnt == N || cnt == 0) {
				return;
			}
			
			int sum1 = 0; // A팀의 스탯 합
			int sum2 = 0; // B팀의 스탯 합

			for (int y = 0; y < N; y++) {
				for (int x = 0; x < N; x++) {
					// 두 선수 모두 이 팀인 경우
					if (isPicked[y] && isPicked[x]) {
						sum1 += stats[y][x];
						// 두 선수 모두 이 팀이 아닌 경우
					} else if (!isPicked[y] && !isPicked[x]) {
						sum2 += stats[y][x];
					}
				}
			}

			// 차이 계산
			int diff = (sum1 <= sum2) ? sum2 - sum1 : sum1 - sum2;

			// 차이 최소값 갱신
			if (diff < minDiff) {
				minDiff = diff;
			}

			return;
		}

		// 팀을 완성하지 못하고 끝까지 간 경우
		if (hereIdx == N) {
			return;
		}

		// 이 선수를 넣는 경우
		isPicked[hereIdx] = true;
		combination(hereIdx + 1, cnt + 1);

		// 이 선수를 넣지 않는 경우
		isPicked[hereIdx] = false;
		combination(hereIdx + 1, cnt);
	}

}