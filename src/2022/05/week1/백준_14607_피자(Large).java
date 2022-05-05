// 11520KB, 76ms

package bj14607;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		long N = Long.parseLong(br.readLine());
		
		long answer = N * (N - 1L) / 2L;
		
		System.out.println(answer);

	} // end main

}