// 78692KB, 536ms

package bj17281;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Field {
	int firstBase;
	int secondBase;
	int thirdBase;
	int outCount;

	public Field() {
		super();
		this.firstBase = 0;
		this.secondBase = 0;
		this.thirdBase = 0;
		this.outCount = 0;
	}

	/** 타격 타입에 따라 필드의 상태를 갱신하고, 이 타격이 얻는 득점량을 리턴 */
	public int getHitResult(int hitType) {
		int score = 0;
		switch (hitType) {
		case 0:
			this.outCount++;
			break;
		case 1:
			score = this.thirdBase;
			this.thirdBase = this.secondBase;
			this.secondBase = this.firstBase;
			this.firstBase = 1;
			break;
		case 2:
			score = this.thirdBase + this.secondBase;
			this.thirdBase = this.firstBase;
			this.secondBase = 1;
			this.firstBase = 0;
			break;
		case 3:
			score = this.thirdBase + this.secondBase + this.firstBase;
			this.thirdBase = 1;
			this.secondBase = 0;
			this.firstBase = 0;
			break;
		case 4:
			score = this.thirdBase + this.secondBase + this.firstBase + 1;
			this.thirdBase = 0;
			this.secondBase = 0;
			this.firstBase = 0;
			break;
		default:
			break;
		} // end switch(hitType)

		return score;
	}
}

public class Main {
	public static final int MAX_N = 50;
	public static int N;
	public static int[][] inningResults = new int[MAX_N][9];
	public static int[] hitOrder = {0, 1, 2, 3, 4, 5, 6, 7, 8};
	public static boolean[] isVisited;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < 9; x++) {
				inningResults[i][x] = Integer.parseInt(st.nextToken());
			}
		}

		int maxScore = 0;
		// 모든 순열에 대해 수행
		do {
			// 1번 타자를 4번 타자로
			swap(0, 3);

			// 게임 시작 시 점수는 0점, 타자는 맨 앞 idx부터 타격
			int score = 0;
			int currentHitterIdx = 0;
			
			// N개 이닝을 수행
			innings: for (int inning = 0; inning < N; inning++) {
				// 매 이닝을 주자 없고, 0아웃인 상태로 시작
				Field field = new Field();

				// 3아웃 당하기 전까지 이닝 수행
				while (field.outCount < 3) {
					int hitType = inningResults[inning][hitOrder[currentHitterIdx]];
					currentHitterIdx = (currentHitterIdx + 1) % 9;
					score += field.getHitResult(hitType);
				}
			}

			// 모든 이닝이 끝난 후 최고 점수를 갱신
			if (maxScore < score) {
				maxScore = score;
			}
			
			// nextPermutation()을 부르기 전에 1번 타자를 4번 타자로 바꿔주었던 것을 다시 원래대로
			swap(0, 3);

		} while (nextPermutation()); // end do while

		System.out.println(maxScore);
	}

	/** 첫 타자는 고정하고 나머지 타자들만 바뀔 때, 다음 순열을 구한다 */
	static boolean nextPermutation() {
		// 처음으로 작아지는 곳의 인덱스를 찾는다
		int idxTop = 1;
		for (int i = 8; i >= 2; i--) {
			if (hitOrder[i - 1] < hitOrder[i]) {
				idxTop = i;
				break;
			}
		}
		if (idxTop == 1)
			return false;

		// idx1 위치의 값보다 큰 값 중 오른쪽에서 가장 먼저 만나는 값의 인덱스를 찾는다
		int idxBigger = 0;
		for (int i = 8; i >= idxTop; i--) {
			if (hitOrder[i] > hitOrder[idxTop - 1]) {
				idxBigger = i;
				break;
			}
		}

		// idx1 위치에 있는 값과 idx2 위치에 있는 값 교환
		swap(idxTop - 1, idxBigger);

		// idxTop 위치부터 맨 뒤까지 오름차순 정렬
		int i = idxTop;
		int k = 8;
		while (i < k) {
			swap(i++, k--);
		}

		return true;
	}

	static void swap(int idx1, int idx2) {
		int tmp = hitOrder[idx1];
		hitOrder[idx1] = hitOrder[idx2];
		hitOrder[idx2] = tmp;
	}
}
