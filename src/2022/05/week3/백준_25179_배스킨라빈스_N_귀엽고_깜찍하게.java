// 11516KB, 84ms

package bj25179;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		long N = Long.parseLong(st.nextToken());
		long M = Long.parseLong(st.nextToken());
		
		if (N % (M + 1L) != 1L) {
			System.out.println("Can win");
		}
		else {
			System.out.println("Can't win");
		}
		
	} // end main
}