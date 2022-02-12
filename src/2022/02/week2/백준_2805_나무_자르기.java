// 171704KB, 516ms

package bj2805;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static long MAX_HEIGHT = 2000000000;
	
	public static int N;
	public static long target;
	public static long[] heights;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		target = Long.parseLong(st.nextToken());

		st = new StringTokenizer(br.readLine(), " ");
		heights = new long[N];
		for (int i = 0; i < N; i++) {
			heights[i] = Long.parseLong(st.nextToken());
		}

		long left = 0;
		long right = MAX_HEIGHT;
		
		for (int i = 0; i < 40; i++) {
			long mid = (left + right) / 2;
			
			if (isPossible(mid)) {
				left = mid;
			} else {
				right = mid;
			}
		}
		
		System.out.println(left);
	}

	
	public static boolean isPossible(long cut) {
		long sum = 0;
		for (long h : heights) {
			if (cut <= h) {
				sum += (h - cut);
			}
		}
		
		if (target <= sum) {
			return true;
		}
		
		return false;
		
	} 
}
