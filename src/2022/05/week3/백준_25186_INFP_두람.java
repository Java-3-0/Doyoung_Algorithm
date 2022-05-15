// 26108KB, 220ms

package bj25186;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());

		long[] seq = new long[N];

		st = new StringTokenizer(br.readLine(), " ");
		long maxNum = 0L;
		long sum = 0L;
		for (int i = 0; i < N; i++) {
			long num = Long.parseLong(st.nextToken());
			seq[i] = num;

			sum += num;
			maxNum = Math.max(maxNum, num);
		}

		// 과반수 계산
		long half = sum / 2L;
		if (sum % 2L != 0L) {
			half++;
		}

		// 옷이 한 종류뿐인 경우를 예외적으로 처리
		if (N == 1) {
			if (sum == 1L) {
				System.out.println("Happy");
			} else {
				System.out.println("Unhappy");
			}
		}

		// 그 외의 경우 한 종류 옷을 제외한 나머지로 과반수는 채워야 한다
		else {
			if (sum - maxNum >= half) {
				System.out.println("Happy");
			} else {
				System.out.println("Unhappy");
			}
		}

	} // end main
}