// 52748KB, 380ms

package boj24517;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_K = 10;
	static final int MAX_BITMASK = (1 << MAX_K) - 1;
	static final int MAX_CYCLE_LEN = MAX_K * (MAX_K + 1) / 2;

	static final int WIN = 1, LOSE = 0, CACHE_EMPTY = -1;
	static final String SWOON = "swoon";
	static final String RAARARAARA = "raararaara";

	static int[][] cache = new int[MAX_CYCLE_LEN + 1][MAX_BITMASK + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int Q = Integer.parseInt(br.readLine());

		initCache();

		for (int query = 0; query < Q; query++) {
			st = new StringTokenizer(br.readLine(), " ");
			int A = Integer.parseInt(st.nextToken());
			int B = Integer.parseInt(st.nextToken());
			int K = Integer.parseInt(st.nextToken());

			// A와 B의 차이
			int diff = B - A;

			// 모든 카드를 다 사용했을 때 더해지는 수
			int cycleLen = K * (K + 1) / 2;

			// 모든 카드를 사용하는 것이 몇 번 도는지 계산하여 사이클 이후 상태 결정
			int quotient = diff / cycleLen;
			int remainder = diff % cycleLen;

			boolean isRaaraFirst = (quotient % 2 == 1 && K % 2 == 1);

			// 선턴이 이기는지 계산
			int result = canFirstPlayerWin(remainder, (1 << K) - 1);

			// raara가 선턴인 경우 결과 뒤집기
			if (isRaaraFirst) {
				result = result == WIN ? LOSE : WIN;
			}

			// 결과를 출력용 스트링빌더에 넣기
			if (result == WIN) {
				sb.append(SWOON).append("\n");
			} else {
				sb.append(RAARARAARA).append("\n");
			}
		}

		// 출력
		System.out.print(sb.toString());

	}

	private static int canFirstPlayerWin(int remainingAddAmount, int usableCards) {
		// base case
		if (remainingAddAmount <= 0) {
			return LOSE;
		}

		// memoization
		if (cache[remainingAddAmount][usableCards] != CACHE_EMPTY) {
			return cache[remainingAddAmount][usableCards];
		}

		// 새로 계산
		for (int i = 0; i < MAX_K; i++) {
			// 남은 카드면 시도
			if ((usableCards & (1 << i)) != 0) {
				// 상대가 무조건 지는 경우가 있으면 나는 이긴다
				int nextUsableCards = (usableCards & (~(1 << i)));
				int opponentWin = canFirstPlayerWin(remainingAddAmount - (i + 1), nextUsableCards);
				if (opponentWin == LOSE) {
					return cache[remainingAddAmount][usableCards] = WIN;
				}
			}
		}

		// 이기는 경우가 없으면 진다
		return cache[remainingAddAmount][usableCards] = LOSE;

	} // end main

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

}