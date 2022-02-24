// 153232KB, 400ms

package bj4354;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;
		StringBuilder sb = new StringBuilder();

		String line = "";

		while (!(line = br.readLine()).equals(".")) {
			// KMP 알고리즘으로 접두사 = 접미사가 되는 부분의 길이를 나타내는 테이블을 구한다.
			int[] prefixTable = getPrefixTable(line);

			// 반복되는 부분의 최소 길이를 구한다.
			int repeatLen = getMinRepeatingLen(prefixTable);

			// 이 길이가 몇 번 반복되었는지가 정답이다.
			int answer = line.length() / repeatLen;

			// 출력
			sb.append(answer).append("\n");
		}
		
		System.out.print(sb.toString());

	} // end main

	public static int getMinRepeatingLen(int[] prefixTable) {
		int len = prefixTable.length;
		int lastIdx = len - 1;

		if (prefixTable[lastIdx] == 0) {
			return len;
		}

		// 뒤에서부터 쭉 감소하다가 1이 나오는 위치의 인덱스를 구한다.
		for (int i = lastIdx - 1; i >= 0; i--) {
			if (prefixTable[i] != prefixTable[i + 1] - 1) {
				return len;
			} else {
				if (prefixTable[i] == 1) {
					// 이 위치 이전까지가 반복되는 부분의 길이가 될 수 있다.
					if (len % i == 0) {
						return i;
					}
					else {
						return len;
					}
				}
			}
		}

		return len;
	}

	public static int[] getPrefixTable(String pattern) {
		char[] arrP = pattern.toCharArray();
		int lenP = arrP.length;
		int[] p = new int[lenP];
		p[0] = 0;

		for (int i = 1, j = 0; i < lenP; i++) {
			while (j > 0 && arrP[i] != arrP[j]) {
				j = p[j - 1];
			}

			if (arrP[i] == arrP[j]) {
				p[i] = ++j;
			} else {
				p[i] = 0;
			}
		}

		return p;
	}

}