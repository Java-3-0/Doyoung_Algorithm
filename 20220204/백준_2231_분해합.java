// 분해합 문제
package baek2231;

import java.util.Scanner;

public class Main {
	public final static int MAX_N = 1000000;

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int N = sc.nextInt();
		int answer = minConstructor(N);
		System.out.println(answer);
		sc.close();
	}

	/** 가장 작은 생성자를 리턴한다. 없으면 0을 리턴. O((log N)^2) */
	public static int minConstructor(int n) {
		// n의 생성자는 (n - (9 * n의 길이))보다 작을 수는 없고, n보다 클 수도 없으므로 그 사이 구간만 완전 탐색
		for (int i = n - 9 * numberLength(n); i <= n; i++) {
			if (i + digitSum(i) == n) {
				return i;
			}
		}

		return 0;
	}

	/** 수의 길이(digit의 개수)를 리턴 */
	public static int numberLength(int n) {
		return Integer.toString(n).length();
	}

	/** 수의 각 자리수의 합을 구한다. O(log n) */
	public static int digitSum(int n) {
		// base case: 한 자리 수
		if (n < 10) {
			return n;
		}

		// (마지막 자리수) + (나머지 수로 재귀 호출해서 얻은 값)
		return (n % 10) + digitSum(n / 10);
	}
}
