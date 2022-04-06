// 169728KB, 568ms

package bj12738;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 1000000;
	static final int INF = (int) (10e8 + 10e5);

	static int N;
	/** 수열 */
	static int[] seq = new int[MAX_N + 1];
	/** minLast[i]는 길이가 i인 LIS에서 맨 뒤에 올 수 있는 가장 작은 수 */
	static int[] minLast = new int[MAX_N + 1];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 전역변수 배열 초기값 세팅
		Arrays.fill(minLast, INF);
		minLast[0] = -INF;

		// 수열 크기 입력
		N = Integer.parseInt(br.readLine());

		// 수열 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		// minLast 배열 계산
		for (int i = 0; i < N; i++) {
			int num = seq[i];
			int idx = binSearch(minLast, num);
			minLast[idx] = num;
		}

		// LIS 길이 계산
		int answer = 1;
		for (int i = N; i >= 1; i--) {
			if (minLast[i] != INF) {
				answer = i;
				break;
			}
		}

		// 출력
		System.out.println(answer);

	} // end main

	/** 오름차순 정렬된 arr에서 num 이상의 값들 중 가장 왼쪽에 있는 값의 idx를 리턴 */
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