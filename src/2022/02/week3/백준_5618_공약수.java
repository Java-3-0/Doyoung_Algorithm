// 11804KB, 268ms

package bj5618;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());

		int[] arr = new int[N];
		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < N; i++) {
			arr[i] = Integer.parseInt(st.nextToken());
		}

		int commonGCD = arr[0];
		for (int i = 1; i < N; i++) {
			commonGCD = gcd(commonGCD, arr[i]);
		}
		
		List<Integer> divisors = getDivisors(commonGCD);
		for (int num : divisors) {
			System.out.println(num);
		}
	}

	/** a와 b의 최대공약수를 리턴 */
	public static int gcd(int a, int b) {
		if (a == 0) {
			return b;
		}

		return gcd(b % a, a);
	}

	/** n의 약수들을 리스트에 담아서 리턴 */
	public static List<Integer> getDivisors(int n) {
		List<Integer> ret = new ArrayList<>();
		if (n == 0) {
			return ret;
		}
		
		ret.add(1);
		
		if (n == 1) {
			return ret;
		}
		
		for (int num = 2; num <= n / 2; num++) {
			if (n % num == 0) {
				ret.add(num);
			}
		}
		ret.add(n);
		
		return ret;
	}
}