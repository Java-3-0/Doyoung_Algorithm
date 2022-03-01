// 26020KB, 200ms

package bj4779;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	static final int MAX_SIZE = (int) Math.pow(3, 12);

	static char[] cantor = new char[MAX_SIZE];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		String line = "";
		while ((line = br.readLine()) != null) {
			// 칸토어 집합을 모두 -인 상태로 초기화
			Arrays.fill(cantor, '-');

			// N 입력
			int N = Integer.parseInt(line);
			int size = (int) Math.pow(3, N);

			// 중앙을 비우는 것을 재귀적으로 반복
			cleanMid(0, size);

			// 칸토어 집합을 출력 스트링빌더에 추가
			for (int c = 0; c < size; c++) {
				sb.append(cantor[c]);
			}
			sb.append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	/** 중앙을 비우는 것을 재귀적으로 반복하는 함수 */
	public static void cleanMid(int startIdx, int size) {
		if (size < 3) {
			return;
		}

		int nextSize = size / 3;

		int cleanBeginIdx = startIdx + nextSize;
		for (int i = 0; i < nextSize; i++) {
			cantor[cleanBeginIdx + i] = ' ';
		}

		cleanMid(startIdx, nextSize);
		cleanMid(startIdx + 2 * nextSize, nextSize);
	}
}