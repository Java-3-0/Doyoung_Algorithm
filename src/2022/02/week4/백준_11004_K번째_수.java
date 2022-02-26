// 501812KB, 1132ms

package bj11004;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;
import java.util.StringTokenizer;

public class Main {
	static int N, K;


	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N, K 입력
		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		// 수열 입력
		int[] seq = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}
		br.close();

		// 정렬
		quickSort(seq, 0, N - 1);

		// K번째 값
		int answer = seq[K - 1];

		// 출력
		System.out.println(answer);

	} // end main

	public static int partition(int[] arr, int left, int right) {
		// 랜덤한 pivotIdx 계산
		Random random = new Random();
		int rand = random.nextInt((right - left));
		int pivotIdx = left + rand;

		// pivot 값을 맨 앞에 둔다
		int pivot = arr[pivotIdx];
		swap(arr, left, pivotIdx);

		// pivot 값보다 작거나 같은 것은 왼쪽으로, pivot값보다 큰 것은 오른쪽으로 보낸다.
		int i = left;
		int j = right;
		while (i < j) {
			// 오른쪽부터 가장 먼저 만나는 pivot보다 같거나 작은 원소를 찾는다.
			while (arr[j] > pivot) {
				j--;
			}
			// 왼쪽부터 가장 먼저 만나는 pivot보다 큰 원소를 찾는다.
			while (i < j && arr[i] <= pivot) {
				i++;
			}

			// 둘을 스왑한다.
			swap(arr, i, j);
		}

		// swap이 다 끝난 마지막 i == j가 되는 위치에 pivot을 놓는다.
		swap(arr, left, i);

		return i;
	}

	/** arr[i]와 arr[j]를 스왑한다 */
	public static void swap(int[] arr, int i, int j) {
		int tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;

		return;
	}

	/** 피봇을 기준으로 정렬하는 퀵소트 알고리즘을 사용하여 arr을 정렬하는 함수 */
	public static void quickSort(int[] arr, int left, int right) {
		// base case: 원소가 1개 이하면 리턴
		if (right - left <= 0) {
			return;
		}

		int pivotIdx = partition(arr, left, right);

		if (pivotIdx == K - 1) {
			return;
		} else if (pivotIdx > K - 1) {
			if (!isAllSame(arr, left, pivotIdx - 1)) {
				quickSort(arr, left, pivotIdx - 1);
			}
		} else {
			if (!isAllSame(arr, pivotIdx + 1, right)) {
				quickSort(arr, pivotIdx + 1, right);
			}
		}
	}
	
	/** arr의 left부터 right까지 전부 같다면 true, 아니면 false를 리턴 */
	public static boolean isAllSame(int[] arr, int left, int right) {
		int first = arr[left];
		
		for (int i = left + 1; i <= right; i++) {
			if (arr[i] != first) {
				return false;
			}
		}
		
		return true;
	}
}