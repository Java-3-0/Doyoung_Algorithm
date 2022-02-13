// 11520KB, 80ms

package bj1003;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());

		// 다 곱해서 2^n * 5^m * k꼴 일 때, min(n, m)이 답
		// m <= n이므로 5가 몇 번 곱해지는지만 세면 된다
		System.out.println(N / 125 + N / 25 + N / 5);
	}
}
