// 21668KB, 172ms

package bj16900;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st;

		// 입력
		st = new StringTokenizer(br.readLine(), " ");
		String S = st.nextToken();
		long K = Long.parseLong(st.nextToken());
		
		// 입력받은 문자열을 char[] 로 변환
		char[] pattern = S.toCharArray();
		long len = (long) pattern.length;
		
		// KMP 알고리즘을 통해 접두사 접미사 일치 테이블 계산
		int[] prefixTable = getPrefixTable(pattern);
		
		// 접두사 접미사가 일치하는 길이를 구한다.
		long overlap = (long) prefixTable[prefixTable.length - 1];
		
		// 매 번 overlap만큼은 겹칠 수 있다.
		// 첫 문자열 길이 len
		// 겹치는 부분이 overlap니까, 겹치지 않는 부분은 매 문자열마다 len - overlap
		// 따라서 답은 len + (K - 1) * (len - overlap)
		long answer = len + (K - 1L) * (len - overlap);
		
		// 정답 출력
		System.out.println(answer);
		
	} // end main

	/** KMP 알고리즘을 이용하여 접두사 접미사 일치 테이블 생성 */
	public static int[] getPrefixTable(char[] pattern) {
		int len = pattern.length;
		int p[] = new int[len];
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