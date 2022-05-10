// 11524KB, 76ms

package bj24389;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());

		int twosCompliment = (~N) + 1;

		int bitXOR = N ^ twosCompliment;

		int answer = Integer.bitCount(bitXOR);
		System.out.println(answer);
		
	} // end main
}