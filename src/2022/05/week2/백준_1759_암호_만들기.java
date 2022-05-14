// 11580KB, 80ms

package bj1759;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
	static int L, C;
	static char[] alphabetsToUse;
	static int[] combi;

	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		st = new StringTokenizer(br.readLine(), " ");
		L = Integer.parseInt(st.nextToken());
		C = Integer.parseInt(st.nextToken());

		combi = new int[L];
		alphabetsToUse = new char[C];

		st = new StringTokenizer(br.readLine(), " ");
		for (int i = 0; i < C; i++) {
			alphabetsToUse[i] = st.nextToken().charAt(0);
		}

		Arrays.sort(alphabetsToUse);

		combination(0, 0, 0);

		System.out.print(sb.toString());
		
	} // end main

	public static void combination(int startIdx, int cntVowels, int cntConsonants) {
		int cnt = cntVowels + cntConsonants;
		
		if (cnt == L) {
			if (cntVowels >= 1 && cntConsonants >= 2) {
				for (int idx : combi) {
					sb.append(alphabetsToUse[idx]);
				}
				sb.append("\n");
			}
			return;
		}

		if (startIdx == C) {
			return;
		}

		for (int pick = startIdx; pick < C; pick++) {
			combi[cnt] = pick;
			if (isVowel(alphabetsToUse[pick])) {
				combination(pick + 1, cntVowels + 1, cntConsonants);
			} else {
				combination(pick + 1, cntVowels, cntConsonants + 1);
			}
		}

	}

	public static boolean isVowel(char c) {
		if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
			return true;
		}
		return false;
	}
}