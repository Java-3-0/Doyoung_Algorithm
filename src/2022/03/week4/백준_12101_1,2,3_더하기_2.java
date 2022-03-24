// 11616KB, 84ms

package bj12101;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 10;
	static final int CACHE_EMPTY = -1;
	
	static int[] cache = new int[MAX_N + 1];
	static List<Integer> answer = new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		initCache();

		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		getKth(N, K);
		
		if (answer.isEmpty()) {
			System.out.println(-1);
		}
		else {
			for (int num : answer) {
				sb.append(num).append("+");
			}
			sb.setLength(sb.length() - 1);
		}
		
		System.out.print(sb.toString());
		
	}
	
	public static void initCache() {
		Arrays.fill(cache, CACHE_EMPTY);
	}

	public static void getKth(int n, int k) {
		if (n <= 0) {
			return;
		}
		
		int firstOne = getNumberOfWays(n - 1);
		int firstTwo = getNumberOfWays(n - 2);
		int firstThree = getNumberOfWays(n - 3);
		
		if (firstOne >= k) {
			answer.add(1);
			getKth(n - 1, k);
			return;
		}
		if (firstOne + firstTwo >= k) {
			answer.add(2);
			getKth(n - 2, k - firstOne);
			return;
		}
		if (firstOne + firstTwo + firstThree >= k) {
			answer.add(3);
			getKth(n - 3, k - firstOne - firstTwo);
			return;
		}
		
	}
	
	public static int getNumberOfWays(int n) {
		// base case
		if (n < 0) {
			return 0;
		}
		
		if (n == 0) {
			return 1;
		}
		
		// 캐시에 이미 계산되어 있는 경우
		if (cache[n] != CACHE_EMPTY)
			return cache[n];

		// 새로 계산하는 경우
		int ret = 0;
		// 첫 칸에 1 놓는 경우, 2 놓는 경우, 3 놓는 경우를 각각 더한다.
		ret += getNumberOfWays(n - 1);
		ret += getNumberOfWays(n - 2);
		ret += getNumberOfWays(n - 3);

		return cache[n] = ret;
	}
}