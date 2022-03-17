// 203480KB, 932ms

package bj17952;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000000;

	static int N;

	static class Homework {
		/** 과제를 완료했을 때 받는 점수 */
		int score;
		/** 과제를 완료하기까지 남은 시간 */
		int remainingTime;

		public Homework(int score, int remainingTime) {
			super();
			this.score = score;
			this.remainingTime = remainingTime;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N 입력
		N = Integer.parseInt(br.readLine());

		// 정답
		int answer = 0;
		// 과제들이 담길 스택
		Stack<Homework> stack = new Stack<>();
		// 처리할 과제
		Homework current = null;

		for (int time = 0; time < N; time++) {
			// 과제 입력
			st = new StringTokenizer(br.readLine(), " ");
			int inputType = Integer.parseInt(st.nextToken());

			if (inputType == 1) {
				int score = Integer.parseInt(st.nextToken());
				int remainingTime = Integer.parseInt(st.nextToken());
				Homework hw = new Homework(score, remainingTime);

				// 새 과제가 들어왔을 때, 기존 과제가 있었다면 스택에 푸쉬
				if (current != null) {
					stack.push(current);
				}

				// 처리할 과제 = 새 과제
				current = hw;
			}

			// current가 존재하지 않으면 스택에서 하나 가져온다
			if (current == null && !stack.isEmpty()) {
				current = stack.pop();
			}

			// current 과제를 처리한다
			if (current != null) {
				current.remainingTime--;

				// 과제가 끝난 경우
				if (current.remainingTime == 0) {
					answer += current.score;
					current = null;
				}
			}

		} // end for time

		System.out.println(answer);

	} // end main
	
}
