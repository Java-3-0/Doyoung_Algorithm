// 77080KB, 1344ms

package bj17503;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int INF = Integer.MAX_VALUE;

	static int N, M, K;
	static Beer[] beers;

	static class Beer implements Comparable<Beer> {
		int pref;
		int degree;

		public Beer(int pref, int degree) {
			super();
			this.pref = pref;
			this.degree = degree;
		}

		/** 선호도 내림차순, 같다면 도수 오름차순으로 정렬하기 위한 비교함수 */
		@Override
		public int compareTo(Beer b) {
			if (this.pref == b.pref) {
				return this.degree - b.degree;
			}
			return -(this.pref - b.pref);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, M, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		beers = new Beer[K];
		
		// 맥주 입력
		for (int i = 0; i < K; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int pref = Integer.parseInt(st.nextToken());
			int degree = Integer.parseInt(st.nextToken());
			beers[i] = new Beer(pref, degree);
		}

		// 맥주 배열 정렬
		Arrays.sort(beers);
		
		if (!isPossible(INF)) {
			System.out.println(-1);
		} else {
			// 정답 계산
			int answer = solve();

			// 출력
			System.out.println(answer);
		}

	} // end main

	/** 이분탐색으로 정답 계산 */
	public static int solve() {
		int left = 1;
		int right = INF;

		while (left < right) {
			int mid = (left / 2) + (right / 2);
			if (left % 2 == 1 && right % 2 == 1) {
				mid += 1;
			}

			if (isPossible(mid)) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}

		return right;
	}

	/** x가 가능한 답인지 여부를 리턴 */
	public static boolean isPossible(int x) {
		int cnt = 0;
		int sumPref = 0;

		for (int i = 0; i < K; i++) {
			if (cnt == N) {
				break;
			}

			if (beers[i].degree <= x) {
				sumPref += beers[i].pref;
				cnt++;
			}
		}

		if (sumPref >= M && cnt == N) {
			return true;
		} else {
			return false;
		}
	}

}