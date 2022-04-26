// 시험 버전
// 13068KB, 128ms
// dp 메모리 = 30 * 15000

package bj2629;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	/** 추의 최대 개수 */
	static final int MAX_N = 30;
	/** 질문의 최대 개수 */
	static final int MAX_Q = 7;
	/** 무게 최대 */
	static final int MAX_WEIGHT = 500;
	/** 무게 합 최대 */
	static final int MAX_TOTAL_WEIGHT = MAX_N * MAX_WEIGHT;

	/** 추의 개수 */
	static int N;
	/** 질문 개수 */
	static int Q;
	/** 추의 무게들 */
	static int[] weights;
	/** isPossible[i][j]는 i번 구슬까지 고려했을 때, j 무게의 측정 가능 여부 */
	static boolean[][] isPossible = new boolean[MAX_N + 1][MAX_TOTAL_WEIGHT + 1]; // 굳이 줄이지 않아도 메모리 제한 충분

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 추의 개수 입력
		N = Integer.parseInt(br.readLine());

		// weights[] 메모리 할당
		weights = new int[N];

		// 추의 무게들 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			weights[i] = Integer.parseInt(st.nextToken());
		}

		// dp를 통해 모든 무게의 가능 여부를 구한다
		isPossible[0][0] = true;

		// 추를 하나씩 더 고려해본다
		for (int idx = 1; idx <= N; idx++) {
			int choo = weights[idx - 1];

			// 모든 무게에 대해 isPossible을 계산
			for (int w = 0; w <= MAX_TOTAL_WEIGHT; w++) {
				// 이전 추까지 가능했던 무게는 계속 가능하다
				isPossible[idx][w] |= isPossible[idx - 1][w];

				// 이전 추까지 가능했던 무게와 이번 추의 무게의 합 또는 차는 가능하다
				if (isInRange(w + choo)) {
					isPossible[idx][w] |= isPossible[idx - 1][w + choo];
				}
				if (isInRange(w - choo)) {
					isPossible[idx][w] |= isPossible[idx - 1][w - choo];
				}
				if (isInRange(choo - w)) {
					isPossible[idx][w] |= isPossible[idx - 1][choo - w];
				}
			}
		}

		// 질문 개수 입력
		Q = Integer.parseInt(br.readLine());

		// 각 질문 입력받아서 처리
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < Q; i++) {
			int target = Integer.parseInt(st.nextToken());

			// 가능 여부에 따라 해당되는 문자를 출력 스트링빌더에 추가
			if (isInRange(target) && isPossible[N][target]) {
				sb.append("Y").append(" ");
			} else {
				sb.append("N").append(" ");
			}
		}
		sb.append("\n");

		// 출력
		System.out.print(sb.toString());
	}

	/** 무게가 isPossible 배열의 인덱스 범위 내인지 여부를 리턴 */
	public static boolean isInRange(int weight) {
		if (0 <= weight && weight <= MAX_TOTAL_WEIGHT) {
			return true;
		} else {
			return false;
		}
	}
}