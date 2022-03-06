// 243280KB, 680ms

package bj10942;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 2000;
	static final int MAX_NUM = 100000;
	static final int MAX_M = 1000000;
	static final int CACHE_EMPTY = -1;

	static int N, M;
	static int[][] cache = new int[MAX_N + 1][MAX_N + 1];
	static int[] seq;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 캐시 초기화
		initCache();

		// N 입력
		N = Integer.parseInt(br.readLine());

		// 수열 입력
		st = new StringTokenizer(br.readLine(), " ");
		seq = new int[N + 1];
		for (int i = 1; i <= N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}
		
		// M 입력
		M = Integer.parseInt(br.readLine());

		// 질문 입력
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());
			
			// 팰린드롬 여부를 계산해서 출력 스트링빌더에 추가
			sb.append(isPalindrome(start, end)).append("\n");
		}
		
		// 출력
		System.out.print(sb.toString());

	} // end main

	/** start부터 end까지가 팰린드롬이면 1, 아니면 0을 리턴 */
	public static int isPalindrome(int start, int end) {
		// base case: 길이가 1 이하이면 팰린드롬이다.
		if (start >= end) {
			return cache[start][end] = 1;
		}
		
		// 캐시에 이미 저장되어 있으면 그대로 리턴
		if (cache[start][end] != CACHE_EMPTY) {
			return cache[start][end];
		}
		
		// 새로 계산해야 하는 경우
		
		// 양 끝이 일치한다면 가운데 부분에 대해 재귀 호출
		if (seq[start] == seq[end]) {
			return cache[start][end] = isPalindrome(start + 1, end - 1);
		}
		// 양 끝이 일치하지 않는다면 팰린드롬이 아니다.
		else {
			return cache[start][end] = 0;
		}
	}
	
	/** 캐시 초기화 */
	public static void initCache() {
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
	}
}