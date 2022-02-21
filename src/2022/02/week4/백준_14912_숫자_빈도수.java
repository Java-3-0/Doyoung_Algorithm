// 11756KB, 88ms

package bj14912;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static final int DIGITS = 10;
	public static int[] counts = new int[DIGITS];
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int d = Integer.parseInt(st.nextToken());
		
		for (int num = 1; num <= N; num++) {
			countDigits(num);
		}
		
		System.out.println(counts[d]);
	}

	public static void countDigits (int n) {
		while (n > 0) {
			counts[n % 10]++;
			n /= 10;
		}
	}
}
