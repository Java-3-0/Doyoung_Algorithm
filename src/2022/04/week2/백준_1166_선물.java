// 11748KB, 92ms

package bj1166;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final double MAX_NUM = (double) 1e9;
	static double N, L, W, H;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Long.parseLong(st.nextToken());
		L = Double.parseDouble(st.nextToken());
		W = Double.parseDouble(st.nextToken());
		H = Double.parseDouble(st.nextToken());

		double answer = solve();

		System.out.println(answer);

	} // end main

	public static double solve() {
		double left = 0.0;
		double right = MAX_NUM;

		for (int i = 0; i < 500; i++){
			double mid = (left + right) / 2.0;

			if (isPossible(mid)) {
				left = mid;
			} else {
				right = mid;
			}
		}
		
		return left;

	}

	public static boolean isPossible(double A) {
		if (A == 0.0) {
			return true;
		}

		long a = (long) Math.floor(L / A);
		long b = (long) Math.floor(W / A);
		long c = (long) Math.floor(H / A);

		long cnt = a * b;
		if (cnt < 0) {
			return true;
		}
		cnt = cnt * c;
		if (cnt < 0) {
			return true;
		}

		if (cnt >= N) {
			return true;
		} else {
			return false;
		}

	}

}