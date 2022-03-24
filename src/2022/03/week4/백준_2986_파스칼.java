// 11560KB, 76ms

package bj2986;

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
		int sqrt = (int)Math.floor(Math.sqrt(N));
		
		for (int num = 2; num <= sqrt; num++) {
			if (N % num == 0) {
				return N - (N / num);
			}
		}
		
		return N - 1;
	}

}