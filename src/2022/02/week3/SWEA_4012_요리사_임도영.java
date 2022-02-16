import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Solution {
	/** 최대 식재료 개수 */
	public static final int MAX_N = 16;
	/** 식재료들 사이의 시너지를 나타낸 2차원 배열 */
	public static int[][] synergy = new int[MAX_N][MAX_N];
	/** 식재료 선택 여부를 나타낼 boolean 배열 */
	public static boolean[] isPicked = new boolean[MAX_N];

	/** 식재료 개수 */
	public static int N;
	/** 식재료 개수의 절반 */
	public static int halfN;
	/** 구하려는 답의 최소값 */
	public static int minDiff;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int testCase = 1; testCase <= TESTS; testCase++) {
			// 전역변수 초기화
			minDiff = Integer.MAX_VALUE;
			Arrays.fill(isPicked, false);

			// 입력
			N = Integer.parseInt(br.readLine());
			halfN = N / 2;

			for (int y = 0; y < N; y++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int x = 0; x < N; x++) {
					int input = Integer.parseInt(st.nextToken());
					synergy[y][x] = input;
				}
			}

			// N개 중 halfN개를 고르는 모든 조합을 만들어보며 minDiff 값 갱신
			combination(0, 0);

			// 구하려는 답은 minDiff
			int answer = minDiff;

			// 스트링빌더에 형식에 맞게 답 추가
			sb.append("#").append(testCase).append(" ").append(answer).append("\n");
		} // end for testCase

		System.out.print(sb.toString());
	}

	/** N개의 식재료 중 halfN개의 식재료를 고르는 조합을 완전탐색하면서 minDiff 값 갱신 */
	public static void combination(int hereIdx, int cnt) {
		// 음식 조합이 완성되었을 경우
		if (cnt == halfN) {
			int sum1 = 0; // A요리의 시너지 합
			int sum2 = 0; // B요리의 시너지 합

			for (int y = 0; y < N; y++) {
				for (int x = 0; x < N; x++) {
					// A요리에 두 재료 모두 들어간 경우, 그 시너지를 A쪽에 더함
					if (isPicked[y] && isPicked[x]) {
						sum1 += synergy[y][x];
						// B요리에 두 재료 모두 들어간 경우, 그 시너지를 B쪽에 더함
					} else if (!isPicked[y] && !isPicked[x]) {
						sum2 += synergy[y][x];
					}
				}
			}

			// 차이 계산
			int diff = (sum1 <= sum2) ? sum2 - sum1 : sum1 - sum2;

			// 차이 최소값 갱신
			if (diff < minDiff) {
				minDiff = diff;
			}
		}

		// 음식 조합을 완성하지 못하고 끝까지 간 경우
		if (hereIdx == N) {
			return;
		}

		// 이 재료를 넣는 경우
		isPicked[hereIdx] = true;
		combination(hereIdx + 1, cnt + 1);

		// 이 재료를 넣지 않는 경우
		isPicked[hereIdx] = false;
		combination(hereIdx + 1, cnt);
	}

}
