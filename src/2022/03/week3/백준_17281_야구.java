// 20852KB, 448ms

package bj17281;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Field {
	/** 각 루의 상태 (주자가 존재하면 1, 아니면 0 */
	int firstBase;
	int secondBase;
	int thirdBase;
	/** 아웃카운트 */
	int outCount;

	/** 모든 루가 비어있고, 아웃카운트가 0인 상태로 야구장 필드 객체 생성 */
	public Field() {
		super();
		this.firstBase = 0;
		this.secondBase = 0;
		this.thirdBase = 0;
		this.outCount = 0;
	}

	/** 타격 타입이 주어졌을 때, 아웃카운트와 주자 상황을 갱신하고, 이 타격으로 얻게 되는 점수를 리턴 */
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

	public static int[] candidates = { 1, 2, 3, 4, 5, 6, 7, 8 };
	public static boolean[] isUsed = new boolean[8];

	public static int[] permu = new int[9];
	public static int maxScore = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 이닝 수 입력
		N = Integer.parseInt(br.readLine());

		// 각 이닝의 결과 입력
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < 9; x++) {
				inningResults[i][x] = Integer.parseInt(st.nextToken());
			}
		}

		// 타자 8명을 배치하는 모든 순열을 만들어 보면서 최고 점수 갱신
		permutation(0);

		// 최고 점수 출력
		System.out.println(maxScore);
	}

	/** 현재 타자 순서 permu에서 게임을 플레이해보고, 점수를 리턴 */
	public static int getScore() {
		// 1, 2, 3번 타자
		int[] hitOrder = new int[9];
		for (int i = 0; i <= 2; i++) {
			hitOrder[i] = permu[i];
		}
		// 4번 타자
		hitOrder[3] = 0;
		// 5, 6, 7, 8, 9번 타자
		for (int i = 4; i < 9; i++) {
			hitOrder[i] = permu[i - 1];
		}

		// 게임 시작
		int score = 0;
		int currentHitterIdx = 0;
		// N개 이닝을 수행
		for (int inning = 0; inning < N; inning++) {
			// 매 이닝을 주자 없고, 0아웃인 상태로 시작
			Field field = new Field();

			// 3아웃 당하기 전까지 이닝 수행
			while (field.outCount < 3) {
				int hitType = inningResults[inning][hitOrder[currentHitterIdx]];
				currentHitterIdx = (currentHitterIdx + 1) % 9;
				score += field.getHitResult(hitType);
			}
		}

		return score;
	}

	/** 모든 순열을 만들어보면서 최고 점수를 갱신 */
	public static void permutation(int idx) {
		// 순열이 완성된 경우, 이 타자 배치에서의 점수를 계산해서 최고 점수를 갱신한다.
		if (idx == 8) {
			int score = getScore();
			maxScore = maxScore < score ? score : maxScore;
		}

		// 순열이 완성되지 않은 경우, 재귀 호출로 순열을 만든다.
		for (int i = 0; i < 8; i++) {
			if (!isUsed[i]) {
				isUsed[i] = true;
				permu[idx] = candidates[i];
				permutation(idx + 1);
				isUsed[i] = false;
			}
		}
	}

}