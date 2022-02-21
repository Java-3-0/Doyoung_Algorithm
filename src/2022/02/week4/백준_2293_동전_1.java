// 15892KB, 100ms

package bj2293;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_N = 100;
	public static final int MAX_K = 10000;
	public static final int CACHE_EMPTY = -1;
	
	public static Integer[] coins;
	public static int[][] cache = new int[MAX_N + 1][MAX_K + 1];
	public static int len;
	public static int minCoinVal;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		// 동전들을 입력받아서 중복을 제거하고, 배열에 담는다.
		Set<Integer> tmp = new HashSet<>();
		for (int i = 0; i < N; i++) {
			tmp.add(Integer.parseInt(br.readLine()));
		}
		coins = tmp.toArray(new Integer[tmp.size()]);
		len = coins.length;
		
		// 동전들을 가치 내림차순으로 정렬한다.
		Arrays.sort(coins, Collections.reverseOrder());
		
		// 캐시 초기화
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], CACHE_EMPTY);
		}
		
		// 경우의 수 계산
		int answer = countWays(0, K);
		
		// 출력
		System.out.println(answer);
	}
	
	/** idx번 동전부터의 동전을 사용해서 k원을 만드는 방법의 수를 리턴 */
	public static int countWays (int idx, int k) {
		// base case 1: 배열 범위 끝까지 갔거나, 남은 금액이 최소 동전 가치보다 작은 경우
		if (idx == len || k < minCoinVal) {
			return 0;
		}

		// base case 2: 남은 금액이 최소 금액 가치와 같아서 방법이 1개뿐인 경우
		if (k == minCoinVal) {
			return 1;
		}
		
		// 캐시에 이미 계산된 값이 있는 경우
		if (cache[idx][k] != CACHE_EMPTY) {
			return cache[idx][k];
		}
		
		// 새로 계산해서 캐시에 넣는 경우
		return cache[idx][k] = countWays(idx, k - coins[idx]) + countWays(idx + 1, k);
	}
}