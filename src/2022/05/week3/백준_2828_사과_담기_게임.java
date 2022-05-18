// 11416KB, 72ms

package bj2828;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		int N = Integer.parseInt(st.nextToken());
		int M = Integer.parseInt(st.nextToken());

		int J = Integer.parseInt(br.readLine());

		int left = 1;
		int right = left + M - 1;

		int answer = 0;
		for (int i = 0; i < J; i++) {
			int apple = Integer.parseInt(br.readLine());

			// 사과가 현재 바구니 범위 내로 떨어지는 경우
			if (left <= apple && apple <= right) {

			}
			// 사과가 오른쪽으로 떨어지는 경우
			else if (right < apple) {
				int move = apple - right;
				left += move;
				right += move;
				answer += move;
			}
			// 사과가 왼쪽으로 떨어지는 경우
			else {
				int move = left - apple;
				left -= move;
				right -= move;
				answer += move;
			}
		}

		System.out.println(answer);

	} // end main
}