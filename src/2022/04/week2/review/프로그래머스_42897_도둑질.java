package pg42897;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Solution {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());

		int[] money = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			money[i] = Integer.parseInt(st.nextToken());
		}

		int answer = solution(money);

		System.out.println(answer);

	} // end main

	public static int solution(int[] money) {
		int len = money.length;
		int lastIdx = len - 1;

		// dp[i]는 i번 집까지 고려한 최대 스틸량

		// 첫 집을 터는 경우의 dp
		int[] dpFirst = new int[len];
		dpFirst[0] = money[0];
		dpFirst[1] = Math.max(money[0], money[1]);

		for (int i = 2; i < lastIdx; i++) {
			dpFirst[i] = Math.max(dpFirst[i - 1], money[i] + dpFirst[i - 2]);
		}

		dpFirst[lastIdx] = dpFirst[lastIdx - 1];

		// 첫 집을 털지 않는 경우의 dp
		int[] dpNoFirst = new int[len];
		dpNoFirst[0] = 0;
		dpNoFirst[1] = money[1];

		for (int i = 2; i <= lastIdx; i++) {
			dpNoFirst[i] = Math.max(dpNoFirst[i - 1], money[i] + dpNoFirst[i - 2]);
		}

		// 두 경우 중 더 많이 훔치는 쪽을 리턴
		int answer = Math.max(dpFirst[len - 1], dpNoFirst[len - 1]);

		return answer;
	}
}