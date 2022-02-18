// 11436KB, 72ms

package bj5347;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int T = Integer.parseInt(br.readLine());
		
		for (int test = 1; test <= T; test++) {
			st = new StringTokenizer(br.readLine(), " ");
			long A = Long.parseLong(st.nextToken());
			long B = Long.parseLong(st.nextToken());
			
			sb.append(lcm(A, B)).append("\n");
		}
		
		System.out.print(sb.toString());
		
	}


	/** a와 b의 최대공약수를 리턴 */
	public static long gcd(long a, long b) {
		if (a == 0) {
			return b;
		}

		return gcd(b % a, a);
	}
	
	/** a와 b의 최소공배수를 리턴 */
	public static long lcm(long a, long b) {
		return a * b / gcd(a, b);
	}
}