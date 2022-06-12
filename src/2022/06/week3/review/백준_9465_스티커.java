// 122736KB, 656ms

package bj9465;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000;
	static final int CACHE_EMPTY = -1;
	static final int NOT_USED = 0, TOP_USED = 1, BOTTOM_USED = 2;

	static int N;
	static int[][] stickers = new int[2][MAX_N];
	static int[][] cache = new int[MAX_N + 1][3];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// 캐시 초기화
			initCache();

			// 스티커 가로 길이 입력
			N = Integer.parseInt(br.readLine());

			// 스티커 입력
			for (int y = 0; y < 2; y++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int x = 0; x < N; x++) {
					stickers[y][x] = Integer.parseInt(st.nextToken());
				}
			}

			// 얻을 수 있는 최고 점수 계산
			int answer = getMaxScore(0, NOT_USED);

			// 출력 스트링빌더에 추가
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	/** (startX - 1)번 열의 사용 상태가 주어졌을 때, startX번 열부터 가능한 최대 점수를 리턴 */
	public static int getMaxScore(int startX, int leftStatus) {
		// 1. base case : 스티커의 끝에 도달한 경우
		if (startX >= N) {
			return 0;
		}

		// 2. 캐시에 계산된 값이 있는 경우
		if (cache[startX][leftStatus] != CACHE_EMPTY) {
			return cache[startX][leftStatus];
		}

		// 3. 새로 계산해서 캐시에 저장하는 경우

		// 3-1. 이 열을 사용하지 않는 경우
		int ret = getMaxScore(startX + 1, NOT_USED);

		// 3-2. 이 열의 위칸을 뜯는 경우
		if (leftStatus != TOP_USED) {
			ret = Math.max(ret, stickers[0][startX] + getMaxScore(startX + 1, TOP_USED));
		}

		// 3-3. 이 열의 아래칸을 뜯는 경우
		if (leftStatus != BOTTOM_USED) {
			ret = Math.max(ret, stickers[1][startX] + getMaxScore(startX + 1, BOTTOM_USED));
		}

		return cache[startX][leftStatus] = ret;
	}
}