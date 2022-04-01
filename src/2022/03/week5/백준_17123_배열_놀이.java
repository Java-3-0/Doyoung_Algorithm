// 351572KB, 2160ms

package bj17123;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {

	static final int MAX_N = 1000, MAX_M = 1000;
	static int N, M;
	static int[][] grid = new int[MAX_N + 1][MAX_N + 1];
	static int[] rowSums = new int[MAX_N + 1];
	static int[] colSums = new int[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			// 메모리 초기화
			initMemory();

			// N, M 입력
			st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());

			// 그리드 초기 상태 입력
			for (int y = 1; y <= N; y++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int x = 1; x <= N; x++) {
					grid[y][x] = Integer.parseInt(st.nextToken());
				}
			}

			// 초기 상태에서의 rowSum[], colSum[] 배열 계산
			for (int y = 1; y <= N; y++) {
				int sum = 0;
				for (int x = 1; x <= N; x++) {
					sum += grid[y][x];
				}
				rowSums[y] = sum;
			}

			for (int x = 1; x <= N; x++) {
				int sum = 0;
				for (int y = 1; y <= N; y++) {
					sum += grid[y][x];
				}
				colSums[x] = sum;
			}

			// M개의 업데이트 요청 수행하면서 rowSums, colSums 갱신
			for (int i = 0; i < M; i++) {
				st = new StringTokenizer(br.readLine(), " ");
				int y1 = Integer.parseInt(st.nextToken());
				int x1 = Integer.parseInt(st.nextToken());
				int y2 = Integer.parseInt(st.nextToken());
				int x2 = Integer.parseInt(st.nextToken());
				int v = Integer.parseInt(st.nextToken());

				int yLen = y2 - y1 + 1;
				int xLen = x2 - x1 + 1;

				int rowUpdate = v * xLen;
				int colUpdate = v * yLen;

				for (int y = y1; y <= y2; y++) {
					rowSums[y] += rowUpdate;
				}
				for (int x = x1; x <= x2; x++) {
					colSums[x] += colUpdate;
				}

			}

			// 출력 스트링빌더에 추가
			for (int y = 1; y <= N; y++) {
				sb.append(rowSums[y]).append(" ");
			}
			sb.append("\n");
			for (int x = 1; x <= N; x++) {
				sb.append(colSums[x]).append(" ");
			}
			sb.append("\n");

		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 메모리 초기화 */
	public static void initMemory() {
		for (int i = 0; i < grid.length; i++) {
			Arrays.fill(grid[i], 0);
		}
		Arrays.fill(rowSums, 0);
		Arrays.fill(colSums, 0);
	}

}