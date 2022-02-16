// 11484KB, 76ms

package bj1619;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.StringTokenizer;
 
public class Main {
	public static long M;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		long A = Long.parseLong(st.nextToken());
		long B = Long.parseLong(st.nextToken());
		M = Long.parseLong(st.nextToken());
		
		long answer = pow(A, B);
		System.out.println(answer);
	}
	
	/** A^B MOD M을 리턴 */
	public static long pow(long A, long B) {
		if (B == 0) return 1;
		
		long tmp = pow(A, B / 2);
		
		if (B % 2 == 0) {
			return tmp * tmp % M;
		}
		else {
			return ((tmp * tmp) % M) * A % M;
		}
	}
}