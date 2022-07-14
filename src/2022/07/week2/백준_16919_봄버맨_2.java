// 12796KB, 112ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final char BOMB = 'O', EMPTY = '.';
	static final int MAX_R = 200, MAX_C = 200, MAX_N = (int) 1e9;
	static final int[] dy = { 0, -1, 1, 0, 0 };
	static final int[] dx = { 0, 0, 0, -1, 1 };

	static int R, C, N;
	static char[][] grid = new char[MAX_R][MAX_C];
	static char[][] explode = new char[MAX_R][MAX_C];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		R = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());
		N = Integer.parseInt(st.nextToken());

		// 초기 상태 입력
		for (int y = 0; y < R; y++) {
			String line = br.readLine();
			for (int x = 0; x < C; x++) {
				grid[y][x] = line.charAt(x);
			}
		}

		// 초기에 설치된 폭탄들이 터진 상태 계산
		for (int y = 0; y < R; y++) {
			Arrays.fill(explode[y], BOMB);
		}

		for (int y = 0; y < R; y++) {
			for (int x = 0; x < C; x++) {
				if (grid[y][x] == BOMB) {
					for (int dir = 0; dir < dy.length; dir++) {
						int ny = y + dy[dir];
						int nx = x + dx[dir];
						if (0 <= ny && ny < R && 0 <= nx && nx < C) {
							explode[ny][nx] = EMPTY;
						}
					}
				}
			}
		}

		// 정답 계산
		char[][] answer = getAnswer();

		System.out.print(toString(answer));

	} // end main

	public static char[][] getAnswer() {
		char[][] ret = new char[R][C];

		// 1일 때는 초기 상태 그대로
		if (N == 1) {
			for (int y = 0; y < R; y++) {
				for (int x = 0; x < C; x++) {
					ret[y][x] = grid[y][x];
				}
			}
			
			return ret;
		}
		
		for (int y = 0; y < R; y++) {
			Arrays.fill(ret[y], BOMB);
		}
		
		int sec = N % 4;
		switch (sec) {
		// 모든 칸에 폭탄을 놓은 상태
		case 0:
		case 2:
			break;

		// 새로 설치한 폭탄이 터진 상태
		case 1:
			for (int y = 0; y < R; y++) {
				for (int x = 0; x < C; x++) {
					if (explode[y][x] == BOMB) {
						for (int dir = 0; dir < dy.length; dir++) {
							int ny = y + dy[dir];
							int nx = x + dx[dir];
							if (0 <= ny && ny < R && 0 <= nx && nx < C) {
								ret[ny][nx] = EMPTY;
							}
						}
					}
				}
			}
			break;
		
		// 초기에 있던 폭탄이 터진 상태
		case 3:
			for (int y = 0; y < R; y++) {
				for (int x = 0; x < C; x++) {
					ret[y][x] = explode[y][x];
				}
			}
			break;

		default:
			break;
		}

		return ret;
	}

	public static String toString(char[][] arr) {
		StringBuilder sb = new StringBuilder();
		for (int y = 0; y < R; y++) {
			for (int x = 0; x < C; x++) {
				sb.append(arr[y][x]);
			}
			sb.append("\n");
		}

		return sb.toString();
	}

}