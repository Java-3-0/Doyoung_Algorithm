// 11548KB, 76ms

package bj2435;

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

		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(st.nextToken());

		st = new StringTokenizer(br.readLine(), " ");
		int[] seq = new int[N + 1];
		int[] psums = new int[N + 1];
		for (int i = 1, sum = 0; i <= N; i++) {
			int input = Integer.parseInt(st.nextToken());
			sum += input;
			seq[i] = input;
			psums[i] = sum;
		}

		int maxSum = Integer.MIN_VALUE;
		for (int i = 0; i + K <= N; i++) {
			int sumOfKDays = psums[i + K] - psums[i];
			maxSum = Math.max(maxSum, sumOfKDays);
		}

		System.out.println(maxSum);

	} // end main

}