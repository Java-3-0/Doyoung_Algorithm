// 12624KB, 84ms

package bj12919;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class Main {
	public static Set<String> triedStrings = new HashSet<>();
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		String S = br.readLine();
		String T = br.readLine();

		int answer = isPossible(S, T) ? 1 : 0;

		System.out.println(answer);

	} // end main

	public static boolean isPossible(String S, String T) {
		// base case 1: 이미 해 본 문자열인 경우 포기
		if (triedStrings.contains(S)) {
			return false;
		}
		triedStrings.add(S);
		
		// base case 2: 길이가 같으면 문자열 비교
		int lenS = S.length();
		int lenT = T.length();
		if (lenS == lenT) {
			return S.equals(T);
		}
		
		// S를 역순으로 바꾼 문자열 생성
		StringBuilder sb = new StringBuilder();
		sb.append(S);
		String revS = sb.reverse().toString();
		
		// T에 S도 없고 역순S도 없으면 뭘 얼마나 더 붙여도 불가능하니까 여기서 재귀호출을 그만둔다.
		if (!T.contains(S) && !T.contains(revS)) {
			return false;
		}
		
		// 이외의 경우 재귀호출을 해서 계속 시도해 본다.
		return isPossible(S + 'A', T) || isPossible('B' + revS, T);

	}
}