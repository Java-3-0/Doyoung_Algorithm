// 123296KB, 428ms

package bj16570;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		int N = Integer.parseInt(br.readLine());

		st = new StringTokenizer(br.readLine(), " ");

		// 수열을 입력받아서 역방향으로 저장한다.
		int[] seq = new int[N];
		for (int i = 0; i < N; i++) {
			seq[N - 1 - i] = Integer.parseInt(st.nextToken());
		}

		// 접두사 일치 테이블 계산
		int[] prefixTable = getPrefixTable(seq);

		// 접두사와 접미사가 일치하는 최대 길이 계산, 그 길이가 몇 번 존재하는지도 카운트
		int max = 0;
		int count = 0;
		for (int num : prefixTable) {
			if (max < num) {
				count = 1;
				max = num;
			} else if (max == num) {
				count++;
			}
		}

		// 결과 출력
		if (max == 0) {
			System.out.println(-1);
		} else {
			System.out.println(max + " " + count);
		}

	} // end main

	/** KMP 알고리즘을 이용하여 접두사 접미사 일치 테이블 생성해서 리턴 */
	public static int[] getPrefixTable(int[] pattern) {
		int len = pattern.length;
		int[] p = new int[len];
		p[0] = 0;

		for (int i = 1, j = 0; i < len; i++) {
			while (j > 0 && pattern[i] != pattern[j]) {
				j = p[j - 1];
			}

			if (pattern[i] == pattern[j]) {
				p[i] = ++j;
			} else {
				p[i] = 0;
			}
		}

		return p;
	}
}