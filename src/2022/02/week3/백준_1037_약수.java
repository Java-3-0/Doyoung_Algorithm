// 11564KB, 76ms

package bj1037;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		int N = Integer.parseInt(br.readLine());

		st = new StringTokenizer(br.readLine(), " ");
		long[] input = new long[N];
		for (int i = 0; i < N; i++) {
			input[i] = Long.parseLong(st.nextToken());
		}

		// 최소공배수 계산
		long lcmAccum = input[0];
		for (long num : input) {
			lcmAccum = lcm(lcmAccum, num);
		}

		// 그냥 약수가 아니라 진짜 약수라서 N이 A의 배수인 것 뿐만 아니라, A가 1과 N이 아니어야 한다.
		outer: for (long num : input) {
			if (num == lcmAccum) {
				for (int i = 2; i <= 1000000; i++) {
					if (lcmAccum % i == 0) {
						lcmAccum *= i;
						break outer;
					}
				}
			}
		}

		// 결과 출력
		System.out.println(lcmAccum);
	}

	/** 두 수의 최대공약수를 리턴 */
	public static long gcd(long a, long b) {
		if (b == 0) {
			return a;
		}

		return gcd(b, a % b);
	}

	/** 두 수의 최소공배수를 리턴 */
	public static long lcm(long a, long b) {
		return a * b / gcd(a, b);
	}
}