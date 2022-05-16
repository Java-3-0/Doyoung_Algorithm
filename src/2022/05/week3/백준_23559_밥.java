// 40044KB, 436ms

package bj23559;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

public class Main {
	static class DailyOption implements Comparable<DailyOption> {
		int menuFive;
		int menuOne;
		int profit;

		public DailyOption(int menuFive, int menuOne) {
			super();
			this.menuFive = menuFive;
			this.menuOne = menuOne;
			this.profit = menuFive - menuOne;
		}

		/** 이익 내림차순으로 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(DailyOption m) {
			return -(this.profit - m.profit);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, X 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int X = Integer.parseInt(st.nextToken());

		// 5000원 짜리를 사 먹었을 때의 이득이 큰 순서로 pq에 담는다
		PriorityQueue<DailyOption> pq = new PriorityQueue<>();
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int menuFive = Integer.parseInt(st.nextToken());
			int menuOne = Integer.parseInt(st.nextToken());
			pq.offer(new DailyOption(menuFive, menuOne));
		}

		// 현재 보유금으로 5000원 짜리를 구매 가능한 최대 개수
		int limitFive = (X - 1000 * N) / 4000;

		// 실제로 5000원 짜리를 구매한 개수를 카운트할 변수
		int cntFive = 0;

		// 5000원 짜리를 구매한 이득이 큰 것부터 최대한 구매한다
		// (단, 이득이 음수이면 구매 가능 수량이 남았어도 1000원짜리를 산다)
		int answer = 0;
		while (!pq.isEmpty()) {
			DailyOption dailyOption = pq.poll();

			if (cntFive < limitFive && dailyOption.profit >= 0) {
				answer += dailyOption.menuFive;
				cntFive++;

			} else {
				answer += dailyOption.menuOne;
			}
		}

		// 정답 출력
		System.out.println(answer);

	} // end main
}