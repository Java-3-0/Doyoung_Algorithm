// 11536KB, 84ms

package bj1214;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final long INF = (long) (1e18 + 1e16);

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		long D = Long.parseLong(st.nextToken());
		long P = Long.parseLong(st.nextToken());
		long Q = Long.parseLong(st.nextToken());

		long answer = solve(D, P, Q);

		System.out.println(answer);

	} // end main

	/** D, P, Q가 주어졌을 때 정답을 구해서 리턴 */
	public static long solve(long D, long P, long Q) {
		if (P < Q) {
			return solve(D, Q, P);
		}

		long minMoney = INF;

		// P원 지폐를 Q개 사용하는 것은 Q원 지폐를 P번 사용하는 것과 같으므로, 그 이상의 개수는 해 볼 필요 없다.
		// D / P + 1L개의 P원 지폐를 사용하면 금액이 이미 달성되므로, 그 이상의 개수는 해 볼 필요 없다.
		long maxCntP = Math.min(D / P + 1L, Q);

		for (long cntP = 0L; cntP <= maxCntP; cntP++) {
			// P원 지폐를 cntP개 사용하고, 나머지 금액은 Q원 지폐로 채운다
			long priceP = cntP * P;
			long remaining = D - priceP;

			// P원 지폐가 사용된 금액 계산
			long money = priceP;

			// Q원 지폐가 사용된 금액 계산
			if (remaining > 0) {
				long cntQ = remaining / Q;
				if (remaining % Q != 0) {
					cntQ++;
				}
				long priceQ = cntQ * Q;

				money += priceQ;
			}

			minMoney = money < minMoney ? money : minMoney;

		}

		return minMoney;
	}

}