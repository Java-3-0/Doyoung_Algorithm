// 64096KB, 328ms

package bj20937;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_C = 50000;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());

		int[] counts = new int[MAX_C + 1];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			int c = Integer.parseInt(st.nextToken());
			counts[c]++;
		}
		
		int answer = 0;
		for (int cnt : counts) {
			answer = answer < cnt ? cnt : answer;
		}
		
		System.out.println(answer);

	} // end main

}