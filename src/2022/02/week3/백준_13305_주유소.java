// 41368KB, 372ms

package bj13305;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());
		long[] roadLengths = new long[N - 1];
		long[] oilPrices = new long[N];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N - 1; i++) {
			roadLengths[i] = Long.parseLong(st.nextToken());
		}

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			oilPrices[i] = Long.parseLong(st.nextToken());
		}

		long minPrice = Long.MAX_VALUE;

		long sumPayment = 0L;
		for (int i = 0; i < N - 1; i++) {
			if (oilPrices[i] < minPrice) {
				minPrice = oilPrices[i];
			}
			sumPayment += (roadLengths[i] * minPrice);
		}

		System.out.println(sumPayment);
	}

}