// 38340KB, 424ms

package bj16471;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 카드 개수 입력
		int N = Integer.parseInt(br.readLine());

		// 내 카드들 정보 입력
		int[] myCards = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			myCards[i] = Integer.parseInt(st.nextToken());
		}

		// 상대 카드들 정보 입력
		int[] enemyCards = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			enemyCards[i] = Integer.parseInt(st.nextToken());
		}

		// 카드들을 오름차순 정렬한다
		Arrays.sort(myCards);
		Arrays.sort(enemyCards);

		// 초기 상태
		int wins = 0;
		int myIdx = 0;

		// 상대는 가장 작은 수부터 낸다고 가정한다
		for (int enemyIdx = 0; enemyIdx < N; enemyIdx++) {
			// 내가 제일 작은 수를 냈을 때 이길 수 있으면 이긴다
			if (myCards[myIdx] < enemyCards[enemyIdx]) {
				myIdx++;
				wins++;
			}

			// 못 이길 거면 제일 큰 수를 버린다.
		}

		// 라운드 중 과반수 이상 승리 시 게임 승리
		if (N / 2 < wins) {
			System.out.println("YES");
		} else {
			System.out.println("NO");
		}

	} // end main
}