// 64204KB, 744ms

package bj11663;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = (int) 1e5, MAX_M = (int) 1e5;
	static final int MAX_POS = (int) 1e9;
	static final int FAIL = -1;

	static int N, M;
	static int[] points;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		// N, M 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// N개의 점 입력
		points = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			points[i] = Integer.parseInt(st.nextToken());
		}

		// 점들을 오름차순으로 정렬
		Arrays.sort(points);

		// M개의 선분 입력받으면서 처리
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine(), " ");
			int start = Integer.parseInt(st.nextToken());
			int end = Integer.parseInt(st.nextToken());

			int answer = countPoints(start, end);

			sb.append(answer).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	/** start위치에서 end위치 사이에 점의 개수를 센다 */
	public static int countPoints(int start, int end) {
		int left = findLeftGreater(start);
		int right = findRightSmaller(end);

		if (left == FAIL || right == FAIL) {
			return 0;
		} else {
			return right - left + 1;
		}

	}

	/** num보다 크거나 같은 수 중 가장 왼쪽에 있는 수의 index를 리턴 */
	public static int findLeftGreater(int num) {
		int left = 0;
		int right = N - 1;

		if (points[right] < num) {
			return FAIL;
		}

		while (left < right) {
			int mid = (left + right) / 2;

			if (num <= points[mid]) {
				right = mid;
			} else {
				left = mid + 1;
			}
		}

		return left;
	}

	/** num보다 작거나 같은 수 중 가장 오른쪽에 있는 수의 index를 리턴 */
	public static int findRightSmaller(int num) {
		int left = 0;
		int right = N - 1;

		if (points[left] > num) {
			return FAIL;
		}

		while (left < right) {
			int mid = (left + right + 1) / 2;

			if (points[mid] <= num) {
				left = mid;
			} else {
				right = mid - 1;
			}
		}

		return right;
	}

}