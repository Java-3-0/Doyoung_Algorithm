// 11852KB, 88ms

package bj11635;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static final long INF = 987654321098765L;
	static final long FAIL = -1L;

	/** 확장 유클리드 알고리즘을 수행한 결과를 나타내는 객체 */
	static class EucResult {
		long gcd;
		long s;
		long t;

		public EucResult(long gcd, long s, long t) {
			super();
			this.gcd = gcd;
			this.s = s;
			this.t = t;
		}

		@Override
		public String toString() {
			return "EucResult [gcd=" + gcd + ", s=" + s + ", t=" + t + "]";
		}
	}

	/** ax + by = c의 일반해를 나타내기 위한 객체 */
	static class GeneralSolution {
		long x0;
		long dx;
		long y0;
		long dy;

		public GeneralSolution(long x0, long dx, long y0, long dy) {
			super();
			this.x0 = x0;
			this.dx = dx;
			this.y0 = y0;
			this.dy = dy;
		}

		@Override
		public String toString() {
			return String.format("General Solution: x = %d + (%d)k, y = %d + (%d)k", x0, dx, y0, dy);
		}

	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		final int TESTS = Integer.parseInt(br.readLine());

		for (int tc = 1; tc <= TESTS; tc++) {
			st = new StringTokenizer(br.readLine(), " ");
			long A = Long.parseLong(st.nextToken());
			long B = Long.parseLong(st.nextToken());
			long Q = Long.parseLong(st.nextToken());

			// 일반해를 구한다.
			GeneralSolution sol = solveEquation(A, B, Q);
			long x0 = sol.x0;
			long dx = sol.dx;
			long y0 = sol.y0;
			long dy = sol.dy;

			// x = x0 + dx * k, y = y0 + dy * k에서 문제 조건에 맞는 k값의 범위를 구한다
			long upperBoundK = INF;
			long lowerBoundK = -INF;
			if (dx > 0) {
				long minK = (1 - x0) / dx + ((1 - x0) % dx != 0 ? 1 : 0);
				lowerBoundK = Math.max(lowerBoundK, minK);
			} else if (dx < 0) {
				long maxK = (1 - x0) / dx;
				upperBoundK = Math.min(upperBoundK, maxK);
			}

			if (dy > 0) {
				long minK = (1 - y0) / dy + ((1 - y0) % dy != 0 ? 1 : 0);
				lowerBoundK = Math.max(lowerBoundK, minK);
			} else if (dy < 0) {
				long maxK = (1 - y0) / dy;
				upperBoundK = Math.min(upperBoundK, maxK);
			}

			// 그 중 x를 최소화, y를 최소화 할 수 있는 K값을 구한다
			long answerK;
			if (dx > 0) {
				answerK = lowerBoundK;
			} else if (dx < 0) {
				answerK = upperBoundK;
			} else {
				if (dy > 0) {
					answerK = lowerBoundK;
				} else if (dy < 0) {
					answerK = upperBoundK;
				} else {
					answerK = 0;
				}
			}

			// 그 K를 사용했을 때의 x, y값을 구한다
			long answerX = x0 + dx * answerK;
			long answerY = y0 + dy * answerK;

			// 출력
			sb.append(answerX).append(" ").append(answerY).append("\n");
		}

		System.out.print(sb.toString());

	} // end main

	// a * s + b * t = c를 푼다
	public static GeneralSolution solveEquation(long a, long b, long c) {
		EucResult eucRes = getEucResult(a, b);

		long gcd = eucRes.gcd;
		long s = eucRes.s;
		long t = eucRes.t;

		long x0 = s * c / gcd;
		long y0 = t * c / gcd;
		long dx = b / gcd;
		long dy = -a / gcd;

		return new GeneralSolution(x0, dx, y0, dy);
	}

	/** a * s + b * t = gcd(a, b)가 되는 gcd(a, b), s, t값을 확장 유클리드 알고리즘으로 구해서 리턴 */
	public static EucResult getEucResult(long a, long b) {
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

		return new EucResult(rPrev, sPrev, tPrev);
	}

	/** a의 mod에서의 곱셈 역원을 리턴 */
	public static long getMultInv(long a, long mod) {
		long gcd = getGCD(a, mod);

		if (gcd != 1L) {
			return FAIL;
		}

		else {
			return (getEucResult(a, mod).s + mod) % mod;
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