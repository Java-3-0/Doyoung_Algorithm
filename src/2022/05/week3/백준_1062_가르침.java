// 12664KB, 308ms

package bj1062;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	static int MAX_N = 50, MAX_K = 26;
	static int ALPHABETS = 26;

	static int N, K;
	static String[] words;
	static int learned = 0;
	static int maxReading = 0;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		words = new String[N];
		for (int i = 0; i < N; i++) {
			words[i] = br.readLine();
		}

		// 5글자 배우고 시작
		if (K >= 5) {
			K -= 5;
			learned |= (1 << charToIdx('a'));
			learned |= (1 << charToIdx('n'));
			learned |= (1 << charToIdx('t'));
			learned |= (1 << charToIdx('i'));
			learned |= (1 << charToIdx('c'));
			combination(0, 0);
		}
		
		System.out.println(maxReading);

	} // end main

	public static void combination(int startIdx, int cnt) {
		if (cnt == K) {
			int reading = 0;
			for (String word : words) {
				if (canRead(word)) {
					reading++;
				}
			}
			maxReading = Math.max(maxReading, reading);

			return;
		}

		for (int pick = startIdx; pick < ALPHABETS; pick++) {
			if ((learned & (1 << pick)) != 0) {
				continue;
			}

			learned |= (1 << pick);
			combination(pick + 1, cnt + 1);
			learned &= ~(1 << pick);
		}
	}

	public static boolean canRead(String word) {
		int len = word.length();
		for (int i = 0; i < len; i++) {
			char c = word.charAt(i);
			if ((learned & (1 << charToIdx(c))) == 0) {
				return false;
			}
		}

		return true;
	}

	public static int charToIdx(char c) {
		return c - 'a';
	}

}