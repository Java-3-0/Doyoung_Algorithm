// 11468KB, 76ms

package bj16438;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static final int DAYS = 7;
	static char[][] answer;
	static int N;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// N 입력받고 메모리 할당
		N = Integer.parseInt(br.readLine());
		answer = new char[DAYS][N];

		// 팀을 나눈다.
		for (int day = 0; day < DAYS; day++) {
			for (int monkey = 0; monkey < N; monkey++) {
				if ((monkey & (1 << day)) == 0) {
					answer[day][monkey] = 'A';
				} else {
					answer[day][monkey] = 'B';
				}
			}
		}

		// 모두 같은 팀에 들어가는 경우를 예외적으로 처리
		for (int y = 0; y < DAYS; y++) {
			if (isAllSame(answer[y])) {
				if (answer[y][N - 1] == 'A') {
					answer[y][N - 1] = 'B';
				} else {
					answer[y][N - 1] = 'A';
				}
			}
		}

		// 출력
		for (int y = 0; y < DAYS; y++) {
			for (int x = 0; x < N; x++) {
				sb.append(answer[y][x]);
			}
			sb.append("\n");
		}

		System.out.print(sb.toString());
	}

	public static boolean isAllSame(char[] arr) {
		char first = arr[0];

		int len = arr.length;
		for (int i = 1; i < len; i++) {
			if (arr[i] != first) {
				return false;
			}
		}

		return true;
	}
}