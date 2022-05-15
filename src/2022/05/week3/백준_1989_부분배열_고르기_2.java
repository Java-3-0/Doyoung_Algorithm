// 27216KB, 336ms

package bj1989;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = (int) 1e5;
	static final long INF = 987654321098765L;
	
	static int N;
	static long[] seq = new long[MAX_N + 1];
	static Answer answer = new Answer(-INF, -1, -1);

	static class Answer {
		long maxScore;
		int left;
		int right;

		public Answer(long maxScore, int left, int right) {
			super();
			this.maxScore = maxScore;
			this.left = left;
			this.right = right;
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 수열 크기 입력
		N = Integer.parseInt(br.readLine());

		// 수열 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 1; i <= N; i++) {
			seq[i] = Long.parseLong(st.nextToken());
		}

		// 최대 점수 계산
		findMaxScore(1, N);

		// 출력
		sb.append(answer.maxScore).append("\n");
		sb.append(answer.left).append(" ").append(answer.right).append("\n");

		System.out.print(sb.toString());

	} // end main

	/** [left, right]번 칸 내에서 가능한 최대 점수를 계산해서 리턴 */
	public static void findMaxScore(int left, int right) {
		// base case. 한 칸인 겨우
		if (left == right) {
			long score = seq[left] * seq[left];
			updateResult(score, left, left);
			return;
		}

		// 분할 정복
		int mid = (left + right) / 2;

		// A. mid는 포함하고 mid + 1은 포함하지 않는 것 고려
		findMaxScore(left, mid);

		// B. mid + 1은 포함하고 mid는 포함하지 않는 것 고려
		findMaxScore(mid + 1, right);

		// C. mid와 mid + 1 모두 포함하는 것 고려
		int curLeft = mid;
		int curRight = mid + 1;

		// 초기에 mid와 mid + 1만 포함한 상태
		long curMin = Math.min(seq[curLeft], seq[curRight]);
		long curSum = seq[curLeft] + seq[curRight];
		long score = curMin * curSum;
		updateResult(score, curLeft, curRight);

		// 모든 칸을 포함시킬 때까지 한 칸씩 추가하는 것을 반복
		while (left < curLeft || curRight < right) {
			// 오른쪽 칸의 값이 더 크면 오른쪽으로 한 칸 이동
			if (curRight < right && (curLeft == left || seq[curRight + 1] >= seq[curLeft - 1])) {
				curRight++;
				curSum += seq[curRight];
				curMin = Math.min(curMin, seq[curRight]);
			}
			// 왼쪽 칸의 값이 더 크면 왼쪽으로 한 칸 이동
			else {
				curLeft--;
				curSum += seq[curLeft];
				curMin = Math.min(curMin, seq[curLeft]);
			}

			// 점수 계산
			score = curMin * curSum;
			updateResult(score, curLeft, curRight);
		}

		return;
	}

	/** 정답 결과를 갱신 */
	public static void updateResult(long score, int left, int right) {
		if (answer.maxScore < score) {
			answer.maxScore = score;
			answer.left = left;
			answer.right = right;
		}
	}
}