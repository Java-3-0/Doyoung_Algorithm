// 11544KB, 76ms

package bj2004;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		
		// 입력
		st = new StringTokenizer(br.readLine(), " ");
		long N = Long.parseLong(st.nextToken());
		long M = Long.parseLong(st.nextToken());
		
		// 곱해지는 2의 개수, 5의 개수 계산
		long fives = factorialFives(N) - factorialFives(M) - factorialFives(N - M);
		long twos = factorialTwos(N) - factorialTwos(M) - factorialTwos(N - M);
		// 둘 중 작은 쪽이 0의 개수
		long answer = fives <= twos ? fives : twos;
		System.out.println(answer);
	}
	
	/** n!에 곱해지는 2의 개수를 리턴 */
	public static long factorialTwos (long n) {
		long ret = 0L;
		while (n > 0L) {
			ret += n / 2L;
			n /= 2L;
		}
		
		return ret;
	}
	
	/** n!에 곱해지는 5의 개수를 리턴 */
	public static long factorialFives (long n) {
		long ret = 0L;
		while (n > 0L) {
			ret += n / 5L;
			n /= 5L;
		}
		
		return ret;
	}

}