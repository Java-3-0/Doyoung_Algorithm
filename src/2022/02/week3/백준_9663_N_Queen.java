// 11840KB, 2572ms

package bj9663;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static final int MAX_N = 14;
	public static int N;

	public static int count = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 입력
		N = Integer.parseInt(br.readLine());
		
		// 퀸을 놓으면서 카운트 값 갱신
		nQueen(N);
		
		// 결과 출력
		System.out.println(count);
	}

	public static void nQueen(int N) {
		// 각 가로, 세로, 대각선이 사용되었는지 여부를 나타내는 boolean값들을 false로 초기화
		boolean[] isUsedX = new boolean[N];
		boolean[] isUsedDiag1 = new boolean[2 * N];
		boolean[] isUsedDiag2 = new boolean[2 * N];

		// 각 행마다 퀸을 놓아본다
		fillRow(0, isUsedX, isUsedDiag1, isUsedDiag2);

		return;
	}

	public static void fillRow(int rowIdx, boolean[] isUsedX, boolean[] isUsedDiag1, boolean[] isUsedDiag2) {
		// 다 채운 경우
		if (rowIdx == N) {
			count++;
			return;
		}

		int y = rowIdx;

		for (int x = 0; x < N; x++) {
			int idxDiag1 = y + x;
			int idxDiag2 = y - x + N;

			// 이미 같은 열이나 같은 대각선에 놓인 것이 있으면 패스
			if (isUsedX[x] || isUsedDiag1[idxDiag1] || isUsedDiag2[idxDiag2]) {
				continue;
			}

			// 이 칸에 채우고 다음 줄로 넘어가는 경우
			isUsedX[x] = true;
			isUsedDiag1[idxDiag1] = true;
			isUsedDiag2[idxDiag2] = true;
			fillRow(rowIdx + 1, isUsedX, isUsedDiag1, isUsedDiag2);
			
			// 이 칸에 안 채우는 경우
			isUsedX[x] = false;
			isUsedDiag1[idxDiag1] = false;
			isUsedDiag2[idxDiag2] = false;
		}
	}

}