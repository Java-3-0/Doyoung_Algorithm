// 89780KB, 1081ms

package swea5656;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 4;
	static final int MAX_W = 12;
	static final int MAX_H = 15;
	static final int[] dy = { 0, 0, 1, -1 };
	static final int[] dx = { -1, 1, 0, 0 };
	static final int INF = 987654321;

	static int N, W, H;
	static int[][] grid = new int[MAX_H][MAX_W];
	static int[] permu = new int[MAX_N];
	static int minBlocks;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			minBlocks = INF;

			// N, W, H 입력
			st = new StringTokenizer(br.readLine(), " ");
			N = Integer.parseInt(st.nextToken());
			W = Integer.parseInt(st.nextToken());
			H = Integer.parseInt(st.nextToken());

			// grid 입력
			for (int y = 0; y < H; y++) {
				st = new StringTokenizer(br.readLine(), " ");
				for (int x = 0; x < W; x++) {
					int input = Integer.parseInt(st.nextToken());
					grid[y][x] = input;
				}
			}

			// 모든 중복순열을 완전탐색하면서 minBlocks 갱신
			permutation(0);

			// 정답
			int answer = minBlocks;

			// 정답을 형식에 맞게 스트링빌더에 추가
			sb.append("#").append(tc).append(" ").append(answer).append("\n");
		}

		// 출력
		System.out.print(sb.toString());

	} // end main

	/** 모든 중복순열을 완전탐색하면서 minBlocks를 갱신한다 */
	public static void permutation(int cnt) {
		// 중복순열이 완성된 경우
		if (cnt == N) {
			int blocks = getRemainingBlocks();
			minBlocks = blocks < minBlocks ? blocks : minBlocks;
			return;
		}

		// 중복순열을 만드는 경우
		for (int i = 0; i < W; i++) {
			permu[cnt] = i;
			permutation(cnt + 1);
		}
	}

	/** 현재 permu 대로 구슬을 쐈을 때 남는 블럭 수를 리턴 */
	public static int getRemainingBlocks() {
		// grid 배열을 gridCopy에 복사
		int[][] gridCopy = new int[H][W];
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				gridCopy[y][x] = grid[y][x];
			}
		}

		// N번의 구슬 쏘기 시행
		for (int i = 0; i < N; i++) {
			int x = permu[i];
			for (int y = 0; y < H; y++) {
				// 맨 위 블록을 만나면
				if (gridCopy[y][x] != 0) {
					// 그 위치에서부터 폭발 범위만큼 폭발
					explodeBlocks(gridCopy, y, x, gridCopy[y][x]);

					// 폭발 후 빈 공간이 있는 만큼 블록은 떨어진다
					dropBlocks(gridCopy);

					break;
				}
			}
		}

		// 남은 블록 수 세서 리턴
		return countBlocks(gridCopy);
	}

	/** arr 전체에서 빈 공간들에 블록이 떨어지는 것을 시뮬레이션하여 arr을 갱신 */
	public static void dropBlocks(int[][] arr) {
		for (int x = 0; x < W; x++) {
			// 남아있는 블록들을 alives에 담는다
			List<Integer> alives = new ArrayList<>();
			for (int y = H - 1; y >= 0; y--) {
				int cur = arr[y][x];
				if (cur != 0) {
					alives.add(cur);
				}
				
				// arr은 비운다
				arr[y][x] = 0;
			}

			// arr의 맨 아래칸부터 alives에 들어있는 값들로 채운다
			for (int i = 0; i < alives.size(); i++) {
				arr[H - 1 - i][x] = alives.get(i);
			}
		}
	}

	/** (y, x)위치를 중심으로 일어나는 블록 폭발을 시뮬레이션 하여 arr를 갱신 */
	public static void explodeBlocks(int[][] arr, int y, int x, int explodeRange) {
		for (int dir = 0; dir < dy.length; dir++) {
			for (int dist = 0; dist < explodeRange; dist++) {
				int ny = y + dy[dir] * dist;
				int nx = x + dx[dir] * dist;
				if (isInRange(ny, nx)) {
					// 연쇄 폭발 수행
					if (arr[ny][nx] != 0) {
						int newRange = arr[ny][nx];
						arr[ny][nx] = 0;
						explodeBlocks(arr, ny, nx, newRange);
					}

				}
			}
		}
	}

	/** arr에 존재하는 블록의 수를 세서 리턴 */
	public static int countBlocks(int[][] arr) {
		int ret = 0;

		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				if (arr[y][x] != 0) {
					ret++;
				}
			}
		}

		return ret;
	}

	/** (y, x)가 그리드 범위 내인지 여부를 리턴 */
	public static boolean isInRange(int y, int x) {
		if (0 <= y && y < H && 0 <= x && x < W) {
			return true;
		}

		return false;
	}

	public static void print(int[][] arr) {
		for (int y = 0; y < H; y++) {
			for (int x = 0; x < W; x++) {
				System.out.print(arr[y][x] + " ");
			}
			System.out.println();
		}

		System.out.println();
	}
}