// 13204KB, 116ms

package boj2449;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 200, MAX_K = 20;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;

	static int N, K;
	static List<Integer> seq = new ArrayList<Integer>();
	static int[][] cache = new int[MAX_N][MAX_N];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		// 전구 초기 상태 입력
		int prev = -1;
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			int now = Integer.parseInt(st.nextToken());

			if (prev != now) {
				seq.add(now);
				prev = now;
			}
		}

		// 캐시 초기화
		initCache();

		// 정답 계산
		int answer = getMinOperations(0, seq.size() - 1);

		// 출력
		System.out.println(answer);

	} // end main

	/** [leftIdx, rightIdx] 구간의 색을 일치시키는 데 필요한 최소 연산 수를 구해서 리턴 */
	public static int getMinOperations(int leftIdx, int rightIdx) {
		// base case
		if (leftIdx == rightIdx) {
			return 0;
		}

		// memoization
		if (cache[leftIdx][rightIdx] != CACHE_EMPTY) {
			return cache[leftIdx][rightIdx];
		}

		int ret = INF;
		for (int div = leftIdx; div <= rightIdx - 1; div++) {
			// 구간 나눠서 처리
			int leftOperations = getMinOperations(leftIdx, div);
			int rightOperations = getMinOperations(div + 1, rightIdx);
			int totalOperations = leftOperations + rightOperations;

			// 두 구간의 색이 다른 경우 한 번의 연산이 추가로 필요
			if (seq.get(leftIdx) != seq.get(div + 1)) {
				totalOperations++;
			}

			ret = Math.min(ret, totalOperations);
		}

		return cache[leftIdx][rightIdx] = ret;
	}

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

}