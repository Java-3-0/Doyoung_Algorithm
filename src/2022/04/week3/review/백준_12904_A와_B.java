// 15180KB, 96ms

package bj12904;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// S, T 입력
		String S = br.readLine();
		String T = br.readLine();

		// S로 T를 만드는 것이 가능한지 여부에 따라 정답 결정
		int answer = isPossible(S, T) ? 1 : 0;

		// 결과 출력
		System.out.println(answer);

	} // end main

	/** S로 T를 만드는 것이 가능한지 여부를 리턴한다 */
	public static boolean isPossible(String S, String T) {
		// base case: 길이가 같으면 문자열 비교
		int lenS = S.length();
		int lenT = T.length();
		if (lenS == lenT) {
			return S.equals(T);
		}

		// T의 마지막 글자를 구한다.
		int lastIdx = lenT - 1;
		char lastChar = T.charAt(lastIdx);
		
		// 끝이 A라면 마지막에 한 연산은 1번 연산이다.
		if (lastChar == 'A') {
			return isPossible(S, T.substring(0, lenT - 1));
		}
		
		// 끝이 B라면 마지막에 한 연산은 2번 연산이다.
		else {
			StringBuilder sb = new StringBuilder();
			sb.append(T.substring(0, lenT - 1));
			String nextT = sb.reverse().toString();
			return isPossible(S, nextT);
		}
	}
}