// 12388KB, 84ms

package bj19947;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_YEAR = 100000;
	static final double CACHE_EMPTY = -1.0;

	static double startMoney;
	static int endYear;
	static double[] cache = new double[MAX_YEAR + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 캐시 초기화
		Arrays.fill(cache, CACHE_EMPTY);

		// 입력
		st = new StringTokenizer(br.readLine(), " ");
		startMoney = Double.parseDouble(st.nextToken());
		endYear = Integer.parseInt(st.nextToken());

		// 계산
		long answer = (long) getMaxMoney(endYear);

		System.out.println(answer);

	} // end main

	/** year년이 지난 후의 최대 돈을 리턴 */
	public static double getMaxMoney(int year) {
		if (year < 0) {
			return 0.0;
		}

		if (year == 0) {
			return startMoney;
		}

		if (cache[year] != CACHE_EMPTY) {
			return cache[year];
		}

		double ret1 = Math.floor(1.05 * getMaxMoney(year - 1));
		double ret3 = Math.floor(1.2 * getMaxMoney(year - 3));
		double ret5 = Math.floor(1.35 * getMaxMoney(year - 5));
		double ret = Math.max(ret1, ret3);
		ret = Math.max(ret, ret5);

		return cache[year] = ret;
	}

}