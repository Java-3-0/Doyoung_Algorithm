// 11564KB, 76ms

package bj2745;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine(), " ");

		String s = st.nextToken();
		int B = Integer.parseInt(st.nextToken());
		
		int len = s.length();
		
		int answer = 0;
		for (int i = 0; i < len; i++) {
			// 뒷자리에서부터 한 문자씩 처리
			char c = s.charAt(len - 1 - i);
			
			answer += charToInt(c) * Math.pow(B, i);
		}
		
		System.out.println(answer);
	}
	
	/** 각 문자가 나타내는 수를 리턴 */
	public static int charToInt (char c) {
		// 숫자인 경우
		if (Character.isDigit(c)) {
			return c - '0';
		}
		
		// 알파벳 문자인 경우
		return c - 'A' + 10;
	}

}