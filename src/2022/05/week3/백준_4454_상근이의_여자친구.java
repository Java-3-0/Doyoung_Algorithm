// 13632KB, 112ms

package bj4454;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static double A, B, C, D, M, T;
	static final double INF = 987654321098765.0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		String line = "";
		while ((line = br.readLine()) != null) {
			st = new StringTokenizer(line, " ");
			A = Double.parseDouble(st.nextToken());
			B = Double.parseDouble(st.nextToken());
			C = Double.parseDouble(st.nextToken());
			D = Double.parseDouble(st.nextToken());
			M = Double.parseDouble(st.nextToken());
			T = Double.parseDouble(st.nextToken());

			double answer = solve();

			sb.append(String.format("%.2f", answer)).append("\n");

		}

		System.out.print(sb.toString());

	} // end main

	public static double solve() {
		double left = 1e-10;
		double right = INF;
		
		for (int i = 0; i < 100; i++) {
			double mid = (left + right) / 2.0;
			
			if (isPossible(mid)) {
				left = mid;
			}
			else {
				right = mid;
			}
		}
		
		return Math.floor(left * 100.0) / 100.0;
	}

	public static boolean isPossible(double v) {
		double time = M / v;

		double fuelUse = 0.0;
		fuelUse += A * Math.pow(v, 4);
		fuelUse += B * Math.pow(v, 3);
		fuelUse += C * Math.pow(v, 2);
		fuelUse += D * v;

		fuelUse *= time;
		
		if (fuelUse <= T) {
			return true;
		} else {
			return false;
		}
	}
}