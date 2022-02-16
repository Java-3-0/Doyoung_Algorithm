// 24360KB, 916ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
	static final int WIN = 1, DRAW = 0, LOSE = -1;

	static final int MAX_NUM = 18;
	static final int HAND = MAX_NUM / 2;
	static int[] kyCards = new int[HAND];
	static int[] iyCards = new int[HAND];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		for (int testCase = 1; testCase <= T; testCase++) {
			// 규영이의 카드인지를 나타낼 boolean 배열
			boolean[] isCardOfKY = new boolean[MAX_NUM + 1];

			// 입력
			st = new StringTokenizer(br.readLine(), " ");
			for (int i = 0; i < HAND; i++) {
				int cardNum = Integer.parseInt(st.nextToken());
				isCardOfKY[cardNum] = true;
			} // end for i

			// 규영, 인영 각자의 카드를 오름차순으로 각자의 배열에 담는다
			int idxKY = 0;
			int idxIY = 0;
			for (int num = 1; num <= MAX_NUM; num++) {
				if (isCardOfKY[num]) {
					kyCards[idxKY++] = num;
				} else {
					iyCards[idxIY++] = num;
				}
			} // end for num

			// 인영이의 모든 카드 조합에 대해 승패 수 계산
			int winCount = 0;
			int loseCount = 0;
			do {
				if (getGameResult() == WIN) {
					winCount++;
				} else if (getGameResult() == LOSE) {
					loseCount++;
				}
			} while (nextPermutationOfIY()); // end do while

			// 출력에 저장
			sb.append("#").append(testCase).append(" ");
			sb.append(winCount).append(" ");
			sb.append(loseCount).append("\n");

		} // end for testCase

		System.out.print(sb.toString());
	}

	/**
	 * 현 카드 배열에서의 게임 결과를 리턴
	 * 
	 * @return 규영이의 승리 시 WIN, 무승부 시 DRAW, 패배 시 LOSE
	 */
	static int getGameResult() {
		// 각자의 점수를 센다
		int kyScore = 0;
		int iyScore = 0;
		for (int i = 0; i < HAND; i++) {
			int scoreOfRound = iyCards[i] + kyCards[i];
			if (iyCards[i] < kyCards[i]) {
				kyScore += scoreOfRound;
			} else {
				iyScore += scoreOfRound;
			}
		}

		// 점수에 따른 게임 결과 리턴
		if (iyScore < kyScore) {
			return WIN;
		} else if (iyScore == kyScore) {
			return DRAW;
		} else {
			return LOSE;
		}
	}

	/**
	 * 인영이가 내는 카드 순서의 오름차순에서의 다음 순열을 만들어서 iyCards 배열을 갱신
	 * 
	 * @return 다음 순열을 만드는 것이 가능하면 true, 더 이상의 다음 순열이 없다면 false
	 */
	static boolean nextPermutationOfIY() {
		// 오른쪽부터 보면서 오름차순으로 가다가, 내림차순으로 바뀌게 되는 그 꼭대기 지점
		int idxTop = 0;
		for (int i = HAND - 1; i > 0; i--) {
			if (iyCards[i - 1] < iyCards[i]) {
				idxTop = i;
				break;
			}
		}

		// 마지막 순열까지 다 찾은 경우
		if (idxTop == 0)
			return false;

		// 꼭대기 지점 하나 왼쪽 칸이 스왑할 지점
		int swapIdx1 = idxTop - 1;

		// 오른쪽부터 보면서 swapIdx1의 값보다 큰 것 중 가장 먼저 만나는 것을 찾는다
		int swapIdx2 = 0;
		for (int i = HAND - 1; i >= idxTop; i--) {
			if (iyCards[swapIdx1] < iyCards[i]) {
				swapIdx2 = i;
				break;
			}
		}

		// 두 위치를 스왑한다
		swapIY(swapIdx1, swapIdx2);

		// idxTop부터 오른쪽 끝까지를 오름차순으로 정렬한다
		int i = idxTop;
		int k = HAND - 1;
		while (i < k) {
			swapIY(i++, k--);
		}

		return true;
	}

	/** iyCards[]의 idx1 위치와 idx2 위치를 swap 한다 */
	static void swapIY(int idx1, int idx2) {
		int tmp = iyCards[idx1];
		iyCards[idx1] = iyCards[idx2];
		iyCards[idx2] = tmp;
	}
}
