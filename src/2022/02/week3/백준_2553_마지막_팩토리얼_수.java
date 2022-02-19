// 11528KB, 84ms

package bj2553;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	// 2만 * 2만을 한다고 치면 4억이므로 억 단위 자리까지만 남기고 그 위로는 없애도 된다.
	public static final long MOD = 1000000000;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		long N = Long.parseLong(br.readLine());

		long prev = 1L;
		
		// 하나씩 곱해보면서 마지막 9자리 중 0이 아닌 부분을 계속 prev에 갱신해서 담는다
		for (long num = 1L; num <= N; num++) {
			prev = (prev * num) % MOD;
			
			while (prev % 10L == 0L) {
				prev /= 10L;
			}
		}
		
		System.out.println(prev % 10L);
	}
}
