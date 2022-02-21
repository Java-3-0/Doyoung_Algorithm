// 12820KB, 208ms

package bj11170;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		int T = Integer.parseInt(br.readLine());
		for (int testCase = 1; testCase <= T; testCase++) {
			// 입력
			st = new StringTokenizer(br.readLine(), " ");
			int N = Integer.parseInt(st.nextToken());
			int M = Integer.parseInt(st.nextToken());

			// N ~ M까지의 모든 수에 대해 0의 개수를 세서 누적한다.
			int count = 0;
			for (int num = N; num <= M; num++) {
				count += countZeros(num);
			}

			// 결과를 출력 스트링빌더에 추가
			sb.append(count).append("\n");
		}

		// 출력
		System.out.println(sb.toString());

	}

	/** 정수 n에 존재하는 0의 개수를 리턴 */
	public static int countZeros(int n) {
		int ret = 0;
		do {
			if (n % 10 == 0) {
				ret++;
			}

			n /= 10;
		} while (n > 0);

		return ret;
	}
}