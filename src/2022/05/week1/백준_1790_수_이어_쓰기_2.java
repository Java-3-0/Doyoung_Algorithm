// 11508KB, 76ms

package bj1790;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken()) - 1;

		int len = String.valueOf(N).length();

		int i = 1;
		for (; i < len; i++) {
			int cnt = 9 * (int) Math.pow(10, i - 1);
			if (K >= i * cnt) {
				K -= i * cnt;
			}
			else {
				break;
			}
		}
		
		int number = (int) Math.pow(10, i - 1) + (K / i);
		if (number > N) {
			System.out.println(-1);
		}
		else {
			int answer = String.valueOf(number).charAt(K % i) - '0';
			
			System.out.println(answer);
		}
		
		
		
	} // end main

}