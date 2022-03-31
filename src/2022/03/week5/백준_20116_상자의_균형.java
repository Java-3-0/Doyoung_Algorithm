// 55868KB, 320ms

package bj20116;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_N = 200000;

	static int N;
	static double L;
	static double[] boxes;
	static double[] psums;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		L = Double.parseDouble(st.nextToken());

		boxes = new double[N + 1];
		psums = new double[N + 1];

		double sum = 0.0;
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			double input = Double.parseDouble(st.nextToken());
			sum += input;
			boxes[i] = input;
			psums[i] = sum;
		}

		String answer = isStable() ? "stable" : "unstable";

		System.out.println(answer);

	} // end main

	public static boolean isStable() {
		for (int base = 1; base <= N; base++) {
			double rangeLeft = boxes[base] - L;
			double rangeRight = boxes[base] + L;

			double center = getCenterOf(base + 1, N);

			if (center <= rangeLeft || center >= rangeRight) {
				return false;
			}
		}

		return true;
	}

	public static double getCenterOf(int bottom, int top) {
		double sum = psums[top] - psums[bottom - 1];
		double cnt = (double) (top - (bottom - 1));

		return sum / cnt;
	}

}