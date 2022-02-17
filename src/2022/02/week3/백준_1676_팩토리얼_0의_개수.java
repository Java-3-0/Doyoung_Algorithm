// 11528KB, 76ms

package bj1003;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		long N = Long.parseLong(br.readLine());
		
		System.out.println(factorialFives(N));
	}
	
	/** n!에 곱해지는 5의 개수를 리턴 */
	public static long factorialFives (long n) {
		long ret = 0;
		while (n > 0) {
			ret += n / 5;
			n /= 5;
		}
		
		return ret;
	}

}