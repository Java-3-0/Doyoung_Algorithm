// 15540KB, 300ms

package bj1660;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {
	static final int MAX_N = 300000;
	static final int CACHE_EMPTY = -1;
	static final int INF = 987654321;

	static int N;
	static List<Integer> tetrahedrons;
	static int[] cache = new int[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		tetrahedrons = getTetrahedrons();

		initCache();

		tetrahedrons.sort(Collections.reverseOrder());
		
		N = Integer.parseInt(br.readLine());
		
		int answer = solve(N);

		System.out.println(answer);

	} // end main

	public static int solve(int n) {
		if (n == 0) {
			return 0;
		}

		if (cache[n] != CACHE_EMPTY) {
			return cache[n];
		}

		// 1과 4로만 만드는 경우 (재귀호출 스택오버플로우를 막기 위해 따로 처리)
		int ret = n / 4 + n % 4;

		// 10 이상의 사면체를 사용하는 경우
		for (int num : tetrahedrons) {
			if (n - num < 0) {
				continue;
			}
	
			ret = Math.min(ret, 1 + solve(n - num));
		}

		return cache[n] = ret;
	}

	/** 30만 이하 크기의 모든 사면체 크기를 계산 */
	public static List<Integer> getTetrahedrons() {
		List<Integer> ret = new ArrayList<>();

		int sum = 4;
		int i = 3;
		while (true) {
			sum += (i * (i + 1) / 2);
			if (sum >= MAX_N) {
				break;
			}
			ret.add(sum);
			i++;
		}

		return ret;
	}

	/** 캐시 초기화 */
	public static void initCache() {
		Arrays.fill(cache, CACHE_EMPTY);
	}

}