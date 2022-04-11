// 23000KB, 244ms

package bj20159;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());

		int[] psumsOdd = new int[1 + N / 2];
		int[] psumsEven = new int[1 + N / 2];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1, sumOdd = 0, sumEven = 0; i <= N / 2; i++) {
			int inputOdd = Integer.parseInt(st.nextToken());
			int inputEven = Integer.parseInt(st.nextToken());

			sumOdd += inputOdd;
			sumEven += inputEven;

			psumsOdd[i] = sumOdd;
			psumsEven[i] = sumEven;
		}

		int answer = getMaximum(psumsOdd, psumsEven);

		System.out.println(answer);

	} // end main

	public static int getSum(int[] psum, int start, int end) {
		if (start > end) {
			return 0;
		}

		return psum[end] - psum[start - 1];
	}

	public static int getMaximum(int[] psumsOdd, int[] psumsEven) {
		int ret = 0;

		// 내가 밑장을 가져가는 경우
		for (int i = 0; i < psumsEven.length; i++) {
			int sum = getSum(psumsOdd, 1, i) + getSum(psumsEven, i + 1, psumsEven.length - 1);
			ret = ret < sum ? sum : ret;
		}

		// 상대가 밑장을 가져가는 경우
		for (int i = 1; i < psumsEven.length; i++) {
			int sum = getSum(psumsOdd, 1, i) + getSum(psumsEven, i, psumsEven.length - 1)
					- getSum(psumsEven, psumsEven.length - 1, psumsEven.length - 1);
			ret = ret < sum ? sum : ret;
		}

		return ret;
	}

}