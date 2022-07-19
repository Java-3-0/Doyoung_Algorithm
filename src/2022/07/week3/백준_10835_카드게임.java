// 29360KB, 224ms

package boj10835;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 2000;
	static final int CACHE_EMPTY = -1;

	static int N;
	static int[] seqLeft = new int[MAX_N];
	static int[] seqRight = new int[MAX_N];
	static int[][] cache = new int[MAX_N][MAX_N];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		initCache();

		// N 입력
		N = Integer.parseInt(br.readLine());

		// 왼쪽 더미 카드 값 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seqLeft[i] = Integer.parseInt(st.nextToken());
		}

		// 오른쪽 더미 카드 값 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seqRight[i] = Integer.parseInt(st.nextToken());
		}

		int answer = getMaxScore(0, 0);
		
		System.out.println(answer);

	} // end main

	public static int getMaxScore(int leftIdx, int rightIdx) {
		// base case
		if (leftIdx == N || rightIdx == N) {
			return 0;
		}

		// memoization
		if (cache[leftIdx][rightIdx] != CACHE_EMPTY) {
			return cache[leftIdx][rightIdx];
		}

		// 새로 계산
		int ret = 0;

		// 왼쪽만 버리는 경우
		ret = Math.max(ret, getMaxScore(leftIdx + 1, rightIdx));

		// 둘 다 버리는 경우
		ret = Math.max(ret, getMaxScore(leftIdx + 1, rightIdx + 1));

		// 오른쪽만 버리는 경우 -> 점수 얻음
		int leftVal = seqLeft[leftIdx];
		int rightVal = seqRight[rightIdx];
		if (leftVal > rightVal) {
			ret = Math.max(ret, rightVal + getMaxScore(leftIdx, rightIdx + 1));
		}

		return cache[leftIdx][rightIdx] = ret;
	}

	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}

}