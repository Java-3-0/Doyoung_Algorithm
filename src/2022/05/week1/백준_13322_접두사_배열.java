// 18200KB, 180ms

package bj13322;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		String s = br.readLine();
		int len = s.length();

		for (int i = 0; i < len; i++) {
			sb.append(i).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

}