// 12872KB, 116ms

package bj3980;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int PLAYERS = 11;
	static final int MAX_STAT = 100;

	static int[][] stats = new int[PLAYERS][PLAYERS];
	static boolean[] isPicked = new boolean[PLAYERS];
	static int answer = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// 메모리 초기화
			initMemory();

			// 입력
			for (int player = 0; player < PLAYERS; player++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int position = 0; position < PLAYERS; position++) {
					stats[player][position] = Integer.parseInt(st.nextToken());
				}
			}

			// 선수 배치 시도
			placePlayer(0, 0);

			// 최적 결과를 출력 스트링빌더에 추가
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** playerNum번 플레이어부터 마지막 플레이어까지 포지션에 배치 시도 */
	public static void placePlayer(int playerNum, int accumStats) {
		// 순열이 완성된 경우
		if (playerNum == PLAYERS) {
			answer = Math.max(answer, accumStats);
			return;
		}

		// 순열 시도
		for (int position = 0; position < PLAYERS; position++) {
			if (!isPicked[position] && stats[playerNum][position] != 0) {
				isPicked[position] = true;
				placePlayer(playerNum + 1, accumStats + stats[playerNum][position]);
				isPicked[position] = false;
			}
		}
	}

	/** 전역변수 메모리 초기화 */
	public static void initMemory() {
		for (int i = 0; i < stats.length; i++) {
			Arrays.fill(stats[i], 0);
		}
		Arrays.fill(isPicked, false);
		answer = 0;
	}
}