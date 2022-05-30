// 11476KB, 76ms

package bj9625;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int K = Integer.parseInt(br.readLine());

		int[] seqA = new int[K + 1];
		int[] seqB = new int[K + 1];

		seqA[0] = 1;
		seqB[0] = 0;
		for (int i = 1; i <= K; i++) {
			seqA[i] = seqB[i - 1];
			seqB[i] = seqB[i - 1] + seqA[i - 1];
		}

		sb.append(seqA[K]).append(" ").append(seqB[K]).append("\n");

		System.out.print(sb.toString());
	} // end main

}