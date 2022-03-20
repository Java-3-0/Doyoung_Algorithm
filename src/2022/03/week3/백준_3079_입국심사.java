// 22884KB, 280ms

package bj3079;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000;
	static final long MAX_M = 1000000000L, MAX_T = 1000000000L;
	
	static int N;
	static long M;
	static long[] judgeTime;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine().trim(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Long.parseLong(st.nextToken());
		
		judgeTime = new long[N];
		for (int i = 0; i < N; i++) {
			judgeTime[i] = Long.parseLong(br.readLine().trim());
		}
		
		long answer = getMinTime();
		
		System.out.println(answer);
	}

	public static long getMinTime() {
		long left = 0L;
		long right = MAX_T * MAX_M;
		
		// left < 정답 <= right를 유지하면서 범위를 줄여나간다.
		while (left < right) {
			long mid = (left + right) / 2L;
			
			if (isPossible(mid)) {
				right = mid;
			}
			else {
				left = mid + 1L;
			}
		}
		
		return right;
	}
	
	public static boolean isPossible (long time) {
		long cnt = 0L;
		for (long jt : judgeTime) {
			cnt += (time / jt);
			if (cnt >= M) {
				return true;
			}
		}
		
		return false;
	}
	
}
