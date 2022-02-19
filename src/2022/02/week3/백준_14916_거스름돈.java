// 11492KB, 84ms

package bj14916;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		br.close();
		
		// 최소 동전 개수 계산
		int answer = getMinCoins(N);
		
		// 출력
		System.out.println(answer);
	}
	
	/** 거스름돈 액수가 파라미터로 주어졌을 때, 최소 동전 수를 리턴 */
	public static int getMinCoins(int N) {
		for (int numTwos = 0; numTwos <= 4; numTwos++) {
			int remaining = N - 2 * numTwos;
			if (remaining >= 0 && remaining % 5 == 0) {
				return numTwos + remaining / 5;
			}
		}
		
		return -1;
	}
}