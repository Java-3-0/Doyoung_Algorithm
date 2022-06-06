// 11536KB, 80ms

package bj25238;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		double a = Double.parseDouble(st.nextToken());
		double b = Double.parseDouble(st.nextToken());

		double result = a * ((100.0 - b) / 100.0);
		
		if (result >= 100.0) {
			System.out.println(0);
		}
		else {
			System.out.println(1);
		}

	} // end main

}