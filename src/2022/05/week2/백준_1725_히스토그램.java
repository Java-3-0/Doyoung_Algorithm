// 20920KB, 248ms

package bj1725;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static final int MAX_N = (int) 1e5;

	static int N;
	static int[] heights = new int[MAX_N];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 직사각형 개수 입력
		N = Integer.parseInt(br.readLine());

		// 직사각형들의 높이 입력
		for (int i = 0; i < N; i++) {
			heights[i] = Integer.parseInt(br.readLine());
		}

		// 직사각형 최대 넓이 계산 (n log n 시간 복잡도)
		int answer = getMaxArea(0, N - 1);

		// 출력
		System.out.println(answer);

	} // end main

	/** [left, right]번 칸 내에서 가능한 직사각형의 최대 넓이를 계산해서 리턴 */
	public static int getMaxArea(int left, int right) {
		// base case. 칸이 1개인 경우
		if (left == right) {
			return heights[left];
		}

		int ret = 0;

		// 분할 정복
		int mid = (left + right) / 2;

		// mid는 포함하고 mid + 1은 포함하지 않는 것 고려
		ret = Math.max(ret, getMaxArea(left, mid));

		// mid + 1은 포함하고 mid는 포함하지 않는 것 고려
		ret = Math.max(ret, getMaxArea(mid + 1, right));

		// mid와 mid + 1 모두 포함하는 것 고려
		int curLeft = mid;
		int curRight = mid + 1;

		int curHeight = Math.min(heights[curLeft], heights[curRight]);
		ret = Math.max(ret, curHeight * 2);

		// 모든 칸을 포함시킬 때까지 반복
		while (left < curLeft || curRight < right) {
			// 오른쪽 칸의 높이가 더 높으면 오른쪽으로 한 칸 이동
			if (curRight < right && (curLeft == left || heights[curRight + 1] >= heights[curLeft - 1])) {
				curRight++;
				curHeight = Math.min(curHeight, heights[curRight]);
			}
			// 왼쪽 칸의 높이가 더 높으면 왼쪽으로 한 칸 이동
			else {
				curLeft--;
				curHeight = Math.min(curHeight, heights[curLeft]);
			}

			ret = Math.max(ret, curHeight * (curRight - curLeft + 1));
		}
		
		return ret;
	}
}