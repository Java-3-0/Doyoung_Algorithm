package bj10867;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();
		
		// 수열 크기 입력
		int N = Integer.parseInt(br.readLine());
		
		// 메모리 할당
		int[] seq = new int[N];

		// 수열 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}
		
		Arrays.sort(seq);
		
		sb.append(seq[0]).append(" ");
		for (int i = 1; i < N; i++) {
			if (seq[i - 1] != seq[i]) {
				sb.append(seq[i]).append(" ");
			}
		}
		sb.append("\n");
		
		System.out.print(sb.toString());
		
		
	} // end main

}