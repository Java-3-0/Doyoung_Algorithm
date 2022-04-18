// 11528KB, 80ms

package bj9659;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	static final long MAX_N = (long) 1e12;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		long N = Long.parseLong(br.readLine());

		long remainder = N % 4L;
		if (remainder == 1L || remainder == 3L) {
			System.out.println("SK");
		}
		else {
			System.out.println("CY");
		}

	} // end main
}