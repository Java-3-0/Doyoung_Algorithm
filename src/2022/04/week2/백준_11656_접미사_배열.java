// 17428KB, 108ms

package bj11656;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		String S = br.readLine();

		int len = S.length();
		String[] suffix = new String[len];

		for (int i = 0; i < len; i++) {
			suffix[i] = S.substring(i);
		}

		Arrays.sort(suffix);

		for (String suf : suffix) {
			sb.append(suf).append("\n");
		}

		System.out.print(sb.toString());
		
	} // end main

}