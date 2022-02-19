// 11568KB, 84ms

package bj16953;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static int answer = 0;
	public static boolean canMake = false;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		long A = Long.parseLong(st.nextToken());
		long B = Long.parseLong(st.nextToken());

		// A로 B를 만들어보는 것을 시도
		tryMaking(A, B, 0);

		// 성공하면 연산 수행 횟수 출력
		if (canMake) {
			System.out.println(answer + 1);
		}

		// 실패하면 -1 출력
		else {
			System.out.println(-1);
		}
	}

	public static void tryMaking(long A, long B, int accum) {
		// 만드는 데 성공한 경우 정답 갱신
		if (A == B) {
			answer = accum;
			canMake = true;
			return;
		}

		// A가 B보다 커져버리면 중단
		else if (A > B) {
			return;
		}

		// 10A + 1이 2A보다 빨리 커지므로 먼저 시도
		tryMaking(A * 10 + 1, B, accum + 1);
		tryMaking(A * 2, B, accum + 1);

	}
}