// 11908KB, 108ms

package bj1548;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static int[] seq;
	static boolean[][][] isTriangle;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// 입력
		N = Integer.parseInt(br.readLine());
		seq = new int[N];
		isTriangle = new boolean[N][N][N];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		// 수열 정렬
		Arrays.sort(seq);

		// 삼각형 여부 미리 계산
		precalcTriangles();

		// 정답 계산
		int answer = 0;
		if (seq.length <= 2) {
			answer = seq.length;
		} else {
			for (int left = 0; left < N; left++) {
				for (int right = left + 1; right < N; right++) {
					if (isTriangleSequence(left, right)) {
						answer = Math.max(answer, right - left + 1);
					}
				}
			}
		}

		// 출력
		System.out.println(answer);

	}

	/** seq[left]부터 seq[right]까지가 부분 삼각 수열인지 여부를 리턴 */
	public static boolean isTriangleSequence(int left, int right) {
		for (int i = left; i <= right; i++) {
			for (int j = i + 1; j <= right; j++) {
				for (int k = j + 1; k <= right; k++) {
					if (!isTriangle[i][j][k]) {
						return false;
					}
				}
			}
		}

		return true;
	}

	/** seq에서 삼각형이 되는 것들을 미리 계산 */
	public static void precalcTriangles() {
		for (int i = 0; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				for (int k = j + 1; k < N; k++) {
					int x = seq[i];
					int y = seq[j];
					int z = seq[k];

					if (x + y > z && y + z > x && z + x > y) {
						isTriangle[i][j][k] = true;
					}
				}
			}
		}
	}
}