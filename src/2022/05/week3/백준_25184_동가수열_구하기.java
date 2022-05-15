// 12108KB, 88ms

package bj25184;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		int N = Integer.parseInt(br.readLine());

		int half = N / 2;

		List<Integer> result = new ArrayList<>();
		for (int i = 1; i <= half; i++) {
			result.add(i + half);
			result.add(i);
		}

		if (N % 2 != 0) {
			result.add(N);
		}

		for (int num : result) {
			sb.append(num).append(" ");
		}
		sb.setLength(sb.length() - 1);
		
		sb.append("\n");

		System.out.print(sb.toString());

	} // end main
}