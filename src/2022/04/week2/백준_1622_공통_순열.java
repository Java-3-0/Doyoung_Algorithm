// 11568KB, 76ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int ALPHABETS = 26;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int[] counts1 = new int[ALPHABETS];
		int[] counts2 = new int[ALPHABETS];

		String line1 = "";

		while ((line1 = br.readLine()) != null) {
			Arrays.fill(counts1, 0);
			Arrays.fill(counts2, 0);

			String line2 = br.readLine();

			for (char c : line1.toCharArray()) {
				counts1[c - 'a']++;
			}
			for (char c : line2.toCharArray()) {
				counts2[c - 'a']++;
			}

			for (int i = 0; i < ALPHABETS; i++) {
				char c = (char) (i + 'a');
				int minCnt = Math.min(counts1[i], counts2[i]);
				for (int k = 0; k < minCnt; k++) {
					sb.append(c);
				}
			}

			sb.append("\n");
		}

		System.out.print(sb.toString());

	} // end main

}