// 14274KB, 740ms

package bj11066;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_K = 500;
	static final int INF = 987654321;
	static final int CACHE_EMPTY = -1;

	static int K;
	static int[] files = new int[MAX_K + 1];
	static int[] psums = new int[MAX_K + 1];
	static int[][] cache = new int[MAX_K + 1][MAX_K + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// 캐시 초기화
			initCache();

			// K 입력
			K = Integer.parseInt(br.readLine());

			// 파일들 입력받고 누적합 계산
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 1, sum = 0; i <= K; i++) {
				int input = Integer.parseInt(st.nextToken());
				sum += input;
				files[i] = input;
				psums[i] = sum;
			}

			// 최소 비용 계산
			int answer = getMinCost(1, K);

			// 정답을 출력 스트링빌더에 추가
			sb.append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** [startIdx, endIdx] 구간을 합치는 최소 비용을 리턴 */
	public static int getMinCost(int startIdx, int endIdx) {
		// base case. 하나의 파일이라 더 이상의 분할 정복이 불가능한 경우
		if (endIdx - startIdx == 0) {
			return 0;
		}

		// 캐시에 이미 계산되어 있는 경우 그대로 리턴
		if (cache[startIdx][endIdx] != CACHE_EMPTY) {
			return cache[startIdx][endIdx];
		}

		// 이 아래로는 새로 계산해서 캐시에 저장하는 경우
		int ret = INF;

		// [startIdx, i], [i + 1, endIdx]의 두 부분으로 나누어 분할 정복
		for (int i = startIdx; i < endIdx; i++) {
			// 두 부분 내에서 각각 합치는 최소 비용의 합
			int cost = getMinCost(startIdx, i) + getMinCost(i + 1, endIdx);

			// 최종적으로 완성된 두 부분을 합치는 비용
			int finalMerge = getSum(startIdx, i) + getSum(i + 1, endIdx);

			// 이 합의 최소값을 찾는다
			ret = Math.min(ret, cost + finalMerge);
		}

		return cache[startIdx][endIdx] = ret;
	}

	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

	/** files에서 [startIdx, endIdx] 구간의 합을 리턴 */
	public static int getSum(int startIdx, int endIdx) {
		return psums[endIdx] - psums[startIdx - 1];
	}

}