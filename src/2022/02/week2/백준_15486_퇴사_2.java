// 366360KB, 996ms

package baek15486;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_DAYS = 1500000;
	public static final int MAX_TERM = 50;
	public static final int MAX_PROFIT = 1000;
	public static int days;
	public static int[] terms = new int[MAX_DAYS];
	public static int[] profits = new int[MAX_DAYS];
	public static int[] cache = new int[MAX_DAYS];

	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		days = Integer.parseInt(br.readLine());

		StringTokenizer st;
		for (int i = 0; i < days; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			terms[i] = Integer.parseInt(st.nextToken());
			profits[i] = Integer.parseInt(st.nextToken());
		}

		// 캐시 초기화
		Arrays.fill(cache, 0, days, -1);

		// 최대 수익 계산
		int answer = maxProfit(0);

		// 출력
		System.out.println(answer);
	}

	/**
	 * 특정 날짜부터 벌 수 있는 최대 비용을 리턴
	 * 
	 * @param startDay : 시작할 날
	 * @return 이 날부터 벌 수 있는 최대 비용
	 */
	public static int maxProfit(int startDay) {
		// 기저 사례 : 시작 날짜가 주어진 기간을 초과
		if (startDay >= days) {
			return 0;
		}

		// 캐시에 이미 계산된 값이 있는 경우 그대로 리턴
		if (cache[startDay] != -1) {
			return cache[startDay];
		}

		// 이외의 경우 이 날의 상담을 하는 것과 안 하는 것 둘로 나눠서 재귀
		int ret = maxProfit(startDay + 1);
		if (startDay + terms[startDay] - 1 < days) {
			int profitIfIncluded = profits[startDay] + maxProfit(startDay + terms[startDay]);
			if (ret < profitIfIncluded) {
				ret = profitIfIncluded;
			}
		}

		return cache[startDay] = ret;
	}
}