// 13432KB, 96ms

package bj1251;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeSet;

public class Main {
	
	
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		// 원본 단어 입력
		char[] original = br.readLine().toCharArray();
		int len = original.length;
		
		// 3개로 나눠서 합치는 방법을 완전탐색하면서 TreeSet에 넣는다.
		TreeSet<String> words = new TreeSet<>();
		for (int cut1 = 1; cut1 < len; cut1++) {
			for (int cut2 = cut1 + 1; cut2 < len; cut2++) {
				StringBuilder sb = new StringBuilder();
				for (int i = cut1 - 1; i >= 0; i--) {
					sb.append(original[i]);
				}
				for (int i = cut2 - 1; i >= cut1; i--) {
					sb.append(original[i]);
				}
				for (int i = len - 1; i >= cut2; i--) {
					sb.append(original[i]);
				}
				
				words.add(sb.toString());
			}
		}
		
		// 사전 순으로 가장 앞서는 것 출력
		String answer = words.first();
		System.out.println(answer);

	} // end main

}