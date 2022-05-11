// 11488KB, 80ms

package bj17358;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static final long MOD = (long) (1e9 + 7);
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		long N = Long .parseLong(br.readLine());
		
		long answer = 1L;
		for (long i = N; i >= 2; i -= 2) {
			answer *= (i - 1);
			answer %= MOD;
		}

		System.out.println(answer);
		
	} // end main
}