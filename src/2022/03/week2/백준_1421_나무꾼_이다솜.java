// 11680KB, 92ms

package bj1421;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_HEIGHT = 10000;
	static final int MAX_COST = 10000;
	static final int MAX_PRICE = 10000;
	static final int MAX_N = 50;

	static int N, cost, price;
	static int[] trees;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 나무 개수, 자르는 비용, 단위 당 가격 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		cost = Integer.parseInt(st.nextToken());
		price = Integer.parseInt(st.nextToken());

		// 나무 길이 입력
		trees = new int[N];
		for (int i = 0; i < N; i++) {
			trees[i] = Integer.parseInt(br.readLine());
		}

		// 모든 높이를 완전탐색
		long maxProfit = 0L;
		for (int height = 1; height <= MAX_HEIGHT; height++) {
			maxProfit = Math.max(maxProfit, getProfit(height));
		}

		// 정답 출력
		System.out.println(maxProfit);
	}

	/** 나무들을 height 길이로 자를 때 얻는 수익을 리턴 */
	public static long getProfit(int height) {
		long totalProfit = 0L;

		for (int tree : trees) {
			int pieces = tree / height;
			int cuts = pieces;

			if (tree % height == 0) {
				cuts--;
			}

			long profit = (long) (pieces * height * price - cuts * cost);

			totalProfit += Math.max(profit, 0L);
		}

		return totalProfit;
	}
}