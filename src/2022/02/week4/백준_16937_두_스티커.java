package bj16937;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_STICKERS = 100;

	public static int height, width;
	public static int numStickers;

	public static int[] stickerHeights = new int[MAX_STICKERS];
	public static int[] stickerWidths = new int[MAX_STICKERS];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 모눈종이 높이, 너비 입력
		st = new StringTokenizer(br.readLine(), " ");
		height = Integer.parseInt(st.nextToken());
		width = Integer.parseInt(st.nextToken());

		// 스티커 개수 입력
		numStickers = Integer.parseInt(br.readLine());

		// 각 스티커 크기 입력
		for (int i = 0; i < numStickers; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			stickerHeights[i] = Integer.parseInt(st.nextToken());
			stickerWidths[i] = Integer.parseInt(st.nextToken());
		}

		// 최대 넓이 계산
		int maxArea = 0;
		for (int idx1 = 0; idx1 < numStickers; idx1++) {
			for (int idx2 = idx1 + 1; idx2 < numStickers; idx2++) {
				int area = getArea(idx1, idx2);
				maxArea = Math.max(maxArea, area);
			}
		}

		// 출력
		System.out.println(maxArea);
	}

	/** idx1번 스티커와 idx2번 스티커를 붙여서 얻을 수 있는 넓이를 리턴 */
	public static int getArea(int idx1, int idx2) {
		int h1 = stickerHeights[idx1];
		int h2 = stickerHeights[idx2];
		int w1 = stickerWidths[idx1];
		int w2 = stickerWidths[idx2];

		int area = h1 * w1 + h2 * w2;

		// 모든 놓는 방법 시도
		if (h1 + h2 <= height && w1 <= width && w2 <= width) {
			return area;
		}

		if (h1 + h2 <= width && w1 <= height && w2 <= height) {
			return area;
		}
		
		if (w1 + w2 <= height && h1 <= width && h2 <= width) {
			return area;
		}

		if (w1 + w2 <= width && h1 <= height && h2 <= height) {
			return area;
		}
		
		if (h1 + w2 <= height && w1 <= width && h2 <= width) {
			return area;
		}

		if (h1 + w2 <= width && w1 <= height && h2 <= height) {
			return area;
		}
		
		if (w1 + h2 <= height && h1 <= width && w2 <= width) {
			return area;
		}

		if (w1 + h2 <= width && h1 <= height && w2 <= height) {
			return area;
		}

		// 안 겹치고 놓을 수 없는 경우
		return 0;
	}
}