// 12096KB, 184ms

package bj19949;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int PROBLEMS = 10;
	static int[] correctAnswers = new int[PROBLEMS];
	static int[] permu = new int[PROBLEMS];
	static int answer = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 시험 정답 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < PROBLEMS; i++) {
			correctAnswers[i] = Integer.parseInt(st.nextToken());
		}

		// 모든 순열 완전탐색
		permutation(0, -1, -1);
		
		// 출력
		System.out.println(answer);
	}

	public static void permutation(int nowIdx, int prev, int prev2) {
		if (nowIdx == PROBLEMS) {
			int cnt = 0;
			for (int i = 0; i < PROBLEMS; i++) {
				if (permu[i] == correctAnswers[i]) {
					cnt++;
				}
			}
			
			if (cnt >= 5) {
				answer++;
			}

			return;
		}

		for (int pick = 1; pick <= 5; pick++) {
			// 3연속으로 같은 번호는 쓰지 않는다.
			if (pick == prev && pick == prev2) {
				continue;
			}

			permu[nowIdx] = pick;
			permutation(nowIdx + 1, pick, prev);
		}

	}

}