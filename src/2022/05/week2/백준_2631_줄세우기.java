// 11604KB, 72ms

package bj2631;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_N = 200;
	static final int INF = 987654321;

	static int N;
	/** 수열 */
	static int[] seq = new int[MAX_N];
	/** minLast[i]는 길이가 i인 LIS에서 맨 뒤에 올 수 있는 가장 작은 수 */
	static int[] minLast = new int[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 전역변수 배열 초기값 세팅
		Arrays.fill(minLast, INF);

		// 수열 크기 입력
		N = Integer.parseInt(br.readLine());

		// 수열 입력
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(br.readLine());
		}

		// minLast 배열 계산
		for (int i = 0; i < N; i++) {
			int num = seq[i];
			int idx = binSearch(minLast, num);
			minLast[idx] = num;
		}

		// LIS 길이 계산
		int lenLIS = 1;
		for (int i = N - 1; i >= 0; i--) {
			if (minLast[i] != INF) {
				lenLIS = i + 1;
				break;
			}
		}

		// 출력 스트링빌더에 LIS 길이를 추가
		int answer = N - lenLIS;

		// 출력
		System.out.println(answer);

	} // end main

	/** arr[]에서 num보다 크거나 같은 수 중 가장 왼쪽에 있는 것의 인덱스를 리턴 */
	public static int binSearch(int[] arr, int num) {
		int left = 0;
		int right = arr.length - 1;

		int mid;
		while (left < right) {
			mid = (left + right) / 2;

			if (arr[mid] < num) {
				left = mid + 1;
			} else {
				right = mid;
			}
		}

		return left;
	}

}