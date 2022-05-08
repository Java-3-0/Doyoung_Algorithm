// 359720KB, 1204ms

package bj13701;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_NUM = 33554432;
	static final int INT_SIZE = 32;
	static int[] bitMasks = new int[1 + MAX_NUM / INT_SIZE];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		st = new StringTokenizer(br.readLine(), " ");

		while (st.hasMoreTokens()) {
			int A = Integer.parseInt(st.nextToken());
			if (!isUsed(A)) {
				sb.append(A).append(" ");
				use(A);
			}
		}
		sb.append("\n");

		System.out.print(sb.toString());
	} // end main

	public static void use(int n) {
		bitMasks[n / INT_SIZE] |= (1 << n % INT_SIZE);
	}

	public static boolean isUsed(int n) {
		if ((bitMasks[n / INT_SIZE] & (1 << n % INT_SIZE)) != 0) {
			return true;
		}
		return false;
	}

}