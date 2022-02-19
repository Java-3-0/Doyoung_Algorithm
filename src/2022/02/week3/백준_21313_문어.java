// 11508KB, 76ms

package bj21313;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		
		int N = Integer.parseInt(br.readLine());
		
		int[] answer = new int[N];
		for (int i = 0; i < N - 1; i++) {
			answer[i] = i % 2 == 0 ? 1 : 2;
		}
		
		if (N % 2 == 0) {
			answer[N - 1] = 2;
		}
		else {
			answer[N - 1] = 3;
		}
		
		for (int num : answer) {
			sb.append(num).append(" ");
		}
		sb.append("\n");
		
		System.out.print(sb.toString());
	}
}
