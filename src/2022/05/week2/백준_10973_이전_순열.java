// 13444KB, 112ms

package bj10973;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int N;
	static int[] permu;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		N = Integer.parseInt(br.readLine());

		permu = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			permu[i] = Integer.parseInt(st.nextToken());
		}

		if (prevPermutation()) {
			for (int num : permu) {
				sb.append(num).append(" ");
			}
		}
		else {
			sb.append(-1);
		}
		sb.append("\n");
		
		System.out.print(sb.toString());
		
	} // end main

	public static boolean prevPermutation() {
		// 오른쪽부터 왼쪽으로 가면서 증가하는 부분을 찾는다
		int idx = N - 1;
		while (0 < idx && permu[idx - 1] < permu[idx]) {
			idx--;
		}
		
		int swapIdx1 = idx - 1;
		if (swapIdx1 == -1) {
			return false;
		}
		
		// 오른쪽부터 permu[swapIdx1]보다 작은 수 중 가장 먼저 만나는 수를 찾는다
		int idx2 = N - 1;
		while (swapIdx1 < idx2 && permu[swapIdx1] <= permu[idx2]) {
			idx2--;
		}
		
		int swapIdx2 = idx2;
		
		// 스왑한다
		swap(swapIdx1, swapIdx2);
		
		// 오른쪽 부분을 내림차순 정렬한다
		for (int i = swapIdx1 + 1; i < N; i++) {
			for (int j = i + 1; j < N; j++) {
				if (permu[i] < permu[j]) {
					swap(i, j);
				}
			}
		}

		return true;
	}
	
	public static void swap(int idx1, int idx2) {
		int tmp = permu[idx1];
		permu[idx1] = permu[idx2];
		permu[idx2] = tmp;
	}
}