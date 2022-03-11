// 12920KB, 100ms

package bj15779;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final int MAX_N = 5000;

	static int N;
	static int[] seq;
	static boolean[] isZigZag = new boolean[MAX_N - 2];

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// N 입력
		N = Integer.parseInt(br.readLine());

		// 메모리 할당
		seq = new int[N];
		isZigZag = new boolean[N - 2];

		// 수열 입력
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			seq[i] = Integer.parseInt(st.nextToken());
		}

		// 지그재그가 되는 위치들 미리 계산
		precalcZigzags();

		// 연속 지그재그의 최대 길이 계산
		int maxCnt = 0;
		int cnt = 0;
		for (int i = 0; i < N - 2; i++) {
			if (isZigZag[i]) {
				cnt++;
				maxCnt = maxCnt < cnt ? cnt : maxCnt;
			}
			else {
				cnt = 0;
			}
		}

		int answer = maxCnt + 2;
		
		// 출력
		System.out.println(answer);
	}

	public static void precalcZigzags() {
		for (int i = 0; i < N - 2; i++) {
			int a = seq[i];
			int b = seq[i + 1];
			int c = seq[i + 2];

			if (a <= b && b <= c) {
				continue;
			}
			if (a >= b && b >= c) {
				continue;
			}

			isZigZag[i] = true;
		}
	}
}