// 11452KB, 72ms

package bj4375;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		String line = "";
		
		while ((line = br.readLine()) != null) {
			int N = Integer.parseInt(line);

			int remainder = 1 % N;
			
			int digits = 1;
			while (remainder != 0) {
				remainder = (10 * remainder + 1) % N;
				digits++;
			}
			
			sb.append(digits).append("\n");

		}
		
		System.out.print(sb.toString());

	} // end main

}