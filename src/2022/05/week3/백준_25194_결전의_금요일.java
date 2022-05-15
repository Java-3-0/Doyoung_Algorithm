// 11804KB, 84ms

package bj25194;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int WEEK = 7;
	static final int MONDAY = 0;
	static final int FRIDAY = 4;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 일의 개수 입력
		int N = Integer.parseInt(br.readLine());

		// 초기 상태 세팅
		boolean[][] isPossible = new boolean[2][WEEK];
		isPossible[0][MONDAY] = true;
		int prev = 0;
		int now = 1;

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			now = (prev + 1) % 2;

			int delta = Integer.parseInt(st.nextToken());
			for (int day = 0; day < WEEK; day++) {
				if (isPossible[prev][day]) {
					// 기존에 가능하던 요일은 계속 가능
					isPossible[now][day] = true;

					// 새로운 delta를 더한 요일도 가능
					int nextDay = (day + delta) % WEEK;
					isPossible[now][nextDay] = true;
				}
			}

			prev = now;
		}

		if (isPossible[now][FRIDAY]) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}

	} // end main
}