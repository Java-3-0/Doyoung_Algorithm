// 14152KB, 104ms

package bj1633;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000;
	static final int TEAM = 15;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;
	static final int FAIL = -INF;

	static int N;
	static List<Player> players = new ArrayList<Player>();
	static int[][][] cache = new int[MAX_N + 1][TEAM + 1][TEAM + 1];

	static class Player {
		int whiteStat;
		int blackStat;

		public Player(int whiteStat, int blackStat) {
			super();
			this.whiteStat = whiteStat;
			this.blackStat = blackStat;
		}

		@Override
		public String toString() {
			return "Player [whiteStat=" + whiteStat + ", blackStat=" + blackStat + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		initCache();

		// 입력
		String line = "";
		while ((line = br.readLine()) != null) {
			st = new StringTokenizer(line, " ");
			int whiteStat = Integer.parseInt(st.nextToken());
			int blackStat = Integer.parseInt(st.nextToken());

			players.add(new Player(whiteStat, blackStat));
		}

		// 선수 수 계산
		N = players.size();

		// 정답 계산
		int answer = getMaxStat(0, 0, 0);

		// 출력
		System.out.println(answer);

	} // end main

	/** 
	 * 가능한 최대 스탯을 리턴
	 * @param startIdx : 현재 처리할 선수 인덱스
	 * @param cntWhite : 이전까지 포함시킨 백 능력치 수
	 * @param cntBlack : 이전까지 포함시킨 흑 능력치 수
	 * @return
	 */
	public static int getMaxStat (int startIdx, int cntWhite, int cntBlack) {
		// 팀 인원수를 초과한 경우
		if (cntWhite > 15 || cntBlack > 15) {
			return FAIL;
		}
		
		// 양쪽 팀을 완성한 경우
		if (cntWhite == 15 && cntBlack == 15) {
			return 0;
		}
		
		// 팀을 완성하지 못한 채로 인덱스 끝까지 간 경우
		if (startIdx == N) {
			return FAIL;
		}
		
		// 캐시에 이미 계산되어 있는 경우
		if (cache[startIdx][cntWhite][cntBlack] != CACHE_EMPTY) {
			return cache[startIdx][cntWhite][cntBlack];
		}
		
		// 새로 계산해야 하는 경우
		Player player = players.get(startIdx);
		int whiteStat = player.whiteStat;
		int blackStat = player.blackStat;
		
		// 1. 이 플레이어를 쓰지 않는 경우
		int ret = getMaxStat(startIdx + 1, cntWhite, cntBlack);
		
		// 2. 백으로 쓰는 경우
		ret = Math.max(ret, whiteStat + getMaxStat(startIdx + 1, cntWhite + 1, cntBlack));
		
		// 3. 흑으로 쓰는 경우
		ret = Math.max(ret, blackStat + getMaxStat(startIdx + 1, cntWhite, cntBlack + 1));
		
		return cache[startIdx][cntWhite][cntBlack] = ret;
	}

	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			for (int j = 0; j < cache[i].length; j++) {
				Arrays.fill(cache[i][j], CACHE_EMPTY);
			}
		}
	}

}