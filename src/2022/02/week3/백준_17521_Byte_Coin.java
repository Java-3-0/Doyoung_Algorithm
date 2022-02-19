// 11708KB, 76ms

package bj17521;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		
		// 날짜 수, 시작 자산 입력
		int numDays = Integer.parseInt(st.nextToken());
		BigInteger startMoney = new BigInteger(st.nextToken());
		
		// 날짜 별 코인 가격 입력
		BigInteger[] priceAtDay = new BigInteger[numDays];
		for (int i = 0; i < numDays; i++) {
			priceAtDay[i] = new BigInteger(br.readLine());
		}
		
		// 시작할 때 코인은 0개 들고 시작
		BigInteger myMoney = startMoney;
		BigInteger myCoins = new BigInteger("0");
		// 마지막 날 전까지, 각 날짜마다 매수, 매도, 홀드 판단
		for (int day = 0; day < numDays - 1; day++) {
			BigInteger todayPrice = priceAtDay[day];
			BigInteger tomorrowPrice = priceAtDay[day + 1];
			
			// 오르기 전이라면 전량 매수
			if (todayPrice.compareTo(tomorrowPrice) < 0) {
				// 구매 가능한 양
				BigInteger buyAmount = myMoney.divide(todayPrice);
				// 가능한 최대치로 구매하고 보유 현금, 코인 갱신
				myMoney = myMoney.remainder(todayPrice);
				myCoins = myCoins.add(buyAmount);
			}
			
			// 떨어지기 전이라면 전량 매도
			else if (todayPrice.compareTo(tomorrowPrice) > 0) {
				// 판매 가능한 양
				BigInteger sellAmount = myCoins;
				// 가능한 최대치로 판매하고 보유 현금, 코인 갱신
				myMoney = myMoney.add(sellAmount.multiply(todayPrice));
				myCoins = myCoins.subtract(sellAmount);
			}
			
		} // end for day
		
		// 마지막 날이 되면 남은 코인 전량 매도
		BigInteger sellAmount = myCoins;
		myMoney = myMoney.add(sellAmount.multiply(priceAtDay[numDays - 1]));
		myCoins = myCoins.subtract(sellAmount);
		
		// 내 보유 현금 출력
		System.out.println(myMoney.toString());
	}
}