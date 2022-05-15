// 11484KB, 76ms

package bj20310;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// 입력
		char[] text = br.readLine().toCharArray();

		// 각 문자 카운팅
		int[] counts = new int[2];
		for (char c : text) {
			int num = c - '0';
			counts[num]++;
		}

		// 필요한 문자 개수 계산
		int targetZeros = counts[0] / 2;
		int targetOnes = counts[1] / 2;

		// 앞에서부터 개수만큼 0은 넣고, 1은 없앤다
		int appendedZeros = 0;
		int deletedOnes = 0;
		for (char c : text) {
			if (c == '1') {
				if (deletedOnes < targetOnes) {
					deletedOnes++;
				}
				else {
					sb.append(1);
				}
			} else {
				if (appendedZeros >= targetZeros) {
					continue;
				} else {
					sb.append(0);
					appendedZeros++;
				}
			}
		}
		sb.append("\n");

		System.out.print(sb.toString());
	} // end main
}