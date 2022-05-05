// 16164KB, 152ms

package bj2154;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int N = Integer.parseInt(br.readLine());
		
		for (int num = 1; num <= N; num++) {
			sb.append(num);
		}
		
		String str = sb.toString();
		
		int answer = 1 + str.indexOf(String.valueOf(N));
		
		System.out.println(answer);
		
	} // end main

}