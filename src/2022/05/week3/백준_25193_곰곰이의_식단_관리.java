// 14108KB, 92ms

package bj25193;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());

		char[] text = br.readLine().toCharArray();
		int len = text.length;

		int cntOthers = 0;
		for (int i = 0; i < len; i++) {
			if (text[i] != 'C') {
				cntOthers++;
			}
		}

		int cntChickens = len - cntOthers;

		int answer = cntChickens / (cntOthers + 1);
		if (cntChickens % (cntOthers + 1) != 0) {
			answer++;
		}

		System.out.println(answer);

	} // end main
}