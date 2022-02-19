// 15692KB, 192ms

package bj21275;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Main {
	public static final int MAX_BASE = 36;
	public static final int MIN_BASE = 2;
	
	public static final BigInteger ZERO = new BigInteger("0");
	public static final BigInteger INVALID = new BigInteger("-1");
	public static final BigInteger MAX_NUMBER = new BigInteger(String.valueOf(Long.MAX_VALUE));
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		String s1 = st.nextToken();
		String s2 = st.nextToken();
		
		BigInteger[] numbers1 = new BigInteger[MAX_BASE + 1];
		BigInteger[] numbers2 = new BigInteger[MAX_BASE + 1];
		
		for (int base = MIN_BASE; base <= MAX_BASE; base++) {
			numbers1[base] = convertToBase10(s1, base);
			numbers2[base] = convertToBase10(s2, base);
		}
		
		int count = 0;
		String answer = "";
		for (int A = MIN_BASE; A <= MAX_BASE; A++) {
			for (int B = MIN_BASE; B <= MAX_BASE; B++) {
				BigInteger X1 = numbers1[A];
				BigInteger X2 = numbers2[B];
				if (A != B && X1.equals(X2) && !X1.equals(INVALID) && X1.compareTo(MAX_NUMBER) <= 0) {
					answer = String.format("%d %d %d", numbers1[A], A, B);
					count++;
				}
			}
		}
		
		if (count == 0) {
			System.out.println("Impossible");
		}
		else if (count >= 2) {
			System.out.println("Multiple");
		} else {
			System.out.println(answer);
		}
	}
	
	/** 문자를 정수로 변환해서 리턴 */
	public static int charToInt(char c) {
		if (Character.isDigit(c)) {
			return c - '0';
		}
		
		return (c - 'a') + 10;
	}
	
	/** base 진법에서 s라는 스트링으로 나타내는 수를 10 진법으로 변환해서 리턴 */
	public static BigInteger convertToBase10 (String s, int base) {
		int len = s.length();
		int lastIdx = len - 1;
		
		BigInteger ret = ZERO;
		for (int i = 0; i < len; i++) {
			int num = charToInt(s.charAt(lastIdx - i));
			if (num >= base) {
				return INVALID;
			}
			
			BigInteger numBig = new BigInteger(String.valueOf(num));
			BigInteger baseBig = new BigInteger(String.valueOf(base));

			ret = ret.add(numBig.multiply(baseBig.pow(i)));
		}
		
		return ret;
	}
}