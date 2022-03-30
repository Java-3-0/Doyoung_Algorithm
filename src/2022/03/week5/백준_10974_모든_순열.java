package bj10974;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static StringBuilder sb = new StringBuilder();
	static int N;
	static int[] permu;
	static boolean[] isUsed;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// N 입력
		N = Integer.parseInt(br.readLine());

		// 전역변수 메모리 할당
		permu = new int[N];
		isUsed = new boolean[N + 1];

		permutation(0);

		System.out.print(sb.toString());

	} // end main

	public static void permutation(int cnt) {
		if (cnt == N) {
			for (int num : permu) {
				sb.append(num).append(" ");
			}
			sb.append("\n");
			return;
		}

		for (int i = 1; i <= N; i++) {
			if (isUsed[i]) {
				continue;
			}

			isUsed[i] = true;
			permu[cnt] = i;
			permutation(cnt + 1);
			isUsed[i] = false;
		}

	}

}