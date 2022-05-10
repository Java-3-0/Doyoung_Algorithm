package bj2247;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static final long MOD = (long) 1e6;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// N 입력
		long N = Long.parseLong(br.readLine());
		long halfN = N / 2L;

		// num을 제외한 num의 배수들의 개수만큼 num이 더해진다
		long sum = 0L;
		for (long num = 2L; num <= halfN; num++) {
			sum += num * ((N  / num) - 1L);
			sum %= MOD;
		}

		// 출력
		System.out.println(sum);

	} // end main
}