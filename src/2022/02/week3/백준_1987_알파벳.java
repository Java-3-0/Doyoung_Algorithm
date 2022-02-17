// 12124KB, 1016ms

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static final int[] dy = { 0, 0, -1, 1 };
	public static final int[] dx = { -1, 1, 0, 0 };

	/** 보드의 최대 크기 */
	public static final int MAX_SIZE = 20;
	/** 알파벳 개수 */
	public static final int ALPHABETS = 26;
	/** 보드의 높이, 너비 */
	public static int height, width;
	/** 알파벳을 0 ~ 25 사이의 int로 변환한 값을 저장해 두는 2차원 배열 */
	public static int[][] board = new int[MAX_SIZE][MAX_SIZE];
	/** 각 알파벳마다의 사용 여부를 나타내는 boolean 배열 */
	public static boolean[] isUsed = new boolean[ALPHABETS];
	/** 말이 지날 수 있는 최대 칸수를 담을 변수 */
	public static int maxCount = Integer.MIN_VALUE;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		// 보드 높이, 너비 입력
		height = Integer.parseInt(st.nextToken());
		width = Integer.parseInt(st.nextToken());

		// 알파벳을 입력받아 0 ~ 25사이에 해당하는 정수로 변환시켜 보드에 저장
		for (int y = 0; y < height; y++) {
			String line = br.readLine();
			for (int x = 0; x < width; x++) {
				board[y][x] = line.charAt(x) - 'A';
			}
		}

		dfs(0, 0, 0);

		System.out.println(maxCount);
	}

	public static void dfs(int hereY, int hereX, int count) {
		int hereValue = board[hereY][hereX];
		
		// 방문한 곳이면 리턴
		if (isUsed[hereValue]) {
			maxCount = Math.max(maxCount, count);
			return;
		}

		// 새로 방문
		isUsed[hereValue] = true;
		count++;
		
		// 연결된 지점들 dfs
		for (int dir = 0; dir < 4; dir++) {
			int thereY = hereY + dy[dir];
			int thereX = hereX + dx[dir];

			if (isInRange(thereY, thereX)) {
				dfs(thereY, thereX, count);
			}
		}
		
		// dfs가 끝난 후 isUsed값 다시 원래대로 갱신하고 윗 단계로 돌아가기
		isUsed[hereValue] = false;
	}

	public static boolean isInRange(int y, int x) {
		if (0 <= y && y < height && 0 <= x && x < width) {
			return true;
		}
		
		return false;
	}

}
