// 178488KB, 476ms

package bj13325;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_K = 20;

	static int N;
	static int[] weights;
	static int[] maxDists;
	static int answer = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int K = Integer.parseInt(br.readLine());

		N = (1 << (K + 1)) - 1;

		weights = new int[N + 1];
		maxDists = new int[N + 1];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 2; i <= N; i++) {
			int weight = Integer.parseInt(st.nextToken());
			weights[i] = weight;
			answer += weight;
		}

		dfs(1);

		System.out.println(answer);

	} // end main

	public static int dfs(int start) {
		if (start > N) {
			return 0;
		}

		int left = dfs(2 * start);
		int right = dfs(2 * start + 1);

		answer += Math.abs(right - left);

		int ret = weights[start] + Math.max(left, right);
		return maxDists[start] = ret;
	}

}