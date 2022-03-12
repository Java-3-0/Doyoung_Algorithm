// 12400KB, 172ms

package bj1487;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int MAX_N = 50;
	static int MAX_PRICE = 1000000;

	static int N;
	static int[] customerPays = new int[MAX_N];
	static int[] deliveryCosts = new int[MAX_N];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		N = Integer.parseInt(br.readLine());
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			customerPays[i] = Integer.parseInt(st.nextToken());
			deliveryCosts[i] = Integer.parseInt(st.nextToken());
		}

		// 가능한 최대 수익 계산
		int maxProfit = 0;
		int bestPrice = 0;
		// 모든 가격을 완전탐색
		for (int price = 0; price <= MAX_PRICE; price++) {
			int profit = 0;
			for (int i = 0; i < N; i++) {
				if (price <= customerPays[i]) {
					// 배달비가 가격보다 비싸면 안 파는 게 낫다
					profit += Math.max(0, price - deliveryCosts[i]);
				}
			}

			// maxProfit 갱신하고 그때의 가격을 저장
			if (maxProfit < profit) {
				maxProfit = profit;
				bestPrice = price;
			}

		}

		// 출력
		System.out.println(bestPrice);

	} // end main

}