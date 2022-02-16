// 25084KB, 252ms

package bj1932;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_N = 500;
	public static final int EMPTY = -1;
	public static int N;
	public static int[][] input;
	public static int[][] cache = new int[MAX_N][MAX_N];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		N = Integer.parseInt(br.readLine());
		input = new int[N][N];
		for (int y = 0; y < N; y++) {
			st = new StringTokenizer(br.readLine(), " ");
			for (int x = 0; x <= y; x++) {
				input[y][x] = Integer.parseInt(st.nextToken());
			}
		}

		// 캐시 초기화
		for (int i = 0; i < cache.length; i++) {
			Arrays.fill(cache[i], EMPTY);
		}

		// 정답 계산
		int answer = maxSumFrom(0, 0);

		// 출력
		System.out.println(answer);
	}

	/** (y, x)위치에서부터 아래로 내려가는 최대 경로를 리턴 */
	public static int maxSumFrom(int y, int x) {
		// base case
		if (y == N)
			return 0;

		// 캐시에 있는 경우
		if (cache[y][x] != EMPTY) {
			return cache[y][x];
		}

		// 새로 계산해서 캐시에 넣는 경우
		int max1 = maxSumFrom(y + 1, x);
		int max2 = maxSumFrom(y + 1, x + 1);

		return cache[y][x] = input[y][x] + ((max1 <= max2) ? max2 : max1);

	}

}