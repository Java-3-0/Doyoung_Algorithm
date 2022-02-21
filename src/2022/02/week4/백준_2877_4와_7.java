// 11516KB, 72ms

package bj2877;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		int K = Integer.parseInt(br.readLine());
		K = K + 1;
		
		StringBuilder sb = new StringBuilder();
		do {
			if (K % 2 == 0) {
				sb.append(4);
			}
			else {
				sb.append(7);
			}
			
			K = K / 2;
		} while (K != 1);
		
		System.out.println(sb.reverse().toString());
	}
}