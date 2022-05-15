// 25904KB, 284ms

package bj12846;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = (int) 1e5;
	static final long INF = 987654321098765L;
	static int N;
	static long[] dailyPays = new long[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 날짜 수 입력
		N = Integer.parseInt(br.readLine());

		// 날마다 받을 수 있는 급여 정보 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			dailyPays[i] = Long.parseLong(st.nextToken());
		}

		// 최대 수입 계산
		long maxMoney = getMaxMoney(0, N - 1);

		// 출력
		System.out.println(maxMoney);

	} // end main

	/** [left, right]번 날 내에서 벌 수 있는 최대 금액을 계산해서 리턴 */
	public static long getMaxMoney(int left, int right) {
		// base case. 하루인 겨우
		if (left == right) {
			long area = dailyPays[left] * 1;
			return area;
		}

		// 분할 정복
		long ret = -INF;
		int mid = (left + right) / 2;

		// A. mid는 포함하고 mid + 1은 포함하지 않는 것 고려
		ret = Math.max(ret, getMaxMoney(left, mid));

		// B. mid + 1은 포함하고 mid는 포함하지 않는 것 고려
		ret = Math.max(ret, getMaxMoney(mid + 1, right));

		// C. mid와 mid + 1 모두 포함하는 것 고려
		int curLeft = mid;
		int curRight = mid + 1;

		// 초기에 mid와 mid + 1만 포함한 상태
		long curDailyPay = Math.min(dailyPays[curLeft], dailyPays[curRight]);
		long curDays = 2L;
		long curMoney = curDailyPay * curDays;
		ret = Math.max(ret, curMoney);

		// 모든 날을 포함시킬 때까지 한 칸씩 추가하는 것을 반복
		while (left < curLeft || curRight < right) {
			// 오른쪽 날의 급여가 더 크면 오른쪽으로 한 칸 이동
			if (curRight < right && (curLeft == left || dailyPays[curRight + 1] >= dailyPays[curLeft - 1])) {
				curRight++;
				curDays++;
				curDailyPay = Math.min(curDailyPay, dailyPays[curRight]);
			}
			// 왼쪽 날의 급여가 더 크면 왼쪽으로 한 칸 이동
			else {
				curLeft--;
				curDays++;
				curDailyPay = Math.min(curDailyPay, dailyPays[curLeft]);
			}

			// 총 금액 계산
			curMoney = curDailyPay * curDays;
			ret = Math.max(ret, curMoney);
		}

		return ret;
	}

}