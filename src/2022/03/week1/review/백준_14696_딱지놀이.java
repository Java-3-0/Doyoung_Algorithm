// 26216KB, 246ms

package bj14696;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	public static final int SHAPE_TYPES = 4;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());

		for (int round = 1; round <= N; round++) {
			// 어린이 A의 딱지 정보 입력하고 모양 개수 세기
			st = new StringTokenizer(br.readLine(), " ");
			int numShapesA = Integer.parseInt(st.nextToken());
			int[] countA = new int[SHAPE_TYPES + 1];
			Arrays.fill(countA, 0);
			for (int i = 0; i < numShapesA; i++) {
				int num = Integer.parseInt(st.nextToken());
				countA[num]++;
			}

			// 어린이 B의 딱지 정보 입력하고 모양 개수 세기
			st = new StringTokenizer(br.readLine(), " ");
			int numShapesB = Integer.parseInt(st.nextToken());
			int[] countB = new int[SHAPE_TYPES + 1];
			Arrays.fill(countB, 0);
			for (int i = 0; i < numShapesB; i++) {
				int num = Integer.parseInt(st.nextToken());
				countB[num]++;
			}

			// 게임 결과 계산
			char result = gameResult(countA, countB);

			// 출력에 추가
			sb.append(result).append("\n");
		}

		System.out.print(sb.toString());
	}

	/** 어린이 두 명의 딱지 상태를 입력받아 게임 결과를 리턴 */
	public static char gameResult(int[] countA, int[] countB) {
		for (int shape = 4; shape >= 1; shape--) {
			if (countA[shape] > countB[shape]) {
				return 'A';
			}

			else if (countA[shape] < countB[shape]) {
				return 'B';
			}
		}

		return 'D';
	}
}