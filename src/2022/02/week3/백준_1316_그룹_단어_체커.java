// 11584KB, 76ms

package bj1316;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
	public static final int ALPHABETS = 26;
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());
		
		// 그룹 단어의 수를 센다
		int count = 0;
		for (int i = 0; i < N; i++) {
			String s = br.readLine();
			if (isGroupWord(s)) {
				count++;
			}
		}
		
		// 출력
		System.out.println(count);
	}
	
	public static boolean isGroupWord(String s) {
		boolean[] isUsed = new boolean[ALPHABETS];
		
		int length = s.length();
		isUsed[s.charAt(0) - 'a'] = true;
		char prev = s.charAt(0);
		for (int i = 1; i < length; i++) {
			char now = s.charAt(i);
			// 이전 것과 같으면 같은 그룹
			if (now == prev) {
				continue;
			}
			
			// 이전에 존재했던 그룹이면, false
			if (isUsed[now - 'a']) {
				return false;
			}
			
			// 새로 만난 그룹이면, 여기부터 새 그룹 시작
			isUsed[now - 'a'] = true;
			prev = s.charAt(i);
		}
		
		return true;
	}
}
