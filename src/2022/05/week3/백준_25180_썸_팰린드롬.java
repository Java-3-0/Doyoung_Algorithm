// 11476KB, 76ms

package bj25180;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());

		int answer = solve(N);
		
		System.out.println(answer);
		
	} // end main
	
	
	public static int solve(int N) {
		if (N == 0) {
			return 0;
		}
		if (N <= 9) {
			return 1;
		}
		if (N <= 18) {
			if (N % 2 == 0) {
				return 2;
			}
			else {
				return 3;
			}
		}
		
		// 18을 만들어야 하는 개수
		int cnt = N / 18;
		
		// 18을 한 개 만들 때마다 2의 길이, 거기에 나머지 부분을 만들기 위한 길이를 더한다
		int ret = 2 * cnt + solve(N % 18);
		return ret;
	}
}