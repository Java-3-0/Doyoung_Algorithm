// 11668KB, 84ms

package bj20412;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final long FAIL = -1L;

	static class EuclideanResult {
		long gcd;
		long s;
		long t;

		public EuclideanResult(long gcd, long s, long t) {
			super();
			this.gcd = gcd;
			this.s = s;
			this.t = t;
		}

		@Override
		public String toString() {
			return "EuclideanResult [gcd=" + gcd + ", s=" + s + ", t=" + t + "]";
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			st = new StringTokenizer(br.readLine(), " ");
			long K = Long.parseLong(st.nextToken());
			long C = Long.parseLong(st.nextToken());

			// C * X = 1 MOD K의 해 X를 구한다
			long inv = getMultInv(C, K);

			if (inv == FAIL) {
				sb.append("IMPOSSIBLE");
			} else {
				while (C * inv < K + 1) {
					inv += K;
				}

				if (inv <= (long) 1e9) {
					sb.append(inv);
				} else {
					sb.append("IMPOSSIBLE");
				}
			}
			sb.append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	/** a * s + b * t = gcd(a, b)가 되는 gcd(a, b), s, t값을 확장 유클리드 알고리즘으로 구해서 리턴 */
	public static EuclideanResult getExtendedEuclidean(long a, long b) {
		long sPrev = 1L, sNow = 0L;
		long tPrev = 0L, tNow = 1L;
		long rPrev = a, rNow = b;

		while (rNow != 0) {
			// q 계산
			long q = rPrev / rNow;

			// r 계산
			long rTmp = rPrev % rNow;
			rPrev = rNow;
			rNow = rTmp;

			// s 계산
			long sTmp = sPrev - sNow * q;
			sPrev = sNow;
			sNow = sTmp;

			// t 계산
			long tTmp = tPrev - tNow * q;
			tPrev = tNow;
			tNow = tTmp;
		}

		return new EuclideanResult(rPrev, sPrev, tPrev);
	}

	/** a의 mod에서의 곱셈 역원을 리턴 */
	public static long getMultInv(long a, long mod) {
		long gcd = getGCD(a, mod);

		if (gcd != 1L) {
			return FAIL;
		}

		else {
			return (getExtendedEuclidean(a, mod).s + mod) % mod;
		}
	}

	/** a와 b의 최대공약수를 리턴 */
	public static long getGCD(long a, long b) {
		if (a == 0L) {
			return b;
		}

		return getGCD(b % a, a);
	}

}