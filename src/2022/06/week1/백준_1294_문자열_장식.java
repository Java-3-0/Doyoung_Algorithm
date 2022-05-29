// 90412KB, 268ms

package bj1294;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.TreeSet;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();

		// N 입력
		int N = Integer.parseInt(br.readLine());

		// 단어들 입력
		TreeSet<String> words = new TreeSet<>(comp);
		for (int i = 0; i < N; i++) {
			words.add(br.readLine());
		}
		
		// 사전순으로 가장 앞서는 단어의 첫 글자를 쓰는 것을 반복
		while (!words.isEmpty()) {
			String word = words.pollFirst();
			sb.append(word.charAt(0));
			if (word.length() >= 2) {
				words.add(word.substring(1));
			}
		}
		sb.append("\n");
		
		// 정답 출력
		System.out.print(sb.toString());
	
	} // end main

	/** 문자열을 사전순으로 정렬하되 "CA"와 "C"의 대소비교 같은 상황에서 "CA"가 앞에 오도록 하기 위한 컴퍼레이터 */
	public static Comparator<String> comp = new Comparator<String>() {
		@Override
		public int compare(String s1, String s2) {
			int len1 = s1.length();
			int len2 = s2.length();
			
			int minLen = Math.min(len1, len2);
			
			if (s1.substring(0, minLen).equals(s2.substring(0, minLen))) {
				if (len1 <= len2) {
					return 1;
				}
				else {
					return -1;
				}
			}
			
			else {
				return s1.compareTo(s2);
			}
		}
	};
	
}