// 11540KB, 80ms

package bj3036;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static class RationalNumber {
		/** 분자 */
		int numer;
		/** 분모 */
		int denom;
		public RationalNumber(int numer, int denom) {
			super();
			this.numer = numer;
			this.denom = denom;
		}
		@Override
		public String toString() {
			return numer+"/"+denom;
		}
		
		/** 유리수의 곱셈 연산을 구현한 함수 */
		public RationalNumber multiply (RationalNumber r) {
			int n = this.numer * r.numer;
			int d = this.denom * r.denom;
			
			return new RationalNumber(n, d);
		}
		
		/** 유리수를 약분하여 기약분수 형태로 리턴해주는 함수 */
		public RationalNumber reduce() {
			int gcd = gcd(this.numer, this.denom);
			return new RationalNumber(this.numer / gcd, this.denom / gcd);
		}
	}
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		// 입력
		int N = Integer.parseInt(br.readLine());
		st = new StringTokenizer(br.readLine());
		int[] rings = new int[N];
		for (int i = 0; i < N; i++) {
			rings[i] = Integer.parseInt(st.nextToken());
		}
		
		// 각 링의 회전수를 기약분수 형태로 구한다
		RationalNumber r = new RationalNumber(1, 1);
		for (int i = 1; i < N; i++) {
			int prev = rings[i-1];
			int now = rings[i];
			r = r.multiply(new RationalNumber(prev, now)).reduce();
			sb.append(r.toString()).append("\n");
		}
		
		// 출력
		System.out.print(sb.toString());
	}
	
	/** 두 수의 최대공약수를 리턴 */
	public static int gcd (int a, int b) {
		if (a > b) {
			return gcd (b, a);
		}
		
		if (a == 0) return b;
		
		return gcd(b % a, a);
	}
}