// 11476KB, 84ms

package bj12871;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String s = br.readLine();
		String t = br.readLine();

		if (isSameInf(s, t)) {
			System.out.println(1);
		} else {
			System.out.println(0);
		}

	} // end main

	public static boolean isSameInf(String s, String t) {
		int lenS = s.length();
		int lenT = t.length();

		int lenLCM = lcm(lenS, lenT);

		StringBuilder sbS = new StringBuilder();
		for (int i = 0; i < lenLCM / lenS; i++) {
			sbS.append(s);
		}

		StringBuilder sbT = new StringBuilder();
		for (int i = 0; i < lenLCM / lenT; i++) {
			sbT.append(t);
		}

		return sbS.toString().equals(sbT.toString());
	}

	public static int gcd(int a, int b) {
		if (a == 0) {
			return b;
		}

		return gcd(b % a, a);
	}

	public static int lcm(int a, int b) {
		return a * b / gcd(a, b);
	}

}