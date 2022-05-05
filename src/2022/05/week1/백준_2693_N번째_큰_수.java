package bj2693;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static final int SIZE = 10;
	static int[] seq = new int[SIZE];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < SIZE; i++) {
				seq[i] = Integer.parseInt(st.nextToken());
			}
			
			Arrays.sort(seq);
			
			int answer = seq[SIZE - 1 - 2];
			
			sb.append(answer).append("\n");
		}
		
		System.out.print(sb.toString());

	} // end main

}