package bj14565;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Main {
	public static final BigInteger ZERO = new BigInteger("0");
	public static final BigInteger ONE = new BigInteger("1");
	public static final BigInteger TWO = new BigInteger("2");
	public static final BigInteger NEG_ONE = new BigInteger("-1");
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		BigInteger MOD = new BigInteger(st.nextToken());
		BigInteger A = new BigInteger(st.nextToken());

		BigInteger addInv = getAddInv(A, MOD);
		BigInteger multInv = getMultInv(A, MOD);

		System.out.printf("%d %d\n", addInv, multInv);
	} // end main

	/** MOD에서 a의 덧셈 역원을 리턴 */
	public static BigInteger getAddInv(BigInteger A, BigInteger MOD) {
		return MOD.subtract(A);
	}

	/** MOD에서 a의 곱셈 역원을 리턴 */
	public static BigInteger getMultInv(BigInteger A, BigInteger MOD) {
		BigInteger gcd = gcd(A, MOD);
		
		// 두 수가 서로소가 아니면 역원이 존재하지 않는다.
		if (!gcd.equals(ONE)) {
			return NEG_ONE;
		}
		
		// MOD * s + A * t = 1이 되는 정수 s, t를 확장 유클리드 알고리즘으로 찾는다.
		// s0 = 1, s1 = 0, t0 = 0, t1 = 1, r0 = MOD, r1 = A
		// qi = r(i-1) / r(i)
		// ri = r(i-2) MOD r(i-1)
		// si = s(i-2) - s(i-1) * q(i-1)
		// ti = t(i-2) - t(i-1) * q(i-1)
		
		BigInteger sPrev = ONE, sNow = ZERO, tPrev = ZERO, tNow = ONE, rPrev = MOD, rNow = A;
		
		while (!rNow.equals(ZERO)) {
			// q 계산
			BigInteger q = rPrev.divide(rNow);
			
			// r 계산
			BigInteger rTmp = rPrev.remainder(rNow);
			rPrev = rNow;
			rNow = rTmp;
			
			// s 계산
			BigInteger sTmp = sPrev.subtract(sNow.multiply(q));
			sPrev = sNow;
			sNow = sTmp;
			
			// t 계산
			BigInteger tTmp = tPrev.subtract(tNow.multiply(q));
			tPrev = tNow;
			tNow = tTmp;
		}
		
		return tPrev.add(MOD).remainder(MOD);
	}

	/** a^b를 MOD로 나눈 나머지를 구해서 리턴 */
	public static BigInteger pow(BigInteger A, BigInteger B, BigInteger MOD) {
		if (B.equals(ZERO))
			return ONE;

		BigInteger half = pow(A, B.divide(TWO), MOD);

		BigInteger halfSqr = half.multiply(half).remainder(MOD);
		if (B.remainder(TWO).equals(ZERO)) {
			return halfSqr;
		}

		else {
			return halfSqr.multiply(A).remainder(MOD);
		}
	}

	/** a와 b의 gcd를 리턴 */
	public static BigInteger gcd(BigInteger a, BigInteger b) {
		if (a.equals(ZERO)) {
			return b;
		}

		return gcd(b.remainder(a), a);
	}
}