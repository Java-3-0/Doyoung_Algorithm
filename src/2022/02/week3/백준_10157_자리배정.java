// 11560KB, 80ms

package bj10157;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	// 상 -> 우 -> 하 -> 좌 시계방향
	public static final int[] dy = { 1, 0, -1, 0 };
	public static final int[] dx = { 0, 1, 0, -1 };

	public static void main(String[] args) throws IOException {
		// 입력
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");
		int width = Integer.parseInt(st.nextToken());
		int height = Integer.parseInt(st.nextToken());
		int K = Integer.parseInt(br.readLine());
		br.close();

		int[] answer = getPosition(K, height, width, 1, 1);
		if (answer[0] == 0 && answer[1] == 0) {
			System.out.println(0);
		} else {
			for (int num : answer) {
				System.out.print(num + " ");
			}
			System.out.println();
		}
	}

	public static int[] getPosition(int targetNum, int h, int w, int startY, int startX) {
		if (targetNum > h * w)
			return new int[] { 0, 0 };

		// 이 테두리에 존재하는 수의 개수
		int count = 2 * (h - 1) + 2 * (w - 1);

		// targetNum이 이 테두리에 없는 경우 안쪽 테두리로 재귀 호출
		if (count < targetNum) {
			return getPosition(targetNum - count, h - 2, w - 2, startY + 1, startX + 1);
		}

		// targetNum이 이 테두리에 있는 경우
		int[] ret = new int[2];
		int y = startY;
		int x = startX;

		// (startY, startX)로부터 시계방향으로 move 칸 만큼 이동해야 함
		int move = targetNum - 1;

		for (int dir = 0; dir < dy.length; dir++) {
			int maxMove = dir % 2 == 0 ? h - 1 : w - 1;
			int thisDirMove = maxMove < move ? maxMove : move;

			y += thisDirMove * dy[dir];
			x += thisDirMove * dx[dir];
			move -= thisDirMove;
		}

		ret[1] = y;
		ret[0] = x;
		return ret;
	}

}
