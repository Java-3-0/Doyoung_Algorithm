// 171092KB, 920ms

package bj17829;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1024;

	static int N;
	static int[][] grid = new int[MAX_N][MAX_N];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 그리드 크기 입력
		N = Integer.parseInt(br.readLine());

		// 그리드 입력
		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x < N; x++) {
				grid[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		// 222풀링 값 계산
		int answer = solve(0, 0, N);

		// 출력
		System.out.println(answer);

	} // end main

	/** (startY, startX) 부터 size * size크기의 공간에 2 x 2 풀링을 적용한 결과를 리턴 */
	public static int solve(int startY, int startX, int size) {
		// base case
		if (size == 2) {
			List<Integer> l = new ArrayList<>();
			l.add(grid[startY][startX]);
			l.add(grid[startY][startX + 1]);
			l.add(grid[startY + 1][startX]);
			l.add(grid[startY + 1][startX + 1]);

			// 오름차순 정렬
			Collections.sort(l);

			// 뒤에서 2번째 원소가 답
			return l.get(l.size() - 2);
		}

		// 4분할해서 재귀적으로 푼다.
		int nextSize = size / 2;

		List<Integer> l = new ArrayList<>();
		l.add(solve(startY, startX, nextSize));
		l.add(solve(startY, startX + nextSize, nextSize));
		l.add(solve(startY + nextSize, startX, nextSize));
		l.add(solve(startY + nextSize, startX + nextSize, nextSize));

		// 오름차순 정렬
		Collections.sort(l);

		// 뒤에서 2번째 원소가 답
		return l.get(l.size() - 2);
	}

}