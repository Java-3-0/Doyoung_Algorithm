// 23112KB, 260ms

package bj1806;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int INF = Integer.MAX_VALUE;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, S 입력
		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int S = Integer.parseInt(st.nextToken());

		// 수열을 입력받아서 prefix sum을 계산
		int[] psums = new int[N + 1];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1, sum = 0; i <= N; i++) {
			sum += Integer.parseInt(st.nextToken());
			psums[i] = sum;
		}

		int left = 0;
		int right = N;
		int minLen = INF;
		while (0 <= left && left < right && right <= N && S <= psums[right]) {
			int sum = psums[right] - psums[left];
			// 오른쪽 커서를 고정시켜 놓고, 왼쪽 커서를 옮겨본다.
			if (S <= sum) {
				int len = right - left;
				if (len < minLen) {
					minLen = len;
				}
				left++;
			}
			// 합이 S보다 작아진 순간, 오른쪽 커서를 한 칸 왼쪽으로 옮기고, 왼쪽 커서를 그에 맞게 옮긴다.
			else {
				right--;
				while (0 <= left && psums[right] - psums[left] < S) {
					left--;
				}
			}
		}

		if (minLen == INF) {
			System.out.println(0);
		} else {
			System.out.println(minLen);
		}

	} // end main

}