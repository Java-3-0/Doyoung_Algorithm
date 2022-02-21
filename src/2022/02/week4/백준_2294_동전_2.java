// 17272KB, 112ms

package bj2294;

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
	public static final int IMPOSSIBLE = 987654321;
	
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
		int answer = minCoinUses(0, K);
		
		// 출력
		if (answer >= IMPOSSIBLE) {
			System.out.println(-1);
		}
		else {
			System.out.println(answer);
		}
		
	}
	
	/** idx번 동전부터의 동전을 사용해서 k원을 만드는 방법 중 가장 적게 동전을 사용하는 개수를 리턴 */
	public static int minCoinUses (int idx, int k) {
		// base case 1: 다 만든 경우, 끝
		if (k == 0) {
			return 0;
		}
		
		// base case 2: 배열 범위 끝까지 갔거나 남은 금액이 최소 동전 가치보다 작은 경우, 실패
		if (idx == len || k < minCoinVal) {
			return IMPOSSIBLE;
		}
		
		// 캐시에 이미 계산된 값이 있는 경우
		if (cache[idx][k] != CACHE_EMPTY) {
			return cache[idx][k];
		}
		
		// 새로 계산해서 캐시에 넣는 경우
		
		// 이 코인을 쓴 경우
		int min1 = 1 + minCoinUses(idx, k - coins[idx]);
		
		// 안 쓴 경우
		int min2 = minCoinUses(idx + 1, k);
		
		return cache[idx][k] = Math.min(min1, min2);
	}
}