// 13264KB, 116ms

package bj14890;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100;
	static final int MAX_L = MAX_N;

	static int N, L;
	static int[][] rows;
	static int[][] cols;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, L 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		L = Integer.parseInt(st.nextToken());

		// 지도 상태 입력
		rows = new int[N][N];
		cols = new int[N][N];
		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < N; x++) {
				int input = Integer.parseInt(st.nextToken());
				rows[y][x] = input;
				cols[x][y] = input;
			}
		}

		// 지나갈 수 있는 길의 수 계산
		int answer = countPassablePaths();

		// 출력
		System.out.println(answer);

	} // end main

	/** 지나갈 수 있는 길의 수를 계산해서 리턴 */
	public static int countPassablePaths() {
		int ret = 0;
		for (int i = 0; i < N; i++) {
			if (canPass(rows[i])) {
				ret++;
			}
			if (canPass(cols[i])) {
				ret++;
			}
		}

		return ret;
	}

	/** 길의 높이 정보가 arr에 담겨 있을 때, 통과 가능하면 true, 아니면 false를 리턴 */
	public static boolean canPass(int[] arr) {
		boolean[] isUsed = new boolean[N];

		int prev = arr[0];
		for (int i = 1; i < N; i++) {
			int now = arr[i];
			int diff = now - prev;
			
			// 높이가 같은 경우, 아무 것도 하지 않고 다음 칸으로 이동
			if (diff == 0) {
				continue;
			}
			
			// 높이가 1 높아진 경우, 이전 위치부터 좌측으로 경사로 놓기
			else if (diff == 1) {
				for (int k = 0; k < L; k++) {
					int idx = i - 1 - k;

					// 범위를 초과하거나, 높이가 다르거나, 이미 경사로가 놓인 칸을 만나면 실패
					if (idx < 0 || arr[idx] != prev || isUsed[idx]) {
						return false;
					}

					// 아니면 이 칸에 경사로를 놓아본다.
					isUsed[idx] = true;
				}
			}

			// 높이가 1 낮아진 경우, 현재 위치부터 우측으로 경사로 놓기 시도
			else if (diff == -1) {
				for (int k = 0; k < L; k++) {
					int idx = i + k;

					// 범위를 초과하거나, 높이가 다르거나, 이미 경사로가 놓인 칸을 만나면 실패
					if (idx >= N || arr[idx] != now || isUsed[idx]) {
						return false;
					}

					// 아니면 이 칸에 경사로를 놓아본다.
					isUsed[idx] = true;
				}
			}

			// 높이 차이가 1을 초과하면 실패
			else {
				return false;
			}

			// prev 값 갱신
			prev = now;
			
		} // end for i

		// 길의 끝까지 실패하지 않았다면, 성공
		return true;
		
	} // end canPass
}