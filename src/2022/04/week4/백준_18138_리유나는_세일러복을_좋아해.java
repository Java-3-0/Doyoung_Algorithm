// 13444KB, 140ms

package bj18138;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 200, MAX_M = 200;

	/** 정의역 크기, 공역 크기 */
	static int N, M;
	/** 정의역 */
	static double[] domain;
	/** 공역 */
	static double[] codomain;
	/** 확인 여부를 나타내는 배열 */
	static boolean[] isChecked;
	/** 레인지의 각 원소가 도메인의 몇 번째 원소와 매칭된 상태인지 나타내는 배열 */
	static int[] isMatchedTo;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 전역변수 메모리 할당
		domain = new double[N + 1];
		codomain = new double[M + 1];
		isChecked = new boolean[M + 1];
		isMatchedTo = new int[M + 1];

		// 정의역 정보 입력
		for (int x = 1; x <= N; x++) {
			domain[x] = Double.parseDouble(br.readLine());
		}

		// 공역 정보 입력
		for (int y = 1; y <= M; y++) {
			codomain[y] = Double.parseDouble(br.readLine());
		}

		// 최대 매칭 수 계산
		int answer = getMaxMatches();

		// 출력
		System.out.println(answer);

	} // end main

	/** 가능한 최대 매칭 수를 구해서 리턴 */
	public static int getMaxMatches() {
		int ret = 0;

		for (int x = 1; x <= N; x++) {
			Arrays.fill(isChecked, false);

			if (dfs(x)) {
				ret++;
			}
		}

		return ret;
	}

	/** dfs를 수행하면서 매칭 시도 */
	public static boolean dfs(int x) {
		for (int y = 1; y <= M; y++) {
			// 매칭이 불가능하면 다음 후보로 이동
			if (!canMatch(x, y)) {
				continue;
			}

			if (!isChecked[y]) {
				isChecked[y] = true;

				// 아직 매칭되지 않았거나, 기존 매칭을 옮겨줄 수 있는 경우
				if (isMatchedTo[y] == 0 || dfs(isMatchedTo[y])) {
					isMatchedTo[y] = x;
					return true;
				}
			}
		}

		return false;
	}

	/** 정의역의 x번째 원소와 공역의 y번째 원소의 매칭이 문제에서 주어진 조건에 맞는지 여부를 리턴 */
	public static boolean canMatch(int x, int y) {
		double wx = domain[x];
		double wy = codomain[y];

		// w/2이상 w*3/4이하인 경우
		if (wx / 2.0 <= wy && wy <= wx * 3.0 / 4.0) {
			return true;
		}

		// w이상 w*5/4이하인 경우
		if (wx <= wy && wy <= wx * 5.0 / 4.0) {
			return true;
		}

		return false;
	}
}