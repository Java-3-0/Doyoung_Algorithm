// 115972KB, 524ms

package bj23630;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_A = 1000000;
	static final int MAX_BITS = 21;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());

		
		int[] counts = new int[MAX_BITS];
		
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			int num = Integer.parseInt(st.nextToken());
			for (int k = 0; k < MAX_BITS; k++) {
				if ((num & (1 << k)) != 0) {
					counts[k]++;
				}
			}
		}
		
		int answer = 0;
		for (int cnt : counts) {
			answer = Math.max(answer, cnt);
		}
		
		System.out.println(answer);
		

	} // end main
}