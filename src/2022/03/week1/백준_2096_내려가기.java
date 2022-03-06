// 40056KB, 332ms

package bj2096;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 100000;

	static int N;
	static int[][] cacheMax = new int[2][3];
	static int[][] cacheMin = new int[2][3];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N 입력
		N = Integer.parseInt(br.readLine());

		// 한 줄씩 입력을 받아 내려가면서 최대 점수, 최소 점수를 슬라이딩 윈도우 방식으로 캐시에 저장한다
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int left = Integer.parseInt(st.nextToken());
			int mid = Integer.parseInt(st.nextToken());
			int right = Integer.parseInt(st.nextToken());

			int nowIdx = i % 2;
			int prevIdx = (i - 1 + 2) % 2;

			cacheMax[nowIdx][0] = left + Math.max(cacheMax[prevIdx][0], cacheMax[prevIdx][1]);
			cacheMax[nowIdx][1] = mid + Math.max(Math.max(cacheMax[prevIdx][0], cacheMax[prevIdx][1]), cacheMax[prevIdx][2]);
			cacheMax[nowIdx][2] = right + Math.max(cacheMax[prevIdx][1], cacheMax[prevIdx][2]);

			cacheMin[nowIdx][0] = left + Math.min(cacheMin[prevIdx][0], cacheMin[prevIdx][1]);
			cacheMin[nowIdx][1] = mid + Math.min(Math.min(cacheMin[prevIdx][0], cacheMin[prevIdx][1]), cacheMin[prevIdx][2]);
			cacheMin[nowIdx][2] = right + Math.min(cacheMin[prevIdx][1], cacheMin[prevIdx][2]);
		}

		// 마지막 줄까지 내려온 상태에서의 최대 점수, 최소 점수를 구한다.
		int lastIdx = (N - 1) % 2;
		int max = Integer.MIN_VALUE;
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < 3; i++) {
			max = max < cacheMax[lastIdx][i] ? cacheMax[lastIdx][i] : max;
			min = min > cacheMin[lastIdx][i] ? cacheMin[lastIdx][i] : min;
		}

		// 결과 출력
		sb.append(max).append(" ").append(min).append("\n");
		System.out.print(sb.toString());

	} // end main

}