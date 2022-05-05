// 11480KB, 76ms

package bj1748;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String line = br.readLine();
		long len = (long) line.length();
		long N = Long.parseLong(line);
		
		long answer = 0L;
		
		long totalCnt = 0L;
		for (long i = 1L; i < len; i++) {
			long cnt = 9L * (long) Math.pow(10, i - 1);
			totalCnt += cnt ;
			answer += cnt * i;
		}
		
		answer += len * (N - totalCnt);
		
		System.out.println(answer);
		
	} // end main

}