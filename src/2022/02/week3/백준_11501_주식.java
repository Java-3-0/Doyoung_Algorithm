// 347232KB, 4900ms

package bj11501;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Collections;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.TreeMap;

public class Main {
	public static final BigInteger ZERO = new BigInteger("0");
	public static final BigInteger ONE = new BigInteger("1");
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		int T = Integer.parseInt(br.readLine());
		for (int testCase = 1; testCase <= T; testCase++) {
			int numDays = Integer.parseInt(br.readLine());
			
			// 가격 -> 그 가격이 마지막으로 존재하는 날짜로의 매핑
			Map<Integer, Integer> priceToLastDay = new TreeMap<>(Collections.reverseOrder());
			int[] prices = new int[numDays];
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < numDays; i++) {
				int price = Integer.parseInt(st.nextToken());
				priceToLastDay.put(price, i);
				prices[i] = price;
			}
			
			BigInteger myMoney = ZERO;
			BigInteger myStocks = ZERO;
			
			// 미래에 존재하는 가장 높은 가격이 sellDay이고, 이 때 (lastSellDay + 1) ~ (sellDay - 1) 까지 사서 sellDay에 판다
			int lastSellDay = -1;
			for (int price : priceToLastDay.keySet()) {
				int sellDay = priceToLastDay.get(price);
				if (sellDay == lastSellDay + 1) {
					lastSellDay = sellDay;
					continue;
				}
				
				else if (sellDay <= lastSellDay) {
					continue;
				}
				
				// 이전 판매 날 다음부터, 이번 판매날 이전까지 하루에 1개씩 구매
				for (int day = lastSellDay + 1; day < sellDay; day++) {
					myStocks = myStocks.add(ONE);
					myMoney = myMoney.subtract(new BigInteger(String.valueOf(prices[day])));
				}
				
				// 이번 판매날에 모두 판매
				BigInteger sellPrice = new BigInteger(String.valueOf(prices[sellDay]));
				BigInteger sellAmount = myStocks;
				myStocks = ZERO;
				myMoney = myMoney.add(sellPrice.multiply(sellAmount));
				
				// 이번 판매날이 이전 판매날이 되도록 갱신
				lastSellDay = sellDay;
			}
			
			sb.append(myMoney.toString()).append("\n");
			
		} // end for testCase
		
		System.out.print(sb.toString());
		
	}
}