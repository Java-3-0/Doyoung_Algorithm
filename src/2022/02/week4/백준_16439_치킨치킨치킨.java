// 12696KB, 112ms

package bj16439;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_PEOPLE = 30, MAX_CHICKENS = 30;
	public static int people, chickens;

	/** preference[y][x]는 y번 고객이 x번 치킨에 대해 가지는 선호도 */
	public static int[][] preferences = new int[MAX_PEOPLE][MAX_CHICKENS];
	public static boolean[] isSelected = new boolean[MAX_CHICKENS];
	public static int maxPref = 0;

	// 메인 함수 시작
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		// N, M 입력받음
		st = new StringTokenizer(br.readLine(), " ");
		people = Integer.parseInt(st.nextToken());
		chickens = Integer.parseInt(st.nextToken());

		// 치킨 선호도 입력받아서 2차원 배열로 저장
		for (int y = 0; y < people; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < chickens; x++) {
				preferences[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		// 모든 조합을 만들어보며, maxPref 갱신
		combination(0, 0);

		// 출력
		System.out.println(maxPref);
	}

	public static void combination(int hereIdx, int cnt) {
		// base case 1 : 조합이 완성된 경우
		if (cnt == 3) {
			int sum = 0;

			// 각 사람마다, 선택된 세 치킨 중 가장 선호도가 높은 것을 고른다.
			for (int person = 0; person < people; person++) {
				int pref = 0;
				for (int chicken = 0; chicken < chickens; chicken++) {
					if (isSelected[chicken]) {
						pref = Math.max(pref, preferences[person][chicken]);
					}
				}
				sum += pref;
			}

			maxPref = Math.max(maxPref, sum);
			return;
		}

		// base case 2 : 조합을 완성하지 못하고 배열 끝까지 간 경우
		if (hereIdx == chickens) {
			return;
		}

		// 이 치킨을 넣는 경우
		isSelected[hereIdx] = true;
		combination(hereIdx + 1, cnt + 1);

		// 이 치킨을 넣지 않는 경우
		isSelected[hereIdx] = false;
		combination(hereIdx + 1, cnt);
	}
}